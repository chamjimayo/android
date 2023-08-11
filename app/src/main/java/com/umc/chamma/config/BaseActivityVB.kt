package com.umc.chamma.config


import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import com.umc.chamma.util.LoadingDialog
import com.umc.chamma.util.OnlyTitleTwoButtonDialog
import com.umc.chamma.util.TitleImageTwoButtonDialog
import com.umc.chamma.util.TitleTwoButtonDialog

abstract class BaseActivityVB<B : ViewBinding>(private val inflate: (LayoutInflater) -> B) :
    AppCompatActivity() {
    protected lateinit var binding: B
    private lateinit var titleTwoButtonDialog : TitleTwoButtonDialog
    private lateinit var titleImageTwoButtonDialog : TitleImageTwoButtonDialog
    private lateinit var onlyTitleTwoButtonDialog: OnlyTitleTwoButtonDialog
    private var loadingDialog = LoadingDialog()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = inflate(layoutInflater)
        setContentView(binding.root)
    }


    fun showLoading(){
        loadingDialog.show(supportFragmentManager,"")
    }

    fun dismissLoading(){
        loadingDialog.dismiss()
    }

    fun showCustomToast(message: String) {
        val toast = Toast.makeText(this, message, Toast.LENGTH_SHORT)
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

    fun showOnlyTitleTwoButtonDialog(
        context: Context,
        title: String,
        firstButton: String,
        secondButton: String,
        onFirstButtonClick: View.OnClickListener,
        onSecondButtonClick: View.OnClickListener
    ){
        onlyTitleTwoButtonDialog = OnlyTitleTwoButtonDialog(context,title,firstButton,secondButton,onFirstButtonClick,onSecondButtonClick)
        onlyTitleTwoButtonDialog.show()
    }

    fun dismissOnlyTitleTwoButtonDialog(){
        onlyTitleTwoButtonDialog.dismiss()
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

}