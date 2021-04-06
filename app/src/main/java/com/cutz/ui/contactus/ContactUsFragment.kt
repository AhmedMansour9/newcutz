package com.cutz.ui.contactus

import android.os.Bundle
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.navGraphViewModels
import com.bumptech.glide.Glide
import com.cutz.R
import com.cutz.base.BaseFragment
import com.cutz.data.remote.model.ProfileResponse
import com.cutz.databinding.FragmentContactUsBinding
import com.cutz.databinding.FragmentEditProfileBinding
import com.cutz.ui.editprofile.EditProfileNavigator
import com.cutz.ui.editprofile.EditProfileViewModel
import com.cutz.utils.SharedData
import com.cutz.utils.Status
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.io.File


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ContactUsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */

@AndroidEntryPoint
class ContactUsFragment : BaseFragment<FragmentContactUsBinding>() {
    var  compressedFile1 : File? = null

    override var idLayoutRes: Int=R.layout.fragment_contact_us
    private val mViewModel: ContactUsViewModel by navGraphViewModels(R.id.graph_home) {
        defaultViewModelProviderFactory
    }
    private var token: String? = String()
    private var data: SharedData? = null
    private var GALLERY = 0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        data = SharedData(requireContext())
        mViewDataBinding.registerViewModel = mViewModel
        token = data?.getValue(SharedData.ReturnValue.STRING, "token")
        setupObserver()
        onClickBtn()

    }

    private fun onClickBtn() {
        mViewDataBinding.btnSend.setOnClickListener(){
            mViewModel.contactus()
        }
    }

    private fun setupObserver() {
        mViewModel.accountResponse.observe(viewLifecycleOwner, Observer {
            when (it.staus) {
                Status.SUCCESS -> {
                    dismissLoading()
                    Toast.makeText(requireContext(), resources.getString(R.string.sendsuccess), Toast.LENGTH_SHORT).show()
                    emptyData()
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

    private fun emptyData() {
        mViewDataBinding.EEmail.text=null
        mViewDataBinding.EFullName.text=null
        mViewDataBinding.EPhone.text=null
        mViewDataBinding.EMessage.text=null

    }

}