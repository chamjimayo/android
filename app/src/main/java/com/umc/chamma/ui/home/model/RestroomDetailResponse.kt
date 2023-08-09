package com.umc.chamma.ui.home.model

import com.google.gson.annotations.SerializedName
import com.umc.chamma.config.BaseResponse

data class RestroomDetailResponse (
    @SerializedName("data")val data:RDResult

) : BaseResponse()

data class RDResult(
    @SerializedName("restroomName")val restroomName : String,
    @SerializedName("longitude")val longitude: Double,
    @SerializedName("latitude")val latitude: Double,
    @SerializedName("unisex")val unisex:Boolean,
    @SerializedName("address")val address:String,
    @SerializedName("operatingHour")val operatingHour:String,
    @SerializedName("restroomPhoto")val restroomPhoto:String,
    @SerializedName("equipmentExistenceProbability")val equipmentExistenceProbability:Int,//
    @SerializedName("publicOrPaid")val publicOrPaid:String,
    @SerializedName("accessibleToiletExistence")val accessibleToiletExistence:Boolean,
    @SerializedName("maleToiletCount")val maleToiletCount:Int,
    @SerializedName("femaleToiletCount")val femaleToiletCount:Int,
    @SerializedName("availableMaleToiletCount")val availableMaleToiletCount:Int,
    @SerializedName("availableFemaleToiletCount")val availableFemaleToiletCount:Int,
    @SerializedName("equipments")val equipements: ArrayList<equipementsList>,
    @SerializedName("reviews")val reviews:ArrayList<reviewList>,
    @SerializedName("restroomManager")val restroomManager:ArrayList<restroomManager>
    )

data class equipementsList(
    @SerializedName("equipmentName")val equimentName:String,
    @SerializedName("id")val id:Int
)

data class reviewList(
    @SerializedName("reviewContent")val reviewContent:String,
    @SerializedName("id")val id:Int
)

data class restroomManager(
    @SerializedName("id")val id:Int,
    @SerializedName("restroomManagerName")val restroomManagerName:String
)