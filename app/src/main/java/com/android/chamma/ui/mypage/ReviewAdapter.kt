package com.android.chamma.ui.mypage

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.android.chamma.databinding.ItemReviewMypageBinding
import com.bumptech.glide.Glide
import java.text.SimpleDateFormat
import java.util.*

class ReviewAdapter: ListAdapter<ListReview, ReviewAdapter.ViewHolder>(diffUtil) {

    inner class ViewHolder (private val binding: ItemReviewMypageBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(listReview: ListReview) {
            val format = SimpleDateFormat("MM월dd일")
            val date = Date(listReview.date)

            binding.tvTitleItemReview.text = listReview.title
            binding.tvDateItemReview.text = format.format(date).toString()
            if(listReview.URl.isNotEmpty()){
                Glide.with(binding.ivRestRoomItemReview)
                    .load(listReview.URl)
                    .into(binding.ivRestRoomItemReview)
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemReviewMypageBinding.inflate(LayoutInflater.from(parent.context),parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    companion object{
        val diffUtil = object : DiffUtil.ItemCallback<ListReview>(){
            override fun areItemsTheSame(oldItem: ListReview, newItem: ListReview): Boolean {
                return oldItem.date == newItem.date
            }

            override fun areContentsTheSame(oldItem: ListReview, newItem: ListReview): Boolean {
                return oldItem == newItem
            }
        }
    }
}