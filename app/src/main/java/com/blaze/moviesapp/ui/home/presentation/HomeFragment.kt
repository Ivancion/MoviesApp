package com.blaze.moviesapp.ui.home.presentation

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager.HORIZONTAL
import com.blaze.moviesapp.R
import com.blaze.moviesapp.databinding.FragmentHomeBinding
import com.blaze.moviesapp.other.Constants.NOW_PLAYING
import com.blaze.moviesapp.other.Constants.POPULAR
import com.blaze.moviesapp.other.Constants.TOP_RATED
import com.blaze.moviesapp.other.Constants.UPCOMING
import com.blaze.moviesapp.other.MovieCategory
import com.blaze.moviesapp.other.collectLatestLifecycleFlow
import com.blaze.moviesapp.ui.base.BaseFragment
import com.blaze.moviesapp.ui.error.ServerErrorFragmentManager
import com.blaze.moviesapp.ui.home.viewmodel.HomeViewModel
import com.blaze.moviesapp.ui.home.presenter.CategoriesRVAdapter
import com.blaze.moviesapp.ui.home.presenter.CategoryMoviesPagingAdapter
import com.blaze.moviesapp.ui.home.presenter.HorizontalSpaceRVItemDecoration
import com.blaze.moviesapp.ui.home.presenter.TrendingMoviesRVAdapter
import com.blaze.moviesapp.ui.main.CardSelector
import com.blaze.moviesapp.ui.main.MainActivityNavManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment: BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

    private lateinit var trendingRvAdapter: TrendingMoviesRVAdapter
    private lateinit var categoriesRVAdapter: CategoriesRVAdapter
    private lateinit var categoryMoviesPagingAdapter: CategoryMoviesPagingAdapter

    private val viewModel: HomeViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()

        viewModel.getMoviesByCategory(MovieCategory.NowPlaying)

        viewModel.trendingMovies.observe(viewLifecycleOwner) {
            trendingRvAdapter.differ.submitList(it)
        }

        viewModel.categories.observe(viewLifecycleOwner) {
            categoriesRVAdapter.differ.submitList(it)
        }

        binding.etSearch.setOnClickListener {
            (requireActivity() as MainActivityNavManager).changeTab(R.id.miSearch)
        }

        binding.btnExit.setOnClickListener {
            viewModel.deleteSession()
        }

        collectLatestLifecycleFlow(viewModel.isSessionDeleted) {
            if(it) {
                (requireActivity() as MainActivityNavManager).exitFromApp()
            }
        }

        collectLatestLifecycleFlow(viewModel.categoryMoviesPageResponse) {
            categoryMoviesPagingAdapter.submitData(it)
        }

        collectLatestLifecycleFlow(viewModel.serverError) {
            (requireActivity() as ServerErrorFragmentManager).showServerErrorFragment()
        }
    }

    private fun setupRecyclerView() {
        trendingRvAdapter = TrendingMoviesRVAdapter(viewModel.getApiConfiguration())
        binding.rvTrending.apply {
            adapter = trendingRvAdapter
            layoutManager = LinearLayoutManager(requireContext(), HORIZONTAL, false)
            addItemDecoration(HorizontalSpaceRVItemDecoration(24))
        }

        trendingRvAdapter.setOnItemClickListener {
            (requireActivity() as CardSelector).selectCard(it)
        }

        categoriesRVAdapter = CategoriesRVAdapter()
        binding.rvCategories.apply {
            adapter = categoriesRVAdapter
            layoutManager = LinearLayoutManager(requireContext(), HORIZONTAL, false)
        }

        categoriesRVAdapter.setOnItemClickListener {
            getMovieCategoryFromString(it)?.let { category ->
                viewModel.getMoviesByCategory(category)

                lifecycleScope.launch {
                    delay(300)
                    binding.rvCategoryMovies.scrollToPosition(0)
                }
            }
        }

        categoryMoviesPagingAdapter = CategoryMoviesPagingAdapter(viewModel.getApiConfiguration())
        binding.rvCategoryMovies.apply {
            adapter = categoryMoviesPagingAdapter
            layoutManager = GridLayoutManager(requireContext(), 3)
        }

        categoryMoviesPagingAdapter.setOnItemClickListener {
            (requireActivity() as CardSelector).selectCard(it)
        }
    }

    private fun getMovieCategoryFromString(category: String): MovieCategory? {
        return when(category) {
            NOW_PLAYING -> MovieCategory.NowPlaying
            UPCOMING -> MovieCategory.Upcoming
            TOP_RATED -> MovieCategory.TopRated
            POPULAR -> MovieCategory.Popular
            else -> null
        }
    }

}