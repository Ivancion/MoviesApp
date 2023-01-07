package com.blaze.moviesapp.ui.details.presentation

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager.HORIZONTAL
import coil.load
import coil.transform.RoundedCornersTransformation
import com.blaze.moviesapp.R
import com.blaze.moviesapp.databinding.FragmentDetailsBinding
import com.blaze.moviesapp.other.Constants.ABOUT_MOVIE
import com.blaze.moviesapp.other.Constants.CAST
import com.blaze.moviesapp.other.Constants.REVIEWS
import com.blaze.moviesapp.other.collectLatestLifecycleFlow
import com.blaze.moviesapp.other.collectLifecycleFlow
import com.blaze.moviesapp.ui.base.BaseFragment
import com.blaze.moviesapp.ui.details.viewmodel.DetailsViewModel
import com.blaze.moviesapp.ui.details.presenter.DetailTabsRVAdapter
import com.blaze.moviesapp.ui.details.presenter.ReviewsCastRVAdapter
import com.blaze.moviesapp.ui.details.presenter.ReviewsCastRVItemDecoration
import com.blaze.moviesapp.ui.error.ServerErrorFragmentManager
import com.blaze.moviesapp.ui.home.presenter.HorizontalSpaceRVItemDecoration
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailsFragment : BaseFragment<FragmentDetailsBinding>(FragmentDetailsBinding::inflate) {

    private val viewModel: DetailsViewModel by activityViewModels()
    private lateinit var detailTabsRVAdapter: DetailTabsRVAdapter
    private lateinit var reviewsCastRVAdapter: ReviewsCastRVAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()

        viewModel.infoTabs.observe(viewLifecycleOwner) {
            detailTabsRVAdapter.tabs = it
        }

        binding.btnBack.setOnClickListener {
            requireActivity().onBackPressed()
        }

        binding.btnAddToWatchlist.setOnClickListener {
            viewModel.addToWatchlist()
        }

        collectLifecycleFlow(viewModel.movieStates) {
            it?.let { state ->
                if(state.watchlist == true) {
                    binding.btnAddToWatchlist.setImageResource(R.drawable.ic_watchlist_filled)
                } else {
                    binding.btnAddToWatchlist.setImageResource(R.drawable.ic_watchlist_empty)
                }
            }
        }

        collectLifecycleFlow(viewModel.movieDetail) {
            it?.let { detail ->
                val configuration = viewModel.getApiConfiguration()
                val backdropPath =
                    configuration.images?.baseUrl + configuration.images?.backdropSizes?.get(1) + detail.backdropPath
                val imagePath =
                    configuration.images?.baseUrl + configuration.images?.posterSizes?.last() + detail.posterPath

                binding.apply {
                    ivBanner.load(backdropPath) {
                        transformations(
                            RoundedCornersTransformation(
                                bottomLeft = 32f,
                                bottomRight = 32f
                            )
                        )
                        placeholder(R.drawable.ic_image)
                        error(R.drawable.ic_image)
                    }

                    ivMovie.load(imagePath) {
                        transformations(RoundedCornersTransformation(32f))
                        placeholder(R.drawable.ic_image)
                        error(R.drawable.ic_image)
                    }

                    tvRate.text = String.format("%.1f", detail.voteAverage?.toFloat())
                    tvTitle.text = detail.title
                    tvReleaseDate.text = detail.releaseDate?.substringBefore("-") ?: ""
                    tvTimeDuration.text = "${detail.runtime} Minutes"
                    tvGenre.text = detail.genres?.firstOrNull()?.name ?: ""
                    tvOverview.text = detail.overview
                }
            }
        }

        collectLatestLifecycleFlow(viewModel.serverError) {
            (requireActivity() as ServerErrorFragmentManager).showServerErrorFragment()
        }
    }

    private fun setupRecyclerView() {
        detailTabsRVAdapter = DetailTabsRVAdapter()
        binding.rvInfoTabs.apply {
            adapter = detailTabsRVAdapter
            layoutManager = LinearLayoutManager(requireContext(), HORIZONTAL, false)
            addItemDecoration(HorizontalSpaceRVItemDecoration(16))
        }

        reviewsCastRVAdapter = ReviewsCastRVAdapter(viewModel.getApiConfiguration())
        binding.rvReviewsCast.adapter = reviewsCastRVAdapter

        detailTabsRVAdapter.setOnItemClickListener {

            when (it) {
                ABOUT_MOVIE -> {
                    binding.tvOverview.isVisible = true
                    binding.rvReviewsCast.isVisible = false
                }
                REVIEWS -> {
                    reviewsCastRVAdapter.items = viewModel.movieReviews ?: emptyList()
                    binding.rvReviewsCast.apply {
                        layoutManager = LinearLayoutManager(requireContext())
                        while (itemDecorationCount > 0) {
                            removeItemDecorationAt(0);
                        }
                        addItemDecoration(ReviewsCastRVItemDecoration(1, 12))
                    }
                    binding.tvOverview.isVisible = false
                    binding.rvReviewsCast.isVisible = true
                }
                CAST -> {
                    reviewsCastRVAdapter.items = viewModel.movieCast ?: emptyList()
                    binding.rvReviewsCast.apply {
                        layoutManager = GridLayoutManager(requireContext(), 2)
                        while (itemDecorationCount > 0) {
                            removeItemDecorationAt(0);
                        }
                        addItemDecoration(ReviewsCastRVItemDecoration(2, 24))
                    }
                    binding.tvOverview.isVisible = false
                    binding.rvReviewsCast.isVisible = true
                }
            }

        }
    }
}