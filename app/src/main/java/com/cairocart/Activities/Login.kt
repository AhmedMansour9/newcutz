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
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.GraphRequest
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.cairocart.ChangeLanguage
import com.cairocart.Model.Register_Model
import com.cairocart.R
import com.cairocart.SharedPrefManager
import com.cairocart.ViewModel.Register_ViewModel
import com.cairocart.utils.Loading
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.GoogleApiClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.android.synthetic.main.activity_login.*
import org.greenrobot.eventbus.EventBus
import java.util.*
import java.util.regex.Pattern

class Login : AppCompatActivity() {
    var DeviceLang:String?= String()
    var DeviceToken:String?= String()
    private lateinit var dataSaver: SharedPreferences
    lateinit var mAuth: FirebaseAuth
    lateinit var googleApiClient: GoogleApiClient
    var RequestSignInCode:Int=7
    lateinit var googleSignInOptions: GoogleSignInOptions
    private var callbackManager: CallbackManager? = null
    var socialid:String?= String()
    var email:String?=String()
    var name:String?=String()
    private val PASSWORD_PATTERN = Pattern.compile(
            "^" +
                    "(?=\\S+$)" +           //no white spaces
                    ".{5,}"                //at least 4 characters
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        changeLanguage()
        setContentView(R.layout.activity_login)
        mAuth = FirebaseAuth.getInstance();
        GoogleSignOpition();
        LoginFacebook()
        Login_Google()
        getLanguage()
        getUserToken()
        openRegister()
        openHome()

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

    private fun openRegister() {
        btnRegister.setOnClickListener(){
            val intent = Intent(this, Register::class.java)
            startActivity(intent)
            finish()
        }
    }
//    private fun openForget() {
//        btnForget.setOnClickListener(){
//            val intent = Intent(this, ForgetPassword::class.java)
//            startActivity(intent)
//
//        }
//    }
    fun openHome(){
        btnLogin.setOnClickListener() {
            if (!ValidateEmailLogin() or !ValidatePasswordLogin()) {
                return@setOnClickListener
            }

            var RegisterViewModel = ViewModelProvider.NewInstanceFactory().create(
                Register_ViewModel::class.java
            )
            Loading.Show(this)
            btnLogin.isEnabled = false
            btnLogin.hideKeyboard()
            RegisterViewModel.getLogin(
                E_EmailLogin.text.toString(),
                E_PasswordLogin.text.toString(),
                applicationContext
            ).observe(this,
                Observer<Register_Model> { loginmodel ->
                    btnLogin.isEnabled = true
                    Loading.Disable()
                    if (loginmodel!=null) {
                        if(loginmodel.status?.code==200){
                            dataSaver.edit().putString("token", loginmodel.data?.token).apply()
                            SharedPrefManager.getInstance(this).saveToken(loginmodel.data?.token)
                            val intent = Intent(this, TabsLayout::class.java)
                            startActivity(intent)
                            finish()
                        }else {
                            E_PasswordLogin.text=null
                            Toast.makeText(
                                applicationContext,
                                applicationContext.getString(R.string.wrongemailorpass),
                                Toast.LENGTH_LONG
                            ).show()
                        }
                     }

                }
            )
        }

    }




    private fun ValidateEmailLogin():Boolean{
        val firstName: String = E_EmailLogin.text.toString()
        val Email = firstName.replace("\\s".toRegex(), "")
        if(Email.isEmpty()){
            E_EmailLogin.error=resources.getString(R.string.feildempty)
            return false
        } else if (!Patterns.EMAIL_ADDRESS.matcher(Email).matches()) run {
            E_EmailLogin.error =
                    resources.getString(R.string.validemail)
            return false
        }
        else {
            E_EmailLogin.error=null

            return true
        }
    }

    private fun ValidatePasswordLogin():Boolean{
        val Fullname=E_PasswordLogin.text.toString()
        if(Fullname.isEmpty()){
            E_PasswordLogin.error=resources.getString(R.string.feildempty)
            return false
        } else if (!PASSWORD_PATTERN.matcher(Fullname).matches()) run {
            E_PasswordLogin.error =
                    resources.getString(R.string.pasweak)
            return false
        }
        else {
            E_PasswordLogin.error=null
            return true
        }
    }

    fun View.hideKeyboard() {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(windowToken, 0)
    }
    private fun GoogleSignOpition() {

        googleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build();
        googleApiClient =  GoogleApiClient.Builder(applicationContext)
//                .enableAutoManage(applicationContext)
            .addApi(Auth.GOOGLE_SIGN_IN_API, googleSignInOptions)
            .build();
    }


    private  fun Login_Google(){
        googleBtn.setOnClickListener(){
            val signInIntent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
            startActivityForResult(signInIntent, RequestSignInCode)
        }

    }

    private fun LoginFacebook() {
        faceBtn.setOnClickListener(View.OnClickListener {
            // Login
            callbackManager = CallbackManager.Factory.create()
            LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile", "email"))
            LoginManager.getInstance().registerCallback(callbackManager,
                object : FacebookCallback<LoginResult> {
                    override fun onSuccess(loginResult: LoginResult) {
                        val request = GraphRequest.newMeRequest(loginResult.accessToken) { `object`, response ->
                            if (`object`.has("email")) {
                                email =`object`.get("email").toString()
                            }
                            if (`object`.has("id")) {
                                socialid =`object`.get("id").toString()
                            }
                            if (`object`.has("name")) {
                                name =`object`.get("name").toString()
                            }

                            LoginFaceBooks(socialid,email,name)
                        }
                        val parameters = Bundle()
                        parameters.putString("fields", "name,email,id,picture.type(large)")
                        request.parameters = parameters
                        request.executeAsync()
                    }

                    override fun onCancel() {
                    }

                    override fun onError(error: FacebookException) {
                        Toast.makeText(applicationContext,error.toString(),Toast.LENGTH_LONG).show()
                    }
                })

        })
    }

    fun LoginFaceBooks(id:String?,email:String? ,name:String?){
            var RegisterViewModel =  ViewModelProvider.NewInstanceFactory().create(
                Register_ViewModel::class.java)
          Loading.Show(this)
            RegisterViewModel.getLoginFacebook(id, email,name,applicationContext).observe(this,
                Observer<Register_Model> { loginmodel ->
                   Loading.Disable()
                    if (loginmodel != null) {

                        val customer_id = loginmodel.data?.token
                        dataSaver.edit().putString("token", customer_id).apply()
                        finish()
                    } else {

                    }
                }
            )


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        callbackManager?.onActivityResult(requestCode, resultCode, data)
        if(requestCode==RequestSignInCode){
//            var googleSignInResult: GoogleSignInResult = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
//            if (googleSignInResult.isSuccess()) {
//                var googleSignInAccount: GoogleSignInAccount = googleSignInResult.signInAccount!!;
//                FirebaseUserAuth(googleSignInAccount);
//            }

        }

    }
    private fun FirebaseUserAuth(acct: GoogleSignInAccount) {

        val credential = GoogleAuthProvider.getCredential(acct.idToken, null)
        mAuth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    val user = mAuth.currentUser
                    LoginFaceBooks(
                        mAuth.currentUser!!.uid,
                        mAuth.currentUser!!.email,
                        mAuth.currentUser!!.displayName
                    )

                } else {
                    // If sign in fails, display a message to the user.
                }
            }
    }


}
