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
import com.android.chamma.ui.main.MainActivity
import com.android.chamma.util.Constants.TAG
import com.naver.maps.map.LocationTrackingMode
import com.naver.maps.map.MapView
import com.naver.maps.map.NaverMap
import com.naver.maps.map.OnMapReadyCallback
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
    }

    private fun locationBtnListener(){

        // 현위치 tracking 활성화 / 비활성화 버튼
        binding.btnLocation.setOnClickListener {
            if(locationState){
                naverMap.locationTrackingMode = LocationTrackingMode.None
                locationState = false
            }else{
                naverMap.locationTrackingMode = LocationTrackingMode.Follow
                locationState = true
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


}