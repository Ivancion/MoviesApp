package com.blaze.moviesapp.domain.use_case

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.blaze.moviesapp.domain.models.Movie
import kotlinx.coroutines.flow.Flow

class GetPagerSearchScreenUseCase(
    private val searchMoviesUseCase: SearchMoviesUseCase,
    private val getGenresUseCase: GetGenresUseCase,
) {

    operator fun invoke(
        query: String,
        emptyResponse: (Boolean) -> Unit
    ): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                initialLoadSize = 20
            ),
            pagingSourceFactory = {
                SearchMoviesPagingSource(
                    searchMoviesUseCase,
                    getGenresUseCase,
                    query,
                    emptyResponse
                )
            }
        ).flow
    }
}