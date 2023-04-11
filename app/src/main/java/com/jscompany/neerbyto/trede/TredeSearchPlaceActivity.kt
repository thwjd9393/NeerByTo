package com.jscompany.neerbyto.trede

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.jscompany.neerbyto.Common
import com.jscompany.neerbyto.KakaoSerchPlaceResponce
import com.jscompany.neerbyto.R
import com.jscompany.neerbyto.RetrofitApiService
import com.jscompany.neerbyto.RetrofitBaseUrl
import com.jscompany.neerbyto.databinding.ActivityTredeSearchPlaceBinding
import com.jscompany.neerbyto.login.LocalAddressRecyclerAdaper
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class TredeSearchPlaceActivity : AppCompatActivity() {

    val binding by lazy { ActivityTredeSearchPlaceBinding.inflate(layoutInflater) }

    // 검색명
    var searchQuery : String = ""
    var page : String = "1"

    //검색결과 응답객체 참조변수
    var serchPlaceResponce : KakaoSerchPlaceResponce? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        
        //카카오 검색으로 찾기 연동
        //인텐트로 선택한 값 보내주기
        binding.etSearch.setOnEditorActionListener { textView, i, keyEvent ->
            searchQuery = binding.etSearch.text.toString()

            //카카오 검색 API를 이용하여 장소를 검색하기
            if (searchQuery != "") searchPlace()
            else Common.makeToast(this,"검색어를 입력해주세요")

            false
        }
        
    }

    private fun searchPlace() {

        var header = getString(R.string.KakaoAuthorization)

        //카카오 keword plase serch api 사용 REST API 작업 - Retrofit 사용
        //1. 클래스로 만들어 놓은 RetrofitHelper를 사용
        val retrofit : Retrofit = RetrofitBaseUrl.getRetrofitInstance(Common.kakaoBaseUrl)
        //2. 서비스 객체
        val retrofitApiService =retrofit.create(RetrofitApiService::class.java)
        retrofitApiService.keywordPlace(header, searchQuery, Common.longitude!!, Common.latitude!!, page)
            .enqueue(object : Callback<KakaoSerchPlaceResponce>{
                override fun onResponse(
                    call: Call<KakaoSerchPlaceResponce>,
                    response: Response<KakaoSerchPlaceResponce>
                ) {
                    serchPlaceResponce = response.body()

                    binding.emptyWarp.visibility = View.GONE
                    binding.locationRecycler.visibility = View.VISIBLE

                    //리스트 다시 만들기
                    binding.locationRecycler.adapter =
                        serchPlaceResponce?.let { TredeSearchPlaceAdapter(this@TredeSearchPlaceActivity, it.documents) }

                }

                override fun onFailure(call: Call<KakaoSerchPlaceResponce>, t: Throwable)
                    = Common.makeToast(this@TredeSearchPlaceActivity, "서버에 문제가 있습니다")
            })

    }

}