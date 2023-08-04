package com.android.chamma.ui.toiletlist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.chamma.databinding.LayoutRestroomViewpagerItemBinding
import com.example.weeks6.RestroomPagerViewHolder

class RestroomVPAdapter (private var pageList: ArrayList<Int>) : RecyclerView.Adapter<RestroomPagerViewHolder>(){

/*
    inner class ViewHolder(val binding: LayoutIntroPagerItemBinding):RecyclerView.ViewHolder(binding.root) {
        fun bind(data:PageItem){
            binding.pagerItemImage.setImageResource(data.imageSrc)
            binding.pagerItemBg.setBackgroundResource(data.bgColor)
        }
    }
 */

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RestroomPagerViewHolder {
        return RestroomPagerViewHolder( LayoutRestroomViewpagerItemBinding.inflate(LayoutInflater.from(parent.context),
            parent,false))
    }

    override fun getItemCount(): Int {
        return pageList.size
    }

    override fun onBindViewHolder(holder: RestroomPagerViewHolder, position: Int) {
        holder.bindWithView(pageList[position])
    }

}