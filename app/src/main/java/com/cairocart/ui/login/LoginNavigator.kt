package com.cairocart.ui.login

import com.cairocart.base.BaseNavigator

interface LoginNavigator : BaseNavigator {

    fun loginClick()

    fun createAccoutClick()

    fun forgetPasswordClick();

    fun loginGoogleClick();

    fun loginFacebookClick()

}