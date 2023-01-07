package com.blaze.moviesapp.ui.search.presentation

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.blaze.moviesapp.databinding.FragmentSearchBinding
import com.blaze.moviesapp.other.collectLatestLifecycleFlow
import com.blaze.moviesapp.ui.base.BaseFragment
import com.blaze.moviesapp.ui.main.CardSelector
import com.blaze.moviesapp.ui.search.viewmodel.SearchViewModel
import com.blaze.moviesapp.ui.search.presenter.SearchMoviesPagingAdapter
import com.blaze.moviesapp.ui.search.presenter.SearchMoviesRVItemDecoration
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment: BaseFragment<FragmentSearchBinding>(FragmentSearchBinding::inflate) {

    private val viewModel: SearchViewModel by activityViewModels()

    private lateinit var searchMoviesPagingAdapter: SearchMoviesPagingAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tilSearch.requestFocus()

        setupRecyclerView()

        collectLatestLifecycleFlow(viewModel.searchMoviesPageResponse) {
            searchMoviesPagingAdapter.submitData(it)
        }

        collectLatestLifecycleFlow(viewModel.isEmptyResponse) {
            binding.noSearchResults.isVisible = it
        }

        binding.tilSearch.editText?.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                s?.let {
                    viewModel.searchMovies(s.toString())
                }
            }
            override fun afterTextChanged(s: Editable?) = Unit
        })
    }

    private fun setupRecyclerView() {
        searchMoviesPagingAdapter = SearchMoviesPagingAdapter(viewModel.getApiConfiguration())
        binding.rvSearch.apply {
            adapter = searchMoviesPagingAdapter
            layoutManager = LinearLayoutManager(requireContext())
            addItemDecoration(SearchMoviesRVItemDecoration())
        }

        searchMoviesPagingAdapter.setOnItemClickListener {
            (requireActivity() as CardSelector).selectCard(it)
        }
    }
}