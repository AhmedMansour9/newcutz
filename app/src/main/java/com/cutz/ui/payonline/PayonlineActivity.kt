package com.cutz.ui.payonline

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.browser.customtabs.CustomTabsIntent
import androidx.lifecycle.lifecycleScope
import com.cutz.R
import com.cutz.base.BaseActivity
import com.cutz.databinding.ActivityPayonlineBinding
import com.xpay.kotlinutils.XpayUtils
import com.xpay.kotlinutils.models.BillingInfo
import com.xpay.kotlinutils.models.PaymentMethods
import com.xpay.kotlinutils.models.ServerSetting
import kotlinx.coroutines.launch

class PayonlineActivity : BaseActivity<ActivityPayonlineBinding>() {

    override var idLayoutRes: Int= R.layout.activity_payonline
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        XpayUtils.apiKey = "c1vG6Osg.ZOn5pgceV9yH1VExsR77ouybxe5dsbsb"
        XpayUtils.communityId = "YBZyk2k"
        XpayUtils.apiPaymentId = 81
        // default server settings are testing servers which is equivalent to
       XpayUtils.serverSetting = ServerSetting.TEST
        lifecycleScope.launch {
            try {
                val res = XpayUtils.prepareAmount(20.00)
                // read active payment methods list and set payUsing property
                // this test community returns CARD and KIOSK payment options
                // the following line is equivalent to XpayUtils.payUsing = XpayUtils.activePaymentMethods[0]
                XpayUtils.payUsing = PaymentMethods.CARD
                // read the total amount of card payment
                // this is also equivalent to
                // val totalAmount = res?.totalAmount!!
                val totalAmount = XpayUtils.PaymentOptionsTotalAmounts?.card!!
                // set billing information
                XpayUtils.billingInfo = BillingInfo( "John Doe", "j.doe@test.com", "+201068620964")
                // make payment
                val paymentRespone = XpayUtils.pay()
                // get payment form url and navigate to it to complete your payment
                val formUrl = paymentRespone?.iframe_url!!
                val builder = CustomTabsIntent.Builder()
                builder.setToolbarColor(resources.getColor(R.color.colorPrimary))
                builder.setShowTitle(true)
                val customTabsIntent: CustomTabsIntent = builder.build()
                customTabsIntent.launchUrl(this@PayonlineActivity, Uri.parse(paymentRespone.iframe_url))
                //in case of KIOSK payment method, read the message respone which contains your payment reference number from AMAN
                // val formUrl = paymentRespone?.message!!

                // that's it !
            } catch (e: Exception) {
                e.message?.let { msg -> error(resources.getString(R.string.error),msg) }
            }
        }

    }
}