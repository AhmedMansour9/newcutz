package com.cutzegypt.ui.login

import com.cutzegypt.base.BaseNavigator

interface LoginNavigator : BaseNavigator {

    fun loginClick()

    fun createAccoutClick()

    fun forgetPasswordClick();

    fun loginGoogleClick();

    fun loginFacebookClick()

}