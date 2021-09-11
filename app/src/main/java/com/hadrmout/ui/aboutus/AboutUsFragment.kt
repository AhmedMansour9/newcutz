package com.hadrmout.ui.aboutus

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import com.hadrmout.ChangeLanguage
import com.hadrmout.R
import com.hadrmout.base.BaseFragment
import com.hadrmout.data.remote.model.AboutUsResponse
import com.hadrmout.databinding.FragmentAboutUsBinding
import com.hadrmout.utils.Status
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [AboutUsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */

@AndroidEntryPoint
class AboutUsFragment : BaseFragment<FragmentAboutUsBinding>() {
    override var idLayoutRes: Int = R.layout.fragment_about_us
    private var searchJob: Job? = null

    val mViewModel: AboutUsViewModel by navGraphViewModels(R.id.graph_home) {
        defaultViewModelProviderFactory
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        init()
        getData()
        setupObserver()
        onClickBack()

    }
    private fun onClickBack() {
        mViewDataBinding.ImgBack.setOnClickListener() {
            findNavController().popBackStack()
        }
    }
    private fun init() {
        mViewModel.Lang.value = ChangeLanguage.getLanguage(requireContext())
    }


    private fun getData() {
        mViewModel.aboutUs()

    }

    private fun setupObserver() {
        mViewModel.aboutResponse.observe(viewLifecycleOwner, Observer {
            when (it.staus) {
                Status.SUCCESS -> {
                    dismissLoading()
                    setData(it.data?.data)

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

    private fun setData(data: AboutUsResponse.Data?) {
       mViewDataBinding.model=data
    }

}