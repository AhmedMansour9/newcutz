package com.cairocart.Adapter

import com.cairocart.R
import com.cairocart.domain.Category
import com.cairocart.treeview.Node
import tellh.com.recyclertreeview_lib.LayoutItemType


class Dir (dirName: Node<Category>): LayoutItemType {
    var dirName: Category? = null



    override fun getLayoutId(): Int {
        return R.layout.item_dir
    }

}