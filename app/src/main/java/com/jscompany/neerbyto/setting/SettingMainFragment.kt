package com.jscompany.neerbyto.setting

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.GestureDetector
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.jscompany.neerbyto.R
import com.jscompany.neerbyto.databinding.FragmentSettingMainBinding

class SettingMainFragment : Fragment(){

    private val binding:FragmentSettingMainBinding by lazy { FragmentSettingMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return binding.root
    }

    lateinit var detector : GestureDetector

    lateinit var selectTv : TextView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //앱 타이틀
        val toolbar: Toolbar = view.findViewById(R.id.toolbar) // 상단바
        toolbar.setTitle(R.string.setting)
        toolbar.setNavigationIcon(R.drawable.ic_action_back)


//        binding.contentWarp.setOnTouchListener { v, event ->
//
//            binding.contentWarp.requestDisallowInterceptTouchEvent(true)
//            when(event.action) {
//                MotionEvent.ACTION_DOWN -> {
//                    Toast.makeText(activity, "${}", Toast.LENGTH_SHORT).show()
//                }
//            }
//            false
//        }

    }



    @SuppressLint("SetTextI18n")
    private fun clickTv(tvItem : TextView){
        tvItem.setOnClickListener {
            if (it == binding.btnAlarm) clickAlarm()
            else if(it == binding.btnServiceTerms) clickTerms()
            else if(it == binding.btnOpenlicense) clickOpenlicense()
            else if(it == binding.btnVersion) binding.btnVersion.text = "${R.string.app_version} 1.0"
            else if(it == binding.btnWithdraw) clickWithdraw()
        }
    }

    private fun clickAlarm() {
        Toast.makeText(activity, "알람 클릭", Toast.LENGTH_SHORT).show()
    }

    private fun clickTerms() {
        Toast.makeText(activity, "이용약관 클릭", Toast.LENGTH_SHORT).show()
    }

    private fun clickOpenlicense() {
        Toast.makeText(activity, "오플 라이센스 클릭", Toast.LENGTH_SHORT).show()
    }

    //탈퇴
    private fun clickWithdraw() {
        Toast.makeText(activity, "탈퇴하기 클릭", Toast.LENGTH_SHORT).show()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        //if (item.itemId == android.R.id.home) requireActivity().finish()
        if (item.itemId == android.R.id.home) requireActivity().finish()
        return super.onOptionsItemSelected(item)
    }

}