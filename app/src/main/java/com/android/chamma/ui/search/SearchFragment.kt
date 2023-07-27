package com.android.chamma.ui.search

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.chamma.config.App
import com.android.chamma.R
import com.android.chamma.config.BaseFragmentVB
import com.android.chamma.databinding.FragmentSearchBinding
import com.android.chamma.models.searchmodel.SearchResultData
import com.android.chamma.ui.search.adapter.RecentKeywordAdapter
import com.android.chamma.ui.search.adapter.SearchResultAdapter

class SearchFragment : BaseFragmentVB<FragmentSearchBinding>(FragmentSearchBinding::bind, R.layout.fragment_search), SearchFragmentInterface {


    private var keyword = ""
    private var isTyping = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        SearchService(this@SearchFragment).getRecentKeyword()

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

                if(keyword.isBlank()) {
                    isTyping = false
                    binding.recyclerData.adapter = null
                    binding.layoutRecentBar.visibility = View.VISIBLE
                    SearchService(this@SearchFragment).getRecentKeyword()
                }
                else {
                    isTyping = true
                    binding.recyclerData.adapter = null
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
        val adapter = RecentKeywordAdapter(data)
        binding.recyclerData.adapter = adapter
        binding.recyclerData.layoutManager = LinearLayoutManager(App.context())
    }

    private fun recyclerSearchResult(data : ArrayList<SearchResultData>, keyword : String){
        val adapter = SearchResultAdapter(data,keyword)
        binding.recyclerData.adapter = adapter
        binding.recyclerData.layoutManager = LinearLayoutManager(App.context())
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
        if(isTyping) recyclerSearchResult(datas, keyword)
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


}