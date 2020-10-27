package com.cairocart.Adapter

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.cairocart.R
import tellh.com.recyclertreeview_lib.TreeNode
import tellh.com.recyclertreeview_lib.TreeViewBinder


class DirectoryNodeBinder : TreeViewBinder<DirectoryNodeBinder.ViewHolder>() {

    override fun provideViewHolder(itemView: View): ViewHolder? {
        return ViewHolder(itemView)
    }



    override fun getLayoutId(): Int {
        return R.layout.item_dir
    }

         class ViewHolder(rootView: View) : TreeViewBinder.ViewHolder(rootView) {
             var ivArrow: ImageView
              var tvName: TextView



             init {
                 ivArrow = rootView.findViewById(R.id.iv_arrow) as ImageView
                 tvName = rootView.findViewById(R.id.tv_name)
             }
         }

    override fun bindView(holder: ViewHolder, p1: Int, node: TreeNode<*>) {
            holder.ivArrow.setRotation(0f)
            holder.ivArrow.setImageResource(R.drawable.ic_keyboard_arrow_right_black_18dp)
            val rotateDegree = if (node.isExpand()) 90f else 0f
            holder.ivArrow.setRotation(rotateDegree)
            val dirNode = node.getContent() as Dir
            holder.tvName.setText(dirNode.dirName?.name)
            if (node.isLeaf()) holder.ivArrow.setVisibility(View.INVISIBLE) else holder?.ivArrow?.setVisibility(
                View.VISIBLE
            )

    }

}