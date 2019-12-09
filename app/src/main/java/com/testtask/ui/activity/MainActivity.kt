package com.testtask.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.findNavController
import com.testtask.R
import com.testtask.databinding.ActivityMainBinding
import com.testtask.domain.state.AuthState
import org.koin.android.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val viewModel: MainActivityViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityMainBinding = DataBindingUtil.setContentView(
            this, R.layout.activity_main
        )
        setSupportActionBar(binding.toolbar)
        viewModel.authStateLiveData.observe(this, Observer {
            val destination = when (it) {
                is AuthState.Authorized -> R.id.mainFragment
                is AuthState.UnAuthorized -> R.id.authFragment
                else -> null
            }
            destination?.let {
                val controller = findNavController(R.id.nav_host_fragment)
                controller.navigate(destination, Bundle().apply { NavOptionsBuilder().launchSingleTop })
            }
        }
        )
    }

    override fun onBackPressed() {
        finish()
    }
}
