package com.umc.chamma.ui.home.main

import android.annotation.SuppressLint
import android.content.Context
import android.content.Context.LOCATION_SERVICE
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.location.LocationManager
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.navigation.fragment.findNavController
import com.umc.chamma.R
import com.umc.chamma.databinding.FragmentHomeBinding
import com.umc.chamma.ui.home.model.NearToiletData
import com.umc.chamma.ui.home.model.NearToiletResponse
import com.umc.chamma.ui.search.model.SearchResultData
import com.umc.chamma.ui.main.MainActivity
import com.umc.chamma.ui.search.SearchFragment
import com.umc.chamma.util.Constants.TAG
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.*
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.overlay.OverlayImage
import com.naver.maps.map.util.FusedLocationSource
import com.umc.chamma.config.App
import com.umc.chamma.config.BaseFragmentVB
import com.umc.chamma.ui.home.restroomInfo.RestroomInfoActivity
import com.umc.chamma.ui.mypage.chargepoint.ChargePointActivity
import com.umc.chamma.ui.mypage.chargepoint.ChargePointActivityInterface
import com.umc.chamma.ui.mypage.chargepoint.ChargePointService
import com.umc.chamma.ui.mypage.chargepoint.model.UserinfoData
import com.umc.chamma.ui.qr.QRActivity
import com.umc.chamma.util.BottomSheet
import kotlin.math.*

enum class markerType(val i : Int){
    PAID(0),
    FREE(1),
    SEARCH(2)
}

class HomeFragment(private val searchData : SearchResultData?=null) : BaseFragmentVB<FragmentHomeBinding>(FragmentHomeBinding::bind, R.layout.fragment_home), OnMapReadyCallback,
    HomeFragmentInterface, ChargePointActivityInterface {

    private lateinit var mainActivity : MainActivity
    private lateinit var mapView : MapView
    private lateinit var naverMap : NaverMap
    private lateinit var locationSource : FusedLocationSource
    private lateinit var lastPosition  : Pair<Double,Double>

    private val LOCATION_PERMISSTION_REQUEST_CODE = 1000
    private var locationState = false
    private var toiletState = "entire"
    val markerList = arrayListOf<Marker>()

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

        binding.btnPoint.setOnClickListener {
            startActivity(Intent(requireContext(), ChargePointActivity::class.java))
        }

        // 사용자 정보 (point) 불러오기
        ChargePointService(this).getUserInfo()

        mapView = mainActivity.findViewById(R.id.mapview)
        mapView.getMapAsync(this)
        locationSource = FusedLocationSource(this, LOCATION_PERMISSTION_REQUEST_CODE)

        if(searchData != null) searchResultonHome()
    }

    // 검색 결과값 가지고 Home 왔을경우
    private fun searchResultonHome(){
        binding.etSearch.visibility = View.GONE
        binding.layoutSearchResultAppbar.visibility = View.VISIBLE
        binding.searchResultEtSearch.setText(searchData!!.searchWord)

        binding.searchResultEtSearch.setOnFocusChangeListener  { view, hasFocus ->
            if(hasFocus){
                parentFragmentManager.beginTransaction()
                    .replace(R.id.frame,SearchFragment(searchData.searchWord))
                    .addToBackStack(null)
                    .commitAllowingStateLoss()
            }
        }

        binding.searchResultBtnBack.setOnClickListener {
            binding.etSearch.visibility = View.VISIBLE
            binding.layoutSearchResultAppbar.visibility = View.GONE
        }
    }



    override fun onMapReady(nM: NaverMap) {
        Log.d(TAG,"onMapReady")
        naverMap = nM
        naverMap.uiSettings.isZoomControlEnabled = false
        naverMap.locationSource = locationSource
        naverMap.locationTrackingMode = LocationTrackingMode.None
        naverMap.minZoom = 10.0
        locationBtnListener()
        toiletBtnListener()
        HomeService(this).getNearToilet(toiletState
            ,naverMap.cameraPosition.target.longitude
            ,naverMap.cameraPosition.target.latitude
            ,2000.0)

        // 화면 이동시 리스너
        naverMap.addOnCameraChangeListener{ reason, animated ->
            // 스와이프 시작하면, 시작위치 저장
            if(!swiping && !locationState){
                swiping = true
                lastPosition = Pair(naverMap.cameraPosition.target.latitude,naverMap.cameraPosition.target.longitude)
            }
        }

        // 화면이동 끝났을때 리스너
        naverMap.addOnCameraIdleListener {
            if(!locationState){
                swiping = false
                // 스와이프 멈추면, 해당 위치에서 마커찍기
                // 300m 이상 스와이프 했을때만
                if(getDistance(lastPosition.first,
                        lastPosition.second,
                        naverMap.cameraPosition.target.latitude,naverMap.cameraPosition.target.longitude) > 300){
                    removeMarker()

                    HomeService(this).getNearToilet(toiletState
                        ,naverMap.cameraPosition.target.longitude
                        ,naverMap.cameraPosition.target.latitude
                    ,2000.0)
                }
            }
        }

        // GPS 기반 위치변화 리스너
        naverMap.addOnLocationChangeListener {
            if(locationState){
                HomeService(this).getNearToilet(toiletState
                    ,it.longitude
                    ,it.latitude)
            }
        }

        // 검색결과 가지고 왔을때 카메라 이동
        if(searchData != null) moveCamera(searchData.latitude, searchData.longitude)
    }

    private fun moveCamera(latitude : Double, longitude : Double){
        val locate = CameraUpdate.scrollTo(LatLng(latitude, longitude))
        naverMap.moveCamera(locate)

        val marker = Marker()
        marker.position = LatLng(searchData!!.latitude,searchData.longitude)
        marker.icon = OverlayImage.fromResource(R.drawable.home_marker_searchloc)
        marker.map = naverMap

        markerList.add(marker)

        HomeService(this).getNearToilet(toiletState
            ,naverMap.cameraPosition.target.longitude
            ,naverMap.cameraPosition.target.latitude
            ,2000.0)
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
                val locationManager = App.context().getSystemService(LOCATION_SERVICE) as LocationManager
                if(!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
                    showCustomToast("위치 정보를 켜주세요")
                    startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
                }else{
                    naverMap.minZoom = 17.0
                    naverMap.locationTrackingMode = LocationTrackingMode.Follow
                    locationState = true
                    binding.btnLocation.setImageResource(R.drawable.home_location_btn_on)
                }
            }

        }
    }

    @SuppressLint("ResourceAsColor")
    private fun toiletBtnListener(){
        binding.btnAlltoilet.setOnClickListener {
            binding.btnAlltoilet.setBackgroundResource(R.drawable.shape_home_topbtn_blue)
            binding.btnAlltoilet.setTextColor(ContextCompat.getColor(com.umc.chamma.config.App.context(),R.color.white))

            binding.btnFreetoilet.setBackgroundResource(R.drawable.shape_home_topbtn_white)
            binding.btnFreetoilet.setTextColor(ContextCompat.getColor(com.umc.chamma.config.App.context(),R.color.chamma_gray))

            binding.btnPaytoilet.setBackgroundResource(R.drawable.shape_home_topbtn_white)
            binding.btnPaytoilet.setTextColor(ContextCompat.getColor(com.umc.chamma.config.App.context(),R.color.chamma_gray))
            toiletState = "entire"
            removeMarker()
            HomeService(this).getNearToilet(toiletState
                ,naverMap.cameraPosition.target.longitude
                ,naverMap.cameraPosition.target.latitude)
        }

        binding.btnFreetoilet.setOnClickListener {
            binding.btnFreetoilet.setBackgroundResource(R.drawable.shape_home_topbtn_blue)
            binding.btnFreetoilet.setTextColor(ContextCompat.getColor(com.umc.chamma.config.App.context(),R.color.white))

            binding.btnAlltoilet.setBackgroundResource(R.drawable.shape_home_topbtn_white)
            binding.btnAlltoilet.setTextColor(ContextCompat.getColor(com.umc.chamma.config.App.context(),R.color.chamma_gray))

            binding.btnPaytoilet.setBackgroundResource(R.drawable.shape_home_topbtn_white)
            binding.btnPaytoilet.setTextColor(ContextCompat.getColor(com.umc.chamma.config.App.context(),R.color.chamma_gray))
            toiletState = "public"
            removeMarker()
            HomeService(this).getNearToilet(toiletState
                ,naverMap.cameraPosition.target.longitude
                ,naverMap.cameraPosition.target.latitude)
        }

        binding.btnPaytoilet.setOnClickListener {
            binding.btnPaytoilet.setBackgroundResource(R.drawable.shape_home_topbtn_blue)
            binding.btnPaytoilet.setTextColor(ContextCompat.getColor(com.umc.chamma.config.App.context(),R.color.white))

            binding.btnFreetoilet.setBackgroundResource(R.drawable.shape_home_topbtn_white)
            binding.btnFreetoilet.setTextColor(ContextCompat.getColor(com.umc.chamma.config.App.context(),R.color.chamma_gray))

            binding.btnAlltoilet.setBackgroundResource(R.drawable.shape_home_topbtn_white)
            binding.btnAlltoilet.setTextColor(ContextCompat.getColor(com.umc.chamma.config.App.context(),R.color.chamma_gray))
            toiletState = "paid"
            removeMarker()
            HomeService(this).getNearToilet(toiletState
                ,naverMap.cameraPosition.target.longitude
                ,naverMap.cameraPosition.target.latitude)
        }

    }
    
    // marker 찍는 메소드
    private fun setMarker(data : NearToiletData, type : markerType){

        val marker = Marker()
        marker.position = LatLng(data.latitude,data.longitude)

        marker.icon = if(type == markerType.FREE) OverlayImage.fromResource(R.drawable.home_marker_freetoilet)
        else if(type == markerType.PAID) OverlayImage.fromResource(R.drawable.home_marker_paytoilet)
        else OverlayImage.fromResource(R.drawable.home_marker_freetoilet)

        marker.map = naverMap

        marker.setOnClickListener {
            marker.icon = if(type == markerType.FREE) OverlayImage.fromResource(R.drawable.home_marker_clicked_freetoilet)
            else if(type == markerType.PAID) OverlayImage.fromResource(R.drawable.home_marker_clicked_paytoilet)
            else OverlayImage.fromResource(R.drawable.home_marker_clicked_freetoilet)

            val bottomsheet = BottomSheet.homeToiletInfo(requireContext(),data, {startActivity(Intent(App.context(),QRActivity::class.java).putExtra("ID",data.restroomId?.toInt()))}) {id->
                val intent = Intent(App.context(), RestroomInfoActivity::class.java)
                    .putExtra("ID",id)
                startActivity(intent)
            }

            bottomsheet.apply{
                window?.run {
                    setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                    setDimAmount(0f)
                }
            }

            bottomsheet.setOnDismissListener {
                marker.icon = if(type == markerType.FREE) OverlayImage.fromResource(R.drawable.home_marker_freetoilet)
                else if(type == markerType.PAID) OverlayImage.fromResource(R.drawable.home_marker_paytoilet)
                else OverlayImage.fromResource(R.drawable.home_marker_freetoilet)
            }

            bottomsheet.show()


            true
        }

        markerList.add(marker)
    }


    override fun onGetNearToiletSuccess(result : NearToiletResponse) {
        result.data.forEach{
            if(it.publicOrPaid == "public") setMarker(it, markerType.FREE)
            else setMarker(it, markerType.PAID)
        }
    }

    override fun onGetUserInfoSuccess(data: UserinfoData) {
        binding.btnPoint.text = data.point.toString() + "P"
    }

    override fun onGetUserInfoFailure(message: String) {
        showCustomToast(message)
    }

    override fun onGetNearToiletFailure(message : String) {
        showCustomToast(message)
    }

    private fun removeMarker(){
        markerList.forEach{it.map = null}
        markerList.clear()
    }

    private fun getDistance(lat1 : Double, lon1 : Double, lat2 : Double, lon2 : Double) : Int{
        val R = 6372.8 * 1000
        val dLat = Math.toRadians(lat2 - lat1)
        val dLon = Math.toRadians(lon2 - lon1)
        val a = sin(dLat / 2).pow(2.0) + sin(dLon / 2).pow(2.0) * cos(Math.toRadians(lat1)) * cos(Math.toRadians(lat2))
        val c = 2 * asin(sqrt(a))
        return (R * c).toInt()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mapView.onDestroy()
    }


}