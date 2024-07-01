package com.example.locationapp

import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import android.Manifest

class LocationUtils(val context:Context) {
//애플리케이션에서  위치 권한을 확인하는  유틸리티 클래스

    fun hasLocationPermission(context:Context) :Boolean{
        //위치 권한 엑세스 여부에 대한 함수 내가 엑세스 권한을 가지고 있으면 true
        return (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED
                &&
                ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION)
                == PackageManager.PERMISSION_GRANTED
                )


    }
}