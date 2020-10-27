package com.cairocart.Adapter

import android.view.View

import android.widget.TextView
import com.cairocart.R
import tellh.com.recyclertreeview_lib.TreeNode

import tellh.com.recyclertreeview_lib.TreeViewBinder
import java.io.File


class FileNodeBinder :TreeViewBinder<FileNodeBinder.ViewHolder>(){

    override fun provideViewHolder(itemView: View): ViewHolder? {
        return ViewHolder(itemView)
    }

    override fun bindView(holder: ViewHolder, position: Int, node: TreeNode<*>) {
//        val fileNode: File = node.getContent() as File
//        holder.tvName.setText(fileNode.)

    }

    override fun getLayoutId(): Int {
        return R.layout.item_file
    }

    class ViewHolder(rootView: View) : TreeViewBinder.ViewHolder(rootView) {
        var tvName: TextView

        init {
            tvName = rootView.findViewById(R.id.tv_name)
        }
    }

}