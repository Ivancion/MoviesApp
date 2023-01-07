package com.blaze.moviesapp.ui.error

import android.os.Bundle
import android.view.View
import com.blaze.moviesapp.databinding.FragmentServerErrorBinding
import com.blaze.moviesapp.ui.base.BaseFragment

class ServerErrorFragment: BaseFragment<FragmentServerErrorBinding>(FragmentServerErrorBinding::inflate) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnTryAgain.setOnClickListener {
            (requireActivity() as ErrorFragmentActionManager).onTryAgainAction()
        }
    }
}