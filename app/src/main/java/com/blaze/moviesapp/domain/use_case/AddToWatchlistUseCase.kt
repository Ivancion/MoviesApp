package com.blaze.moviesapp.domain.use_case

import com.blaze.moviesapp.domain.models.AddToWatchlistResponse
import com.blaze.moviesapp.domain.repositories.AuthRepository
import com.blaze.moviesapp.domain.repositories.MoviesRepository
import com.blaze.moviesapp.other.Constants.UNKNOWN_ERROR
import com.blaze.moviesapp.other.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AddToWatchlistUseCase @Inject constructor(
    private val moviesRepository: MoviesRepository,
    private val authRepository: AuthRepository
) {

    operator fun invoke(add: Boolean, movieId: Int) : Flow<Resource<AddToWatchlistResponse>> {
        val sessionId = authRepository.getSessionId()
        return flow {
            emit(Resource.LoadingState)
            runCatching {
                moviesRepository.getAccountDetails(sessionId)
            }.onSuccess { accountDetails ->
                val accountId = accountDetails.id ?: 0
                runCatching {
                    moviesRepository.addToWatchlist(accountId, add, movieId, sessionId)
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