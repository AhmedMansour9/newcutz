package com.cairocart.base;

import java.lang.System;

@kotlin.Metadata(mv = {1, 4, 0}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000L\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0011\n\u0002\b\u0005\b&\u0018\u0000*\b\b\u0000\u0010\u0001*\u00020\u00022\u00020\u0003B\u0005\u00a2\u0006\u0002\u0010\u0004J\u0006\u0010\u0018\u001a\u00020\u0019J\u0010\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u001c\u001a\u00020\u001dH\u0007J\u0012\u0010\u001e\u001a\u00020\u00192\b\u0010\u001f\u001a\u0004\u0018\u00010 H\u0014J#\u0010!\u001a\u00020\u00192\f\u0010\"\u001a\b\u0012\u0004\u0012\u00020\u001d0#2\u0006\u0010$\u001a\u00020\u0006H\u0007\u00a2\u0006\u0002\u0010%J\b\u0010&\u001a\u00020\u0019H\u0007J\u0006\u0010\'\u001a\u00020\u0019R\u001a\u0010\u0005\u001a\u00020\u0006X\u0096\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0007\u0010\b\"\u0004\b\t\u0010\nR\u0010\u0010\u000b\u001a\u0004\u0018\u00010\fX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\r\u001a\u0004\u0018\u00010\u000eX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0018\u0010\u000f\u001a\u00020\u0006X\u00a6\u000e\u00a2\u0006\f\u001a\u0004\b\u0010\u0010\b\"\u0004\b\u0011\u0010\nR\u001c\u0010\u0012\u001a\u00028\u0000X\u0086.\u00a2\u0006\u0010\n\u0002\u0010\u0017\u001a\u0004\b\u0013\u0010\u0014\"\u0004\b\u0015\u0010\u0016\u00a8\u0006("}, d2 = {"Lcom/cairocart/base/BaseActivity;", "T", "Landroidx/databinding/ViewDataBinding;", "Landroidx/appcompat/app/AppCompatActivity;", "()V", "colorStatusBar", "", "getColorStatusBar", "()I", "setColorStatusBar", "(I)V", "dailog", "Landroid/app/Dialog;", "foldingCube", "Lcom/github/ybq/android/spinkit/style/FoldingCube;", "idLayoutRes", "getIdLayoutRes", "setIdLayoutRes", "mViewDataBinding", "getMViewDataBinding", "()Landroidx/databinding/ViewDataBinding;", "setMViewDataBinding", "(Landroidx/databinding/ViewDataBinding;)V", "Landroidx/databinding/ViewDataBinding;", "dismissLoading", "", "hasPermission", "", "permission", "", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "requestPermissionSafely", "permissions", "", "requestCode", "([Ljava/lang/String;I)V", "setupLoading", "showLoading", "app_debug"})
public abstract class BaseActivity<T extends androidx.databinding.ViewDataBinding> extends androidx.appcompat.app.AppCompatActivity {
    private int colorStatusBar = 0;
    @org.jetbrains.annotations.NotNull()
    public T mViewDataBinding;
    private android.app.Dialog dailog;
    private com.github.ybq.android.spinkit.style.FoldingCube foldingCube;
    private java.util.HashMap _$_findViewCache;
    
    public abstract int getIdLayoutRes();
    
    public abstract void setIdLayoutRes(int p0);
    
    public int getColorStatusBar() {
        return 0;
    }
    
    public void setColorStatusBar(int p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final T getMViewDataBinding() {
        return null;
    }
    
    public final void setMViewDataBinding(@org.jetbrains.annotations.NotNull()
    T p0) {
    }
    
    @java.lang.Override()
    protected void onCreate(@org.jetbrains.annotations.Nullable()
    android.os.Bundle savedInstanceState) {
    }
    
    @android.annotation.TargetApi(value = android.os.Build.VERSION_CODES.M)
    public final boolean hasPermission(@org.jetbrains.annotations.NotNull()
    java.lang.String permission) {
        return false;
    }
    
    @android.annotation.TargetApi(value = android.os.Build.VERSION_CODES.M)
    public final void requestPermissionSafely(@org.jetbrains.annotations.NotNull()
    java.lang.String[] permissions, int requestCode) {
    }
    
    @android.annotation.SuppressLint(value = {"InflateParams"})
    public final void setupLoading() {
    }
    
    public final void showLoading() {
    }
    
    public final void dismissLoading() {
    }
    
    public BaseActivity() {
        super();
    }
}