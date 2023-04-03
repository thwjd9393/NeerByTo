package com.jscompany.neerbyto.trede

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.ActionMode
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.jscompany.neerbyto.R
import com.jscompany.neerbyto.databinding.FragmentMainTredeBinding

class MainTredeFragment : Fragment() {

    //private val binding:FragmentMainTredeBinding by lazy { FragmentMainTredeBinding.inflate(layoutInflater) }

    //싱글턴으로 Fragment 바인딩 하는 방법...
    private var fbinding: FragmentMainTredeBinding? = null
    private val binding get() = fbinding!!

    private val builder  : AlertDialog.Builder by lazy { AlertDialog.Builder(requireActivity()) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        fbinding = FragmentMainTredeBinding.inflate(inflater,container,false)

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
                        Toast.makeText(requireActivity(), items[which], Toast.LENGTH_SHORT).show()
                        binding.tvSelectCategory.text = "' ${items[which]} '"
                    }).create().show()

                    true
                }
                else -> false
            }
        }

        //글쓰기 버튼
        binding.btnWrite.setOnClickListener { clickTredrWrite() }


    }

    private var items: Array<String> = arrayOf("전체","만나서 장보기","대용량 나누기","무료나눔")

    private fun clickTredrWrite() {
        startActivity(Intent(activity,TredeWriteActivity::class.java))
    }



    override fun onDestroyView() {
        super.onDestroyView()
        fbinding = null //메모리 해제
    }
}