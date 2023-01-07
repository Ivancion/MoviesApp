package com.blaze.moviesapp.domain.use_case

import com.blaze.moviesapp.domain.models.GenresResponse
import com.blaze.moviesapp.domain.repositories.Repository
import retrofit2.Response

class GetGenresUseCase(
    private val repository: Repository
) {

    suspend operator fun invoke() : GenresResponse {
        return repository.getGenres()
    }
}