package com.umc.chamma.ui.toiletlist


import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.naver.maps.map.LocationTrackingMode
import com.umc.chamma.R
import com.umc.chamma.config.App
import com.umc.chamma.config.BaseFragmentVB
import com.umc.chamma.databinding.FragmentToiletListBinding
import com.umc.chamma.ui.home.main.HomeFragmentInterface
import com.umc.chamma.ui.home.main.HomeService
import com.umc.chamma.ui.home.model.NearToiletResponse
import com.umc.chamma.ui.home.restroomInfo.RestroomInfoActivity
import com.umc.chamma.ui.main.MainActivity
import com.umc.chamma.ui.search.adapter.RecentKeywordAdapter
import com.umc.chamma.ui.toiletlist.adapter.ToiletListAdapter
import com.umc.chamma.util.BottomSheet
import com.umc.chamma.util.Constants
import com.umc.chamma.util.Constants.TAG


class ToiletlistFragment : BaseFragmentVB<FragmentToiletListBinding>(FragmentToiletListBinding::bind, R.layout.fragment_toilet_list), HomeFragmentInterface{

    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private var sortType = 0
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
       super.onViewCreated(view, savedInstanceState)

        setLocation()
        setBtnListener()

   }

    private fun setLocation(){
        val locationManager = App.context().getSystemService(Context.LOCATION_SERVICE) as LocationManager

        if(!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
            showCustomToast("위치 정보를 켜주세요")
            startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
        }else{
            if(ActivityCompat.checkSelfPermission(App.context(), Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(App.context(), Manifest.permission.ACCESS_COARSE_LOCATION)
                == PackageManager.PERMISSION_GRANTED
            ) {
                fusedLocationClient = LocationServices.getFusedLocationProviderClient(activity as MainActivity)
                fusedLocationClient.lastLocation.addOnSuccessListener { location : Location? ->
//                    HomeService(this).getNearToilet("entire", location!!.longitude, location.latitude)

                    //TODO 인천에 화장실 데이터 없어서 하드코딩 테스트
                    HomeService(this).getNearToilet("paid", 126.9731649095934, 37.560444374518106)
                }
            }else{
                ActivityCompat.requestPermissions((activity as MainActivity), arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION), Constants.RC_PERMISSION)
            }

        }
    }
    
    private fun setBtnListener(){
        binding.btnSort.setOnClickListener { 
            BottomSheet.toiletlistSort(requireContext(), sortType){type->
                // TODO 0 : 거리순 1 : 별점높은순 2 : 별점낮은순 으로 API 호출. 아직 API 구현안됨
                when(type){
                    0->{
                        binding.btnSort.text = "거리순 "
                        sortType = 0
                    }
                    1->{
                        binding.btnSort.text = "별점높은순 "
                        sortType = 1
                    }
                    2->{
                        binding.btnSort.text = "별점낮은순 "
                        sortType = 2
                    }
                }
            }.show()
        }


        binding.btnRangeFilter.setOnClickListener {
            startActivity(Intent(App.context(),ToiletlistFilterActivity::class.java))
        }
    }
    
    private fun clickItem(toiletId : Int){
        val intent = Intent(App.context(),RestroomInfoActivity::class.java)
            .putExtra("ID",toiletId)
        startActivity(intent)
    }

    override fun onGetNearToiletSuccess(result: NearToiletResponse) {

        val adapter = ToiletListAdapter(result.data,::clickItem)
        binding.recycler.adapter = adapter
        binding.recycler.layoutManager = LinearLayoutManager(App.context())
    }

    override fun onGetNearToiletFailure(message: String) {
    }



}