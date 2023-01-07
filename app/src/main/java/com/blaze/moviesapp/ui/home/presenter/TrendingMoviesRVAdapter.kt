package com.blaze.moviesapp.ui.home.presenter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.RoundedCornersTransformation
import com.blaze.moviesapp.R
import com.blaze.moviesapp.databinding.TrendingMovieItemBinding
import com.blaze.moviesapp.domain.models.ApiConfigResponse
import com.blaze.moviesapp.domain.models.Movie

class TrendingMoviesRVAdapter(
    private val configuration: ApiConfigResponse
): RecyclerView.Adapter<TrendingMoviesRVAdapter.TrendingMovieViewHolder>() {

    private val diffUtilCallback = object: DiffUtil.ItemCallback<Movie>() {
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, diffUtilCallback)

    private var onItemClickListener: ((Movie) -> Unit)? = null

    fun setOnItemClickListener(listener: (Movie) -> Unit) {
        onItemClickListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrendingMovieViewHolder {
        val binding = TrendingMovieItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TrendingMovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TrendingMovieViewHolder, position: Int) {
        val data = differ.currentList[position]
        holder.bind(data)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    inner class TrendingMovieViewHolder(
        private val binding: TrendingMovieItemBinding
    ): RecyclerView.ViewHolder(binding.root) {

        fun bind(movie: Movie) {
            val imageUrl = configuration.images?.baseUrl + configuration.images?.posterSizes?.last() + movie.posterPath
            binding.ivPoster.load(imageUrl) {
                placeholder(R.drawable.ic_image)
                error(R.drawable.ic_image)
                transformations(RoundedCornersTransformation(32f))
            }
            itemView.setOnClickListener {
                onItemClickListener?.let {
                    it(movie)
                }
            }
        }
    }
}