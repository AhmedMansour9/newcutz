package com.cairocart.data;

import java.lang.System;

@kotlin.Metadata(mv = {1, 4, 0}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000D\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010$\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\bf\u0018\u00002\u00020\u0001J\u000e\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003H&J\u001f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u00062\u0006\u0010\b\u001a\u00020\tH\u00a6@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\nJ\u001f\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\f0\u00062\u0006\u0010\r\u001a\u00020\u000eH\u00a6@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u000fJ+\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\f0\u00062\u0012\u0010\u0011\u001a\u000e\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\t0\u0012H\u00a6@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0013J\u001f\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\f0\u00062\u0006\u0010\u0015\u001a\u00020\u0016H\u00a6@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0017\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u0006\u0018"}, d2 = {"Lcom/cairocart/data/DataCenterManager;", "", "dataSourcePreference", "Landroidx/datastore/DataStore;", "Landroidx/datastore/preferences/Preferences;", "getCategories", "Lretrofit2/Response;", "Lcom/cairocart/data/remote/model/Categories_Response;", "language", "", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "loginAccount", "Lcom/cairocart/data/remote/model/AccountResponse;", "loginRequest", "Lcom/cairocart/data/remote/model/LoginRequest;", "(Lcom/cairocart/data/remote/model/LoginRequest;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "loginFacebook", "map", "", "(Ljava/util/Map;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "newAccount", "registerRequest", "Lcom/cairocart/data/remote/model/RegisterRequest;", "(Lcom/cairocart/data/remote/model/RegisterRequest;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "app_debug"})
public abstract interface DataCenterManager {
    
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object newAccount(@org.jetbrains.annotations.NotNull()
    com.cairocart.data.remote.model.RegisterRequest registerRequest, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super retrofit2.Response<com.cairocart.data.remote.model.AccountResponse>> p1);
    
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object loginAccount(@org.jetbrains.annotations.NotNull()
    com.cairocart.data.remote.model.LoginRequest loginRequest, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super retrofit2.Response<com.cairocart.data.remote.model.AccountResponse>> p1);
    
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object loginFacebook(@org.jetbrains.annotations.NotNull()
    java.util.Map<java.lang.String, java.lang.String> map, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super retrofit2.Response<com.cairocart.data.remote.model.AccountResponse>> p1);
    
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getCategories(@org.jetbrains.annotations.NotNull()
    java.lang.String language, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super retrofit2.Response<com.cairocart.data.remote.model.Categories_Response>> p1);
    
    @org.jetbrains.annotations.NotNull()
    public abstract androidx.datastore.DataStore<androidx.datastore.preferences.Preferences> dataSourcePreference();
}