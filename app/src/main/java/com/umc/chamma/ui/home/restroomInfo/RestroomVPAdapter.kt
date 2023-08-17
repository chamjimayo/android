package com.umc.chamma.ui.home.restroomInfo

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.GlideException
import com.umc.chamma.R
import com.umc.chamma.databinding.LayoutRestroomViewpagerItemBinding

class RestroomVPAdapter (private var pageList: ArrayList<String>
,private val activity: RestroomInfoActivity
) : RecyclerView.Adapter<RestroomVPAdapter.MyViewHolder>(){


    class MyViewHolder(
        private val binding: LayoutRestroomViewpagerItemBinding,
        private val activity: RestroomInfoActivity
    ): RecyclerView.ViewHolder(binding.root){
        //val root=activity.context
    fun bindWithView(data:String) {
            Glide.with(binding.root.context).load(data).error(R.drawable.restroom_ex).into(binding.pagerItemImage)

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            LayoutRestroomViewpagerItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent, false),activity
            )

    }

    override fun getItemCount(): Int {
        return pageList.size
    }

    override fun onBindViewHolder(holder: RestroomVPAdapter.MyViewHolder, position: Int) {
        holder.bindWithView(pageList[position])
    }

}