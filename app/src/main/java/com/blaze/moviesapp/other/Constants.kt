package com.blaze.moviesapp.other

object Constants {

    const val BASE_URL = "https://api.themoviedb.org/3/"

    //SYSTEM PREFERENCES
    const val SHARED_PREFS_NAME = "movies_app"
    const val SESSION_ID = "session_id"

    const val UNKNOWN_ERROR = "Unknown error occured"
    const val INVALID_LOGIN_DATA = "Invalid username or password"

    //MEDIA_TYPE
    const val ALL = "all"
    const val MOVIE = "movie"
    const val TV = "tv"
    const val PERSON = "person"

    //TIME_WINDOW
    const val DAY = "day"
    const val WEEK = "week"

    //CATEGORIES OF MOVIES
    const val NOW_PLAYING = "Now playing"
    const val UPCOMING = "Upcoming"
    const val TOP_RATED = "Top rated"
    const val POPULAR = "Popular"

    //USER
    const val USERNAME = "username"
    const val PASSWORD = "password"

    //MOVIE DETAIL TABS
    const val ABOUT_MOVIE = "About Movie"
    const val REVIEWS = "Reviews"
    const val CAST = "Cast"

    //REVIEW HELPER BUTTONS
    const val EXPAND = "Expand"
    const val COLLAPSE = "Collapse"

    //SORT RESULTS
    const val DESC = "created_at.desc"
    const val ASC = "created_at.asc"

    //APPEND TO RESPONSE
    const val REVIEWS_AND_CREDITS = "reviews,credits"
}
