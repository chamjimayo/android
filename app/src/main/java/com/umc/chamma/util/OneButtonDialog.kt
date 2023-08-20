package com.umc.chamma.util

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import com.umc.chamma.databinding.DialogOneButtonBinding

class OneButtonDialog(
    context: Context,
    private val title: String,
    private val firstButton: String,
    private val onFirstButtonClick: View.OnClickListener
): Dialog(context) {

    private lateinit var binding: DialogOneButtonBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DialogOneButtonBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()
    }

    private fun initView() = with(binding) {
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        this.tvTitle.text = title

        this.btn.apply {
            text = firstButton
            setOnClickListener(onFirstButtonClick)
        }
    }
}