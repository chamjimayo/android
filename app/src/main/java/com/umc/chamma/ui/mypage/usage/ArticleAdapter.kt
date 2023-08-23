package com.umc.chamma.ui.mypage.usage

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.umc.chamma.R
import com.umc.chamma.config.App
import com.umc.chamma.databinding.ItemUsageBinding
import com.umc.chamma.ui.mypage.usage.model.Content

import com.umc.chamma.ui.mypage.usage.model.MypageUsageData
import java.text.SimpleDateFormat
import java.util.*

class ArticleAdapter(
    private val datas : ArrayList<Content>,
) : RecyclerView.Adapter<ArticleAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding : ItemUsageBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind(item : Content){

            // TODO 화장실사진 / 날짜형식 m월 n일 / 화장실 이름
            Glide.with(App.context())
                .load(item.restroomImageUrl[0])
                .error(R.drawable.profile_select_btn)
                .into(binding.ivImageUsageLyout)

            binding.textView12.text= item.point.toString()
//            binding.ratingBar2.rating = item.rating
            binding.tvDateUsageLayout.text = item.operatingHour

        }

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val viewBinding = ItemUsageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(viewBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(datas[position])
    }

    override fun getItemCount(): Int {
        return datas.size
    }
}