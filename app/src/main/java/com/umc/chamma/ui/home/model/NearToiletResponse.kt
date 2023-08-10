package com.umc.chamma.ui.home.model

import com.google.gson.annotations.SerializedName
import com.umc.chamma.config.BaseResponse
import com.umc.chamma.ui.home.restroomInfo.model.equipementsList
import com.umc.chamma.ui.home.restroomInfo.model.restroomManager

data class NearToiletResponse (
    val data : ArrayList<NearToiletData>
) : BaseResponse()



data class NearToiletData(
    @SerializedName("restroomName")val restroomName : String? = "",
    @SerializedName("longitude")val longitude: Double,
    @SerializedName("latitude")val latitude: Double,
    @SerializedName("unisex")val unisex:Boolean,
    @SerializedName("address")val address:String?="",
    @SerializedName("operatingHour")val operatingHour:String,
    @SerializedName("restroomPhoto")val restroomPhoto:String,
    @SerializedName("equipmentExistenceProbability")val equipmentExistenceProbability:Int,//
    @SerializedName("publicOrPaid")val publicOrPaid:String?="",
    @SerializedName("accessibleToiletExistence")val accessibleToiletExistence:Boolean,
    @SerializedName("maleToiletCount")val maleToiletCount:Int,
    @SerializedName("femaleToiletCount")val femaleToiletCount:Int,
    @SerializedName("availableMaleToiletCount")val availableMaleToiletCount:Int,
    @SerializedName("availableFemaleToiletCount")val availableFemaleToiletCount:Int,
    @SerializedName("equipments")val equipements: ArrayList<equipementsList>,
    @SerializedName("restroomManager")val restroomManager:ArrayList<restroomManager>,
    @SerializedName("reviewRating")val reviewRating : Float? = 0F,
    @SerializedName("distance")val distance : Double? = 0.0,
    @SerializedName("restroomId")val restroomId : Int? = 0
)