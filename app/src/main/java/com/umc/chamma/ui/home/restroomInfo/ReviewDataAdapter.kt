package com.umc.chamma.ui.home.restroomInfo


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.umc.chamma.databinding.ItemReviewBinding
import com.bumptech.glide.Glide
import com.umc.chamma.R
import com.umc.chamma.ui.home.restroomInfo.model.ReviewData
import com.umc.chamma.ui.home.restroomInfo.model.ReviewData2


class ReviewDataAdapter(private val dataList: List<ReviewData>?) :
    RecyclerView.Adapter<ReviewDataAdapter.DataViewHolder>() {
    inner class DataViewHolder(private val viewBinding: ItemReviewBinding) :
        RecyclerView.ViewHolder(viewBinding.root) {
        private val context = viewBinding.root.context

        fun bind(data: ReviewData) {

            viewBinding.yearmonthdayTv.text = data.dateTime

            viewBinding.userNameIv.text = data.nickname
            viewBinding.contentTv.text = data.reviewContent

            viewBinding.ratingBar.rating = data.rating.toFloat()

            Glide.with(context)
                .load(data.userProfile)
                .error(R.drawable.profile_select_btn)
                .into(viewBinding.circleIv)


        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
        val binding = ItemReviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DataViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        holder.bind(dataList!!.get(position))
    }

    override fun getItemCount(): Int {
        return dataList!!.size
    }


}
