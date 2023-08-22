package com.umc.chamma.ui.mypage.usage.model

import com.google.gson.annotations.SerializedName

data class MypageUsageResponse()


data class MypageUsageData(

    @SerializedName("totalElements")
    val totalElements : Int,

    @SerializedName("totalPages")
    val  totalPages : Int,

    @SerializedName("sorted")
    val sorted : Sort,

    @SerializedName("first")
    val first : Boolean,

    @SerializedName("last")
    val last : Boolean,

    @SerializedName("number")
    val number : Int,

    @SerializedName("numberOfElements")
    val numberOfElement : Int,

    @SerializedName("Pageable")
    val pageable : Pageable,

    @SerializedName("size")
    val size : Int,

    @SerializedName("content")
    val content: ArrayList<Content>,

    @SerializedName("empty")
    val empty: Boolean





)


data class Sort(
    @SerializedName ("sorted")
    val sorted : Boolean,

    @SerializedName("unsorted")
    val unsorted : Boolean,

    @SerializedName("empty")
    val empty : Boolean
)

data class Pageable(

    @SerializedName("sort")
    val sort : Sort,

    @SerializedName("pageNumber")
    val pageNumber : Int,

    @SerializedName("pageSize")
    val pageSize : Int,

    @SerializedName ("paged")
    val paged : Boolean,

    @SerializedName("unpaged")
    val unpaged : Boolean,

    @SerializedName("offset")
    val offset : Int
)

data class Content (

    @SerializedName("id")
    val id : Int,

    @SerializedName("restroomName")
    val restroomName : String,

    @SerializedName("roadAddress")
    val roadAddress: String,

    @SerializedName("reviewCont")
    val reviewCount : Int,

    @SerializedName("operatingHour")
    val operatingHour : String,

    @SerializedName("point")
    val poin : Int,

    @SerializedName("restroomImageUrl")
    val restroomImageUrl : String

        )
