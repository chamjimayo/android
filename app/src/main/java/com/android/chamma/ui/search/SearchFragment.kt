package com.android.chamma.ui.search

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.chamma.config.App
import com.android.chamma.R
import com.android.chamma.config.BaseFragmentVB
import com.android.chamma.databinding.FragmentSearchBinding
import com.android.chamma.models.searchmodel.SearchResultData
import com.android.chamma.models.searchmodel.SearchResultResponse
import com.android.chamma.ui.search.adapter.RecentKeywordAdapter
import com.android.chamma.ui.search.adapter.SearchResultAdapter
import com.android.chamma.ui.search.network.RecentKeywordAPI
import com.android.chamma.ui.search.network.SearchAPI
import com.android.chamma.util.Constants.TAG
import com.android.chamma.util.ToastMessageUtil
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchFragment : BaseFragmentVB<FragmentSearchBinding>(FragmentSearchBinding::bind, R.layout.fragment_search) {


    private var keyword = ""

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getRecentKeywordData()

        binding.etSearch.requestFocus()
        binding.etSearch.setOnKeyListener(onEditKeyListener)

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
        App.getRetrofit().create(SearchAPI::class.java)
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
        App.getRetrofit().create(RecentKeywordAPI::class.java)
            .getRecentKeyword().enqueue(object : Callback<SearchResultResponse>{
                override fun onResponse(
                    call: Call<SearchResultResponse>,
                    response: Response<SearchResultResponse>
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
                override fun onFailure(call: Call<SearchResultResponse>, t: Throwable) {
                    Log.d(TAG, "${t.message}")
                }
            })
    }

    private fun recyclerRecentKeyword(data : ArrayList<SearchResultData>){
        val adapter = RecentKeywordAdapter(data)
        binding.recyclerData.adapter = adapter
        binding. recyclerData.layoutManager = LinearLayoutManager(App.context())
    }

    private fun recyclerSearchResult(data : ArrayList<SearchResultData>, keyword : String){
        val adapter = SearchResultAdapter(data,keyword)
        binding.recyclerData.adapter = adapter
        binding. recyclerData.layoutManager = LinearLayoutManager(App.context())
    }

    private val onEditKeyListener = View.OnKeyListener { view, i, keyEvent ->
        if (i == KeyEvent.KEYCODE_ENTER) {
            //검색했을때
            val word = binding.etSearch.text.toString()
            if(word.isBlank()) ToastMessageUtil.showToast(requireContext(),"검색어를 입력해주세요")
            else getSearchData(word)

            val manager = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            manager.hideSoftInputFromWindow(view.windowToken, 0)

            return@OnKeyListener true
        }
        return@OnKeyListener false
    }



}