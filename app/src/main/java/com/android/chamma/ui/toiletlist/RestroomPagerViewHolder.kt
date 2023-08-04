package com.android.chamma.ui.toiletlist

import androidx.recyclerview.widget.RecyclerView
import com.android.chamma.databinding.LayoutRestroomViewpagerItemBinding

class RestroomPagerViewHolder (private val Binding : LayoutRestroomViewpagerItemBinding) : RecyclerView.ViewHolder(Binding.root) {

    private val itemImage = Binding.pagerItemImage


    fun bindWithView(pageItem: Int){
        itemImage.setImageResource(pageItem)
    }

}