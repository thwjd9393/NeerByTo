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
import com.jscompany.neerbyto.databinding.FragmentMyWriteIngBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MyWriteIngFragment : Fragment() {

    private val binding : FragmentMyWriteIngBinding by lazy { FragmentMyWriteIngBinding.inflate(layoutInflater) }

    //아답터
    var writeItems : MutableList<MyWriteItem> = mutableListOf()
    lateinit var writeAdapter : MyWriteFragAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val userNo = arguments?.getString("userNo") ?: ""

        dataLoad(userNo)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //아답터
        writeAdapter = MyWriteFragAdapter(requireActivity(), writeItems)
        binding.recyclerMyWriteIng.adapter = writeAdapter

    }

    private fun dataLoad(userNo:String) {
        RetrofitBaseUrl.getRetrofitInstance(Common.dotHomeUrl).create(MyLikeService::class.java)
            .loadMyTredeData(userNo,Common.STATUS_ING)
            .enqueue(object : Callback<MutableList<MyWriteItem>>{
                override fun onResponse(
                    call: Call<MutableList<MyWriteItem>>,
                    response: Response<MutableList<MyWriteItem>>
                ) {
                    val items : MutableList<MyWriteItem> = response.body() ?: mutableListOf()

                    for(i in 0 until items.size){
                        writeItems.add(items[i])
                    }
                    writeAdapter.notifyItemInserted(writeItems.size-1) //마지막 번호가 추가 됐다 알려주기

                    if (writeItems.size == 0) {
                        binding.warpEmpty.visibility = View.VISIBLE
                        binding.recyclerMyWriteIng.visibility = View.INVISIBLE
                    } else {
                        binding.warpEmpty.visibility = View.INVISIBLE
                        binding.recyclerMyWriteIng.visibility = View.VISIBLE
                    }
                }

                override fun onFailure(call: Call<MutableList<MyWriteItem>>, t: Throwable) {
                    Common.makeToast(requireActivity(),getString(R.string.response_server_error))
                }
            })
    }

}