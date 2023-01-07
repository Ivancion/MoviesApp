package com.blaze.moviesapp.ui.watchlist

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.blaze.moviesapp.databinding.FragmentWatchlistBinding
import com.blaze.moviesapp.other.collectLatestLifecycleFlow
import com.blaze.moviesapp.other.collectLifecycleFlow
import com.blaze.moviesapp.ui.base.BaseFragment
import com.blaze.moviesapp.ui.main.CardSelector
import com.blaze.moviesapp.ui.search.presenter.SearchMoviesPagingAdapter
import com.blaze.moviesapp.ui.search.presenter.SearchMoviesRVItemDecoration
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WatchListFragment: BaseFragment<FragmentWatchlistBinding>(FragmentWatchlistBinding::inflate) {

    private lateinit var watchlistRVAdapter: SearchMoviesPagingAdapter
    private val viewModel: WatchlistViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()

        collectLatestLifecycleFlow(viewModel.watchlistPagingData) {
            watchlistRVAdapter.submitData(it)
        }

        collectLifecycleFlow(viewModel.isEmptyResponse) {
            binding.emptyWatchlistGroup.isVisible = it
        }
    }

    private fun setupRecyclerView() {
        watchlistRVAdapter = SearchMoviesPagingAdapter(viewModel.getApiConfiguration())
        binding.rvWatchlist.apply {
            adapter = watchlistRVAdapter
            layoutManager = LinearLayoutManager(requireContext())
            addItemDecoration(SearchMoviesRVItemDecoration())
        }

        watchlistRVAdapter.setOnItemClickListener {
            (requireActivity() as CardSelector).selectCard(it)
        }
    }


}