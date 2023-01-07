package com.blaze.moviesapp.ui.search.presenter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.RoundedCornersTransformation
import com.blaze.moviesapp.R
import com.blaze.moviesapp.databinding.SearchMovieItemBinding
import com.blaze.moviesapp.domain.models.ApiConfigResponse
import com.blaze.moviesapp.domain.models.Movie
import com.blaze.moviesapp.ui.home.presenter.MovieDiffUtilCallback

class SearchMoviesPagingAdapter(
     private val configuration: ApiConfigResponse
): PagingDataAdapter<Movie, SearchMoviesPagingAdapter.SearchMovieViewHolder>(MovieDiffUtilCallback()) {

     override fun onBindViewHolder(holder: SearchMovieViewHolder, position: Int) {
          val data = getItem(position)
          holder.bind(data)
     }

     override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchMovieViewHolder {
          val binding = SearchMovieItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
          return SearchMovieViewHolder(binding)
     }

     private var onItemClickListener: ((Movie) -> Unit)? = null

     fun setOnItemClickListener(listener: (Movie) -> Unit) {
          onItemClickListener = listener
     }

     inner class SearchMovieViewHolder(
          private val binding: SearchMovieItemBinding
     ): RecyclerView.ViewHolder(binding.root) {

          fun bind(movie: Movie?) {

               movie?.let {
                    val imageUrl = configuration.images?.baseUrl + configuration.images?.posterSizes?.last() + movie.posterPath

                    binding.apply {
                         ivMovie.load(imageUrl) {
                              transformations(RoundedCornersTransformation(32f))
                              placeholder(R.drawable.ic_image)
                              error(R.drawable.ic_image)
                         }

                         tvTitle.text = it.title
                         tvRating.text = String.format("%.1f", it.voteAverage?.toFloat())
                         tvGenre.text = it.genres?.take(2).toString().removeSurrounding("[", "]")
                         tvReleaseDate.text = it.releaseDate?.substringBefore("-") ?: ""
                    }

                    itemView.setOnClickListener {
                         onItemClickListener?.let { it(movie) }
                    }
               }
          }
     }
}