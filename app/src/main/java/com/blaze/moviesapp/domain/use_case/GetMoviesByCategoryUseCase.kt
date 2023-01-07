package com.blaze.moviesapp.domain.use_case

import com.blaze.moviesapp.domain.models.MoviesResponse
import com.blaze.moviesapp.domain.repositories.Repository
import com.blaze.moviesapp.other.MovieCategory
import retrofit2.Response

class GetMoviesByCategoryUseCase(
    private val repository: Repository
) {

    suspend operator fun invoke(category: MovieCategory, page: Int): MoviesResponse {
        return when (category) {
            is MovieCategory.NowPlaying -> repository.getNowPlayingMovies(page)
            is MovieCategory.Upcoming -> repository.getUpcomingMovies(page)
            is MovieCategory.TopRated -> repository.getTopRatedMovies(page)
            is MovieCategory.Popular -> repository.getPopularMovies(page)
        }
    }
}