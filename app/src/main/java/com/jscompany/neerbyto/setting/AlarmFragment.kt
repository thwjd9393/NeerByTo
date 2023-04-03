package com.jscompany.neerbyto.setting

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import android.widget.Toolbar.OnMenuItemClickListener
import androidx.appcompat.widget.Toolbar
import com.jscompany.neerbyto.R
import com.jscompany.neerbyto.databinding.FragmentSettingAlarmBinding
import com.jscompany.neerbyto.databinding.FragmentSettingMainBinding

class AlarmFragment : Fragment() {

    private val binding by lazy { FragmentSettingAlarmBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //앱 타이틀
        val toolbar: Toolbar = view.findViewById(R.id.toolbar) // 상단바
        toolbar.setTitle(R.string.alarm)
        toolbar.setNavigationIcon(R.drawable.ic_action_back)

        //뒤로가기
        toolbar.setNavigationOnClickListener {
//            requireActivity().supportFragmentManager.beginTransaction()
//                .remove(this).commit()
            requireActivity().supportFragmentManager.popBackStack()
        }

        checkSwitch()
    }

    private fun checkSwitch() {
        binding.switchAlram.setOnCheckedChangeListener { compoundButton, b ->
            if (b) Toast.makeText(activity, "스위치 온", Toast.LENGTH_SHORT).show()
            else Toast.makeText(activity, "스위치 오프", Toast.LENGTH_SHORT).show()
        }
    }


}