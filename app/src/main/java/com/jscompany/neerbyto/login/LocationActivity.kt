package com.jscompany.neerbyto.login

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Looper
import android.widget.Toast
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.jscompany.neerbyto.Common
import com.jscompany.neerbyto.R
import com.jscompany.neerbyto.databinding.ActivityLocationBinding
import com.jscompany.neerbyto.main.MainActivity

class LocationActivity : AppCompatActivity() {

    val binding:ActivityLocationBinding by lazy { ActivityLocationBinding.inflate(layoutInflater) }

    //2) 현재 내 위치 정보 객체
    var myLocation : Location? = null //null로 주면 서울 시청

    //내 위치 얻어오기
    val providerClient : FusedLocationProviderClient by lazy { LocationServices.getFusedLocationProviderClient(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        //버튼 클릭 시 GPS 읽아오기
        binding.btnLocation.setOnClickListener { findByGps() }

    }

    private fun findByGps() {

        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_DENIED) {
            permissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        } else {
            requestMyLocation()
        }

    }

    val permissionLauncher : ActivityResultLauncher<String>
        = registerForActivityResult(ActivityResultContracts.RequestPermission(), object : ActivityResultCallback<Boolean>{
        override fun onActivityResult(result: Boolean?) {
            if(result!!) requestMyLocation()
            else Common.makeToast(this@LocationActivity, "위치 정보를 동의하지 않았습니다")
        }
    })

    private fun requestMyLocation(){

        val requestFailReason : LocationRequest = LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY,1000).build()

        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }

        providerClient.requestLocationUpdates(requestFailReason, locationCallback, Looper.getMainLooper())
    }

    private val locationCallback : LocationCallback = object : LocationCallback() {
        override fun onLocationResult(p0: LocationResult) {
            super.onLocationResult(p0)

            myLocation = p0.lastLocation

            providerClient.removeLocationUpdates(this)
            
            //위치 얻었으면 화면이동
            moveToMain ()
        }
    }

    private fun moveToMain (){
        startActivity(Intent(this,MainActivity::class.java))
        finish()
    }

}