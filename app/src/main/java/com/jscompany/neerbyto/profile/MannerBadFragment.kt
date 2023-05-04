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
import com.jscompany.neerbyto.databinding.FragmentMannerBadBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MannerBadFragment : Fragment() {

    private val binding : FragmentMannerBadBinding by lazy { FragmentMannerBadBinding.inflate(layoutInflater) }

    //아답터
    lateinit var adapter : MannerBadAdapter;
    var items : MutableList<BadItem> = mutableListOf()

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
            .loadBManner(userNo).enqueue(object : Callback<MutableList<BadItem>> {
                override fun onResponse(
                    call: Call<MutableList<BadItem>>,
                    response: Response<MutableList<BadItem>>
                ) {
                    val result : MutableList<BadItem> = response.body() ?: mutableListOf()

                    if(items.size < 0) return

                    for (i in 0 until result.size) {
                        items.add(BadItem(result.get(i).userNo, result.get(i).cnt, result.get(i).content))
                    }

                    adapter = MannerBadAdapter(requireActivity(), items)
                    binding.recyclerMannerBad.adapter = adapter

                    binding.warpEmpty.visibility = View.GONE
                }

                override fun onFailure(call: Call<MutableList<BadItem>>, t: Throwable) {
                    Common.makeToast(requireActivity(),getString(R.string.response_server_error))
                    Log.i("TAG","${t.message}")
                }
            })
    }

}