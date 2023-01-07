package com.blaze.moviesapp.ui.search.presenter

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.blaze.moviesapp.other.toPx

class SearchMoviesRVItemDecoration: RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {

        if(parent.getChildAdapterPosition(view) != parent.adapter?.itemCount?.minus(1)) {
            outRect.bottom = 24.toPx.toInt()
        }
    }
}