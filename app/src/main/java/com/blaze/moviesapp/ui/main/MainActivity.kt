package com.blaze.moviesapp.ui.main

import android.content.Intent
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.blaze.moviesapp.R
import com.blaze.moviesapp.databinding.ActivityMainBinding
import com.blaze.moviesapp.domain.models.Movie
import com.blaze.moviesapp.ui.base.BaseActivity
import com.blaze.moviesapp.ui.base.isConnected
import com.blaze.moviesapp.ui.details.presentation.ADD_TO_WATCHLIST_ACTION
import com.blaze.moviesapp.ui.details.presentation.DetailsActivity
import com.blaze.moviesapp.ui.details.presentation.EXTRA_MOVIE_ID
import com.blaze.moviesapp.ui.error.InternetErrorFragment
import com.blaze.moviesapp.ui.error.ServerErrorFragment
import com.blaze.moviesapp.ui.home.presentation.HomeFragment
import com.blaze.moviesapp.ui.login.LoginActivity
import com.blaze.moviesapp.ui.search.presentation.SearchFragment
import com.blaze.moviesapp.ui.watchlist.WatchListFragment
import com.blaze.moviesapp.ui.watchlist.WatchlistViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate),
    MainActivityNavManager, CardSelector {

    private val watchlistViewModel: WatchlistViewModel by viewModels()

    private val registerForActivityDetailResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == RESULT_OK) {
                when {
                    (it.data?.getBooleanExtra(ADD_TO_WATCHLIST_ACTION, false) == true) -> {
                        watchlistViewModel.getWatchlist()
                    }
                }
            }
        }

    override fun setupActivityUI(data: Intent, savedInstanceState: Bundle?) {
        connectionLiveData.observe(this) {
            if (!it) {
                showConnectionErrorFragment()
            }
        }

        setStartFragment()

        watchlistViewModel.getWatchlist()

        binding.bottomNavView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.miHome -> setCurrentFragment(HomeFragment())
                R.id.miSearch -> setCurrentFragment(SearchFragment())
                R.id.miWatchlist -> setCurrentFragment(WatchListFragment())
            }
            true
        }

        binding.bottomNavView.setOnItemReselectedListener {
            return@setOnItemReselectedListener
        }
    }

    private fun setStartFragment() {
        val fragment = supportFragmentManager.findFragmentById(R.id.fl_main)
        fragment?.let {
            when(fragment.tag) {
                HomeFragment::class.simpleName -> {
                    setCurrentFragment(HomeFragment())
                }
                SearchFragment::class.simpleName -> {
                    setCurrentFragment(SearchFragment())
                }
                WatchListFragment::class.simpleName -> {
                    setCurrentFragment(WatchListFragment())
                }
            }
        } ?: run {
            setCurrentFragment(HomeFragment())
        }
    }

    private fun setCurrentFragment(fragment: Fragment) {
        supportFragmentManager.commit {
            replace(R.id.fl_main, fragment, fragment::class.simpleName)
        }
    }

    override fun showConnectionErrorFragment() {
        supportFragmentManager.commit {
            replace(R.id.fl_main, InternetErrorFragment(), InternetErrorFragment::class.simpleName)
            addToBackStack(null)
        }
        binding.bottomNavView.isVisible = false
    }



    override fun changeTab(menuItemId: Int) {
        binding.bottomNavView.selectedItemId = menuItemId
    }

    override fun exitFromApp() {
        Intent(this, LoginActivity::class.java).apply {
            startActivity(this)
            finish()
        }
    }

    override fun selectCard(movie: Movie) {
        Intent(this, DetailsActivity::class.java).apply {
            putExtra(EXTRA_MOVIE_ID, movie.id)
            registerForActivityDetailResult.launch(this)
        }
    }

    override fun showServerErrorFragment() {
        supportFragmentManager.commit {
            replace(R.id.fl_main, ServerErrorFragment(), ServerErrorFragment::class.simpleName)
            addToBackStack(null)
        }
        binding.bottomNavView.isVisible = false
    }

    override fun onTryAgainAction() {
        supportFragmentManager.popBackStack()
        recreate()
        binding.bottomNavView.isVisible = true
    }

}