package com.blaze.moviesapp.domain.use_case

import com.blaze.moviesapp.domain.models.MoviesResponse
import com.blaze.moviesapp.domain.repositories.MoviesRepository
import com.blaze.moviesapp.other.MovieCategory
import javax.inject.Inject

class GetMoviesByCategoryUseCase @Inject constructor(
    private val moviesRepository: MoviesRepository
) {

    suspend operator fun invoke(category: MovieCategory, page: Int): MoviesResponse {
        return when (category) {
            is MovieCategory.NowPlaying -> moviesRepository.getNowPlayingMovies(page)
            is MovieCategory.Upcoming -> moviesRepository.getUpcomingMovies(page)
            is MovieCategory.TopRated -> moviesRepository.getTopRatedMovies(page)
            is MovieCategory.Popular -> moviesRepository.getPopularMovies(page)
        }
    }
}