package com.mgh.Activities

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.preference.PreferenceManager
import com.mgh.ChangeLanguage
import com.mgh.Model.MessageEvent
import com.mgh.Model.Register_Model
import com.mgh.R
import com.mgh.ViewModel.Register_ViewModel
import com.mgh.utils.Loading
import kotlinx.android.synthetic.main.activity_register.*
import org.greenrobot.eventbus.EventBus
import java.util.regex.Pattern


class Register : AppCompatActivity() {
    var City_Id:String=""
    var State_Id:String=""
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

        if(!ValidateMatchPasswordRegister()){
            return
        }
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
                    E_FullName.text.toString(),
                    E_Phone.text.toString(),
                    applicationContext
            ).observe(this,
                    Observer<Register_Model> { loginmodel ->
                        view.isEnabled = true
                        Loading.Disable()
                        if (loginmodel != null) {
                            val customer_id = loginmodel.accessToken
                            dataSaver.edit().putString("token", customer_id.toString()).apply()
//                            val intent = Intent(this, Navigation::class.java)
//                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
//                            startActivity(intent)
                            EventBus.getDefault().postSticky(MessageEvent("login"))

                            finish()
                        } else {
                            E_Password.text=null
                            E_ConfirmPassword.text=null
                            val status: Boolean = RegisterViewModel.getStatus()
                            if (status == true) {
                                if (!Register_ViewModel.LastEmail.isNullOrEmpty()) {
                                    E_Email.error =
                                        Register_ViewModel.LastEmail.toString()
                                }
                                if (!Register_ViewModel.LastPhone.isNullOrEmpty()) {
                                    E_Phone.error =
                                        Register_ViewModel.LastPhone.toString()
                                }

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
        val Fullname=E_FullName.text.toString()
        if(Fullname.isEmpty()){
            E_FullName.error=resources.getString(R.string.feildempty)
            return false
        }else {
            E_FullName.error=null
            return true
        }
    }



    private fun ValidatePhoneRegister():Boolean{
        val Fullname=E_Phone.text.toString()
        if(Fullname.isEmpty()){
            E_Phone.error=resources.getString(R.string.feildempty)
            return false
        }else {
            E_Phone.error=null
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

    private fun ValidateConfirmPasswordRegister():Boolean{
        val Fullname=E_ConfirmPassword.text.toString()
        if(Fullname.isEmpty()){
            E_ConfirmPassword.error=resources.getString(R.string.feildempty)
            return false
        } else if (!PASSWORD_PATTERN.matcher(Fullname).matches()) run {
            E_ConfirmPassword.error =
                resources.getString(R.string.pasweak)
            return false
        }
        else {
            E_ConfirmPassword.error=null
            return true
        }
    }

    private fun ValidateMatchPasswordRegister():Boolean{
        val ConfirmPassword=E_ConfirmPassword.text.toString()
        val Password=E_Password.text.toString()

        if(!Password.equals(ConfirmPassword)){
           Toast.makeText(this,resources.getString(R.string.p_notmatch),Toast.LENGTH_LONG).show()

            return false
        }
        else {
            return true
        }
    }

}

