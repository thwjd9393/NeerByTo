package com.jscompany.neerbyto.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jscompany.neerbyto.Common
import com.jscompany.neerbyto.R
import com.jscompany.neerbyto.RetrofitBaseUrl
import com.jscompany.neerbyto.databinding.FragmentMannerBadBinding
import com.jscompany.neerbyto.databinding.FragmentMyWriteEndBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MyWriteEndFragment : Fragment() {

    private val binding : FragmentMyWriteEndBinding by lazy { FragmentMyWriteEndBinding.inflate(layoutInflater) }

    //아답터
    var writeItems : MutableList<MyWriteItem> = mutableListOf()
    lateinit var writeAdapter : MyWriteFragAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        dataLoad()
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
        binding.recyclerMyWriteEnd.adapter = writeAdapter

    }

    private fun dataLoad() {
        RetrofitBaseUrl.getRetrofitInstance(Common.dotHomeUrl).create(MyLikeService::class.java)
            .loadMyTredeData(Common.getUserNo(requireActivity()), Common.STATUS_END)
            .enqueue(object : Callback<MutableList<MyWriteItem>> {
                override fun onResponse(
                    call: Call<MutableList<MyWriteItem>>,
                    response: Response<MutableList<MyWriteItem>>
                ) {
                    val items : MutableList<MyWriteItem> = response.body() ?: mutableListOf()

                    for(i in 0 until items.size){
                        writeItems.add(items[i])
                    }
                    writeAdapter.notifyItemInserted(writeItems.size-1) //마지막 번호가 추가 됐다 알려주기
                }

                override fun onFailure(call: Call<MutableList<MyWriteItem>>, t: Throwable) {
                    Common.makeToast(requireActivity(),getString(R.string.response_server_error))
                }
            })
    }

}