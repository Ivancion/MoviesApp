package com.blaze.moviesapp.domain.use_case

import androidx.paging.PagingData
import com.blaze.moviesapp.domain.models.Movie
import com.blaze.moviesapp.domain.repositories.MoviesRepository
import com.blaze.moviesapp.other.MovieCategory
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCategoryMoviesPagingFlowUseCase @Inject constructor(
    private val repository: MoviesRepository
) {

    operator fun invoke(
        category: MovieCategory
    ): Flow<PagingData<Movie>> {
        return repository.getCategoryMoviesPagingFlow(category)
    }
}