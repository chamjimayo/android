package com.android.chamma.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.chamma.databinding.ItemReviewBinding
import com.android.chamma.ui.home.model.ReviewData
import com.bumptech.glide.Glide


class ReviewDataAdapter (private val dataList:ArrayList<ReviewData>?):
    RecyclerView.Adapter<ReviewDataAdapter.DataViewHolder>() {
    inner class DataViewHolder(private val viewBinding: ItemReviewBinding) : RecyclerView.ViewHolder(viewBinding.root) {
        private val context = viewBinding.root.context

        fun bind(data: ReviewData) {


            viewBinding.yearTv.text = data.year.toString()
            viewBinding.monthTv.text = data.month.toString()
            viewBinding.dayTv.text = data.day.toString()

            viewBinding.contentTv.text = data.content

            viewBinding.ratingBar.rating=data.starNum

            Glide.with(context)
                .load(data.url)
                .into(viewBinding.circleIv)


        } }




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
