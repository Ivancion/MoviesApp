package com.blaze.moviesapp.domain.use_case

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.blaze.moviesapp.domain.models.Movie
import com.blaze.moviesapp.other.Constants.UNKNOWN_ERROR
import com.blaze.moviesapp.other.MovieCategory
import javax.inject.Inject

class CategoryMoviesPagingSource @Inject constructor(
    private val getMoviesByCategoryUseCase: GetMoviesByCategoryUseCase,
    private val category: MovieCategory
): PagingSource<Int, Movie>() {

    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        val anchorPosition = state.anchorPosition ?: return null
        val page = state.closestPageToPosition(anchorPosition) ?: return null
        return page.prevKey?.plus(1) ?: page.nextKey?.minus(1)
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        val page: Int = params.key ?: 1

        return try {
            val movieResponse = getMoviesByCategoryUseCase(category, page)
            val nextKey = if(page >= movieResponse.totalPages) null else page + 1
            val prevKey = if(page == 1) null else page - 1
            LoadResult.Page(movieResponse.results, prevKey, nextKey)
        } catch (e: Exception) {
            LoadResult.Error(Throwable(e.localizedMessage ?: e.message ?: UNKNOWN_ERROR))
        }
    }
}