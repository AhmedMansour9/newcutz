package com.hadrmout.ui.login

import com.hadrmout.base.BaseNavigator

interface LoginNavigator : BaseNavigator {

    fun loginClick()

    fun createAccoutClick()

    fun forgetPasswordClick();

    fun loginGoogleClick();

    fun loginFacebookClick()

}