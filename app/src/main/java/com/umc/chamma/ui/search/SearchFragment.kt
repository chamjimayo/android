package com.umc.chamma.ui.search

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.umc.chamma.R
import com.umc.chamma.databinding.FragmentSearchBinding
import com.umc.chamma.ui.search.model.SearchResultData
import com.umc.chamma.ui.home.main.HomeFragment
import com.umc.chamma.ui.search.adapter.RecentKeywordAdapter
import com.umc.chamma.ui.search.adapter.SearchResultAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SearchFragment(
    private val fromHomeKeyword : String?=null
) : com.umc.chamma.config.BaseFragmentVB<FragmentSearchBinding>(FragmentSearchBinding::bind, R.layout.fragment_search), SearchFragmentInterface {


    private var keyword = ""
    private var isTyping = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.etSearch.requestFocus()
        binding.etSearch.setOnKeyListener(onEditKeyListener)

        setBtnListener()
        setTextListener()

        if(fromHomeKeyword != null) binding.etSearch.setText(fromHomeKeyword)
        else SearchService(this@SearchFragment).getRecentKeyword()
    }

    private fun setBtnListener(){
        binding.btnErase.setOnClickListener {
            binding.etSearch.text.clear()
            keyword = ""
        }
        binding.btnBack.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.frame, HomeFragment())
                .addToBackStack(null)
                .commitAllowingStateLoss()
        }
        binding.btnEraseAll.setOnClickListener {
            SearchService(this).deleteAllRecentKeyword()
        }
    }

    private fun setTextListener(){
        binding.etSearch.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                keyword = binding.etSearch.text.toString()

                if(keyword.isBlank()) {
                    isTyping = false
                    binding.recyclerData.adapter = null
                    binding.layoutRecentBar.visibility = View.VISIBLE
                    SearchService(this@SearchFragment).getRecentKeyword()
                }
                else {
                    isTyping = true
                    binding.ivNorecentData.visibility = View.GONE
                    binding.layoutRecentBar.visibility = View.GONE
                    SearchService(this@SearchFragment).getSearch(keyword)
                }
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun afterTextChanged(p0: Editable?) {}

        })
    }

    private fun recyclerRecentKeyword(data : ArrayList<SearchResultData>){
        val adapter = RecentKeywordAdapter(data,::keywordClick,::recentKeywordDelete)
        binding.recyclerData.adapter = adapter
        binding.recyclerData.layoutManager = LinearLayoutManager(com.umc.chamma.config.App.context())
    }

    private fun recyclerSearchResult(data : ArrayList<SearchResultData>, keyword : String){
        val adapter = SearchResultAdapter(data,keyword,::keywordClick)
        binding.recyclerData.adapter = adapter
        binding.recyclerData.layoutManager = LinearLayoutManager(com.umc.chamma.config.App.context())
    }

    // 검색어 클릭이벤트 처리
    private fun keywordClick(data : SearchResultData){
        isTyping = false
        CoroutineScope(Dispatchers.IO).launch{
            SearchService(this@SearchFragment).postAddressClick(data)
        }

        parentFragmentManager.beginTransaction()
            .replace(R.id.frame, HomeFragment(data))
            .addToBackStack(null)
            .commitAllowingStateLoss()
    }

    // 최근검색어 삭제 이벤트 처리
    private fun recentKeywordDelete(keyword : String){
        SearchService(this@SearchFragment).deleteRecentKeyword(keyword)
    }

    private val onEditKeyListener = View.OnKeyListener { view, i, keyEvent ->
        if (i == KeyEvent.KEYCODE_ENTER) {
            //검색했을때
            val word = binding.etSearch.text.toString()
            if(word.isBlank()) showCustomToast("검색어를 입력해주세요")
            else SearchService(this@SearchFragment).getSearch(word)

            val manager = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            manager.hideSoftInputFromWindow(view.windowToken, 0)

            return@OnKeyListener true
        }
        return@OnKeyListener false
    }

    override fun onGetSearchSuccess(datas: ArrayList<SearchResultData>, keyword : String) {
        if(isTyping && datas.isNotEmpty()) {
            binding.recyclerData.adapter = null
            recyclerSearchResult(datas, keyword)
        }
    }

    override fun onGetRecentKeywordSuccess(datas: ArrayList<SearchResultData>) {
        if(datas.isEmpty()) binding.ivNorecentData.visibility = View.VISIBLE
        else{
            binding.ivNorecentData.visibility = View.GONE
            recyclerRecentKeyword(datas)
        }
    }

    override fun onGetSearchFailure(message: String) {
        showCustomToast(message)
    }

    override fun onGetRecentKeywordFailure(message: String) {
        showCustomToast(message)
    }

    override fun onDeleteRecentSuccess() {
        // TODO API 통신 없이 RecyclerView 반영하기
        binding.recyclerData.adapter = null
        SearchService(this).getRecentKeyword()
    }
    
    override fun onDeleteAllRecentSuccess() {
        // TODO API 통신 없이 RecyclerView 반영하기
        binding.recyclerData.adapter = null
        SearchService(this).getRecentKeyword()
    }


    override fun onPostAddressClickFailure() {
        showCustomToast("검색기록 저장 실패")
    }
    override fun onDeleteRecentFailure() {
        showCustomToast("검색기록 삭제 실패")
    }
    override fun onDeleteAllRecentFailure() {
        showCustomToast("검색기록 삭제 실패")
    }


}