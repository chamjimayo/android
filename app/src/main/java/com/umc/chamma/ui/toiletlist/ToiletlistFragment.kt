package com.umc.chamma.ui.toiletlist


import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.View
import androidx.core.app.ActivityCompat
import com.naver.maps.map.LocationTrackingMode
import com.umc.chamma.R
import com.umc.chamma.config.App
import com.umc.chamma.config.BaseFragmentVB
import com.umc.chamma.databinding.FragmentToiletListBinding
import com.umc.chamma.ui.home.main.HomeFragmentInterface
import com.umc.chamma.ui.home.main.HomeService
import com.umc.chamma.ui.home.model.NearToiletResponse
import com.umc.chamma.ui.main.MainActivity
import com.umc.chamma.util.Constants
import com.umc.chamma.util.Constants.TAG


class ToiletlistFragment : BaseFragmentVB<FragmentToiletListBinding>(FragmentToiletListBinding::bind, R.layout.fragment_toilet_list), HomeFragmentInterface{
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
       super.onViewCreated(view, savedInstanceState)

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
                val loc = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
//                HomeService(this).getNearToilet("entire",loc!!.longitude, loc.latitude)
            }else{
                ActivityCompat.requestPermissions((activity as MainActivity), arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION), Constants.RC_PERMISSION)
            }

        }

   }

    override fun onGetNearToiletSuccess(result: NearToiletResponse) {
        TODO("Not yet implemented")
    }

    override fun onGetNearToiletFailure(message: String) {
        TODO("Not yet implemented")
    }



}