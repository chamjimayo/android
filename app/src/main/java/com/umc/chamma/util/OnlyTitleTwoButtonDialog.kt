package com.umc.chamma.util

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import com.umc.chamma.databinding.DialogOnlyTitleTwoButtonBinding

class OnlyTitleTwoButtonDialog(
    context: Context,
    private val title: String,
    private val firstButton: String,
    private val secondButton: String,
    private val onFirstButtonClick: View.OnClickListener,
    private val onSecondCheckButtonClick: View.OnClickListener
): Dialog(context) {

    private lateinit var binding: DialogOnlyTitleTwoButtonBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DialogOnlyTitleTwoButtonBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()
    }

    private fun initView() = with(binding) {
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        this.tvTitle.text = title
        this.btnSecond.apply {
            text = secondButton
            setOnClickListener(onSecondCheckButtonClick)
        }
        this.btnFirst.apply {
            text = firstButton
            setOnClickListener(onFirstButtonClick)
        }
    }

}