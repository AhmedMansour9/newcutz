package com.cairocart.ui.login;

import java.lang.System;

@kotlin.Metadata(mv = {1, 4, 0}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u000f\b\u0007\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u00a2\u0006\u0002\u0010\u0005J\u0006\u0010\u0014\u001a\u00020\u0015R\u001a\u0010\u0006\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\t0\b0\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001d\u0010\n\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\t0\b0\u000b8F\u00a2\u0006\u0006\u001a\u0004\b\f\u0010\rR\u001a\u0010\u000e\u001a\u00020\u000fX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0010\u0010\u0011\"\u0004\b\u0012\u0010\u0013\u00a8\u0006\u0016"}, d2 = {"Lcom/cairocart/ui/login/LoginViewModel;", "Lcom/cairocart/base/BaseViewModel;", "Lcom/cairocart/ui/login/LoginNavigator;", "dataCenterManager", "Lcom/cairocart/data/DataCenterManager;", "(Lcom/cairocart/data/DataCenterManager;)V", "_accountResponse", "Landroidx/lifecycle/MutableLiveData;", "Lcom/cairocart/utils/Resource;", "Lcom/cairocart/data/remote/model/AccountResponse;", "accountResponse", "Landroidx/lifecycle/LiveData;", "getAccountResponse", "()Landroidx/lifecycle/LiveData;", "loginRequest", "Lcom/cairocart/data/remote/model/LoginRequest;", "getLoginRequest", "()Lcom/cairocart/data/remote/model/LoginRequest;", "setLoginRequest", "(Lcom/cairocart/data/remote/model/LoginRequest;)V", "login", "", "app_debug"})
public final class LoginViewModel extends com.cairocart.base.BaseViewModel<com.cairocart.ui.login.LoginNavigator> {
    @org.jetbrains.annotations.NotNull()
    private com.cairocart.data.remote.model.LoginRequest loginRequest;
    private final androidx.lifecycle.MutableLiveData<com.cairocart.utils.Resource<com.cairocart.data.remote.model.AccountResponse>> _accountResponse = null;
    
    @org.jetbrains.annotations.NotNull()
    public final com.cairocart.data.remote.model.LoginRequest getLoginRequest() {
        return null;
    }
    
    public final void setLoginRequest(@org.jetbrains.annotations.NotNull()
    com.cairocart.data.remote.model.LoginRequest p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.lifecycle.LiveData<com.cairocart.utils.Resource<com.cairocart.data.remote.model.AccountResponse>> getAccountResponse() {
        return null;
    }
    
    public final void login() {
    }
    
    @androidx.hilt.lifecycle.ViewModelInject()
    public LoginViewModel(@org.jetbrains.annotations.NotNull()
    com.cairocart.data.DataCenterManager dataCenterManager) {
        super(null);
    }
}