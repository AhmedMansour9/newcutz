package com.cairocart.Activities

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.preference.PreferenceManager
import com.cairocart.ChangeLanguage
import com.cairocart.Model.Register_Model
import com.cairocart.R
import com.cairocart.SharedPrefManager
import com.cairocart.ViewModel.Register_ViewModel
import com.cairocart.utils.Loading
import kotlinx.android.synthetic.main.activity_register.*
import java.util.regex.Pattern


class Register : AppCompatActivity() {
    internal lateinit var shared: SharedPreferences
     var DeviceLang:String?= String()
    var DeviceToken:String?= String()

    private lateinit var dataSaver: SharedPreferences
    val EMAIL_REGEX = "^[A-Za-z](.*)([@]{1})(.{1,})(\\.)(.{1,})"
    private val PASSWORD_PATTERN = Pattern.compile(
        "^" +
                "(?=\\S+$)" +           //no white spaces
                ".{7,}"                //at least 4 characters
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        changeLanguage()
        setContentView(R.layout.activity_register)
        getLanguage()
        getUserToken()
    }



    private fun getUserToken(){
      dataSaver = PreferenceManager.getDefaultSharedPreferences(this);
      DeviceToken=dataSaver.getString("token",null)
  }

    private fun changeLanguage(){
     ChangeLanguage.changeLang(this)
    }
    private fun getLanguage(){
       DeviceLang= ChangeLanguage.getLanguage(this)
    }


    fun Btn_Register(view: View) {
        if (!ValidateEmailRegister() or !ValidatePasswordRegister()  or !ValidatePhoneRegister() or !ValidateRegister()) {
            return
        }
//        if (!CheckBox.isChecked) {
//            Toast.makeText(this, resources.getString(R.string.checked), Toast.LENGTH_SHORT ).show()
//            return
//        }
        val firstName: String = E_Email.text.toString()
        val Email = firstName.replace("\\s".toRegex(), "")
        var RegisterViewModel =
                    ViewModelProvider.NewInstanceFactory().create(Register_ViewModel::class.java)
           Loading.Show(this)
            view.isEnabled = false
            view.hideKeyboard()
            RegisterViewModel.getData(
                    Email,
                    E_Password.text.toString(),
                E_FirstName.text.toString(),
                E_LastName.text.toString(),
                    applicationContext
            ).observe(this,
                    Observer<Register_Model> { loginmodel ->
                        view.isEnabled = true
                        Loading.Disable()
                        if (loginmodel!=null) {
                            if(loginmodel.status?.code==200){
                                dataSaver.edit().putString("token", loginmodel.data?.token).apply()
                                SharedPrefManager.getInstance(this).saveToken(loginmodel.data?.token)
                                val intent = Intent(this, BottomNavigation::class.java)
                                startActivity(intent)
                                finish()
                            }else {
                                E_Password.text=null
                                Toast.makeText(
                                    applicationContext,
                                    loginmodel.status?.message,
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                        }
                    }
            )




    }

    fun View.hideKeyboard() {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(windowToken, 0)
    }

    private fun ValidateRegister():Boolean{
        val Fullname=E_FirstName.text.toString()
        if(Fullname.isEmpty()){
            E_FirstName.error=resources.getString(R.string.feildempty)
            return false
        }else {
            E_FirstName.error=null
            return true
        }
    }



    private fun ValidatePhoneRegister():Boolean{
        val Fullname=E_LastName.text.toString()
        if(Fullname.isEmpty()){
            E_LastName.error=resources.getString(R.string.feildempty)
            return false
        }else {
            E_LastName.error=null
            return true
        }
    }
    private fun ValidateEmailRegister():Boolean{
        val firstName: String = E_Email.text.toString()
        val Email = firstName.replace("\\s".toRegex(), "")
        if(Email.isEmpty()){
            E_Email.error=resources.getString(R.string.feildempty)
            return false
        } else if (!Patterns.EMAIL_ADDRESS.matcher(Email).matches()) run {
            E_Email.error =
                    resources.getString(R.string.validemail)
            return false
        }
        else {
            E_Email.error=null
            return true
        }
    }
    private fun ValidatePasswordRegister():Boolean{
        val Fullname=E_Password.text.toString()
        if(Fullname.isEmpty()){
            E_Password.error=resources.getString(R.string.feildempty)
            return false
        } else if (!PASSWORD_PATTERN.matcher(Fullname).matches()) run {
            E_Password.error =
                    resources.getString(R.string.pasweak)
            return false
        }
        else {
            E_Password.error=null
            return true
        }
    }



}

