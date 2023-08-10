package com.umc.chamma.util

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import com.umc.chamma.databinding.DialogTitleTwoButtonBinding

class TitleTwoButtonDialog(
    context: Context,
    private val title: String,
    private val info: String,
    private val firstButton: String,
    private val secondButton: String,
    private val onFirstButtonClick: View.OnClickListener,
    private val onSecondCheckButtonClick: View.OnClickListener
): Dialog(context) {

    private lateinit var binding: DialogTitleTwoButtonBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DialogTitleTwoButtonBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()
    }

    private fun initView() = with(binding) {
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        if(title.isBlank()) this.tvTitle.visibility = View.GONE
        if(info.isBlank()) this.tvText.visibility = View.GONE

        this.tvTitle.text = title
        this.tvText.text = info.replace(" ", "\u00A0")
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