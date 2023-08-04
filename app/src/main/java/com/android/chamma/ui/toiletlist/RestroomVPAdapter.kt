package com.android.chamma.ui.toiletlist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.chamma.databinding.LayoutRestroomViewpagerItemBinding

class RestroomVPAdapter (private var pageList: ArrayList<Int>) : RecyclerView.Adapter<RestroomPagerViewHolder>(){


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