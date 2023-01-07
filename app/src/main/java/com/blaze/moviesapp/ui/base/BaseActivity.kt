package com.blaze.moviesapp.ui.base

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import androidx.viewbinding.ViewBinding
import com.blaze.moviesapp.ui.error.ConnectionErrorFragmentManager
import com.blaze.moviesapp.ui.error.ErrorFragmentActionManager
import com.blaze.moviesapp.ui.error.ServerErrorFragmentManager
import dagger.hilt.android.AndroidEntryPoint

abstract class BaseActivity<B: ViewBinding>(
    private val bindingFactory: (LayoutInflater) -> B
): AppCompatActivity(), ConnectionErrorFragmentManager, ServerErrorFragmentManager, ErrorFragmentActionManager {

    protected val binding: B by lazy { bindingFactory(layoutInflater) }
    protected lateinit var connectionLiveData: ConnectionLiveData

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.apply {
            setContentView(root)
        }

        connectionLiveData = ConnectionLiveData(this)
        setupActivityUI(intent, savedInstanceState)
    }

    abstract fun setupActivityUI(data: Intent, savedInstanceState: Bundle?)

    abstract override fun showConnectionErrorFragment()
    abstract override fun showServerErrorFragment()
    abstract override fun onTryAgainAction()
}