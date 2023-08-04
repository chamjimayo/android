package com.umc.chamma.ui.search

import com.umc.chamma.ui.search.model.SearchResultData

interface SearchFragmentInterface {


    fun onGetSearchSuccess(datas : ArrayList<SearchResultData>, keyword : String){}
    fun onGetSearchFailure(message : String){}
    fun onGetRecentKeywordSuccess(datas : ArrayList<SearchResultData>){}
    fun onGetRecentKeywordFailure(message : String){}
    fun onPostAddressClickSuccess(){}
    fun onPostAddressClickFailure(){}
    fun onDeleteRecentSuccess(){}
    fun onDeleteRecentFailure(){}
    fun onDeleteAllRecentSuccess(){}
    fun onDeleteAllRecentFailure(){}
}