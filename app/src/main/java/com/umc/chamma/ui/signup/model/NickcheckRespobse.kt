package com.umc.chamma.ui.signup.model

import com.umc.chamma.config.BaseResponse

data class NickcheckResponse(
    val data : NickcheckData
) : com.umc.chamma.config.BaseResponse()

data class NickcheckData(
    val nicknameDuplicate : Boolean
)