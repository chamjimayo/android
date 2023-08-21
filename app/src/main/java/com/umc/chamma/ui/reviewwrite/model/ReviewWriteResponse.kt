package com.umc.chamma.ui.reviewwrite.model

import com.umc.chamma.config.BaseResponse

data class ReviewWriteResponse(
    val data : ReviewWriteSuccessData
) : BaseResponse()


data class ReviewWriteSuccessData(
    val reviewId : Int,
    val userId : Int,
    val nickname : String,
    val userProfile : String,
    val restroomId : Int,
    val reviewContent : String,
    val rating : Int,
    val dateTime : String
)
