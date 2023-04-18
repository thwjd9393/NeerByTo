package com.jscompany.neerbyto.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.jscompany.neerbyto.R
import com.jscompany.neerbyto.databinding.FragmentMainMyZoneBinding
import com.jscompany.neerbyto.servicecenter.FnaActivity
import com.jscompany.neerbyto.servicecenter.NoticeActivity
import com.jscompany.neerbyto.setting.SettingActivity

class MainMyZoneFragment : Fragment() {

    private val binding: FragmentMainMyZoneBinding by lazy { FragmentMainMyZoneBinding.inflate(layoutInflater) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //액션바 셋팅
        var toolbar :Toolbar = view.findViewById(R.id.toolbar)
        toolbar.setTitle(R.string.myzone)

        init()

    }

    private fun init(){
        //유저 이미지 셋팅
        //binding.userImg.background

        //로그아웃
        binding.btnLogout.setOnClickListener { clickLogout() }

        //내 프로틸
        binding.goProfile.setOnClickListener { clickProfile() }
        
        //작성글
        binding.imgWrite.setOnClickListener { clickWrite() }

        //참여한글
        binding.imgEnter.setOnClickListener { clickEnter() }
        
        //관심
        binding.imgLike.setOnClickListener { clickLike() }
        
        //공지사항
        binding.btnNotice.setOnClickListener { clickNotice() }
        
        //자주 묻는 질문
        binding.btnFna.setOnClickListener { clickFna() }
        
        //앱설정
        binding.btnSetting.setOnClickListener { clickSetting() }
    }

    private fun clickSetting() {
        startActivity(Intent(activity, SettingActivity::class.java))
    }

    private fun clickFna() {
        startActivity(Intent(activity, FnaActivity::class.java))
        //startActivity(Intent(activity, FnaDetailActivity::class.java))
    }

    private fun clickNotice() {
        startActivity(Intent(activity, NoticeActivity::class.java))
        //startActivity(Intent(activity, NoticeDetailActivity::class.java))
    }

    private fun clickLike() {
        startActivity(Intent(activity, MyLikeActivity::class.java))
    }

    private fun clickEnter() {
        startActivity(Intent(activity, MyFriendActivity::class.java))
    }

    private fun clickWrite() {
        startActivity(Intent(activity, MyWriteActivity::class.java))
    }

    private fun clickProfile() {
        startActivity(Intent(activity, ProfileActivity::class.java))
    }

    private fun clickLogout() {
    }

}