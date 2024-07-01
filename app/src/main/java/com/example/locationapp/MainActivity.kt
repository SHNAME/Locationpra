package com.example.locationapp

import android.content.Context
import android.os.Bundle
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
import androidx.compose.ui.tooling.preview.Preview
import com.example.locationapp.ui.theme.LocationAppTheme
import android.Manifest

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LocationAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                }
            }
        }
    }
}

@Composable
fun LocationDisplay(locationUtils: LocationUtils,context: Context){
    //권한 런처 요청
    val requestPermissionLauncher = rememberLauncherForActivityResult(
        contract =ActivityResultContracts.RequestMultiplePermissions(),
        onResult = { permissions ->if(permissions[Manifest.permission.ACCESS_COARSE_LOCATION] == true
            &&permissions[Manifest.permission.ACCESS_FINE_LOCATION] == true )
        {//TRUE라면 위치 권한이 승인이 되어 있는 것

        }
            else
        {

        }

        })




    Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center)
    {
        Text(text = "Location not available")
        Button(onClick = {
            if(locationUtils.hasLocationPermission(context)){
                //앱에 위치 권한이 승인이 되었다면

            }
            else//그렇지 않은 경우 위치를 업데이트 해야 한다.
            {

            }

        }) {
            Text(text ="Get Location")

        }

    }

}