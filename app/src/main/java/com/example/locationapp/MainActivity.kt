package com.example.locationapp

import android.Manifest
import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.ActivityCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.locationapp.ui.theme.LocationAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val viewModel: LocationViewModel = viewModel()
            LocationAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MyApp(viewModel)

                }
            }
        }
    }
}

@Composable
fun MyApp(viewModel: LocationViewModel){
    val context = LocalContext.current //현재 activity context 요청
    val locationUtils = LocationUtils(context)
    LocationDisplay(locationUtils = locationUtils, context = context,viewModel)
}




@Composable
fun LocationDisplay(locationUtils: LocationUtils,context: Context,
                    viewModel:LocationViewModel){


    val location = viewModel.location.value

    val address = location?.let{
        locationUtils.reverseGeocodeLocation(location)
    }
    //권한 런처 요청
    val requestPermissionLauncher = rememberLauncherForActivityResult(
        contract =ActivityResultContracts.RequestMultiplePermissions(),
        onResult = { permissions ->if(permissions[Manifest.permission.ACCESS_COARSE_LOCATION] == true
            &&permissions[Manifest.permission.ACCESS_FINE_LOCATION] == true )
        {//TRUE라면 위치 권한이 승인된 경우
            locationUtils.requestLocationUpdates(viewModel = viewModel)

        }
            else
        {
            val rationaleRequired = ActivityCompat.shouldShowRequestPermissionRationale(
                context as MainActivity,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) || ActivityCompat.shouldShowRequestPermissionRationale(
                context as MainActivity,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
            
            if(rationaleRequired)//만약 권한 근거를 설명할 필요가 있으면(앱 내부에서 권한 승인이 가능한 경우)
            {
                Toast.makeText(context,"Location Permission is required for this feature to work",
                    Toast.LENGTH_LONG).show()

            }
            else //앱 내부에서 권한 승인이 불가능하고 사용자 휴대폰의 설정에 들어가서 직접 권한을 수락해야하는경우
            {
                Toast.makeText(context,"Location Permission is required Please enable it in the Android Settings",
                    Toast.LENGTH_LONG).show()
                
            }

        }

        })




    Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center)
    {
        if(location != null)
        {
            Text("Address: ${location.latitude} ${location.longitude}\n $address")

        }
        else {
            Text(text = "Location not available")
        }
        Button(onClick = {
            if(locationUtils.hasLocationPermission(context)){
                //앱에 위치 권한이 승인이 되었다면
                locationUtils.requestLocationUpdates(viewModel)
            }
            else//그렇지 않은 경우 위치를 권한을 얻어야한다. 런처사용
            {
                requestPermissionLauncher.launch(arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION
                    ,Manifest.permission.ACCESS_COARSE_LOCATION
                ))


            }

        }) {
            Text(text ="Get Location")

        }

    }

}