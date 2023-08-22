package com.umc.chamma.ui.mypage.review

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.umc.chamma.R
import com.umc.chamma.config.App
import com.umc.chamma.databinding.ItemReviewBinding
import com.umc.chamma.databinding.ItemReviewMypageBinding
import com.umc.chamma.ui.mypage.review.model.MypageReviewData
import java.util.*

class ReviewAdapter(
    private val datas : ArrayList<MypageReviewData>,
) : RecyclerView.Adapter<ReviewAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding : ItemReviewMypageBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind(item : MypageReviewData){

            // TODO 화장실사진 / 날짜형식 m월 n일 / 화장실 이름
            Glide.with(App.context())
                .load(item.userprofile)
                .error(R.drawable.profile_select_btn)
                .into(binding.ivRestRoomItemReview)

            binding.tvReviewItemReview.text= item.reviewContent
            binding.ratingBar2.rating = item.rating
            binding.tvDate.text = item.dateTime

        }

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val viewBinding = ItemReviewMypageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(viewBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(datas[position])
    }

    override fun getItemCount(): Int {
        return datas.size
    }
}