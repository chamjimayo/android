package com.android.chamma.ui.mypage

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.android.chamma.R
import com.android.chamma.databinding.FragmentUpdateUserDataBinding
import com.android.chamma.ui.main.MainActivity


class UpdateUserData : Fragment() {

    lateinit var mainActivity: MainActivity


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        val binding = FragmentUpdateUserDataBinding.inflate(inflater, container, false)

        binding.btnBackUpdate.setOnClickListener { mainActivity.goBackMypage() }

        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if (context is MainActivity) mainActivity = context
    }

}