package com.blaze.moviesapp.domain.use_case

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.blaze.moviesapp.domain.models.Genre
import com.blaze.moviesapp.domain.models.Movie
import com.blaze.moviesapp.other.Constants.UNKNOWN_ERROR

class MovieWatchlistPagingSource(
    private val getMovieWatchlistUseCase: GetMovieWatchlistUseCase,
    private val getGenresUseCase: GetGenresUseCase,
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
            val movieResponse = getMovieWatchlistUseCase(page)
            val genres = getGenresUseCase().genres
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