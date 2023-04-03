package com.jscompany.neerbyto.setting

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import com.jscompany.neerbyto.R
import com.jscompany.neerbyto.databinding.FragmentSettingOpenLicenseBinding

class OpenLicenseFragment : Fragment() {

    private val binding : FragmentSettingOpenLicenseBinding by lazy { FragmentSettingOpenLicenseBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //앱 타이틀
        val toolbar: Toolbar = view.findViewById(R.id.toolbar) // 상단바
        toolbar.setTitle(R.string.openlicense)
        toolbar.setNavigationIcon(R.drawable.ic_action_back)

        //뒤로가기
        toolbar.setNavigationOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }

        //리사이클러 뷰  recycler_license
        

    }

}