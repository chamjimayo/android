package com.umc.chamma.ui.reviewwrite

import com.umc.chamma.ui.reviewwrite.model.ReviewWriteSuccessData

interface ReviewWriteActivityInterface {

    fun onPostReviewWriteSuccess(result : ReviewWriteSuccessData)

    fun onPostReviewWriteFailure(message : String)
}