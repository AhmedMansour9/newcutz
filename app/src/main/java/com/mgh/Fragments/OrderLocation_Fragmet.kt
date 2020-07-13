package com.mgh.Fragments


import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Paint
import android.location.Address
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.preference.PreferenceManager
import com.mgh.Activities.Navigation
import com.mgh.Model.Order_Response
import com.mgh.Model.PromoCode_Response
import com.mgh.R
import com.mgh.ViewModel.AddToCart_ViewModel
import com.mgh.ViewModel.Order_ViewModel
import com.mgh.utils.Loading
import kotlinx.android.synthetic.main.fragment_order_location__fragmet.view.*

/**
 * A simple [Fragment] subclass.
 */
class OrderLocation_Fragmet : Fragment()
{

     var latitude: Double ?=null
     var longitude:Double ?=null
     var addresses: List<Address>?=null
    internal var address: String?=null
    var TotalAfterDescount:Double = 0.0

    var Total:Double = 0.0
    var Currency:String?=null


    private lateinit var DataSaver: SharedPreferences
    var Code:String?=null
    var Items:String?=null
    lateinit var UserToken: String
    lateinit var root:View
    var bundle: Bundle? = Bundle()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        root= inflater.inflate(R.layout.fragment_order_location__fragmet, container, false)
        DataSaver = PreferenceManager.getDefaultSharedPreferences(context!!.applicationContext)
        UserToken = DataSaver.getString("token", null)!!
        bundle=this.arguments!!
        Items=bundle?.getString("items")
        Currency=bundle?.getString("cu")
        Total=bundle?.getString("totalprice")!!.toDouble()
        root.T_TotalCostOrder.text=Items+"  "+resources.getString(R.string.Items)+"/"+resources.getString(R.string.total)+
                " "+ bundle!!.getString("totalprice") +" "+Currency
        Btn_Order()
        AddPromo()


        return root
    }

    fun AddPromo(){
        root.Btn_EnterPromo.setOnClickListener(){
//            if(!NetworkCheck.isConnect(this)) {
//                startActivity(Intent(this, NoItemInternetImage::class.java))
//            }
            if(root.E_Promo.text.toString().isNullOrEmpty()){
                root.E_Promo.error=resources.getString(R.string.feildempty)
                return@setOnClickListener
            }
            Loading.Show(requireContext())
            var addtocart: AddToCart_ViewModel = ViewModelProvider.NewInstanceFactory().create(
                AddToCart_ViewModel::class.java)
            this.requireContext()?.let {
                addtocart.getPromoCodeData(DataSaver.getString("token", null)!!, root.E_Promo.text.toString()!!
                    , it)
                    .observe(viewLifecycleOwner, Observer<PromoCode_Response> { loginmodel ->
                       Loading.Disable()
                        if (loginmodel != null) {
                            Code=loginmodel.data!!.discount.toString()
                            root.Btn_EnterPromo.isEnabled=false
                            root.E_Promo.text=null
                            TotalAfterDescount=Total-loginmodel.data!!.discount
                            root.T_TotalCostOrder.text=Items+"  "+resources.getString(R.string.Items)+"/"+
                                    resources.getString(R.string.total)+
                                    " "+ TotalAfterDescount +" "+Currency
                            root.T_BeforeDiscount.setPaintFlags(root.T_BeforeDiscount.getPaintFlags() or Paint.STRIKE_THRU_TEXT_FLAG)
                            root.T_BeforeDiscount.text=bundle!!.getString("totalprice") +" "+Currency

                        }else {
                            Toast.makeText(
                                context!!.applicationContext,
                                context!!.applicationContext.getString(R.string.ordersuccess),
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    })
            }

    }
}

fun  Btn_Order(){
    root.Btn_order.setOnClickListener(){

        var Name = root.E_Name.text.toString()
        var Phone = root.E_phone.text.toString()
        var City = root.E_City.text.toString()
        var Country = root.E_Country.text.toString()
        var StreetName = root.E_StrretName.text.toString()
        var B_number = root.E_BuildingNumber.text.toString()
        var F_number = root.E_floornumber.text.toString()

        if(Name.isEmpty()){
            root.E_Name.error = "Name required"
            root.E_Name.requestFocus()
        }
        if(Phone.isEmpty()){
            root.E_phone.error = "Phone required"
            root.E_phone.requestFocus()
        }
        if(City.isEmpty()){
            root.E_City.error = "City required"
            root.E_City.requestFocus()
        }
        if(Country.isEmpty()){
            root.E_City.error = "Country required"
            root.E_City.requestFocus()
        }

        if(StreetName.isEmpty()){
            root.E_StrretName.error = "Street Name required"
            root.E_StrretName.requestFocus()
        }
        if(B_number.isEmpty()){
            root.E_BuildingNumber.error = "Buid Number required"
            root.E_BuildingNumber.requestFocus()
        }
        if(F_number.isEmpty()){
            root.E_floornumber.error = "Floor Number required"
            root.E_floornumber.requestFocus()
        }
        else if (!Name.isEmpty() && !Phone.isEmpty() && !City.isEmpty()  &&
            !StreetName.isEmpty() && !B_number.isEmpty() && !F_number.isEmpty()  ) {


                if (root.radios.getCheckedRadioButtonId() != -1) {
                    var id:Int = root.radios.getCheckedRadioButtonId()
                    val radioButton:View = root.radios.findViewById(id)
                    val radioId = root.radios.indexOfChild(radioButton)
                    val btn = root.radios.getChildAt(radioId) as RadioButton
                    val selection = btn.text as String
                    if (selection == "Visa" || selection == "ויזה" || selection == "بطاقة الائتمان") {
//                        val intent = Intent(activity, PayMent::class.java)
//
//                        intent.putExtra("lat", longitude.toString())
//                        intent.putExtra("lng", longitude.toString())
//                        intent.putExtra("phone",Phone)
//                        intent.putExtra("country", Country)
//                        intent.putExtra("street",StreetName)
//                        intent.putExtra("city", City)
//                        intent.putExtra("region", "")
//                        intent.putExtra("B_number", B_number)
//                        intent.putExtra("F_number",F_number)
//
//                        startActivity(intent)

                    } else if (selection == "Cash" || selection == "نقدي" || selection == "כסף מזומן") {
                        Loading.Show(requireContext())
                        root.Btn_order.isEnabled = false
                        var order: Order_ViewModel =
                            ViewModelProviders.of(this)[Order_ViewModel::class.java]
                        order.getData(
                            UserToken,
                            root.E_Name.text.toString(),
                            StreetName,
                            Phone
                            , "cacheOnDelivery"
                            , "0"
                            , root.E_City.text.toString()
                            , root.E_Country.text.toString()
                            , StreetName
                            , "0"
                            , ""
                            , B_number
                            , F_number
                            , ""
                            ,Code
                            , context!!.applicationContext
                        ).observe(viewLifecycleOwner,
                            Observer<Order_Response> { loginmodel ->
                                Loading.Disable()
                                if (loginmodel != null) {
                                    Toast.makeText(
                                        context!!.applicationContext,
                                        context!!.applicationContext.getString(R.string.ordersuccess),
                                        Toast.LENGTH_LONG
                                    ).show()
                                    val intent = Intent(
                                        context!!.applicationContext,
                                        Navigation::class.java
                                    )
                                    startActivity(intent)
                                    activity!!.finish()

                                } else {
                                    root.Btn_order.isEnabled = true
                                    Toast.makeText(
                                        context!!.applicationContext,
                                        context!!.applicationContext.getString(R.string.failedmsg),
                                        Toast.LENGTH_LONG
                                    ).show()
                                }
                            }
                        )




                }



            } else {
                Toast.makeText(
                    context!!.applicationContext,
                    context!!.applicationContext.getString(R.string.nointernet),
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }
}


}



















