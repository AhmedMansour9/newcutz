package com.mgh.Fragments


import android.os.Build
import android.os.Bundle
import android.text.Html
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.mgh.Activities.Navigation
import com.mgh.ChangeLanguage
import com.mgh.Model.About_Response
import com.mgh.R
import com.mgh.ViewModel.AboutUs_ViewModel
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
        init()
        Get_Aboutus()




        return root
    }

    fun init() {
        toolbarhome=root.findViewById(R.id.toolbarAbout)

        val toggle = ActionBarDrawerToggle(
            activity,
            Navigation.drawerLayout,
            toolbarhome,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        toggle.apply {
            syncState()
            isDrawerIndicatorEnabled = false
            setHomeAsUpIndicator(R.drawable.ic_homemenu)
            setToolbarNavigationClickListener { Navigation.drawerLayout!!.openDrawer(GravityCompat.START) }
        }
        Navigation.drawerLayout?.addDrawerListener(toggle)

    }
    fun Get_Aboutus() {
        val aboutUs_viewModel = ViewModelProvider.NewInstanceFactory().create(
             AboutUs_ViewModel::class.java)

        aboutUs_viewModel.getAboutus(
            ChangeLanguage.getLanguage( context!!.applicationContext)+"/"+"about",
                context!!.applicationContext).observe(viewLifecycleOwner,
                Observer<About_Response.Data> { tripsData ->
                    if (tripsData != null) {
                        root.Title.setText(tripsData.title);
//                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                            root.Descrption.setText(
//                                Html.fromHtml(
                                    tripsData.description
                            )
                    }
//                                    Html.FROM_HTML_MODE_LEGACY  )
//                            )

//                        else {
//                            root.Descrption.setText(Html.fromHtml(tripsData.description))
//                        }
                        Glide.with(activity)
                                .load(tripsData.image)
                                .into(root.img_about)

                })
    }
}
