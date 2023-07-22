package com.android.chamma.ui.search.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.chamma.databinding.ItemSearchResultBinding
import com.android.chamma.models.searchmodel.SearchResultData

class SearchResultAdapter(val datas : ArrayList<SearchResultData>, val keyword : String) : RecyclerView.Adapter<SearchResultAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding : ItemSearchResultBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind(item : SearchResultData){
            var text = item.name
            val arr = parseText(text)
            binding.tvNameBefore.text = arr[0]
            binding.tvNameHilight.text = arr[1]
            binding.tvNameAfter.text = arr[2]
            binding.tvAddress.text = item.roadAddress

            binding.layout.setOnClickListener {

            }
        }

    }

    private fun parseText(text : String) : ArrayList<String>{
        val result = arrayListOf<String>()
        val start = text.indexOf(keyword)
        val end = text.lastIndexOf(keyword)
        if(keyword in text){
            result.add(text.substring(0,start))
            result.add(keyword)
            result.add(text.substring(end+1))
            return result
        }else{
            result.add(text)
            result.add("")
            result.add("")
            return result
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val viewBinding = ItemSearchResultBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(viewBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(datas[position])
    }

    override fun getItemCount(): Int {
        return datas.size
    }
}