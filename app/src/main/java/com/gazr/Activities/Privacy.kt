package com.gazr.Activities

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.gazr.BaseActivity
import com.gazr.ChangeLanguage
import com.gazr.Model.About_Response
import com.gazr.R
import com.gazr.ViewModel.AboutUs_ViewModel
import kotlinx.android.synthetic.main.activity_privacy.*

class Privacy : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_privacy)
        Get_Privacy()

    }

    fun Get_Privacy() {
        val aboutUs_viewModel = ViewModelProvider.NewInstanceFactory().create(
            AboutUs_ViewModel::class.java)
        aboutUs_viewModel.getPrivacy(
            "staticPages",
            this).observe(this,
            Observer<About_Response.Data> { tripsData ->
                if (tripsData != null) {
                    Title.setText(tripsData.title);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        Descrption.setText(
                            Html.fromHtml(
                                tripsData.description, Html.FROM_HTML_MODE_LEGACY
                            )
                        )
                    } else {
                        Descrption.setText(Html.fromHtml(tripsData.description))
                    }
                }
                Glide.with(this)
                    .load(tripsData.image)
                    .into(img_about)

            })
    }
}