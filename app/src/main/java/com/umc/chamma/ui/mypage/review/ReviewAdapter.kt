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
import java.text.SimpleDateFormat
import java.util.*

class ReviewAdapter(
    private val datas : ArrayList<MypageReviewData>,
    private val onClickDeleteListener: (reviewId : Int) -> Unit,
    private val onClickPatchListener : (reviewId : Int) -> Unit,
    private val onItemClickListener: (id : Int) -> Unit
) : RecyclerView.Adapter<ReviewAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding : ItemReviewMypageBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind(item : MypageReviewData){


           Glide.with(App.context())
                .load(item.restroomPhotoUrl)
                .error(R.drawable.restroom_ex)
                .into(binding.ivRestRoomItemReview)

            binding.tvReviewItemReview.text= item.reviewContent
            binding.ratingBar2.rating = item.rating
            binding.tvDate.text = item.dateTime
            binding.tvTitleItemReview.text = item.restroomName

            binding.btnDeleteItemReview.setOnClickListener { onClickDeleteListener(item.reviewId) }
            binding.btnModifyItemReview.setOnClickListener { onClickPatchListener(item.reviewId) }
            binding.root.setOnClickListener {
                onItemClickListener(item.restroomId!!)
            }
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