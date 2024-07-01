package com.example.locationapp

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.os.Looper
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.maps.model.LatLng
import java.util.Locale

class LocationUtils(val context:Context) {
//애플리케이션에서  위치 권한을 확인하는  유틸리티 클래스

    private val _fusedLocationClient:FusedLocationProviderClient
    = LocationServices.getFusedLocationProviderClient(context)

    @SuppressLint("MissingPermission")
    fun requestLocationUpdates(viewModel: LocationViewModel){
        val locationCallback = object : LocationCallback(){
            //객체를 상속받지 않고 override가 가능하다.
            override fun onLocationResult(locationResult: LocationResult) {
                super.onLocationResult(locationResult)
                locationResult.lastLocation?.let {
                    val location = LocationData(latitude = it.latitude,
                        longitude =  it.longitude)
                    viewModel.updateLocation(location)
                }
            }
        }
        val locationRequest = LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY,
            1000).build()
        _fusedLocationClient.requestLocationUpdates(locationRequest,locationCallback, Looper.getMainLooper())
    }

    fun hasLocationPermission(context:Context) :Boolean{
        //위치 권한 엑세스 여부에 대한 함수 내가 엑세스 권한을 가지고 있으면 true
        return (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED
                &&
                ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION)
                == PackageManager.PERMISSION_GRANTED
                )


    }

    fun reverseGeocodeLocation(location:LocationData):String{
        val geocoder = Geocoder(context, Locale.KOREA)
        val coordinate = LatLng(location.latitude,location.longitude)
        val addresses:MutableList<Address>? =
            geocoder.getFromLocation(coordinate.latitude,coordinate.longitude,1)
        return if(addresses?.isNotEmpty() == true){
            addresses[0].getAddressLine(0)
        }
        else{
            "Address not found"
        }
    }
}