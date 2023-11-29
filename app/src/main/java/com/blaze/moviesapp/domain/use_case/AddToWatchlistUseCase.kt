package com.blaze.moviesapp.domain.use_case

import com.blaze.moviesapp.domain.models.AddToWatchlistResponse
import com.blaze.moviesapp.domain.repositories.AuthRepository
import com.blaze.moviesapp.domain.repositories.MoviesRepository
import com.blaze.moviesapp.other.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AddToWatchlistUseCase @Inject constructor(
    private val moviesRepository: MoviesRepository,
    private val authRepository: AuthRepository
) {

    operator fun invoke(add: Boolean, movieId: Int) : Flow<Resource<AddToWatchlistResponse>> {
        val sessionId = authRepository.getSessionId()
        return moviesRepository.addToWatchlist(add, movieId, sessionId)
    }
}