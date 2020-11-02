package com.cairocart.ui.register

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import com.cairocart.R
import com.cairocart.base.BaseFragment
import com.cairocart.databinding.RegisterFragmentBinding
import com.cairocart.ui.bottomnavigate.BottomNavigateFragment
import com.cairocart.utils.Status
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterFragment : BaseFragment<RegisterFragmentBinding>(), RegisterNavigator {

    override var idLayoutRes: Int = R.layout.register_fragment

    private val mViewModel: RegisterViewModel by viewModels()


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        mViewDataBinding.registerViewModel = mViewModel
        mViewModel.navigator = this
        setupObserver()
    }

    override fun backLoginClick() {
        Navigation.findNavController(requireActivity(), R.id.navigationFragment).navigateUp()
    }

    override fun sigupClick() {
        mViewModel.register()
    }

    private fun setupObserver() {
        mViewModel.accountResponse.observe(viewLifecycleOwner, Observer {
            when (it.staus) {
                Status.SUCCESS -> {
                    dismissLoading()
                    startActivity(Intent(context, BottomNavigateFragment::class.java))
                    activity?.finish()

//                    Navigation.findNavController(requireActivity(), R.id.navigationFragment)
//                        .navigate(R.id.action_registerFragment_to_homeFragment2) // go to home
                }
                Status.LOADING -> {
                    showLoading()
                }

                Status.ERROR -> {
                    dismissLoading()

                }
            }
        })
    }

}