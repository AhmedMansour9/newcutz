package com.cairocart.ui.splash

import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import androidx.datastore.DataStore
import androidx.datastore.preferences.Preferences
import androidx.datastore.preferences.createDataStore
import androidx.datastore.preferences.preferencesKey
import androidx.lifecycle.Observer
import androidx.lifecycle.asLiveData
import androidx.navigation.Navigation
import com.cairocart.R
import com.cairocart.base.BaseFragment
import com.cairocart.databinding.SplashFragmentBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.map

@AndroidEntryPoint
class SplashFragment : BaseFragment<SplashFragmentBinding>() {

    override var idLayoutRes: Int = R.layout.splash_fragment
    private var cdt: CountDownTimer? = null
    private var dataStore: DataStore<Preferences>? = null

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        dataStore = requireContext().createDataStore("store_cart_app")
        loadUser()
    }

    private fun loadUser() {
        dataStore?.data!!.map { preferences -> preferences[preferencesKey<Boolean>("sign_in")] }
            .asLiveData().observe(viewLifecycleOwner, Observer {
                val signIn: Boolean = it != null && it
                Log.d("SplashFragment", "loadUser: " + (it != null && it))
                if (signIn) {
                    navigateToHome()
                } else
                    navigateToLogin()


            })
    }

    private fun navigateToHome() {
        setupCounterDown {
            Navigation.findNavController(requireActivity(), R.id.navigationFragment)
                .navigate(R.id.action_splashFragment_to_homeFragment2)
        }

    }

    private fun setupCounterDown(action: () -> Unit) {
        cdt = object : CountDownTimer(1000, 3000) {
            override fun onFinish() {
                action()
            }

            override fun onTick(p0: Long) {

            }
        }
        cdt?.start()
    }

    private fun navigateToLogin() {
        setupCounterDown {
            Navigation.findNavController(requireActivity(), R.id.navigationFragment)
                .navigate(R.id.action_splashFragment_to_loginFragment)
        }
    }

    override fun onStop() {
        cdt?.cancel()
        super.onStop()
    }
}