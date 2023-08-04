package com.umc.chamma.ui.search

import com.umc.chamma.config.App
import com.umc.chamma.config.BaseResponse
import com.umc.chamma.ui.search.model.SearchResultData
import com.umc.chamma.ui.search.model.SearchResultResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchService(val view : SearchFragmentInterface) {

    private val searchRetro = com.umc.chamma.config.App.getRetro().create(SearchRetrofitInterface::class.java)

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
        searchRetro.postAddressClick(data).enqueue(object : Callback<com.umc.chamma.config.BaseResponse>{
            override fun onResponse(call: Call<com.umc.chamma.config.BaseResponse>, response: Response<com.umc.chamma.config.BaseResponse>) {}
            override fun onFailure(call: Call<com.umc.chamma.config.BaseResponse>, t: Throwable) {}
        })
    }

    fun deleteRecentKeyword(keyword : String){
        searchRetro.deleteRecentKeyword(keyword).enqueue(object : Callback<com.umc.chamma.config.BaseResponse>{
            override fun onResponse(call: Call<com.umc.chamma.config.BaseResponse>, response: Response<com.umc.chamma.config.BaseResponse>) {
                if(response.code() == 200) view.onDeleteRecentSuccess()
                else view.onDeleteRecentFailure()
            }
            override fun onFailure(call: Call<com.umc.chamma.config.BaseResponse>, t: Throwable) {
                view.onDeleteRecentFailure()
            }
        })
    }


    fun deleteAllRecentKeyword(){
        searchRetro.deleteAllRecentKeyword().enqueue(object : Callback<com.umc.chamma.config.BaseResponse>{
            override fun onResponse(call: Call<com.umc.chamma.config.BaseResponse>, response: Response<com.umc.chamma.config.BaseResponse>) {
                if(response.code() == 200) view.onDeleteAllRecentSuccess()
                else view.onDeleteAllRecentFailure()
            }
            override fun onFailure(call: Call<com.umc.chamma.config.BaseResponse>, t: Throwable) {
                view.onDeleteAllRecentFailure()
            }
        })

    }
}