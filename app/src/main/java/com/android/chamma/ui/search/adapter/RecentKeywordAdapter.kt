package com.android.chamma.ui.search.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.chamma.databinding.ItemSearchRecentsearchBinding
import com.android.chamma.ui.search.model.SearchResultData

class RecentKeywordAdapter(
    private val datas : ArrayList<SearchResultData>,
    private val onItemClickListener: (data : SearchResultData) -> Unit
) : RecyclerView.Adapter<RecentKeywordAdapter.ViewHolder>(){

    inner class ViewHolder(private val binding : ItemSearchRecentsearchBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind(item : SearchResultData){
            binding.tvSearchData.text = item.name
            binding.layout.setOnClickListener {
                onItemClickListener(item)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val viewBinding = ItemSearchRecentsearchBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(viewBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(datas[position])
    }

    override fun getItemCount(): Int {
        return datas.size
    }
}