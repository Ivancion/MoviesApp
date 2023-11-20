package com.blaze.moviesapp.domain.use_case

import com.blaze.moviesapp.domain.models.MoviesResponse
import com.blaze.moviesapp.domain.repositories.LoginRepository
import com.blaze.moviesapp.domain.repositories.MoviesRepository
import javax.inject.Inject

class GetMovieWatchlistUseCase @Inject constructor(
    private val moviesRepository: MoviesRepository,
    private val loginRepository: LoginRepository
) {

    suspend operator fun invoke(page: Int): MoviesResponse? {
        val sessionId = loginRepository.getSessionId()
        return try {
            val accountDetails = moviesRepository.getAccountDetails(sessionId)
            val accountId = accountDetails.id ?: 0
            moviesRepository.getMovieWatchlist(accountId, page, sessionId)
        } catch (e: Exception) {
            null
        }
    }
}