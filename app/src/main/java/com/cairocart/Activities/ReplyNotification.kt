package com.cairocart.Activities

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.preference.PreferenceManager
import com.cairocart.Model.ContactUs_Response
import com.cairocart.R
import com.cairocart.ViewModel.ContactUs_ViewModel
import com.cairocart.utils.Loading
import kotlinx.android.synthetic.main.activity_add__review.*

class ReplyNotification : AppCompatActivity() {

    lateinit var contactUs_viewModel: ContactUs_ViewModel
    private lateinit var DataSaver: SharedPreferences
    var UserToken: String? = String()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reply_notification)
        DataSaver = PreferenceManager.getDefaultSharedPreferences(this)
        UserToken = DataSaver.getString("token", null)

        contactUs_viewModel = ViewModelProvider.NewInstanceFactory().create(
            ContactUs_ViewModel::class.java
        )
        Review()
    }

    fun Review() {

        Btn_Confirm.setOnClickListener(View.OnClickListener { view ->
            if (!ValidateMessage()) {
                return@OnClickListener
            }

            if (E_Messagee.getText().toString() != ""
            ) {
                Loading.Show(this)
                contactUs_viewModel.ReplayNotification(
                    E_Messagee.getText().toString(),
                    intent.getStringExtra("id")!!,
                    UserToken!!,
                    this
                ).observe(this, Observer<ContactUs_Response> { tripsData ->
                    Loading.Disable()
                    if (tripsData != null) {
                        E_Messagee.setText(null)
                        Toast.makeText(
                            this,
                            resources.getString(R.string.success_addcomment),
                            Toast.LENGTH_SHORT
                        ).show()
                        finish()

                    } else {
                        Toast.makeText(
                            this,
                            resources.getString(R.string.failedmsg),
                            Toast.LENGTH_SHORT
                        ).show()

                    }
                })

            }
        })


    }


    private fun ValidateMessage(): Boolean {
        val EMAIL = E_Messagee.getText().toString().trim({ it <= ' ' })
        if (EMAIL.isEmpty()) {
            E_Messagee.error = "Please Enter Message"
            return false
        }
        return true
    }
}