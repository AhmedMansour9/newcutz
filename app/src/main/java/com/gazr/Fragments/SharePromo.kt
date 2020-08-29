package com.gazr.Fragments

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.preference.PreferenceManager
import com.chicchicken.ViewModel.Profile_ViewModel
import com.gazr.Model.Profile_Response
import com.gazr.R
import com.gazr.utils.Loading
import kotlinx.android.synthetic.main.fragment_share_promo.view.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [SharePromo.newInstance] factory method to
 * create an instance of this fragment.
 */
class SharePromo : Fragment() {
    lateinit var root:View
    private lateinit var DataSaver: SharedPreferences
    var UserToken: String?= String()
    var Promo: String?= String()
    var SharingMessage:String?= String()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        root= inflater.inflate(R.layout.fragment_share_promo, container, false)
        DataSaver = PreferenceManager.getDefaultSharedPreferences(requireContext())
        Get_Profle()
        Share()

        return root
    }
    fun Share(){
        root.Img_Share.setOnClickListener(){
         shareApp()
        }
    }

    private fun shareApp() {
        SharingMessage="تم مشاركة كود الخصم ${Promo}\n يمكنك الاستفادة من الخصم عن طريق تحميل تطبيق #جزر # طلباتك_اوامر  \n تحميل ايفون :https://apps.apple.com/us/app/id1525254401d \n تحميل اندرويد https://play.google.com/store/apps/details?id=com.gazr \n حمل التطبيق الان واستمتع بعروض وخصومات كل يوم  "
        val sendIntent = Intent()
        sendIntent.action = Intent.ACTION_SEND
        sendIntent.putExtra(
            Intent.EXTRA_TEXT,
            SharingMessage
        )
        sendIntent.type = "text/plain"
        startActivity(sendIntent)
    }
    fun Get_Profle() {
        var Prof_ViewModel: Profile_ViewModel =
            ViewModelProviders.of(this)[Profile_ViewModel::class.java]
        Loading.Show(requireContext())
        Prof_ViewModel.getData(
            DataSaver.getString("token",null),
            requireContext().applicationContext
        ).observe(viewLifecycleOwner,
            Observer<Profile_Response> { loginmodel ->
                Loading.Disable()
                if (loginmodel != null) {
                    Promo=loginmodel.data?.promocode
                    root.T_Promoo.text=String.format(resources.getString(R.string.your_promo)+" "+loginmodel.data?.promocode)

                } else {
//                    Toast.makeText(
//                        requireContext().applicationContext,
//                        requireContext().applicationContext.getString(R.string.failedmsg),
//                        Toast.LENGTH_LONG
//                    ).show()
                }
            }
        )
    }

}