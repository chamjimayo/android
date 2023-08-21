package com.umc.chamma.ui.using

import com.umc.chamma.ui.using.model.EndUseResponseData

interface EnduseInterface {

    fun onPostenduseSuccess(data : EndUseResponseData)
    fun onPostenduseFailure(message : String)
}