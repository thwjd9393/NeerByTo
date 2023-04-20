package com.jscompany.neerbyto.setting

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jscompany.neerbyto.Common
import com.jscompany.neerbyto.R
import com.jscompany.neerbyto.RetrofitBaseUrl
import com.jscompany.neerbyto.databinding.FragmentSettingTermsPersonalBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [SettingTermsPersonalFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SettingTermsPersonalFragment : Fragment() {

    private val binding : FragmentSettingTermsPersonalBinding by lazy { FragmentSettingTermsPersonalBinding.inflate(layoutInflater) }

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
            .loadFnaDetail(Common.TERMS_PERSONAL).enqueue(object : Callback<MutableList<TermsVO>> {
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