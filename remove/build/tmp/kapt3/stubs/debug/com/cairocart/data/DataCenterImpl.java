package com.cairocart.data;

import java.lang.System;

@kotlin.Metadata(mv = {1, 4, 0}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000L\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010$\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u001d\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005\u00a2\u0006\u0002\u0010\u0007J\u000e\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005H\u0016J\u001f\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\n2\u0006\u0010\f\u001a\u00020\rH\u0096@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u000eJ\u001f\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00100\n2\u0006\u0010\u0011\u001a\u00020\u0012H\u0096@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0013J+\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00100\n2\u0012\u0010\u0015\u001a\u000e\u0012\u0004\u0012\u00020\r\u0012\u0004\u0012\u00020\r0\u0016H\u0096@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0017J\u001f\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u00100\n2\u0006\u0010\u0019\u001a\u00020\u001aH\u0096@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u001bR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u0006\u001c"}, d2 = {"Lcom/cairocart/data/DataCenterImpl;", "Lcom/cairocart/data/DataCenterManager;", "apiRepository", "Lcom/cairocart/data/remote/repository/ApiRepository;", "dataStore", "Landroidx/datastore/DataStore;", "Landroidx/datastore/preferences/Preferences;", "(Lcom/cairocart/data/remote/repository/ApiRepository;Landroidx/datastore/DataStore;)V", "dataSourcePreference", "getCategories", "Lretrofit2/Response;", "Lcom/cairocart/data/remote/model/Categories_Response;", "language", "", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "loginAccount", "Lcom/cairocart/data/remote/model/AccountResponse;", "loginRequest", "Lcom/cairocart/data/remote/model/LoginRequest;", "(Lcom/cairocart/data/remote/model/LoginRequest;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "loginFacebook", "map", "", "(Ljava/util/Map;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "newAccount", "registerRequest", "Lcom/cairocart/data/remote/model/RegisterRequest;", "(Lcom/cairocart/data/remote/model/RegisterRequest;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "app_debug"})
public final class DataCenterImpl implements com.cairocart.data.DataCenterManager {
    private final com.cairocart.data.remote.repository.ApiRepository apiRepository = null;
    private final androidx.datastore.DataStore<androidx.datastore.preferences.Preferences> dataStore = null;
    
    @org.jetbrains.annotations.Nullable()
    @java.lang.Override()
    public java.lang.Object newAccount(@org.jetbrains.annotations.NotNull()
    com.cairocart.data.remote.model.RegisterRequest registerRequest, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super retrofit2.Response<com.cairocart.data.remote.model.AccountResponse>> p1) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    @java.lang.Override()
    public java.lang.Object loginAccount(@org.jetbrains.annotations.NotNull()
    com.cairocart.data.remote.model.LoginRequest loginRequest, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super retrofit2.Response<com.cairocart.data.remote.model.AccountResponse>> p1) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    @java.lang.Override()
    public java.lang.Object loginFacebook(@org.jetbrains.annotations.NotNull()
    java.util.Map<java.lang.String, java.lang.String> map, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super retrofit2.Response<com.cairocart.data.remote.model.AccountResponse>> p1) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    @java.lang.Override()
    public java.lang.Object getCategories(@org.jetbrains.annotations.NotNull()
    java.lang.String language, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super retrofit2.Response<com.cairocart.data.remote.model.Categories_Response>> p1) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    @java.lang.Override()
    public androidx.datastore.DataStore<androidx.datastore.preferences.Preferences> dataSourcePreference() {
        return null;
    }
    
    @javax.inject.Inject()
    public DataCenterImpl(@org.jetbrains.annotations.NotNull()
    com.cairocart.data.remote.repository.ApiRepository apiRepository, @org.jetbrains.annotations.NotNull()
    androidx.datastore.DataStore<androidx.datastore.preferences.Preferences> dataStore) {
        super();
    }
}