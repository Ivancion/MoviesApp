package com.blaze.moviesapp.domain.use_case

import com.blaze.moviesapp.domain.models.MovieState
import com.blaze.moviesapp.domain.repositories.Repository
import com.blaze.moviesapp.other.Constants.UNKNOWN_ERROR
import com.blaze.moviesapp.other.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetMovieStatesUseCase(
    private val repository: Repository
) {

    operator fun invoke(movieId: Int) : Flow<Resource<MovieState>> {
        return flow {
            emit(Resource.LoadingState)
            runCatching {
                repository.getMovieStates(movieId)
            }.onSuccess {
                emit(Resource.Success(it))
            }.onFailure {
                emit(Resource.Error(it.localizedMessage ?: it.message ?: UNKNOWN_ERROR))
            }
        }
    }
}