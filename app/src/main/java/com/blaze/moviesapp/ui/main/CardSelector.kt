package com.blaze.moviesapp.ui.main

import com.blaze.moviesapp.domain.models.Movie

interface CardSelector {
    fun selectCard(movie: Movie)
}