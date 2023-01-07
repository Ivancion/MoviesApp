package com.blaze.moviesapp.ui.details.presenter

import android.view.View
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import coil.load
import coil.transform.CircleCropTransformation
import com.blaze.moviesapp.R
import com.blaze.moviesapp.databinding.CastItemBinding
import com.blaze.moviesapp.databinding.ReviewItemBinding
import com.blaze.moviesapp.domain.models.ApiConfigResponse
import com.blaze.moviesapp.domain.models.Author
import com.blaze.moviesapp.domain.models.Cast
import com.blaze.moviesapp.other.Constants.COLLAPSE
import com.blaze.moviesapp.other.Constants.EXPAND

sealed class DetailViewHolder(binding: ViewBinding) : RecyclerView.ViewHolder(binding.root) {

    class CastViewHolder(
        private val binding: CastItemBinding,
        private val configuration: ApiConfigResponse
    ) : DetailViewHolder(binding) {

        fun bind(cast: Cast) {
            val avatarPath = configuration.images?.baseUrl + configuration.images?.profileSizes?.last() + cast.profilePath
            binding.ivCastPhoto.load(avatarPath) {
                transformations(CircleCropTransformation())
                placeholder(R.drawable.round_image_placeholder)
                error(R.drawable.round_image_placeholder)
            }

            binding.tvCastName.text = cast.name
        }
    }

    class ReviewViewHolder(
        private val binding: ReviewItemBinding,
        private val configuration: ApiConfigResponse
    ) : DetailViewHolder(binding) {

        fun bind(review: Author) {
            val avatarPath = configuration.images?.baseUrl + configuration.images?.profileSizes?.last() + review.authorDetails?.avatarPath
            binding.apply {
                ivAuthorPhoto.load(avatarPath) {
                    transformations(CircleCropTransformation())
                    placeholder(R.drawable.round_image_placeholder).transformations(CircleCropTransformation())
                    error(R.drawable.round_image_placeholder).transformations(CircleCropTransformation())
                }

                tvAuthorName.text = if(review.authorDetails?.name.isNullOrEmpty()) "Author" else review.authorDetails?.name
                tvAuthorReview.text = review.content
                tvAuthorRate.text = (review.authorDetails?.rating ?: "No rate").toString()

                tvAuthorReview.post {
                    if(tvAuthorReview.layout.text.toString() != review.content && tvAuthorReview.maxLines == 4) {
                        tvExpand.text = EXPAND
                        groupHelperText.visibility = View.VISIBLE
                    } else {
                        groupHelperText.visibility = View.GONE
                    }
                }

                tvExpand.setOnClickListener {
                    if(groupHelperText.isVisible) {
                        if(tvExpand.text == EXPAND) {
                            tvAuthorReview.maxLines = Int.MAX_VALUE
                            tvExpand.text = COLLAPSE
                        } else {
                            tvAuthorReview.maxLines = 4
                            tvExpand.text = EXPAND
                        }
                    }
                }
            }
        }
    }
}
