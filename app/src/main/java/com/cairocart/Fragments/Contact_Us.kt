package com.cairocart.Fragments


import android.content.Context
import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.cairocart.Activities.NavigationDrawer
import com.cairocart.Model.ContactUs_Response
import com.cairocart.R
import com.cairocart.ViewModel.ContactUs_ViewModel
import kotlinx.android.synthetic.main.fragment_contact__us.view.*

/**
 * A simple [Fragment] subclass.
 */
class Contact_Us : Fragment() {
    lateinit var contactUs_viewModel: ContactUs_ViewModel
    var toolbarhome: Toolbar? = null

    lateinit var root: View
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_contact__us, container, false)
        contactUs_viewModel = ViewModelProvider.NewInstanceFactory().create(
            ContactUs_ViewModel::class.java
        )
//        init()
        Contact()

        return root
    }
    fun init() {
        toolbarhome=root.findViewById(R.id.toolbarContact)

        val toggle = ActionBarDrawerToggle(
            activity,
            NavigationDrawer.drawerLayout,
            toolbarhome,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        toggle.apply {
            syncState()
            isDrawerIndicatorEnabled = false
            setHomeAsUpIndicator(R.drawable.ic_homemenu)
            setToolbarNavigationClickListener { NavigationDrawer.drawerLayout!!.openDrawer(GravityCompat.START) }
        }
        NavigationDrawer.drawerLayout?.addDrawerListener(toggle)

    }


    fun Contact() {

        root.SendMesg.setOnClickListener(View.OnClickListener { view ->
            if (!ValidateEmail() or !ValidateMessage()or !ValidateName()or !ValidatePhone()) {
                return@OnClickListener
            }
            val firstName: String = root.E_Email.text.toString()
            val Email = firstName.replace("\\s".toRegex(), "")

            if (root.E_Name.getText().toString() != ""
                && root.E_Phone.getText().toString() != ""
                && root.E_Message.getText().toString() != "" && Email != ""
            ) {

                root.SendMesg.hideKeyboard()
                root.SendMesg.setEnabled(false)
                root.progross.visibility=View.VISIBLE
                contactUs_viewModel.getContactus(
                    Email,
                    root.E_Name.getText().toString(),
                    root.E_Phone.getText().toString(),
                    root.E_Message.getText().toString(),
                    requireContext().applicationContext
                ).observe(viewLifecycleOwner, Observer<ContactUs_Response> { tripsData ->
                    root.SendMesg.setEnabled(true)
                    root.progross.visibility=View.GONE
                    if (tripsData != null) {
                        root.E_Email.setText(null)
                        root.E_Name.setText(null)
                        root.E_Phone.setText(null)
                        root.E_Message.setText(null)
                        Toast.makeText(
                            context,
                            resources.getString(R.string.sendsuccess),
                            Toast.LENGTH_SHORT
                        ).show()

                    } else {

                        Toast.makeText(
                            context,
                            resources.getString(R.string.failedmsg),
                            Toast.LENGTH_SHORT
                        ).show()

                    }
                })

            }
        })


    }

    private fun ValidateName(): Boolean {
        val EMAIL = root.E_Name.getText().toString().trim({ it <= ' ' })
        if (EMAIL.isEmpty()) {
            root.E_Name.error="Please Insert Name"
            return false
        }
        return true
    }
    fun View.hideKeyboard() {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(windowToken, 0)
    }
    private fun ValidatePhone(): Boolean {
        val EMAIL = root.E_Phone.getText().toString().trim({ it <= ' ' })
        if (EMAIL.isEmpty() ) {
            root.E_Phone.error="Please Insert Phone"
            return false
        }
        return true
    }
    private fun ValidateMessage(): Boolean {
        val EMAIL = root.E_Message.getText().toString().trim({ it <= ' ' })
        if (EMAIL.isEmpty() ) {
            root.E_Message.error="Please Message Phone"
            return false
        }
        return true
    }
    private fun ValidateEmail(): Boolean {
        val EMAIL = root.E_Email.getText().toString().trim({ it <= ' ' })
        val firstName: String = root.E_Email.text.toString()
        val Email = firstName.replace("\\s".toRegex(), "")

        if (Email.isEmpty() || !isValidEmail(Email)) {
            root.E_Email.error="Please Insert Email"
            //            Toast.makeText(getContext(), ""+getResources().getString(R.string.insertemail), Toast.LENGTH_SHORT).show();
            return false
        } else if (!root.E_Email.getText().toString().matches("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+".toRegex())) {
            root.E_Email.error="Please Insert Email"
            //            Toast.makeText(getContext(), ""+getResources().getString(R.string.insertemail), Toast.LENGTH_SHORT).show();
            return false
        }
        return true
    }

    private fun isValidEmail(email: String): Boolean {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }


}
