package com.blaze.moviesapp.domain.use_case

import com.blaze.moviesapp.domain.models.DeleteSessionResponse
import com.blaze.moviesapp.domain.repositories.Repository
import com.blaze.moviesapp.other.Constants.UNKNOWN_ERROR
import com.blaze.moviesapp.other.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class DeleteSessionUseCase @Inject constructor(
    private val repository: Repository
) {

    operator fun invoke(): Flow<Resource<DeleteSessionResponse>> {
        return flow {
            emit(Resource.LoadingState)
            runCatching {
                repository.deleteSession()
            }.onSuccess {
                emit(Resource.Success(it))
            }.onFailure {
                emit(Resource.Error(it.localizedMessage ?: it.message ?: UNKNOWN_ERROR))
            }
        }
    }
}