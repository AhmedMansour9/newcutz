package com.cairocart.base;

import java.lang.System;

@kotlin.Metadata(mv = {1, 4, 0}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\n\b&\u0018\u0000*\u0004\b\u0000\u0010\u00012\u00020\u0002B\r\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u00a2\u0006\u0002\u0010\u0005R\u0014\u0010\u0003\u001a\u00020\u0004X\u0084\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u001e\u0010\b\u001a\u0004\u0018\u00018\u0000X\u0086\u000e\u00a2\u0006\u0010\n\u0002\u0010\r\u001a\u0004\b\t\u0010\n\"\u0004\b\u000b\u0010\f\u00a8\u0006\u000e"}, d2 = {"Lcom/cairocart/base/BaseViewModel;", "BaseNavigator", "Landroidx/lifecycle/ViewModel;", "dataCenterManager", "Lcom/cairocart/data/DataCenterManager;", "(Lcom/cairocart/data/DataCenterManager;)V", "getDataCenterManager", "()Lcom/cairocart/data/DataCenterManager;", "navigator", "getNavigator", "()Ljava/lang/Object;", "setNavigator", "(Ljava/lang/Object;)V", "Ljava/lang/Object;", "app_debug"})
public abstract class BaseViewModel<BaseNavigator extends java.lang.Object> extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.Nullable()
    private BaseNavigator navigator;
    @org.jetbrains.annotations.NotNull()
    private final com.cairocart.data.DataCenterManager dataCenterManager = null;
    
    @org.jetbrains.annotations.Nullable()
    public final BaseNavigator getNavigator() {
        return null;
    }
    
    public final void setNavigator(@org.jetbrains.annotations.Nullable()
    BaseNavigator p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    protected final com.cairocart.data.DataCenterManager getDataCenterManager() {
        return null;
    }
    
    public BaseViewModel(@org.jetbrains.annotations.NotNull()
    com.cairocart.data.DataCenterManager dataCenterManager) {
        super();
    }
}