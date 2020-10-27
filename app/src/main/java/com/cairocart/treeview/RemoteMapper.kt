package com.cairocart.treeview

import com.cairocart.Model.Categories_Response
import com.cairocart.domain.Category


fun Categories_Response.Data.toTree(): Node<Category>? {
    val root: Node<Category> = Node(Category(-1, null, false, -1, "", -1, -1, false))
    root.children = toCategories(childrenData) ?: mutableListOf()
    return root
}

 fun toCategories(childrenData: List<Categories_Response.Data.ChildrenData?>?): MutableList<Node<Category>>? {
    return childrenData?.mapNotNullTo(mutableListOf()) { children ->
        val id = children?.id
        val name = children?.name
        if (id == null || name == null)
            null
        else {
            val node: Node<Category> = Node(
                Category(
                    id = id,
                    image = children.image,
                    name = name,
                    isActive = children.isActive ?: false,
                    isExpanded = false,
                    level = children.level ?: 0,
                    productCount = children.productCount ?: 0,
                    parentId = children.parentId ?: 0
                )
            )
            node.children = toCategories(children.childrenData) ?: mutableListOf()
            node
        }

    }
}
