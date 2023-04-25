package com.jscompany.neerbyto.profile

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jscompany.neerbyto.Common
import com.jscompany.neerbyto.R
import com.jscompany.neerbyto.RetrofitBaseUrl
import com.jscompany.neerbyto.databinding.FragmentMannerGoodBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MannerGoodFragment : Fragment() {

    private val binding : FragmentMannerGoodBinding by lazy { FragmentMannerGoodBinding.inflate(layoutInflater) }

    //아답터
    lateinit var adapter : MannerGoodAdapter;
    var items : MutableList<GoodItem> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val userNo = arguments?.getString("userNo") ?: ""

        Log.i("TAG", userNo)

        setManner(userNo)
    }

    private fun setManner(userNo:String){
        RetrofitBaseUrl.getRetrofitInstance(Common.dotHomeUrl).create(MyLikeService::class.java)
            .loadGManner(userNo).enqueue(object : Callback<MutableList<GoodItem>>{
                override fun onResponse(
                    call: Call<MutableList<GoodItem>>,
                    response: Response<MutableList<GoodItem>>
                ) {
                    val result : MutableList<GoodItem> = response.body() ?: mutableListOf()

                    if(items.size < 0) return

                    for (i in 0 until result.size) {
                        items.add(GoodItem(result.get(i).userNo, result.get(i).cnt, result.get(i).content))
                    }

                    adapter = MannerGoodAdapter(requireActivity(), items)
                    binding.recyclerMannerGood.adapter = adapter

                    binding.warpEmpty.visibility = View.GONE

                }

                override fun onFailure(call: Call<MutableList<GoodItem>>, t: Throwable) {
                    Common.makeToast(requireActivity(),getString(R.string.response_server_error))
                    Log.i("TAG","${t.message}")
                }
            })
    }

}