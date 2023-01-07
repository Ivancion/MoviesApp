package com.blaze.moviesapp.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.blaze.moviesapp.databinding.FragmentLoginBinding
import com.blaze.moviesapp.other.collectLatestLifecycleFlow
import com.blaze.moviesapp.other.collectLifecycleFlow
import com.blaze.moviesapp.ui.base.BaseFragment
import com.blaze.moviesapp.ui.error.ServerErrorFragmentManager
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment: BaseFragment<FragmentLoginBinding>(FragmentLoginBinding::inflate) {

    private val viewModel: LoginViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnLogin.setOnClickListener {
            viewModel.login(
                binding.etUsername.text.toString().trim(),
                binding.etPassword.text.toString().trim(),
                binding.cbRememberMe.isChecked
            )
        }

        collectLifecycleFlow(viewModel.loading) {
            binding.btnLogin.isClickable = !it
        }

        collectLatestLifecycleFlow(viewModel.snackbarErrorMessage) {
            Snackbar.make(binding.root, it, Snackbar.LENGTH_SHORT).show()
        }

        collectLatestLifecycleFlow(viewModel.isSuccess) {
            (requireActivity() as LoginActivityNavManager).goToMain()
        }

        collectLatestLifecycleFlow(viewModel.serverError) {
            (requireActivity() as ServerErrorFragmentManager).showServerErrorFragment()
        }
    }
}