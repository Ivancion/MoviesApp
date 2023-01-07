package com.blaze.moviesapp.ui.details.presenter

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.blaze.moviesapp.other.toPx

class ReviewsCastRVItemDecoration(
    private val spanCount: Int,
    private val marginTop: Int
): RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val itemPosition = (view.layoutParams as RecyclerView.LayoutParams).bindingAdapterPosition

        if(spanCount == 1) {
            if(itemPosition != 0) {
                outRect.top = marginTop.toPx.toInt()
            }
            return
        }

        if(itemPosition % spanCount == spanCount - 1) {
            outRect.left = parent.width / 6
        }
        if(itemPosition in 0 until spanCount) {
            outRect.top = 0
        } else {
            outRect.top = marginTop.toPx.toInt()
        }
    }
}