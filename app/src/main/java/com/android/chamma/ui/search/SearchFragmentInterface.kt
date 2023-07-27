package com.android.chamma.ui.search

import com.android.chamma.models.searchmodel.SearchResultData

interface SearchFragmentInterface {


    fun onGetSearchSuccess(datas : ArrayList<SearchResultData>, keyword : String){

    }

    fun onGetSearchFailure(message : String){

    }

    fun onGetRecentKeywordSuccess(datas : ArrayList<SearchResultData>){

    }

    fun onGetRecentKeywordFailure(message : String){

    }
}