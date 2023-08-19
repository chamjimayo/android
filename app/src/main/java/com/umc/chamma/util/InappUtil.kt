package com.umc.chamma.util

import android.app.Activity
import android.util.Log
import com.android.billingclient.api.*
import com.umc.chamma.config.App
import com.umc.chamma.ui.mypage.chargepoint.ChargePointRetrofitInterface
import com.umc.chamma.ui.mypage.chargepoint.InappInterface
import com.umc.chamma.ui.mypage.chargepoint.model.ChargePointPostData
import com.umc.chamma.ui.mypage.chargepoint.model.ChargePointResponse
import com.umc.chamma.util.Constants.TAG
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object InappUtil : PurchasesUpdatedListener {

    private val tempList =
        listOf("point_1000", "point_3000", "point_5000", "point_8000", "point_10000")

    var inappInterface : InappInterface?=null

    interface GooglepayUtilDelegate {
        fun onProgress()
    }


    private lateinit var billingCilent: BillingClient
    private var productDetailsList: List<ProductDetails> = mutableListOf()
    private lateinit var consumeListenser: ConsumeResponseListener

    private var productId = ""

    fun setinappInterface(temp: InappInterface) {
        this.inappInterface = temp
    }


    /**
     * Billing Client 초기화h
     * Google Play 연결
     */

    fun initBillingClient(activity : Activity) {

        // Billing Client 초기화
        billingCilent = BillingClient.newBuilder(activity)
            .setListener(this)
            .enablePendingPurchases()
            .build()

        // Billing Client 와 Google Play 연결
        billingCilent.startConnection(object : BillingClientStateListener {
            override fun onBillingServiceDisconnected() {
                //연결이 종료될 시 재시도 요망
                Log.d(TAG, "연결 실패")
            }

            override fun onBillingSetupFinished(billingResult: BillingResult) {
                if (billingResult.responseCode == BillingClient.BillingResponseCode.OK) {
                    // 연결 성공
                    Log.d(TAG, "연결 성공")
                    Log.d(TAG, "billingCilent.connectionState : ${billingCilent.connectionState}")
                    querySkuDetails(activity)

                }
            }

        })

        consumeListenser = ConsumeResponseListener { billingResult, purchaseToken ->
            Log.d(TAG, "billingResult.responseCode : ${billingResult.responseCode}")
            if (billingResult.responseCode == BillingClient.BillingResponseCode.OK) {
                Log.d(TAG, "소모 성공")
            } else {
                Log.d(TAG, "소모 실패")
            }
        }

    }

    // TODO 상품 목록 조회

    fun querySkuDetails(activity: Activity) {
        Log.d(TAG, "querySkuDetails")


        var testMutableList = mutableListOf<QueryProductDetailsParams.Product>()
        for (element in tempList) {
            testMutableList.add(
                QueryProductDetailsParams.Product.newBuilder()
                    .setProductId(element)
                    .setProductType(BillingClient.ProductType.INAPP)
                    .build()
            )
        }

        Log.d(TAG, testMutableList.size.toString())

        //5.0 마이그레이션
        val tempParam = QueryProductDetailsParams.newBuilder()
            .setProductList(
                testMutableList
            ).build()

        billingCilent.queryProductDetailsAsync(tempParam) { billingResult, mutableList ->
            Log.d(TAG, "????? ${mutableList.size}")
            productDetailsList = mutableList
            for (i: Int in 0 until mutableList.size) {
                Log.d(TAG, mutableList[i].name)
            }
        }

    }

    // TODO 상품 결제 요청

    fun getPay(activity: Activity, count: String) {
        var list: MutableList<BillingFlowParams.ProductDetailsParams> = mutableListOf()
        Log.d(TAG, "getpay 실행")

        productId = count

        for (i in productDetailsList.indices) {
            if (productDetailsList[i].productId == count) {
                var flowProductDetailParams = BillingFlowParams.ProductDetailsParams.newBuilder()
                    .setProductDetails(productDetailsList[i])
                    .build()

                list.add(flowProductDetailParams)
            }
        }

        var flowParams = BillingFlowParams.newBuilder()
            .setProductDetailsParamsList(list)
            .build()

        val responseCode = billingCilent.launchBillingFlow(activity, flowParams).responseCode
    }

    // TODO 결제 결과 수신

    override fun onPurchasesUpdated(
        billingResult: BillingResult,
        purchases: MutableList<Purchase>?
    ) {
        if (billingResult.responseCode == BillingClient.BillingResponseCode.OK && purchases != null) {
            for (purchase in purchases) {

                Log.d(TAG, purchase.toString())

                // 서버로 구매성공 데이터 보내기
                val data = ChargePointPostData(productId, purchase.purchaseToken)
                Log.d(TAG, "$data")
                postPurchaseData(data)

                val consumeParams = ConsumeParams.newBuilder()
                    .setPurchaseToken(purchase.purchaseToken)
                    .build()

                billingCilent.consumeAsync(consumeParams, consumeListenser)
            }
        } else if (billingResult.responseCode == BillingClient.BillingResponseCode.USER_CANCELED) {
            Log.d(TAG, "유저 취소")
        }

    }

    private fun postPurchaseData(data : ChargePointPostData){
        val chargePointRetro = App.getRetro().create(ChargePointRetrofitInterface::class.java)
        chargePointRetro.postChargePoint(data)
            .enqueue(object : Callback<ChargePointResponse>{
                override fun onResponse(
                    call: Call<ChargePointResponse>,
                    response: Response<ChargePointResponse>
                ) {
                    response.body()?.let{
                        if(response.code() == 200) inappInterface?.successBill()
                    }
                    if(response.body() == null)  inappInterface?.failBill()

                }

                override fun onFailure(call: Call<ChargePointResponse>, t: Throwable) {
                    inappInterface?.failBill()
                }
            })
    }




}