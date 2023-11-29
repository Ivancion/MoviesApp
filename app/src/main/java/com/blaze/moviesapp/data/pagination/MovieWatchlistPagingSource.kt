package com.blaze.moviesapp.data.pagination

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.blaze.moviesapp.data.remote.MovieService
import com.blaze.moviesapp.domain.models.Genre
import com.blaze.moviesapp.domain.models.Movie
import com.blaze.moviesapp.domain.models.MoviesResponse
import com.blaze.moviesapp.other.Constants.UNKNOWN_ERROR
import javax.inject.Inject

class MovieWatchlistPagingSource @Inject constructor(
    private val sessionId: String,
    private val movieService: MovieService,
    private val emptyResponse: (Boolean) -> Unit
): PagingSource<Int, Movie>() {

    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        val anchorPosition = state.anchorPosition ?: return null
        val page = state.closestPageToPosition(anchorPosition) ?: return null
        return page.prevKey?.plus(1) ?: page.nextKey?.minus(1)
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        val page: Int = params.key ?: 1

        return try {
            val movieResponse = getMovieWatchlistByPage(page)
            val genres = getGenres()
            if(page == 1)
                emptyResponse(movieResponse?.results?.size == 0)
            movieResponse?.results?.forEach {
                it.genres = getGenresForMovie(
                    it.genreIds,
                    genres
                ).filterNotNull()
            }

            val nextKey = if(page >= (movieResponse?.totalPages ?: 0)) null else page + 1
            val prevKey = if(page == 1) null else page - 1
            LoadResult.Page(checkNotNull(movieResponse?.results), prevKey, nextKey)
        } catch (e: Exception) {
            LoadResult.Error(Throwable(e.localizedMessage ?: e.message ?: UNKNOWN_ERROR))
        }
    }

    private suspend fun getGenres(): List<Genre> {
        return movieService.getGenres().genres
    }

    private suspend fun getMovieWatchlistByPage(page: Int): MoviesResponse? {
        return try {
            val accountDetails = movieService.getAccountDetails(sessionId = sessionId)
            val accountId = accountDetails.id ?: 0
            movieService.getMovieWatchlist(
                accountId = accountId,
                page = page,
                sessionId = sessionId
            )
        } catch (e: Exception) {
            null
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