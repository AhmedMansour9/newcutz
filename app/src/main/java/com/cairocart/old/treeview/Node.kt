package com.cairocart.old.treeview

class Node<T>(var value: T) {
    var parent: Node<T>? = null
    var children: MutableList<Node<T>> = mutableListOf()

    fun addChild(node: Node<T>) {
        children.add(node)
        node.parent = this
    }
}