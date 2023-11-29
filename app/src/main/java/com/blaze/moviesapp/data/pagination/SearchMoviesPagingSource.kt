package com.blaze.moviesapp.data.pagination

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.blaze.moviesapp.data.remote.MovieService
import com.blaze.moviesapp.domain.models.Genre
import com.blaze.moviesapp.domain.models.Movie
import com.blaze.moviesapp.other.Constants.UNKNOWN_ERROR
import javax.inject.Inject

class SearchMoviesPagingSource @Inject constructor(
    private val movieService: MovieService,
    private val query: String,
    private val emptyResponse: (Boolean) -> Unit
) : PagingSource<Int, Movie>() {

    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        val anchorPosition = state.anchorPosition ?: return null
        val page = state.closestPageToPosition(anchorPosition) ?: return null
        return page.prevKey?.plus(1) ?: page.nextKey?.minus(1)
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        val page: Int = params.key ?: 1

        return try {
            val response = movieService.searchMovies(
                query = query,
                page = page
            )
            val genres = movieService.getGenres().genres
            if(page == 1)
                emptyResponse(response.results.isEmpty())
            response.results.forEach {
                it.genres = getGenresForMovie(
                    it.genreIds,
                    genres
                ).filterNotNull()
            }

            val nextKey = if (page >= (response.totalPages)) null else page + 1
            val prevKey = if (page == 1) null else page - 1
            LoadResult.Page(response.results, prevKey, nextKey)
        } catch (e: Exception) {
            LoadResult.Error(Throwable(e.localizedMessage ?: e.message ?: UNKNOWN_ERROR))
        }
    }

    private fun getGenresForMovie(
        genreIds: List<Int>?,
        genres: List<Genre>
    ): List<String?> {

        val genreStrList = mutableListOf<String?>()
        genreIds?.forEach { genreId ->
            val genre = genres.find { genre ->
                genre.id == genreId
            }?.name
            genreStrList.add(genre)
        }
        return genreStrList
    }

}