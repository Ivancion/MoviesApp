package com.blaze.moviesapp.ui.home.presenter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.RoundedCornersTransformation
import com.blaze.moviesapp.R
import com.blaze.moviesapp.databinding.CategoryMovieItemBinding
import com.blaze.moviesapp.domain.models.ApiConfigResponse
import com.blaze.moviesapp.domain.models.Movie

class CategoryMoviesPagingAdapter(
    private val configuration: ApiConfigResponse
) : PagingDataAdapter<Movie, CategoryMoviesPagingAdapter.CategoryMovieViewHolder>(
        MovieDiffUtilCallback()
    ) {

    override fun onBindViewHolder(holder: CategoryMovieViewHolder, position: Int) {
        val data = getItem(position)
        holder.bind(movie = data)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryMovieViewHolder {
        val binding =
            CategoryMovieItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        binding.ivMovie.layoutParams.width = parent.width / 3
        return CategoryMovieViewHolder(binding)
    }

    private var onItemClickListener: ((Movie) -> Unit)? = null

    fun setOnItemClickListener(listener: (Movie) -> Unit) {
        onItemClickListener = listener
    }


    inner class CategoryMovieViewHolder(
        private val binding: CategoryMovieItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(movie: Movie?) {
            movie?.let {
                val imageUrl = configuration.images?.baseUrl + configuration.images?.posterSizes?.last() + movie.posterPath
                binding.ivMovie.load(imageUrl) {
                    transformations(RoundedCornersTransformation(32f))
                    placeholder(R.drawable.ic_image)
                    error(R.drawable.ic_image)
                }

                itemView.setOnClickListener {
                    onItemClickListener?.let {
                        it(movie)
                    }
                }
            }
        }
    }
}

class MovieDiffUtilCallback : DiffUtil.ItemCallback<Movie>() {

    override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return oldItem == newItem
    }

}