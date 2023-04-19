package com.jscompany.neerbyto.trede

import android.Manifest
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.jscompany.neerbyto.Common
import com.jscompany.neerbyto.KakaoSearchMyRegion
import com.jscompany.neerbyto.R
import com.jscompany.neerbyto.RetrofitApiService
import com.jscompany.neerbyto.RetrofitBaseUrl
import com.jscompany.neerbyto.databinding.FragmentMainTredeBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class MainTredeFragment : Fragment() {

    private val binding:FragmentMainTredeBinding by lazy { FragmentMainTredeBinding.inflate(layoutInflater) }

    private val builder  : AlertDialog.Builder by lazy { AlertDialog.Builder(requireActivity()) }

    //내 위치 얻어오기
    val providerClient : FusedLocationProviderClient by lazy { LocationServices.getFusedLocationProviderClient(requireActivity()) }
    var myLocation : Location? = null //null로 주면 서울 시청

    //검색결과 응답객체 참조변수
    var searchMyRegionResponce : KakaoSearchMyRegion? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //좌표로 내 위치 가져오기
        var longitude :String = Common.getUserlongitude(requireContext())
        var latitude :String = Common.getUserlatitude(requireContext())

        if(longitude != null && latitude != null){
            searchMyRegion(longitude, latitude, requireActivity())
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //툴바 생성
        val toolbar: Toolbar = view.findViewById(R.id.toolbar) // 상단바
        toolbar.inflateMenu(R.menu.option_trede) // 메뉴xml과 상단바 연결 (프래그먼트xml에서 연결했으면 안해도 됨)

        // 상단바 메뉴 클릭시
        toolbar.setOnMenuItemClickListener{
            when(it.itemId) {
                R.id.option_search -> {
                    //startActivity(Intent(context, SecondActivity::class.java))
                    true
                }
                R.id.option_categoty -> {
                    builder.setItems(items, DialogInterface.OnClickListener { dialog, which ->
                        //Toast.makeText(requireActivity(), items[which], Toast.LENGTH_SHORT).show()
                        binding.tvSelectCategory.text = "' ${items[which]} '"
                    }).create().show()

                    true
                }
                else -> false
            }
        }

        //위치 정보 다시 가져오기
        binding.topFindLocation.setOnClickListener { findByGps()}

        //글쓰기 버튼 - 로그인해야 보이기
        binding.btnWrite.setOnClickListener { clickTredrWrite() }

        //화면에 글 보여주기
        setRecyclerView(requireActivity()) //데이터 로드

        //리프레시
        binding.refreshLayout.setOnRefreshListener { setRecyclerView(requireActivity()); }

    }


    private var items: Array<String> = arrayOf("전체","만나서 장보기","대용량 나누기","무료나눔")

    //private var tredeList : MutableList<TredeVO> = mutableListOf()

    //화면 글 아답터
    private fun setRecyclerView(context: Context){
        Log.i("TAG", "${Common.dong}")

        //대량 데이터 넣기~
        RetrofitBaseUrl.getRetrofitInstance(Common.dotHomeUrl)
            .create(TredeService::class.java).loadTredeData("0",Common.dong.toString())
            .enqueue(object : Callback<MutableList<TredeVO>>{
                override fun onResponse(
                    call: Call<MutableList<TredeVO>>,
                    response: Response<MutableList<TredeVO>>
                ) {
                    var tredeList = response.body()

                    if(tredeList == null) {
                        binding.tvEmpty.visibility = View.VISIBLE
                    }

                    binding.tvEmpty.visibility = View.GONE

                    binding.homeRecycler.adapter = TredeAdapter(context, tredeList!!)

                    binding.refreshLayout.isRefreshing = false
                }

                override fun onFailure(call: Call<MutableList<TredeVO>>, t: Throwable) {
                    Common.makeToast(context,getString(R.string.response_server_error))
                    Log.i("TAG", "setRecyclerView 문제 : ${t.message}")
                }

            })

    }

    private fun searchMyRegion(longitude : String , latitude : String, context: Context) {

        //좌표로 내 지역 찾아오기
        Log.i("TAG", "내 좌표 : ${Common.getUserlatitude(requireActivity())} + ${Common.getUserlongitude(requireActivity())}")

        val header = getString(R.string.KakaoAuthorization)

        val retrofit : Retrofit = RetrofitBaseUrl.getRetrofitInstance(Common.kakaoBaseUrl)

        val retrofitApiService = retrofit.create(RetrofitApiService::class.java)
        retrofitApiService.searchAdress(header, longitude , latitude).enqueue(object : Callback<KakaoSearchMyRegion>{
            override fun onResponse(
                call: Call<KakaoSearchMyRegion>,
                response: Response<KakaoSearchMyRegion>
            ) {
                searchMyRegionResponce = response.body()

                binding.topTvNowlocation.text = searchMyRegionResponce!!.documents[0].region_3depth_name
                Common.dong = searchMyRegionResponce!!.documents[0].region_3depth_name

            }

            override fun onFailure(call: Call<KakaoSearchMyRegion>, t: Throwable) {
                Common.makeToast(context,getString(R.string.response_server_error))
                Log.i("TAG", " 문제 : ${t.message}")
            }
        })
    }

    //GPS로 다시 얻어오기
    private fun findByGps() {

        if (requireActivity().checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_DENIED) {
            permissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        } else {
            requestMyLocation()
        }

    }

    val permissionLauncher : ActivityResultLauncher<String>
            = registerForActivityResult(ActivityResultContracts.RequestPermission(), object :
        ActivityResultCallback<Boolean> {
        override fun onActivityResult(result: Boolean?) {
            if(result!!) requestMyLocation()
            else Common.makeToast(requireActivity(), "위치 정보를 동의하지 않았습니다")
        }
    })

    private fun requestMyLocation(){

        val requestFailReason : LocationRequest = LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY,1000).build()

        if (ActivityCompat.checkSelfPermission(
                requireActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                requireActivity(),
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

        var longitude = Common.getUserlongitude(requireActivity())
        var latitude = Common.getUserlatitude(requireActivity())

        //위치 얻었으면 내 위치 다시 표시
        if(myLocation != null && longitude != null && latitude != null) searchMyRegion(Common.getUserlongitude(requireContext()), Common.getUserlatitude(requireContext()), requireActivity())

    }

    private val locationCallback : LocationCallback = object : LocationCallback() {
        override fun onLocationResult(p0: LocationResult) {
            super.onLocationResult(p0)

            myLocation = p0.lastLocation

            providerClient.removeLocationUpdates(this)
        }
    }

    private fun sharedPreferences (latitude : String, longitude : String){
        val pref = requireActivity().getSharedPreferences("Data", AppCompatActivity.MODE_PRIVATE)
        val editor = pref.edit()

        editor.putString("latitude",latitude); //경도
        editor.putString("longitude",longitude); //위도

        editor.commit()
    }


    private fun clickTredrWrite() {
        startActivity(Intent(activity,TredeWriteActivity::class.java))
    }


}