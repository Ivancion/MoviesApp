package com.blaze.moviesapp.ui.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.fragment.app.commit
import com.blaze.moviesapp.R
import com.blaze.moviesapp.databinding.ActivityLoginBinding
import com.blaze.moviesapp.other.collectLatestLifecycleFlow
import com.blaze.moviesapp.ui.base.BaseActivity
import com.blaze.moviesapp.ui.error.ConnectionErrorFragmentManager
import com.blaze.moviesapp.ui.error.InternetErrorFragment
import com.blaze.moviesapp.ui.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : BaseActivity<ActivityLoginBinding>(ActivityLoginBinding::inflate), LoginActivityNavManager {

    override fun setupActivityUI(data: Intent, savedInstanceState: Bundle?) {

        connectionLiveData.observe(this) {
            if(!it) {
                showConnectionErrorFragment()
            }
        }

        supportFragmentManager.commit {
            replace(R.id.fl_login, LoginFragment(), LoginFragment::class.simpleName)
        }
    }

    override fun showConnectionErrorFragment() {
        supportFragmentManager.commit {
            replace(R.id.fl_login, InternetErrorFragment(), InternetErrorFragment::class.simpleName)
            addToBackStack(null)
        }
    }



    override fun goToMain() {
        Intent(this, MainActivity::class.java).apply {
            startActivity(this)
            finish()
        }
    }

    override fun showServerErrorFragment() {

    }

    override fun onTryAgainAction() {
        supportFragmentManager.popBackStack()
        recreate()
    }

}