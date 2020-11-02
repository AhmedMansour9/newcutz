package com.cairocart.ui.login

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavOptions
import androidx.navigation.Navigation
import com.cairocart.R
import com.cairocart.base.BaseFragment
import com.cairocart.databinding.LoginFragmentBinding
import com.cairocart.ui.bottomnavigate.BottomNavigateFragment
import com.cairocart.utils.Status
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class LoginFragment : BaseFragment<LoginFragmentBinding>(), LoginNavigator {

    override var idLayoutRes: Int = R.layout.login_fragment
    private val mViewModel: LoginViewModel by viewModels()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mViewModel.navigator = this
        mViewDataBinding.loginViewModel = mViewModel
        setupObserver()
    }

    private fun setupObserver() {
        mViewModel.accountResponse.observe(viewLifecycleOwner, Observer {
            when (it.staus) {
                Status.SUCCESS -> {
                    dismissLoading()
                    startActivity(Intent(context, BottomNavigateFragment::class.java))
                    activity?.finish()
//                    Navigation.findNavController(requireActivity(), R.id.navigationFragment)
//                        .navigate(R.id.action_loginFragment_to_homeFragment2) // go to home
                }
                Status.LOADING -> {
                    showLoading()
                }

                Status.ERROR -> {
                    dismissLoading()
                    // toast

                }
            }
        })
    }

    override fun loginClick() {
        mViewModel.login()
    }

    override fun createAccoutClick() {

        Navigation.findNavController(requireActivity(), R.id.navigationFragment)
            .navigate(R.id.action_loginFragment_to_registerFragment)
    }

    override fun forgetPasswordClick() {
        TODO("Not yet implemented")
    }

    override fun loginGoogleClick() {
        TODO("Not yet implemented")
    }

    override fun loginFacebookClick() {
        TODO("Not yet implemented")
    }

}