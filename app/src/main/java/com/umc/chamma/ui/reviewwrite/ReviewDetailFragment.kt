package com.umc.chamma.ui.reviewwrite

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Button
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.navercorp.nid.NaverIdLoginSDK.applicationContext
import com.umc.chamma.R
import com.umc.chamma.config.BaseFragmentVB
import com.umc.chamma.databinding.FragmentReviewDetailBinding
import com.umc.chamma.ui.home.restroomInfo.RestroomInfoActivityInterface
import com.umc.chamma.ui.home.restroomInfo.RestroomInfoService
import com.umc.chamma.ui.home.restroomInfo.model.RestroomDetailResponse

class ReviewDetailFragment(
    private val restroomId : Int,
    private val rating : Float
    ) : BaseFragmentVB<FragmentReviewDetailBinding>(FragmentReviewDetailBinding::bind, R.layout.fragment_review_detail),
    RestroomInfoActivityInterface {

    private var fixtureArr = arrayListOf<Button>()
    private var btnState = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        RestroomInfoService(this).tryToGetRestroomDetail(restroomId)
        binding.ratingbar.rating = rating
        fixtureArr.add(binding.btnIsFixture)
        fixtureArr.add(binding.btnNoFixture)
        fixtureArr.add(binding.btnSomeFixture)

        fixtureBtnListener()
        etFocusListener()
        etChangeListener()

        binding.btnComplete.setOnClickListener {
            
        }
    }

    private fun fixtureBtnListener(){
        fixtureArr.forEach{
            it.setOnClickListener {_->
                btnState = true
                fixtureArr.forEach{ it2->
                    it2.setBackgroundResource(R.drawable.shape_notfill_graystroke)
                    it2.setTextColor(ContextCompat.getColor(applicationContext, R.color.black))
                }
                it.setBackgroundResource(R.drawable.shape_notfill_bluestroke)
                it.setTextColor(ContextCompat.getColor(applicationContext, R.color.chamma_main))

                if(it == binding.btnSomeFixture) binding.etFixtureSpec.visibility = View.VISIBLE
                else binding.etFixtureSpec.visibility = View.GONE
            }
        }
    }

    private fun etFocusListener(){
        binding.etFixtureSpec.setOnFocusChangeListener { view, hasFocus ->
            if(hasFocus) binding.etFixtureSpec.setBackgroundResource(R.drawable.shape_fillgray_strokeblack)
            else binding.etFixtureSpec.setBackgroundResource(R.drawable.shape_fillgray_strokegray)
        }

        binding.etReviewComment.setOnFocusChangeListener { view, hasFocus ->
            if(hasFocus) binding.etReviewComment.setBackgroundResource(R.drawable.shape_fillgray_strokeblack)
            else binding.etReviewComment.setBackgroundResource(R.drawable.shape_fillgray_strokegray)
        }
    }

    private fun etChangeListener(){
        binding.etReviewComment.addTextChangedListener(object: TextWatcher{
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if(btnState && binding.etReviewComment.text.isNotBlank()){
                    binding.btnComplete.isEnabled = true
                    binding.btnComplete.setBackgroundResource(R.drawable.shape_signup_duplicheck)
                    binding.btnComplete.setTextColor(ContextCompat.getColor(applicationContext, R.color.white))
                }else{
                    binding.btnComplete.isEnabled = false
                    binding.btnComplete.setBackgroundResource(R.drawable.shape_signup_gender)
                    binding.btnComplete.setTextColor(ContextCompat.getColor(applicationContext, R.color.chamma_signup_textgray))
                }
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun afterTextChanged(p0: Editable?) {}
        })
    }


    override fun onTryToGetRDSuccess(response: RestroomDetailResponse) {
        Glide.with(this)
            .load(response.data.restroomPhoto[0])
            .error(R.drawable.restroom_ex)
            .into(binding.ivThumnailImg)

        binding.tvRestroomName.text = response.data.restroomName
    }

    override fun onTryToGetRDFailure(message: String) {
        showCustomToast(message)
    }
}