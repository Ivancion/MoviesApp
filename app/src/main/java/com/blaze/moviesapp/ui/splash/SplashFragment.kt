package com.blaze.moviesapp.ui.splash

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import com.blaze.moviesapp.databinding.FragmentSplashBinding
import com.blaze.moviesapp.other.collectLatestLifecycleFlow
import com.blaze.moviesapp.ui.base.BaseFragment
import com.blaze.moviesapp.ui.error.ErrorFragmentActionManager
import com.blaze.moviesapp.ui.error.ServerErrorFragmentManager
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashFragment: BaseFragment<FragmentSplashBinding>(FragmentSplashBinding::inflate) {

    private val viewModel: SplashViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.loadApiConfig()

        collectLatestLifecycleFlow(viewModel.isLoggedIn) {
            if(it) {
                (requireActivity() as SplashActivityNavManager).goToMain()
            } else {
                (requireActivity() as SplashActivityNavManager).goToLogin()
            }
        }

        collectLatestLifecycleFlow(viewModel.serverError) {
            (requireActivity() as ServerErrorFragmentManager).showServerErrorFragment()
        }
    }
}