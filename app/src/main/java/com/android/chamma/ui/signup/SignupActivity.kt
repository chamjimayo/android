package com.android.chamma.ui.signup

import android.os.Bundle
import android.view.View
import com.android.chamma.R
import com.android.chamma.config.BaseActivityVB
import com.android.chamma.databinding.ActivitySignupBinding

class SignupActivity : BaseActivityVB<ActivitySignupBinding>(ActivitySignupBinding::inflate) {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        etFocusListener()
        btnListener()

    }

    private fun etFocusListener(){
        binding.etName.setOnFocusChangeListener { view, hasFocus ->
            if(hasFocus){
                binding.etName.setBackgroundResource(R.drawable.shape_signup_et_focus)
            }else{
                binding.etName.setBackgroundResource(R.drawable.shape_signup_et)
            }
        }

        binding.etNick.setOnFocusChangeListener { view, hasFocus ->
            if(hasFocus){
                binding.etNick.setBackgroundResource(R.drawable.shape_signup_et_focus)
                binding.btnDuplicheck.visibility = View.VISIBLE
            }else{
                binding.etNick.setBackgroundResource(R.drawable.shape_signup_et)
                binding.btnDuplicheck.visibility = View.INVISIBLE
            }
        }
    }

    private fun btnListener(){
        binding.btnMale.setOnClickListener {
            binding.btnFemale.setBackgroundResource(R.drawable.shape_signup_gender)
            binding.btnMale.setBackgroundResource(R.drawable.shape_signup_et_focus)
        }

        binding.btnFemale.setOnClickListener {
            binding.btnMale.setBackgroundResource(R.drawable.shape_signup_gender)
            binding.btnFemale.setBackgroundResource(R.drawable.shape_signup_et_focus)
        }

        binding.btnDuplicheck.setOnClickListener {

        }
    }


}