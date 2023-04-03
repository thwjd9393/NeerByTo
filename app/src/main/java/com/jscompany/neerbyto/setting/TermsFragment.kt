package com.jscompany.neerbyto.setting

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.jscompany.neerbyto.R
import com.jscompany.neerbyto.databinding.FragmentSettingTermsBinding

class TermsFragment : Fragment() {

    private val binding : FragmentSettingTermsBinding by lazy { FragmentSettingTermsBinding.inflate(layoutInflater) }

    private val tabTerms : TabLayout by lazy { binding.tabTermsWarp }

    private val pager : ViewPager2 by lazy { binding.pagerTermsWarp }

    private var tabTitle = arrayOf("서비스 이용 약관","개인정보 처리방침","위치기반서비스 이용약관")

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
        toolbar.setTitle(R.string.service_terms)
        toolbar.setNavigationIcon(R.drawable.ic_action_back)

        //뒤로가기
        toolbar.setNavigationOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }
        
        //탭 레이아웃과  뷰페이저
        //1. 아답터
        val termsAdapter = SettingTermsAdapter(requireActivity())

        //2. 플래그먼트 add
        termsAdapter.addFragment(SettingTermsServiceFragment())
        termsAdapter.addFragment(SettingTermsPersonalFragment())
        termsAdapter.addFragment(SettingTermsLocationFragment())

        //3. 페이저랑 연결
        pager.adapter = termsAdapter

        //4. 프래그먼트 페이지 붙이기
        pager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
            }
        })

        //5. 탭레이아웃이랑 연동
        TabLayoutMediator(tabTerms,pager) { tab, position ->
            tab.text = tabTitle.get(position).toString()
        }.attach()

    }

}