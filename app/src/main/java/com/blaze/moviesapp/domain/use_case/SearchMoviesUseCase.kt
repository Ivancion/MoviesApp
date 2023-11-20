package com.blaze.moviesapp.domain.use_case

import com.blaze.moviesapp.domain.models.MoviesResponse
import com.blaze.moviesapp.domain.repositories.MoviesRepository
import javax.inject.Inject

class SearchMoviesUseCase @Inject constructor(
    private val moviesRepository: MoviesRepository
) {

    suspend operator fun invoke(
        query: String,
        page: Int
    ): MoviesResponse  {
        return moviesRepository.searchMovies(query, page)
    }
}