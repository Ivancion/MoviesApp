package com.blaze.moviesapp.domain.use_case

import com.blaze.moviesapp.domain.models.MoviesResponse
import com.blaze.moviesapp.domain.repositories.Repository
import retrofit2.Response

class SearchMoviesUseCase(
    private val repository: Repository
) {

    suspend operator fun invoke(
        query: String,
        page: Int
    ): MoviesResponse  {
        return repository.searchMovies(query, page)
    }
}