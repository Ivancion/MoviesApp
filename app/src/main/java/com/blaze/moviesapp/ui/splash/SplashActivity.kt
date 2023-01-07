package com.blaze.moviesapp.ui.splash

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.fragment.app.commit
import com.blaze.moviesapp.R
import com.blaze.moviesapp.databinding.ActivitySplashBinding
import com.blaze.moviesapp.other.collectLatestLifecycleFlow
import com.blaze.moviesapp.ui.base.BaseActivity
import com.blaze.moviesapp.ui.base.isConnected
import com.blaze.moviesapp.ui.error.InternetErrorFragment
import com.blaze.moviesapp.ui.error.ServerErrorFragment
import com.blaze.moviesapp.ui.login.LoginActivity
import com.blaze.moviesapp.ui.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint

@SuppressLint("CustomSplashScreen")
@AndroidEntryPoint
class SplashActivity : BaseActivity<ActivitySplashBinding>(ActivitySplashBinding::inflate),
    SplashActivityNavManager {

    override fun setupActivityUI(data: Intent, savedInstanceState: Bundle?) {
        connectionLiveData.observe(this) {
            if (!it) {
                showConnectionErrorFragment()
            }
        }

        if(isConnected) {
            supportFragmentManager.commit {
                replace(R.id.fl_splash, SplashFragment(), SplashFragment::class.simpleName)
            }
        }
    }

    override fun showConnectionErrorFragment() {
        supportFragmentManager.commit {
            replace(
                R.id.fl_splash,
                InternetErrorFragment(),
                InternetErrorFragment::class.simpleName
            )
            addToBackStack(null)
        }
    }

    override fun onTryAgainAction() {
        supportFragmentManager.popBackStack()
        recreate()
    }

    override fun goToLogin() {
        Intent(this, LoginActivity::class.java).apply {
            startActivity(this)
            finish()
        }
    }

    override fun goToMain() {
        Intent(this, MainActivity::class.java).apply {
            startActivity(this)
            finish()
        }
    }

    override fun showServerErrorFragment() {
        supportFragmentManager.commit {
            replace(
                R.id.fl_splash,
                ServerErrorFragment(),
                ServerErrorFragment::class.simpleName
            )
            addToBackStack(null)
        }
    }
}