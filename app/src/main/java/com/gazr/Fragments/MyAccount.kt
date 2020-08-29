package com.gazr.Fragments

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.preference.PreferenceManager
import com.gazr.Activities.Gifts
import com.gazr.Activities.Language
import com.gazr.Activities.TabsLayout
import com.gazr.Model.MessageEvent
import com.gazr.R
import com.gazr.SharedPrefManager
import kotlinx.android.synthetic.main.fragment_my_account.view.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode


/**
 * A simple [Fragment] subclass.
 * Use the [MyAccount.newInstance] factory method to
 * create an instance of this fragment.
 */
class MyAccount : Fragment() ,View.OnClickListener{
    private lateinit var dataSaver: SharedPreferences
    var UserToken: String?= String()

    lateinit var root:View
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        root= inflater.inflate(R.layout.fragment_my_account, container, false)
        dataSaver = PreferenceManager.getDefaultSharedPreferences(context);
        init()


        return root
    }
    fun init(){
        root.Img_profile.setOnClickListener(this)
        root.T_MyProfile.setOnClickListener(this)
        root.Img_about.setOnClickListener(this)
        root.T_Abouts.setOnClickListener(this)
        root.Img_Language.setOnClickListener(this)
        root.T_Language.setOnClickListener(this)
        root.Img_Contact.setOnClickListener(this)
        root.T_Contactus.setOnClickListener(this)
        root.Rela_Logout.setOnClickListener(this)
        root.T_Promo.setOnClickListener(this)
        root.Img_Promo.setOnClickListener(this)
        root.Img_WishLst.setOnClickListener(this)
        root.T_Wishlist.setOnClickListener(this)
        root.Img_Orders.setOnClickListener(this)
        root.T_Orders.setOnClickListener(this)
        root.Img_Login.setOnClickListener(this)
        root.T_Login.setOnClickListener(this)
        root.Img_Logout.setOnClickListener(this)
        root.T_Logout.setOnClickListener(this)
        root.Rela_Address.setOnClickListener(this)
        root.Rela_Wallet.setOnClickListener(this)
        UserToken=dataSaver.getString("token", null)

        if(UserToken==null){
            root.card_Login.visibility=View.VISIBLE
            root.card_view.visibility=View.INVISIBLE
            root.view2.visibility=View.GONE
            root.Img_Logout.visibility=View.GONE
            root.T_Logout.visibility=View.GONE
            root.Rela_Address.visibility=View.GONE
            root.Rela_Wallet.visibility=View.GONE

        }

    }

    override fun onClick(dd: View?) {
        val item_id = dd!!.id
        when(item_id) {
            R.id.Img_profile ->  Navigation.findNavController(dd).navigate(R.id.action_T_Setting_to_myProfile22);

            R.id.T_MyProfile ->  Navigation.findNavController(dd).navigate(R.id.action_T_Setting_to_myProfile22);
            R.id.Img_about -> Navigation.findNavController(dd).navigate(R.id.action_T_Setting_to_about_Us);
            R.id.T_Abouts -> {
                Navigation.findNavController(dd).navigate(R.id.action_T_Setting_to_about_Us);
            }
            R.id.Img_Language -> {
                var intent= Intent(requireContext(), Language::class.java)
                startActivity(intent)
            }
            R.id.Img_Promo -> {
                Navigation.findNavController(dd).navigate(R.id.action_T_Setting_to_sharePromo);
            }
            R.id.T_Promo -> {
                Navigation.findNavController(dd).navigate(R.id.action_T_Setting_to_sharePromo);
            }
            R.id.T_Language ->  {
                var intent= Intent(requireContext(),Language::class.java)
                startActivity(intent)
            }

            R.id.Img_Contact ->  Navigation.findNavController(dd).navigate(R.id.action_T_Setting_to_countries);
            R.id.T_Contactus -> Navigation.findNavController(dd).navigate(R.id.action_T_Setting_to_countries);
            R.id.Img_WishLst ->  Navigation.findNavController(dd).navigate(R.id.action_T_Setting_to_favourit2);
            R.id.T_Wishlist -> Navigation.findNavController(dd).navigate(R.id.action_T_Setting_to_favourit2);
            R.id.Img_Orders ->  Navigation.findNavController(dd).navigate(R.id.action_T_Setting_to_recent_Orders);
            R.id.T_Orders -> Navigation.findNavController(dd).navigate(R.id.action_T_Setting_to_recent_Orders);
            R.id.Img_Login ->  Navigation.findNavController(dd).navigate(R.id.action_T_Setting_to_login);
            R.id.T_Login -> Navigation.findNavController(dd).navigate(R.id.action_T_Setting_to_login);
            R.id.Rela_Address -> Navigation.findNavController(dd).navigate(R.id.action_T_Setting_to_editAddress);
            R.id.Rela_Wallet -> Navigation.findNavController(dd).navigate(R.id.action_T_Setting_to_wallet);

            R.id.Img_Logout -> {
                dataSaver.edit().putString("token", null).apply()
                val intent = Intent(context, TabsLayout::class.java)
                intent.addFlags(
                    Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK
                            or Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
                requireActivity().finish()

            }
            R.id.T_Logout -> {
                dataSaver.edit().putString("token", null).apply()
                val intent = Intent(context, TabsLayout::class.java)
                intent.addFlags(
                    Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK
                            or Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
                requireActivity().finish()
            }
        }
    }


    @Subscribe(sticky = false, threadMode = ThreadMode.MAIN)
    fun onMessageEvent( messsg: MessageEvent) {/* Do something */
        Log.d("IGNORE", "Logging view to curb warnings: $messsg")
        if(messsg.Message.equals("login")) {
            root.card_Login.visibility=View.GONE
            root.card_view.visibility=View.VISIBLE
            root.view2.visibility=View.VISIBLE
            root.Img_Logout.visibility=View.VISIBLE
            root.T_Logout.visibility=View.VISIBLE
            root.Rela_Address.visibility=View.VISIBLE
            root.Rela_Wallet.visibility=View.VISIBLE
        }
    };
    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this)
        }

    }

    fun Get_Gifts() {
        if(UserToken!=null){
            var used:String?= SharedPrefManager.getInstance(context).gift
            if(used.isNullOrEmpty()){
                startActivity(Intent(context, Gifts::class.java))
            }
        }

    }

}