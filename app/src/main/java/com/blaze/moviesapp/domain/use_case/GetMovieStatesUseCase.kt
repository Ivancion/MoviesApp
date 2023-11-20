package com.blaze.moviesapp.domain.use_case

import com.blaze.moviesapp.domain.models.MovieState
import com.blaze.moviesapp.domain.repositories.LoginRepository
import com.blaze.moviesapp.domain.repositories.MoviesRepository
import com.blaze.moviesapp.other.Constants.UNKNOWN_ERROR
import com.blaze.moviesapp.other.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetMovieStatesUseCase @Inject constructor(
    private val moviesRepository: MoviesRepository,
    private val loginRepository: LoginRepository
) {

    operator fun invoke(movieId: Int) : Flow<Resource<MovieState>> {
        val sessionId = loginRepository.getSessionId()
        return flow {
            emit(Resource.LoadingState)
            runCatching {
                moviesRepository.getMovieStates(movieId, sessionId)
            }.onSuccess {
                emit(Resource.Success(it))
            }.onFailure {
                emit(Resource.Error(it.localizedMessage ?: it.message ?: UNKNOWN_ERROR))
            }
        }
    }
}