package com.android.chamma.ui.signup.model

import com.android.chamma.config.BaseResponse

data class NickcheckResponse(
    val data : NickcheckData
) : BaseResponse()

data class NickcheckData(
    val nicknameDuplicate : Boolean
)