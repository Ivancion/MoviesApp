package com.blaze.moviesapp.domain.use_case

import com.blaze.moviesapp.domain.models.MoviesResponse
import com.blaze.moviesapp.domain.repositories.Repository
import retrofit2.Response

class GetMovieWatchlistUseCase(
    private val repository: Repository
) {

    suspend operator fun invoke(page: Int): MoviesResponse? {
        return try {
            val accountDetails = repository.getAccountDetails()
            val accountId = accountDetails.id ?: 0
            repository.getMovieWatchlist(accountId, page)
        } catch (e: Exception) {
            null
        }
    }
}