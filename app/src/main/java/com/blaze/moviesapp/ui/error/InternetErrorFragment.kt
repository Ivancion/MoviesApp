package com.blaze.moviesapp.ui.error

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.blaze.moviesapp.databinding.FragmentInternetErrorBinding
import com.blaze.moviesapp.ui.base.BaseFragment

class InternetErrorFragment: BaseFragment<FragmentInternetErrorBinding>(FragmentInternetErrorBinding::inflate) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnTryAgain.setOnClickListener {
            (requireActivity() as ErrorFragmentActionManager).onTryAgainAction()
        }
    }
}