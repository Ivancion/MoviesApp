package com.blaze.moviesapp.other


sealed interface MovieCategory {
    object NowPlaying: MovieCategory
    object Upcoming: MovieCategory
    object TopRated: MovieCategory
    object Popular: MovieCategory
}