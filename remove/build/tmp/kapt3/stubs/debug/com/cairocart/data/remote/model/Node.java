package com.cairocart.data.remote.model;

import java.lang.System;

@kotlin.Metadata(mv = {1, 4, 0}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010!\n\u0002\b\u000e\n\u0002\u0010\u0002\n\u0002\b\u0002\u0018\u0000*\u0004\b\u0000\u0010\u00012\u00020\u0002B\r\u0012\u0006\u0010\u0003\u001a\u00028\u0000\u00a2\u0006\u0002\u0010\u0004J\u0014\u0010\u0014\u001a\u00020\u00152\f\u0010\u0016\u001a\b\u0012\u0004\u0012\u00028\u00000\u0000R&\u0010\u0005\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00000\u00000\u0006X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0007\u0010\b\"\u0004\b\t\u0010\nR\"\u0010\u000b\u001a\n\u0012\u0004\u0012\u00028\u0000\u0018\u00010\u0000X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\f\u0010\r\"\u0004\b\u000e\u0010\u000fR\u001c\u0010\u0003\u001a\u00028\u0000X\u0086\u000e\u00a2\u0006\u0010\n\u0002\u0010\u0013\u001a\u0004\b\u0010\u0010\u0011\"\u0004\b\u0012\u0010\u0004\u00a8\u0006\u0017"}, d2 = {"Lcom/cairocart/data/remote/model/Node;", "T", "", "value", "(Ljava/lang/Object;)V", "children", "", "getChildren", "()Ljava/util/List;", "setChildren", "(Ljava/util/List;)V", "parent", "getParent", "()Lcom/cairocart/data/remote/model/Node;", "setParent", "(Lcom/cairocart/data/remote/model/Node;)V", "getValue", "()Ljava/lang/Object;", "setValue", "Ljava/lang/Object;", "addChild", "", "node", "app_debug"})
public final class Node<T extends java.lang.Object> {
    @org.jetbrains.annotations.Nullable()
    private com.cairocart.data.remote.model.Node<T> parent;
    @org.jetbrains.annotations.NotNull()
    private java.util.List<com.cairocart.data.remote.model.Node<T>> children;
    private T value;
    
    @org.jetbrains.annotations.Nullable()
    public final com.cairocart.data.remote.model.Node<T> getParent() {
        return null;
    }
    
    public final void setParent(@org.jetbrains.annotations.Nullable()
    com.cairocart.data.remote.model.Node<T> p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.cairocart.data.remote.model.Node<T>> getChildren() {
        return null;
    }
    
    public final void setChildren(@org.jetbrains.annotations.NotNull()
    java.util.List<com.cairocart.data.remote.model.Node<T>> p0) {
    }
    
    public final void addChild(@org.jetbrains.annotations.NotNull()
    com.cairocart.data.remote.model.Node<T> node) {
    }
    
    public final T getValue() {
        return null;
    }
    
    public final void setValue(T p0) {
    }
    
    public Node(T value) {
        super();
    }
}