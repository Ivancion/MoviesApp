package com.blaze.moviesapp.domain.use_case

import androidx.paging.PagingData
import com.blaze.moviesapp.domain.models.Movie
import com.blaze.moviesapp.domain.repositories.MoviesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetSearchMoviesPagingFlowUseCase @Inject constructor(
    private val movieRepository: MoviesRepository
) {

    operator fun invoke(
        query: String,
        emptyResponse: (Boolean) -> Unit
    ): Flow<PagingData<Movie>> {
        return movieRepository.getSearchMoviesPagingFlow(query, emptyResponse)
    }
}