package com.blaze.moviesapp.domain.use_case

import androidx.paging.PagingData
import com.blaze.moviesapp.domain.models.Movie
import com.blaze.moviesapp.domain.repositories.AuthRepository
import com.blaze.moviesapp.domain.repositories.MoviesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetWatchlistPagingFlowUseCase @Inject constructor(
    private val authRepository: AuthRepository,
    private val moviesRepository: MoviesRepository
) {

    operator fun invoke(emptyResponse: (Boolean) -> Unit): Flow<PagingData<Movie>> {
        val sessionId = authRepository.getSessionId()
        return moviesRepository.getWatchlistPagingFlow(sessionId, emptyResponse)
    }
}