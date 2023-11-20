package com.blaze.moviesapp.domain.use_case

import com.blaze.moviesapp.domain.models.GenresResponse
import com.blaze.moviesapp.domain.repositories.MoviesRepository
import javax.inject.Inject

class GetGenresUseCase @Inject constructor(
    private val moviesRepository: MoviesRepository
) {

    suspend operator fun invoke() : GenresResponse {
        return moviesRepository.getGenres()
    }
}