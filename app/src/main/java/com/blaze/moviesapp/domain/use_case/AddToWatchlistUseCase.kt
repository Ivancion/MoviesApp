package com.blaze.moviesapp.domain.use_case

import com.blaze.moviesapp.domain.models.AddToWatchlistResponse
import com.blaze.moviesapp.domain.repositories.Repository
import com.blaze.moviesapp.other.Constants.UNKNOWN_ERROR
import com.blaze.moviesapp.other.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AddToWatchlistUseCase @Inject constructor(
    private val repository: Repository
) {

    operator fun invoke(add: Boolean, movieId: Int) : Flow<Resource<AddToWatchlistResponse>> {
        return flow {
            emit(Resource.LoadingState)
            runCatching {
                repository.getAccountDetails()
            }.onSuccess { accountDetails ->
                val accountId = accountDetails.id ?: 0
                runCatching {
                    repository.addToWatchlist(accountId, add, movieId)
                }.onSuccess {
                    emit(Resource.Success(it))
                }.onFailure {
                    emit(Resource.Error(it.localizedMessage ?: it.message ?: UNKNOWN_ERROR))
                }
            }.onFailure {
                emit(Resource.Error(it.localizedMessage ?: it.message ?: UNKNOWN_ERROR))
            }
        }
    }
}