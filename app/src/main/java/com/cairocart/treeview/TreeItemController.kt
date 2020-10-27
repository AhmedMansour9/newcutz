package com.cairocart.treeview

import com.airbnb.epoxy.TypedEpoxyController
import com.cairocart.domain.Category
import kotlinx.coroutines.ExperimentalCoroutinesApi


@ExperimentalCoroutinesApi
class TreeItemController(
    val onClicked: (node: Node<Category>) -> Unit
) : TypedEpoxyController< Node<Category>>() {

    override fun buildModels(root: Node<Category>) {
        buildTreeItemsModels(root.children)
    }

    private fun buildTreeItemsModels(nodes: List<Node<Category>>) {
        nodes.forEach { node ->

            treeItemView {
                id(node.value.id)
                itemData(node.value)
                itemClickedListener { onClicked(node) }
            }
            if (node.value.isExpanded) {
                buildTreeItemsModels(node.children)
            }
        }
    }
}

