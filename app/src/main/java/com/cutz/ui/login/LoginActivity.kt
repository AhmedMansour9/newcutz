package com.cutz.ui.login

import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.cutz.R
import com.cutz.base.BaseActivity
import com.cutz.databinding.LoginFragmentBinding
import com.cutz.ui.bottomnavigate.BottomNavigateActivity
import com.cutz.ui.forgetpassword.ForgetPasswordActivity
import com.cutz.ui.nointernet.NoInternertActivity
import com.cutz.ui.phone.VerifyPhoneActivity
import com.cutz.ui.register.RegisterActivity
import com.cutz.utils.SharedData
import com.cutz.utils.Status
import com.cutz.utils.isConnected
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.GraphRequest
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.auth.api.signin.GoogleSignInResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import dagger.hilt.android.AndroidEntryPoint
import org.greenrobot.eventbus.EventBus
import java.util.*


@AndroidEntryPoint
class LoginActivity : BaseActivity<LoginFragmentBinding>(), LoginNavigator {

    override var idLayoutRes: Int = R.layout.login_fragment
    private val mViewModel: LoginViewModel by viewModels()
    private var data: SharedData? = null
    lateinit var mAuth: FirebaseAuth
    lateinit var googleApiClient: GoogleApiClient
    var RequestSignInCode:Int=7
    lateinit var googleSignInOptions: GoogleSignInOptions
    private var callbackManager: CallbackManager? = null
    var socialid:String?= String()
    var email:String?=String()
    var name:String?=String()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mViewModel.navigator = this
        mAuth = FirebaseAuth.getInstance();
        mViewDataBinding.loginViewModel = mViewModel
        data = SharedData(this)
        setupObserver()
        GoogleSignOpition();
        forgetPassword()
        checkDirections()

    }

    private fun checkDirections() {
         var Lang:String? = data?.getValue(SharedData.ReturnValue.STRING, "Lann")
        if (!Lang.isNullOrEmpty()) {
           if(Lang.equals("ar"))
               mViewDataBinding.EEmailLogin.gravity=Gravity.RIGHT
            mViewDataBinding.EPasswordLogin.gravity=Gravity.RIGHT
        }
    }

    private fun forgetPassword() {
        mViewDataBinding.btnForget.setOnClickListener(){
            val intent=Intent(this, ForgetPasswordActivity::class.java)
            startActivity(intent)
        }


    }

    private fun setupObserver() {
        mViewModel.accountResponse.observe(this, Observer {
            when (it.staus) {
                Status.SUCCESS -> {
                    dismissLoading()
                    data?.putValue("token", it?.data?.data?.accessToken)
                    val intent=Intent(this, BottomNavigateActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(intent)
                    finish()
                }
                Status.LOADING -> {
                    showLoading()
                }
                Status.ERROR -> {
                    dismissLoading()
                   if(it.message.equals("Verifiy Your Account ")){
                       var intent=Intent(this, VerifyPhoneActivity::class.java)
                       intent.putExtra("phone",mViewDataBinding.EEmailLogin.text.toString())
                       intent.putExtra("type","register")
                       startActivity(intent)
                       finish()
                   }else {
                       error(resources.getString(R.string.error),it.message.toString())
                   }
                }
            }
        })
    }
    override fun loginClick() {
        if(!this.isConnected){
            startActivity(Intent(this, NoInternertActivity::class.java))
        }
        mViewModel.login()
    }

    override fun createAccoutClick() {
        startActivity(Intent(this,RegisterActivity::class.java))
        finish()
    }

    override fun forgetPasswordClick() {
    }

    override fun loginGoogleClick() {
        val signInIntent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
        startActivityForResult(signInIntent, RequestSignInCode)

    }

    override fun loginFacebookClick() {
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
                        Toast.makeText(applicationContext,error.toString(), Toast.LENGTH_LONG).show()
                    }
                })



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






    fun LoginFaceBooks(id:String?,email:String? ,name:String?){
        mViewModel.loginSocial(id,email,name)


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        callbackManager?.onActivityResult(requestCode, resultCode, data)
        if(requestCode==RequestSignInCode){
            var googleSignInResult: GoogleSignInResult =
                Auth.GoogleSignInApi.getSignInResultFromIntent(data)!!
            if (googleSignInResult.isSuccess()) {
                var googleSignInAccount: GoogleSignInAccount = googleSignInResult.signInAccount!!;
                FirebaseUserAuth(googleSignInAccount);
            }

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