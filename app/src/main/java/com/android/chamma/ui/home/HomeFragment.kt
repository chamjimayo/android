package com.android.chamma.ui.home

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.content.ContextCompat
import com.android.chamma.App
import com.android.chamma.R
import com.android.chamma.config.BaseFragmentVB
import com.android.chamma.databinding.FragmentHomeBinding
import com.android.chamma.models.homemodel.MarkerData
import com.android.chamma.ui.main.MainActivity
import com.android.chamma.util.Constants.TAG
import com.android.chamma.util.ToastMessageUtil.showToast
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.LocationTrackingMode
import com.naver.maps.map.MapView
import com.naver.maps.map.NaverMap
import com.naver.maps.map.OnMapReadyCallback
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.overlay.OverlayImage
import com.naver.maps.map.util.FusedLocationSource

class HomeFragment : BaseFragmentVB<FragmentHomeBinding>(FragmentHomeBinding::bind, R.layout.fragment_home), OnMapReadyCallback {

    private lateinit var mainActivity : MainActivity
    private lateinit var mapView : MapView
    private lateinit var naverMap : NaverMap
    private lateinit var locationSource : FusedLocationSource

    private val LOCATION_PERMISSTION_REQUEST_CODE = 1000
    private var locationState = true

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mapView = mainActivity.findViewById(R.id.mapview)
        mapView.getMapAsync(this)
        locationSource = FusedLocationSource(this, LOCATION_PERMISSTION_REQUEST_CODE)
    }

    override fun onMapReady(nM: NaverMap) {
        Log.d(TAG,"onMapReady")
        naverMap = nM
        naverMap.uiSettings.isZoomControlEnabled = false
        naverMap.locationSource = locationSource
        naverMap.locationTrackingMode = LocationTrackingMode.Follow
        locationBtnListener()
        toiletBtnListener()

        // 화면 이동시 리스너
        naverMap.addOnCameraChangeListener{ reason, animated ->
            Log.d("NaverMap", "카메라 변경 - latitude : ${naverMap.cameraPosition.target.latitude}" +
                    "longitude : ${naverMap.cameraPosition.target.longitude}")
        }


        // 서울시청역 부근 테스트 데이터
        val d1 = MarkerData(37.5670135,126.9783740,"테스트공중화장실1")
        val d2 = MarkerData(37.5400,126.9783740,"테스트유료화장실1")
        val d3 = MarkerData(37.5500,126.9783740,"테스트공중화장실2")
        val d4 = MarkerData(37.5300,126.9783740,"테스트유료화장실2")
        val d5 = MarkerData(37.5500,126.9883740,"테스트공중화장실3")
        val d6 = MarkerData(37.5300,126.9883740,"테스트유료화장실3")

        val freedatas = arrayListOf(d1,d3,d5)
        val paydatas = arrayListOf(d2,d4,d6)

        setFreetoiletMarker(freedatas)
        setPaytoiletMarker(paydatas)
    }

    private fun locationBtnListener(){

        // 현위치 tracking 활성화 / 비활성화 버튼
        binding.btnLocation.setOnClickListener {
            if(locationState){
                naverMap.locationTrackingMode = LocationTrackingMode.None
                locationState = false
                binding.btnLocation.setImageResource(R.drawable.home_location_btn)
            }else{
                naverMap.locationTrackingMode = LocationTrackingMode.Follow
                locationState = true
                binding.btnLocation.setImageResource(R.drawable.home_location_btn_on)
            }

        }
    }

    @SuppressLint("ResourceAsColor")
    private fun toiletBtnListener(){
        binding.btnAlltoilet.setOnClickListener {
            binding.btnAlltoilet.setBackgroundResource(R.drawable.shape_home_topbtn_blue)
            binding.btnAlltoilet.setTextColor(ContextCompat.getColor(App.context(),R.color.white))

            binding.btnFreetoilet.setBackgroundResource(R.drawable.shape_home_topbtn_white)
            binding.btnFreetoilet.setTextColor(ContextCompat.getColor(App.context(),R.color.chamma_gray))

            binding.btnPaytoilet.setBackgroundResource(R.drawable.shape_home_topbtn_white)
            binding.btnPaytoilet.setTextColor(ContextCompat.getColor(App.context(),R.color.chamma_gray))
        }

        binding.btnFreetoilet.setOnClickListener {
            binding.btnFreetoilet.setBackgroundResource(R.drawable.shape_home_topbtn_blue)
            binding.btnFreetoilet.setTextColor(ContextCompat.getColor(App.context(),R.color.white))

            binding.btnAlltoilet.setBackgroundResource(R.drawable.shape_home_topbtn_white)
            binding.btnAlltoilet.setTextColor(ContextCompat.getColor(App.context(),R.color.chamma_gray))

            binding.btnPaytoilet.setBackgroundResource(R.drawable.shape_home_topbtn_white)
            binding.btnPaytoilet.setTextColor(ContextCompat.getColor(App.context(),R.color.chamma_gray))
        }

        binding.btnPaytoilet.setOnClickListener {
            binding.btnPaytoilet.setBackgroundResource(R.drawable.shape_home_topbtn_blue)
            binding.btnPaytoilet.setTextColor(ContextCompat.getColor(App.context(),R.color.white))

            binding.btnFreetoilet.setBackgroundResource(R.drawable.shape_home_topbtn_white)
            binding.btnFreetoilet.setTextColor(ContextCompat.getColor(App.context(),R.color.chamma_gray))

            binding.btnAlltoilet.setBackgroundResource(R.drawable.shape_home_topbtn_white)
            binding.btnAlltoilet.setTextColor(ContextCompat.getColor(App.context(),R.color.chamma_gray))
        }



    }

    private fun setFreetoiletMarker(datas : ArrayList<MarkerData>){

        for(data in datas){
            val marker = Marker()
            marker.position = LatLng(data.latitude,data.longitude)
            marker.icon = OverlayImage.fromResource(R.drawable.home_marker_freetoilet)
            marker.map = naverMap

            marker.setOnClickListener {

                showToast(App.context(),"${data.name}")

                true
            }
        }
    }

    private fun setPaytoiletMarker(datas : ArrayList<MarkerData>){

        for(data in datas){
            val marker = Marker()
            marker.position = LatLng(data.latitude,data.longitude)
            marker.icon = OverlayImage.fromResource(R.drawable.home_marker_paytoilet)
            marker.map = naverMap

            marker.setOnClickListener {

                showToast(App.context(),"${data.name}")

                true
            }
        }
    }

    private fun removeMarker(){
        val marker = Marker()
        marker.map = null
    }


}