package com.blaze.moviesapp.domain.use_case

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.blaze.moviesapp.domain.models.Movie
import com.blaze.moviesapp.other.MovieCategory
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPagerHomeScreenUseCase @Inject constructor(
    private val getMoviesByCategoryUseCase: GetMoviesByCategoryUseCase
) {

    operator fun invoke(
        category: MovieCategory
    ): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20
            ),
            pagingSourceFactory = {
                CategoryMoviesPagingSource(
                    getMoviesByCategoryUseCase,
                    category
                )
            }
        ).flow
    }
}