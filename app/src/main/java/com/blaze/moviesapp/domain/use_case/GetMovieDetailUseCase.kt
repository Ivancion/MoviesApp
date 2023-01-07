package com.blaze.moviesapp.domain.use_case

import com.blaze.moviesapp.domain.models.MovieDetail
import com.blaze.moviesapp.domain.repositories.Repository
import com.blaze.moviesapp.other.Constants.UNKNOWN_ERROR
import com.blaze.moviesapp.other.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetMovieDetailUseCase(
    private val repository: Repository
) {

    operator fun invoke(id: Int): Flow<Resource<MovieDetail>> {
        return flow {
            emit(Resource.LoadingState)
            runCatching {
                repository.getMovieDetail(id)
            }.onSuccess {
                emit(Resource.Success(it))
            }.onFailure {
                emit(Resource.Error(it.localizedMessage ?: it.message ?: UNKNOWN_ERROR))
            }
        }
    }
}