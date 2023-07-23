package com.android.chamma.ui.search

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.chamma.App
import com.android.chamma.R
import com.android.chamma.config.BaseFragmentVB
import com.android.chamma.databinding.FragmentSearchBinding
import com.android.chamma.models.searchmodel.RecentKeywordData
import com.android.chamma.models.searchmodel.RecentKeywordResponse
import com.android.chamma.models.searchmodel.SearchResultData
import com.android.chamma.models.searchmodel.SearchResultResponse
import com.android.chamma.ui.search.adapter.RecentKeywordAdapter
import com.android.chamma.ui.search.adapter.SearchResultAdapter
import com.android.chamma.ui.search.network.RecentKeywordAPI
import com.android.chamma.ui.search.network.SearchAPI
import com.android.chamma.util.Constants.TAG
import com.android.chamma.util.RetrofitInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchFragment : BaseFragmentVB<FragmentSearchBinding>(FragmentSearchBinding::bind, R.layout.fragment_search) {


    private var keyword = ""

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.etSearch.requestFocus()

        binding.btnErase.setOnClickListener {
            binding.etSearch.text.clear()
            keyword = ""
        }

        textListener()

    }

    private fun textListener(){
        binding.etSearch.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                keyword = binding.etSearch.text.toString()
                if(keyword.isBlank()) binding.layoutRecentBar.visibility = View.VISIBLE
                else binding.layoutRecentBar.visibility = View.GONE
                getSearchData(keyword)
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun afterTextChanged(p0: Editable?) {}

        })
    }

    private fun getSearchData(keyword : String){
        RetrofitInterface.retrofit.create(SearchAPI::class.java)
            .getSearch(keyword).enqueue(object : Callback<SearchResultResponse> {
                override fun onResponse(
                    call: Call<SearchResultResponse>,
                    response: Response<SearchResultResponse>
                ) {
                    if(response.code() == 200){
                        recyclerSearchResult(response.body()!!.data, keyword)
                    }
                }

                override fun onFailure(call: Call<SearchResultResponse>, t: Throwable) {
                    Log.d(TAG, "${t.message}")
                }
            })
    }

    private fun getRecentKeywordData(){
        RetrofitInterface.retrofit.create(RecentKeywordAPI::class.java)
            .getRecentKeyword().enqueue(object : Callback<RecentKeywordResponse>{
                override fun onResponse(
                    call: Call<RecentKeywordResponse>,
                    response: Response<RecentKeywordResponse>
                ) {
                    if(response.code() == 200){
                        if(response.body()!!.data.isEmpty()){
                            binding.ivNorecentData.visibility = View.VISIBLE
                        }else{
                            binding.ivNorecentData.visibility = View.GONE
                            recyclerRecentKeyword(response.body()!!.data)
                        }
                    }
                }

                override fun onFailure(call: Call<RecentKeywordResponse>, t: Throwable) {
                    Log.d(TAG, "${t.message}")
                }
            })
    }

    private fun recyclerRecentKeyword(data : ArrayList<RecentKeywordData>){
        val adapter = RecentKeywordAdapter(data)
        binding.recyclerData.adapter = adapter
        binding. recyclerData.layoutManager = LinearLayoutManager(App.context())
    }

    private fun recyclerSearchResult(data : ArrayList<SearchResultData>, keyword : String){
        val adapter = SearchResultAdapter(data,keyword)
        binding.recyclerData.adapter = adapter
        binding. recyclerData.layoutManager = LinearLayoutManager(App.context())
    }




}