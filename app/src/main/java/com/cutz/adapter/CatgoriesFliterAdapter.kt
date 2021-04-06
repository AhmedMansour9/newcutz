package com.cutz.adapter

import com.cutz.databinding.RowCategoryfilterBinding

class CatgoriesFliterAdapter{
//    (var categoryData: CategoryItemListener) : RecyclerView.Adapter<CatgoriesFliterAdapter.DeveloperViewHolder>() {
//
//    private var mListModel: MutableList<CategoriesResponse.Data.Category>? = arrayListOf()
//
//     var lastSelectedPosition = -1
//
//
//    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): DeveloperViewHolder {
//        val mTradersResponse = DataBindingUtil.inflate<RowCategoryfilterBinding>(
//            LayoutInflater.from(viewGroup.context),
//            R.layout.row_categoryfilter, viewGroup, false
//        )
//
//        return DeveloperViewHolder(mTradersResponse)
//    }
//
//    override fun onBindViewHolder(mDeveloperViewHolder: DeveloperViewHolder, i: Int) {
//        val currentStudent = mListModel?.get(i)
//        mDeveloperViewHolder.mListMo.model = currentStudent
//        mDeveloperViewHolder.onBind(i)
//
//
//
//
//    }
//
//
//    override fun getItemCount(): Int {
//        return if (mListModel != null) {
//            mListModel!!.size
//        } else {
//            0
//        }
//    }
//
//    fun setList(mDeveloperModel: MutableList<CategoriesResponse.DataCategory.ChildrenDataa>) {
//        this.mListModel = mDeveloperModel
//        notifyDataSetChanged()
//    }
//
//    inner class DeveloperViewHolder(var mListMo: RowCategoryfilterBinding) :
//        RecyclerView.ViewHolder(mListMo.root){
//
//        fun onBind(position: Int) {
//            if(lastSelectedPosition==position){
//                itemView.Frame.visibility= View.VISIBLE
//                itemView.RadioButton.setChecked(true);
//            }else {
//                itemView.Frame.visibility= View.GONE
//                itemView.RadioButton.setChecked(false);
//            }
//
//            itemView.setOnClickListener(){
//                lastSelectedPosition = position
//                run {
//                    categoryData.itemClicked(mListModel?.get(position)!!)
//                }
//                notifyDataSetChanged();
//            }
//
//
//        }
//
//    }
//
//    interface CategoryItemListener {
//        fun itemClicked(productData: CategoriesResponse.DataCategory.ChildrenDataa);
//
//    }

}