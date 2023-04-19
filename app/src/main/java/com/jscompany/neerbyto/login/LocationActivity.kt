package com.jscompany.neerbyto.login

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.jscompany.neerbyto.Common
import com.jscompany.neerbyto.KakaoSearchPlaceVO
import com.jscompany.neerbyto.R
import com.jscompany.neerbyto.RetrofitApiService
import com.jscompany.neerbyto.RetrofitBaseUrl
import com.jscompany.neerbyto.databinding.ActivityLocationBinding
import com.jscompany.neerbyto.main.MainActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import java.time.LocalDate

class LocationActivity : AppCompatActivity() {

    val binding:ActivityLocationBinding by lazy { ActivityLocationBinding.inflate(layoutInflater) }

    var myLocation : Location? = null //null로 주면 서울 시청

    // 검색명
    var searchQuery : String = ""
    var page : String = "1"

    //내 위치 얻어오기
    val providerClient : FusedLocationProviderClient by lazy { LocationServices.getFusedLocationProviderClient(this) }

    //검색결과 응답객체 참조변수
    var searchAdressResponce : KakaoSearchPlaceVO? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        //버튼 클릭 시 GPS 읽아오기
        binding.btnLocation.setOnClickListener { findByGps() }

        binding.etSearch.setOnEditorActionListener { textView, i, keyEvent ->
            searchQuery = binding.etSearch.text.toString()

            //카카오 검색 API를 이용하여 장소를 검색하기
            if (searchQuery != "") searchPlace()
            else Common.makeToast(this,"검색어를 입력해주세요")

            false
        }

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

        Log.i("TAG","GPS : ${myLocation?.latitude} , ${myLocation?.longitude}")

        Common.latitude = myLocation?.latitude.toString()
        Common.longitude = myLocation?.longitude.toString()

        //쉐어드에 저장
        sharedPreferences(myLocation?.latitude.toString(), myLocation?.longitude.toString())

        //위치 얻었으면 화면이동
        if(myLocation != null ) moveToMain ()
    }

    private fun sharedPreferences (latitude : String, longitude : String){
        val pref = getSharedPreferences("Data", MODE_PRIVATE)
        val editor = pref.edit()

        editor.putString("latitude",latitude); //경도
        editor.putString("longitude",longitude); //위도

        editor.commit()
    }

    private val locationCallback : LocationCallback = object : LocationCallback() {
        override fun onLocationResult(p0: LocationResult) {
            super.onLocationResult(p0)

            myLocation = p0.lastLocation

            providerClient.removeLocationUpdates(this)
        }
    }

    //검색으로 찾기
    private fun searchPlace() {
        val header : String = getString(R.string.KakaoAuthorization)

        val retrofit : Retrofit = RetrofitBaseUrl.getRetrofitInstance(Common.kakaoBaseUrl)

        val retrofitApiService = retrofit.create(RetrofitApiService::class.java)
        retrofitApiService.searchPlace(header,searchQuery, page).enqueue(object : Callback<KakaoSearchPlaceVO>{
            override fun onResponse(
                call: Call<KakaoSearchPlaceVO>,
                response: Response<KakaoSearchPlaceVO>
            ) {
                searchAdressResponce = response.body()

                binding.emptyWarp.visibility = View.GONE
                binding.locationRecycler.visibility = View.VISIBLE

                //리스트 다시 만들기
                binding.locationRecycler.adapter =
                    searchAdressResponce?.let { LocalAddressRecyclerAdaper(this@LocationActivity, it.documents) }

            }

            override fun onFailure(call: Call<KakaoSearchPlaceVO>, t: Throwable) {
                Common.makeToast(this@LocationActivity, getString(R.string.response_server_error))
                Log.i("TAG", t.toString())

                binding.emptyWarp.visibility = View.VISIBLE
                binding.locationRecycler.visibility = View.GONE
            }
        })

    }


    private fun moveToMain (){
        startActivity(Intent(this,MainActivity::class.java))
        finish()
    }

}