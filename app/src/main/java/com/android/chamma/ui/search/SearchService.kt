package com.android.chamma.ui.search

import com.android.chamma.config.App
import com.android.chamma.config.BaseResponse
import com.android.chamma.ui.search.model.SearchResultData
import com.android.chamma.ui.search.model.SearchResultResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchService(val view : SearchFragmentInterface) {

    private val searchRetro = App.getRetro().create(SearchRetrofitInterface::class.java)

    fun getSearch(keyword : String){
        searchRetro.getSearch(keyword).enqueue(object : Callback<SearchResultResponse>{
            override fun onResponse(
                call: Call<SearchResultResponse>,
                response: Response<SearchResultResponse>
            ) {
                response.body()?.let{
                    if(response.code() == 200) view.onGetSearchSuccess(it.data, keyword)
                    else view.onGetSearchFailure("API 오류입니다")
                }
            }

            override fun onFailure(call: Call<SearchResultResponse>, t: Throwable) {
                view.onGetSearchFailure("네트워크 연결 실패")
            }
        })
    }

    fun getRecentKeyword(){
        searchRetro.getRecentKeyword().enqueue(object : Callback<SearchResultResponse>{
            override fun onResponse(
                call: Call<SearchResultResponse>,
                response: Response<SearchResultResponse>
            ) {
                response.body()?.let{
                    if(response.code() == 200) view.onGetRecentKeywordSuccess(it.data)
                    else view.onGetRecentKeywordFailure("API 오류입니다")
                }
            }

            override fun onFailure(call: Call<SearchResultResponse>, t: Throwable) {
                view.onGetRecentKeywordFailure("네트워크 연결 실패")
            }
        })
    }

    fun postAddressClick(data : SearchResultData){
        searchRetro.postAddressClick(data).enqueue(object : Callback<BaseResponse>{
            override fun onResponse(call: Call<BaseResponse>, response: Response<BaseResponse>) {
                TODO("Not yet implemented")
            }

            override fun onFailure(call: Call<BaseResponse>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })

    }
}