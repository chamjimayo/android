package com.android.chamma.ui.home

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.content.ContextCompat
import com.android.chamma.config.App
import com.android.chamma.R
import com.android.chamma.config.BaseFragmentVB
import com.android.chamma.databinding.FragmentHomeBinding
import com.android.chamma.models.homemodel.MarkerData
import com.android.chamma.models.homemodel.NearToiletResponse
import com.android.chamma.ui.main.MainActivity
import com.android.chamma.ui.search.SearchFragment
import com.android.chamma.util.Constants.TAG
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.*
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.overlay.OverlayImage
import com.naver.maps.map.util.FusedLocationSource
import kotlin.math.*

class HomeFragment : BaseFragmentVB<FragmentHomeBinding>(FragmentHomeBinding::bind, R.layout.fragment_home), OnMapReadyCallback, HomeFragmentInterface {

    private lateinit var mainActivity : MainActivity
    private lateinit var mapView : MapView
    private lateinit var naverMap : NaverMap
    private lateinit var locationSource : FusedLocationSource
    private lateinit var lastPosition  : Pair<Double,Double>

    private val LOCATION_PERMISSTION_REQUEST_CODE = 1000
    private var locationState = true
    private var toiletState = "entire"
    private val markerList = arrayListOf<Marker>()

    private var swiping = false

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.etSearch.setOnFocusChangeListener  { view, hasFocus ->
            if(hasFocus){
                parentFragmentManager.beginTransaction()
                    .replace(R.id.frame,SearchFragment())
                    .addToBackStack(null)
                    .commitAllowingStateLoss()
            }
        }

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
        naverMap.minZoom = 15.0
        locationBtnListener()
        toiletBtnListener()

        // 화면 이동시 리스너
        naverMap.addOnCameraChangeListener{ reason, animated ->
            // 스와이프 시작하면, 시작위치 저장
            if(!swiping && !locationState){
                swiping = true
                lastPosition = Pair(naverMap.cameraPosition.target.latitude,naverMap.cameraPosition.target.longitude)
            }
        }

        naverMap.addOnCameraIdleListener {
            if(!locationState){
                swiping = false
                // 스와이프 멈추면, 해당 위치에서 마커찍기
                // 300m 이상 스와이프 했을때만
                if(getDistance(lastPosition.first,
                        lastPosition.second,
                        naverMap.cameraPosition.target.latitude,naverMap.cameraPosition.target.longitude) > 300){
                    HomeService(this).getNearToilet(toiletState
                        ,naverMap.cameraPosition.target.longitude
                        ,naverMap.cameraPosition.target.latitude
                    ,2000.0)
                }
            }
        }

        naverMap.addOnLocationChangeListener {
            if(locationState){
                HomeService(this).getNearToilet(toiletState
                    ,it.longitude
                    ,it.latitude)
            }
        }

    }

    private fun locationBtnListener(){

        // 현위치 tracking 활성화 / 비활성화 버튼
        binding.btnLocation.setOnClickListener {
            if(locationState){
                naverMap.minZoom = 10.0
                naverMap.locationTrackingMode = LocationTrackingMode.None
                locationState = false
                binding.btnLocation.setImageResource(R.drawable.home_location_btn)
            }else{
                naverMap.minZoom = 15.0
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
            toiletState = "entire"
            removeMarker()
            HomeService(this).getNearToilet("paid"
                ,naverMap.cameraPosition.target.longitude
                ,naverMap.cameraPosition.target.latitude)
        }

        binding.btnFreetoilet.setOnClickListener {
            binding.btnFreetoilet.setBackgroundResource(R.drawable.shape_home_topbtn_blue)
            binding.btnFreetoilet.setTextColor(ContextCompat.getColor(App.context(),R.color.white))

            binding.btnAlltoilet.setBackgroundResource(R.drawable.shape_home_topbtn_white)
            binding.btnAlltoilet.setTextColor(ContextCompat.getColor(App.context(),R.color.chamma_gray))

            binding.btnPaytoilet.setBackgroundResource(R.drawable.shape_home_topbtn_white)
            binding.btnPaytoilet.setTextColor(ContextCompat.getColor(App.context(),R.color.chamma_gray))
            toiletState = "public"
            removeMarker()
            HomeService(this).getNearToilet("paid"
                ,naverMap.cameraPosition.target.longitude
                ,naverMap.cameraPosition.target.latitude)
        }

        binding.btnPaytoilet.setOnClickListener {
            binding.btnPaytoilet.setBackgroundResource(R.drawable.shape_home_topbtn_blue)
            binding.btnPaytoilet.setTextColor(ContextCompat.getColor(App.context(),R.color.white))

            binding.btnFreetoilet.setBackgroundResource(R.drawable.shape_home_topbtn_white)
            binding.btnFreetoilet.setTextColor(ContextCompat.getColor(App.context(),R.color.chamma_gray))

            binding.btnAlltoilet.setBackgroundResource(R.drawable.shape_home_topbtn_white)
            binding.btnAlltoilet.setTextColor(ContextCompat.getColor(App.context(),R.color.chamma_gray))
            toiletState = "paid"
            removeMarker()
            HomeService(this).getNearToilet("paid"
                ,naverMap.cameraPosition.target.longitude
                ,naverMap.cameraPosition.target.latitude)
        }

    }

    private fun setFreetoiletMarker(data : MarkerData){

        val marker = Marker()
        marker.position = LatLng(data.latitude,data.longitude)
        marker.icon = OverlayImage.fromResource(R.drawable.home_marker_freetoilet)
        marker.map = naverMap

        marker.setOnClickListener {
            HomeBottomSheet(data).show(parentFragmentManager, "HomeBottomSheet")
            true
        }

        markerList.add(marker)
    }

    private fun setPaytoiletMarker(data : MarkerData){

        val marker = Marker()
        marker.position = LatLng(data.latitude,data.longitude)
        marker.icon = OverlayImage.fromResource(R.drawable.home_marker_paytoilet)
        marker.map = naverMap

        marker.setOnClickListener {
            HomeBottomSheet(data).show(parentFragmentManager, "HomeBottomSheet")
            true
        }

        markerList.add(marker)
    }

    override fun onGetNearToiletSuccess(result : NearToiletResponse) {
        result.data.forEach{
            if(it.publicOrPaid == "public") setFreetoiletMarker(it)
            else setPaytoiletMarker(it)
        }
    }

    override fun onGetNearToiletFailure(message : String) {
        // TODO 오류내용 Toast 메세지
    }

    private fun removeMarker(){
        (context as MainActivity).runOnUiThread {
            markerList.forEach{it.map = null}
            markerList.clear()
        }
    }

    private fun getDistance(lat1 : Double, lon1 : Double, lat2 : Double, lon2 : Double) : Int{
        val R = 6372.8 * 1000
        val dLat = Math.toRadians(lat2 - lat1)
        val dLon = Math.toRadians(lon2 - lon1)
        val a = sin(dLat / 2).pow(2.0) + sin(dLon / 2).pow(2.0) * cos(Math.toRadians(lat1)) * cos(Math.toRadians(lat2))
        val c = 2 * asin(sqrt(a))
        return (R * c).toInt()
    }


}