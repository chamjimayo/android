package com.umc.chamma.ui.mypage

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.umc.chamma.databinding.ItemUsageBinding
import com.bumptech.glide.Glide
import java.text.SimpleDateFormat
import java.util.*

class ArticleAdapter: ListAdapter<ArticleModel, ArticleAdapter.ViewHolder>(diffUtil) {

    inner class ViewHolder (private val binding: ItemUsageBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(articleModel: ArticleModel) {
            val format = SimpleDateFormat("MM월dd일")
            val date = Date(articleModel.createdAt)

            binding.tvNameUsageLayout.text = articleModel.title
            binding.tvDateUsageLayout.text = format.format(date).toString()
            if(articleModel.imageUrl.isNotEmpty()){
                Glide.with(binding.ivImageUsageLyout)
                    .load(articleModel.imageUrl)
                    .into(binding.ivImageUsageLyout)
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemUsageBinding.inflate(LayoutInflater.from(parent.context),parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    companion object{
        val diffUtil = object : DiffUtil.ItemCallback<ArticleModel>(){
            override fun areItemsTheSame(oldItem: ArticleModel, newItem: ArticleModel): Boolean {
                return oldItem.createdAt == newItem.createdAt
            }

            override fun areContentsTheSame(oldItem: ArticleModel, newItem: ArticleModel): Boolean {
                return oldItem == newItem
            }
        }
    }

}