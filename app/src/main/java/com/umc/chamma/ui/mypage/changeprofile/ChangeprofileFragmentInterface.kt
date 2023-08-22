package com.umc.chamma.ui.mypage.changeprofile

interface ChangeprofileFragmentInterface {


    fun onChangeNickSuccess(message : String)
    fun onChangeNickFailure(message : String)

    fun onChangeImgSuccess(message : String)
    fun onChangeImgFailure(message : String)

}