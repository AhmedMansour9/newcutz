package com.cutz.ui.login

import com.cutz.base.BaseNavigator

interface LoginNavigator : BaseNavigator {

    fun loginClick()

    fun createAccoutClick()

    fun forgetPasswordClick();

    fun loginGoogleClick();

    fun loginFacebookClick()

}