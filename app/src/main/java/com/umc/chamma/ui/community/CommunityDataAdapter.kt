package com.umc.chamma.ui.community


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.umc.chamma.databinding.ItemReviewBinding
import com.bumptech.glide.Glide
import com.umc.chamma.R
import com.umc.chamma.databinding.ItemCommunityBinding
import com.umc.chamma.ui.home.restroomInfo.model.ReviewData
import com.umc.chamma.ui.home.restroomInfo.model.ReviewData2


class CommunityDataAdapter(private val dataList: List<CommunityData>?) :
    RecyclerView.Adapter<CommunityDataAdapter.DataViewHolder>() {
    inner class DataViewHolder(private val viewBinding: ItemCommunityBinding) :
        RecyclerView.ViewHolder(viewBinding.root) {
        private val context = viewBinding.root.context

        fun bind(data: CommunityData) {

            viewBinding.contentTv.text=data.content
            viewBinding.personNumTv.text=data.personNum.toString()
            viewBinding.commentNumTv.text=data.commentNum.toString()

            Glide.with(context)
                .load(data.url1)
                .error(R.drawable.profile_select_btn)
                .into(viewBinding.communitylistImg)

            Glide.with(context)
                .load(data.url2)
                .error(R.drawable.profile_select_btn)
                .into(viewBinding.communitylistImg2)
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
        val binding = ItemCommunityBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DataViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        holder.bind(dataList!!.get(position))
    }

    override fun getItemCount(): Int {
        return dataList!!.size
    }


}
