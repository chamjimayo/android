package com.umc.chamma.ui.home

import androidx.recyclerview.widget.RecyclerView
import com.umc.chamma.databinding.LayoutRestroomViewpagerItemBinding


class RestroomPagerViewHolder (private val Binding : LayoutRestroomViewpagerItemBinding) : RecyclerView.ViewHolder(Binding.root) {

    private val itemImage = Binding.pagerItemImage


    fun bindWithView(pageItem: Int){
        itemImage.setImageResource(pageItem)
    }

}