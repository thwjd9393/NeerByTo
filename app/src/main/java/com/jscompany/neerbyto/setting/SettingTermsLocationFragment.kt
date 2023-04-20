package com.jscompany.neerbyto.setting

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jscompany.neerbyto.Common
import com.jscompany.neerbyto.R
import com.jscompany.neerbyto.RetrofitBaseUrl
import com.jscompany.neerbyto.databinding.FragmentSettingTermsLocationBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SettingTermsLocationFragment : Fragment() {

    private val binding : FragmentSettingTermsLocationBinding by lazy { FragmentSettingTermsLocationBinding.inflate(layoutInflater) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loadData()

    }

    //데이터 로드
    private fun loadData() {
        RetrofitBaseUrl.getRetrofitInstance(Common.dotHomeUrl).create(SettingService::class.java)
            .loadFnaDetail(Common.TERMS_LOCATION).enqueue(object : Callback<MutableList<TermsVO>>{
                override fun onResponse(
                    call: Call<MutableList<TermsVO>>,
                    response: Response<MutableList<TermsVO>>
                ) {
                    val result = response.body() ?: mutableListOf()

                    setData(result[0])
                }

                override fun onFailure(call: Call<MutableList<TermsVO>>, t: Throwable) {
                    Common.makeToast(requireActivity(),getString(R.string.response_server_error))
                }
            })
    }

    private fun setData(termsVO: TermsVO) {
        binding.tvContent.text = termsVO.content
    }

}