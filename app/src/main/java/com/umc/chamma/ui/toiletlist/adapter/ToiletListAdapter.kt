package com.umc.chamma.ui.toiletlist.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.umc.chamma.config.App
import com.umc.chamma.databinding.ItemToiletListBinding
import com.umc.chamma.ui.home.model.NearToiletData
import com.umc.chamma.ui.search.model.SearchResultData

class ToiletListAdapter(
    private val datas : ArrayList<NearToiletData>,
    private val onItemClickListener: (id : Int) -> Unit
) : RecyclerView.Adapter<ToiletListAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding : ItemToiletListBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind(item : NearToiletData){
            Glide.with(App.context())
                .load(item.restroomPhoto)
                .into(binding.ivThumnail)

            binding.tvName.text = item.restroomName
            binding.tvRating.text = item.reviewRating.toString()
            binding.tvDistance.text = "${item.distance?.toInt()}m"

            binding.root.setOnClickListener {
                onItemClickListener(item.restroomId!!)
            }
        }

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val viewBinding = ItemToiletListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(viewBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(datas[position])
    }

    override fun getItemCount(): Int {
        return datas.size
    }
}