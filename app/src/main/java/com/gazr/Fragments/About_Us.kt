package com.gazr.Fragments


import android.os.Build
import android.os.Bundle
import android.text.Html
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.gazr.ChangeLanguage
import com.gazr.Model.About_Response
import com.gazr.R
import com.gazr.ViewModel.AboutUs_ViewModel
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.fragment_about__us.view.*

/**
 * A simple [Fragment] subclass.
 */
class About_Us : Fragment() {
   lateinit var root:View
    var toolbarhome: Toolbar?=null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        root= inflater.inflate(R.layout.fragment_about__us, container, false)
        Get_Aboutus()




        return root
    }


    fun Get_Aboutus() {
        val aboutUs_viewModel = ViewModelProvider.NewInstanceFactory().create(
             AboutUs_ViewModel::class.java)

        aboutUs_viewModel.getAboutus(
            ChangeLanguage.getLanguage( requireContext())+"/"+"policy",
                requireContext()).observe(viewLifecycleOwner,
                Observer<About_Response.Data> { tripsData ->
                    if (tripsData != null) {
                        root.Title.setText(tripsData.title);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                            root.Descrption.setText(
                                Html.fromHtml(
                                    tripsData.description, Html.FROM_HTML_MODE_LEGACY
                                )
                            )
                        } else {
                            root.Descrption.setText(Html.fromHtml(tripsData.description))
                        }
                    }
                        Glide.with(activity)
                                .load(tripsData.image)
                                .into(root.img_about)

                })
    }
}
