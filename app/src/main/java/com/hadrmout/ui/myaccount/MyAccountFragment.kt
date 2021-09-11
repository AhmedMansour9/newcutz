package com.hadrmout.ui.myaccount

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.navigation.navGraphViewModels
import com.bumptech.glide.Glide
import com.hadrmout.R
import com.hadrmout.base.BaseFragment
import com.hadrmout.data.remote.model.MessageEvent
import com.hadrmout.data.remote.model.ProfileResponse
import com.hadrmout.databinding.FragmentMyAccountBinding
import com.hadrmout.databinding.NewMyaccountBinding
import com.hadrmout.ui.address_profile.AddressFragment
import com.hadrmout.ui.editprofile.EditProfileViewModel
import com.hadrmout.ui.languag.Language
import com.hadrmout.ui.login.LoginActivity
import com.hadrmout.utils.SharedData
import com.hadrmout.utils.Status
import dagger.hilt.android.AndroidEntryPoint
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode


/**
 * A simple [Fragment] subclass.
 * Use the [MyAccountFragment.newInstance] factory method to
 * create an instance of this fragment.
 */

@AndroidEntryPoint
class MyAccountFragment : BaseFragment<NewMyaccountBinding>(), MyAccountNavigator {

    override var idLayoutRes: Int = R.layout.new_myaccount
    private val mViewModel: MyAccountViewModel by viewModels()
    private var token: String? = String()
    private var data: SharedData? = null
    private val mViewModel2: EditProfileViewModel by navGraphViewModels(R.id.graph_home) {
        defaultViewModelProviderFactory
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mViewDataBinding.viewModel = mViewModel
        mViewModel.navigator = this
        data = SharedData(requireContext())
        token = data?.getValue(SharedData.ReturnValue.STRING, "token")
        setupObserverProfile()


    }
    fun setData(profile: ProfileResponse){
        mViewDataBinding.TName.setText(profile.data?.name.toString())
        if(profile.data?.phone.isNullOrEmpty()){
//            mViewDataBinding.TPhone.setText(profile.data?.email.toString())

        }else {
//            mViewDataBinding.TPhone.setText(profile.data?.phone.toString()+" - "+profile.data?.email.toString())
        }
        Glide.with(requireActivity()).load(profile.data?.image)
            .error(R.drawable.ic_editimage).into(mViewDataBinding.Photo);

    }

    private fun setupObserverProfile() {
        mViewModel2.getProfileResponse.observe(viewLifecycleOwner, Observer {
            when (it.staus) {
                Status.SUCCESS -> {
                    dismissLoading()
                    it.data?.let { it1 -> setData(it1) }
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


    override fun onClickFavourit() {
        Navigation.findNavController(mViewDataBinding.root)
            .navigate(R.id.action_myAccountFragment_to_wishListFragment);
    }

    override fun onClickLogin() {
        startActivity(Intent(requireActivity(),LoginActivity::class.java))
        activity?.finish()
    }

    override fun onClickProfile() {
        Navigation.findNavController(mViewDataBinding.root)
            .navigate(R.id.action_myAccountFragment_to_editProfileFragment);
    }

    override fun onClickLogOut() {
        data?.putValue("token", "")
        val intent=Intent(requireContext(), LoginActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
        activity?.finish()

    }

    override fun onClickContactUs() {
            Navigation.findNavController(mViewDataBinding.root)
                .navigate(R.id.action_myAccountFragment_to_contactUsFragment);

    }

    override fun onClickAboutUs() {
        Navigation.findNavController(mViewDataBinding.root)
            .navigate(R.id.action_myAccountFragment_to_aboutUsFragment);

    }

    override fun onClickMyOrders() {
        Navigation.findNavController(mViewDataBinding.root)
            .navigate(R.id.action_myAccountFragment_to_myOrdersFragment);
    }

    override fun onClickLanguage() {
        startActivity(Intent(requireContext(),Language::class.java))
    }

    override fun onClickMyAddress() {
        val newDialogFragment = AddressFragment()
        val bundle = Bundle()
        newDialogFragment.setArguments(bundle)
        val transaction: FragmentTransaction =
            requireActivity().supportFragmentManager.beginTransaction()
        newDialogFragment.show(transaction, "New_Dialog_Fragment")
    }


    fun checkToken(): Boolean {
        token = data?.getValue(SharedData.ReturnValue.STRING, "token")

        if (token.isNullOrEmpty()) {
            return false
        }
        return true
    }


    @Subscribe(sticky = false, threadMode = ThreadMode.MAIN)
    fun onMessageEvent(messsg: MessageEvent) {/* Do something */
        if(messsg.Message.equals("profile")){
            mViewModel2.getProfile(token!!)
        }
    };
    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this)
        }

    }

    override fun onDestroy() {
        EventBus.getDefault().unregister(this)
        super.onDestroy()
    }
}