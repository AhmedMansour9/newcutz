package com.mgh.Activities

import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.preference.PreferenceManager
import com.mgh.R

class PayMent : AppCompatActivity() {
    lateinit var UserToken: String
    private lateinit var DataSaver: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pay_ment)
        DataSaver = PreferenceManager.getDefaultSharedPreferences(applicationContext)

        UserToken = DataSaver.getString("token", null)!!

        var lat = intent.getStringExtra("lat")
        var lng = intent.getStringExtra("lng")
        var  country = intent.getStringExtra("country")
        var street = intent.getStringExtra("street")
        var  phone = intent.getStringExtra("phone")
        var note = intent.getStringExtra("note")
        var address = intent.getStringExtra("address")
        var city = intent.getStringExtra("city")
        var region = intent.getStringExtra("region")
        var B_number = intent.getStringExtra("B_number")
        var F_number = intent.getStringExtra("F_number")

//        T_Total.setText(resources.getString(R.string.total) + " " + Cart_Fragment.T_TotalPrice)


//        val webview = findViewById(R.id.webView) as WebView
//        webview.settings.javaScriptEnabled = true
//        webview.loadUrl("https://direct.tranzila.com/georgetest/iframenew.php?sum=" + Cart.T_TotalPrice+ "&currency=1&cred_type=1&nologo=1")
//        val webViewSettings = webview.settings
//        webViewSettings.javaScriptCanOpenWindowsAutomatically = true
//        webViewSettings.javaScriptEnabled = true
//        webViewSettings.builtInZoomControls = true
//        webViewSettings.pluginState = WebSettings.PluginState.ON
//        webview.webViewClient = object : WebViewClient() {
//
//            override fun onPageStarted(view: WebView, url: String, favicon: Bitmap) {
//                // Here you can check your new URL.
//                super.onPageStarted(view, url, favicon)
//                if (url == "https://e-bakers.org/About-Us") {
//                                        var order: Order_ViewModel =
//                                            ViewModelProviders.of(this@PayMent)[Order_ViewModel::class.java]
//                                        order.getData(
//                                            UserToken,
//                                                Cart.T_TotalPrice!!,
//                                            street,
//                                            phone
//                                            , "0"
//                                            , "0"
//                                            , "1"
//                                            , "1"
//                                            , city
//                                            , country
//                                            , street
//                                            , "0"
//                                            , region
//                                            , B_number
//                                            , F_number
//                                            , ""
//                                            , applicationContext
//                                        ).observe(this@PayMent,
//                                            Observer<Order_Response> { loginmodel ->
//                                                if (loginmodel != null) {
//                                                   successpayment()
//                                                } else {
//                                                    Toast.makeText(
//                                                        applicationContext,
//                                                        applicationContext.getString(R.string.failedmsg),
//                                                        Toast.LENGTH_LONG
//                                                    ).show()
//                                                }
//                                            }
//                                        )
//
//                                    } else {
//                                        errornote()
//                                    }
//
//
//
//
//            }
//
//        }
//
//
//
//
    }
    fun errornote() {
        val alt_bld = AlertDialog.Builder(this)
        alt_bld.setMessage("UnSuccessfull PaYment").setCancelable(false)
            .setPositiveButton("ok") { dialog, id -> finish() }
        val alert = alt_bld.create()
        // Title for AlertDialog
        alert.setTitle("Error!")
        alert.setCancelable(false)
        // Icon for AlertDialog
        alert.setIcon(android.R.drawable.ic_dialog_alert)
        alert.show()
    }

    fun successpayment() {
        val alt_bld = AlertDialog.Builder(this)
        alt_bld.setMessage("Successfull PaYment").setCancelable(false)
            .setPositiveButton("ok", DialogInterface.OnClickListener { dialog, id ->
                Toast.makeText(
                    this@PayMent,
                    resources.getString(R.string.ordersuccess),
                    Toast.LENGTH_SHORT
                ).show()
                val intent = Intent(this@PayMent, Navigation::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
                finish()
            })
        val alert = alt_bld.create()
        // Title for AlertDialog
        alert.setTitle("Congratulation!")
        alert.setCancelable(false)
        // Icon for AlertDialog
        alert.setIcon(android.R.drawable.ic_dialog_alert)
        alert.show()
    }
}
