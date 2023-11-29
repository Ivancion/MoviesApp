package com.blaze.moviesapp.data.pagination

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.blaze.moviesapp.data.remote.MovieService
import com.blaze.moviesapp.domain.models.Movie
import com.blaze.moviesapp.other.Constants.UNKNOWN_ERROR
import com.blaze.moviesapp.other.MovieCategory
import javax.inject.Inject

class CategoryMoviesPagingSource @Inject constructor(
    private val category: MovieCategory,
    private val movieService: MovieService
): PagingSource<Int, Movie>() {

    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        val anchorPosition = state.anchorPosition ?: return null
        val page = state.closestPageToPosition(anchorPosition) ?: return null
        return page.prevKey?.plus(1) ?: page.nextKey?.minus(1)
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        val page: Int = params.key ?: 1

        return try {
            val movieResponse = when(category) {
                MovieCategory.NowPlaying -> movieService.getNowPlayingMovies(page = page)
                MovieCategory.Popular -> movieService.getPopularMovies(page = page)
                MovieCategory.TopRated -> movieService.getTopRatedMovies(page = page)
                MovieCategory.Upcoming -> movieService.getUpcomingMovies(page = page)
            }
            val nextKey = if(page >= movieResponse.totalPages) null else page + 1
            val prevKey = if(page == 1) null else page - 1
            LoadResult.Page(movieResponse.results, prevKey, nextKey)
        } catch (e: Exception) {
            LoadResult.Error(Throwable(e.localizedMessage ?: e.message ?: UNKNOWN_ERROR))
        }
    }
}