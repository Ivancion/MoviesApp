package com.blaze.moviesapp.domain.use_case

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.blaze.moviesapp.domain.models.Movie
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPagerWatchlistUseCase @Inject constructor(
    private val getMovieWatchlistUseCase: GetMovieWatchlistUseCase,
    private val getGenresUseCase: GetGenresUseCase
) {

    operator fun invoke(emptyResponse: (Boolean) -> Unit): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20
            ),
            pagingSourceFactory = {
                MovieWatchlistPagingSource(
                    getMovieWatchlistUseCase,
                    getGenresUseCase,
                    emptyResponse
                )
            }
        ).flow
    }
}