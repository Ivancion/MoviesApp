package com.blaze.moviesapp.ui.details.presenter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.blaze.moviesapp.R
import com.blaze.moviesapp.databinding.CastItemBinding
import com.blaze.moviesapp.databinding.ReviewItemBinding
import com.blaze.moviesapp.domain.models.ApiConfigResponse
import com.blaze.moviesapp.domain.models.Author
import com.blaze.moviesapp.domain.models.Cast
import com.blaze.moviesapp.domain.models.Equatable

class ReviewsCastRVAdapter(
    private val configuration: ApiConfigResponse,
): RecyclerView.Adapter<DetailViewHolder>() {

    var items: List<Equatable> = emptyList()
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            //Strange bug when use this
            //notifyItemRangeChanged(0, itemCount)
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailViewHolder {
        return when(viewType) {
            R.layout.cast_item -> {
                val binding = CastItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                binding.ivCastPhoto.layoutParams.width = parent.width / 3
                binding.ivCastPhoto.layoutParams.height = parent.width / 3
                DetailViewHolder.CastViewHolder(binding, configuration)
            }
            R.layout.review_item -> {
                val binding = ReviewItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                DetailViewHolder.ReviewViewHolder(binding, configuration)
            }
            else -> throw IllegalArgumentException("Invalid ViewType Provided")
        }
    }

    override fun onBindViewHolder(holder: DetailViewHolder, position: Int) {
        when(holder) {
            is DetailViewHolder.CastViewHolder -> {
                val cast = items[position] as Cast
                holder.bind(cast)
            }
            is DetailViewHolder.ReviewViewHolder -> {
                val author = items[position] as Author
                holder.bind(author)
            }
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun getItemViewType(position: Int): Int {
        return when (items[position]) {
            is Cast -> R.layout.cast_item
            is Author -> R.layout.review_item
            else -> 0
        }
    }

}