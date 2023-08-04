package com.umc.chamma.config

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.umc.chamma.R
import com.umc.chamma.util.LoadingDialog
import com.umc.chamma.util.TitleImageTwoButtonDialog
import com.umc.chamma.util.TitleTwoButtonDialog


abstract class BaseFragmentVB<B : ViewBinding>(
    private val bind: (View) -> B,
    @LayoutRes layoutResId: Int
) : Fragment(layoutResId) {
    private var _binding: B? = null
    protected val binding get() = _binding!!

    private lateinit var titleTwoButtonDialog : TitleTwoButtonDialog
    private lateinit var titleImageTwoButtonDialog: TitleImageTwoButtonDialog
    private var loadingDialog = LoadingDialog()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = bind(super.onCreateView(inflater, container, savedInstanceState)!!)
        return binding.root
    }


    fun showLoading(){
        loadingDialog.show(parentFragmentManager,"")
    }

    fun dismissLoading(){
        loadingDialog.dismiss()
    }

    fun showCustomToast(message: String) {
        val toast = Toast.makeText(activity, message, Toast.LENGTH_SHORT)
        toast.show()
    }

    fun showTitleTwoButtonDialog(
        context: Context,
        title: String,
        info: String,
        firstButton: String,
        secondButton: String,
        onFirstButtonClick: View.OnClickListener,
        onSecondButtonClick: View.OnClickListener
    ) {
        titleTwoButtonDialog = TitleTwoButtonDialog(context, title, info, firstButton, secondButton, onFirstButtonClick, onSecondButtonClick)
        titleTwoButtonDialog.show()
    }

    fun dismissTitleTwoButtonDialog() {
        titleTwoButtonDialog.dismiss()
    }

    fun showTitleImageTwoButtonDialog(
        context: Context,
        img : Int,
        title: String,
        info: String,
        firstButton: String,
        secondButton: String,
        onFirstButtonClick: View.OnClickListener,
        onSecondButtonClick: View.OnClickListener
    ) {
        titleImageTwoButtonDialog = TitleImageTwoButtonDialog(context, img, title, info, firstButton, secondButton, onFirstButtonClick, onSecondButtonClick)
        titleImageTwoButtonDialog.show()
    }

    fun dismissTitleImageTwoButtonDialog() {
        titleImageTwoButtonDialog.dismiss()
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }


}