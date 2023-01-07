package com.blaze.moviesapp.ui.details.presentation

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.fragment.app.commit
import com.blaze.moviesapp.R
import com.blaze.moviesapp.databinding.ActivityDetailsBinding
import com.blaze.moviesapp.ui.base.BaseActivity
import com.blaze.moviesapp.ui.details.viewmodel.DetailsViewModel
import com.blaze.moviesapp.ui.error.InternetErrorFragment
import com.blaze.moviesapp.ui.error.ServerErrorFragment
import dagger.hilt.android.AndroidEntryPoint

const val EXTRA_MOVIE_ID = "movie_id"
const val ADD_TO_WATCHLIST_ACTION = "com.blaze.moviesapp.ui.details.presentation.ADD_TO_WATCHLIST_ACTION"

@AndroidEntryPoint
class DetailsActivity : BaseActivity<ActivityDetailsBinding>(ActivityDetailsBinding::inflate) {

    private val viewModel: DetailsViewModel by viewModels()

    override fun setupActivityUI(data: Intent, savedInstanceState: Bundle?) {

        connectionLiveData.observe(this) {
            if(!it) {
                showConnectionErrorFragment()
            }
        }

        val id = data.getIntExtra(EXTRA_MOVIE_ID, 0)
        viewModel.loadDetailForMovie(id)
        Log.d("Details", id.toString())

        supportFragmentManager.commit {
            replace(R.id.fl_detail, DetailsFragment(), DetailsFragment::class.simpleName)
        }
    }

    override fun onBackPressed() {
        if(viewModel.addToWatchlistAction) {
            setResult(
                RESULT_OK,
                Intent().putExtra(ADD_TO_WATCHLIST_ACTION, true)
            )
            finish()
        } else {
            super.onBackPressed()
        }
    }

    override fun showConnectionErrorFragment() {
        supportFragmentManager.commit {
            replace(R.id.fl_detail, InternetErrorFragment(), InternetErrorFragment::class.simpleName)
            addToBackStack(null)
        }
    }

    override fun showServerErrorFragment() {
        supportFragmentManager.commit {
            replace(R.id.fl_detail, ServerErrorFragment(), ServerErrorFragment::class.simpleName)
            addToBackStack(null)
        }
    }

    override fun onTryAgainAction() {
        supportFragmentManager.popBackStack()
        recreate()
    }


}