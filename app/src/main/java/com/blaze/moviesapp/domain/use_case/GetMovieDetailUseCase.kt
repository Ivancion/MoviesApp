package com.blaze.moviesapp.domain.use_case

import com.blaze.moviesapp.domain.models.MovieDetail
import com.blaze.moviesapp.domain.repositories.MoviesRepository
import com.blaze.moviesapp.other.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMovieDetailUseCase @Inject constructor(
    private val moviesRepository: MoviesRepository
) {

    operator fun invoke(id: Int): Flow<Resource<MovieDetail>> {
        return moviesRepository.getMovieDetail(id)
    }
}