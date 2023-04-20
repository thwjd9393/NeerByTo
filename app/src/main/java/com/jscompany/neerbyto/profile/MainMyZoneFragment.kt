package com.jscompany.neerbyto.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.jscompany.neerbyto.Common
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
        setUserInfo()

        //로그아웃
        binding.btnLogout.setOnClickListener { clickLogout() }

        //내 프로틸
        binding.goProfile.setOnClickListener { clickProfile() }
        
        //작성글
        binding.imgWrite.setOnClickListener { clickWrite() }

        //친구 추가 목록
        binding.imgEnter.setOnClickListener { clickMyFriend() }
        
        //관심
        binding.imgLike.setOnClickListener { clickLike() }
        
        //공지사항
        binding.btnNotice.setOnClickListener { clickNotice() }
        
        //자주 묻는 질문
        binding.btnFna.setOnClickListener { clickFna() }
        
        //앱설정
        binding.btnSetting.setOnClickListener { clickSetting() }
    }

    //유저 정보 셋팅
    private fun setUserInfo() {
        binding.tvNicname.text = Common.getNic(requireActivity())

        var profileImg = ""
        if (Common.PROFILEIMG != "") profileImg = "http://mrhisj23.dothome.co.kr/NeerByTo/"+Common.PROFILEIMG

        Glide.with(requireActivity()).load(profileImg).error(R.drawable.user_line).into(binding.civUserImg)
    }

    //셋팅 화면으로 이동
    private fun clickSetting() {
        startActivity(Intent(activity, SettingActivity::class.java))
    }

    //자주 묻는 질문으로 이동
    private fun clickFna() {
        startActivity(Intent(activity, FnaActivity::class.java))
        //startActivity(Intent(activity, FnaDetailActivity::class.java))
    }

    //공지사항으로 이동
    private fun clickNotice() {
        startActivity(Intent(activity, NoticeActivity::class.java))
        //startActivity(Intent(activity, NoticeDetailActivity::class.java))
    }

    //관심글로 이동
    private fun clickLike() {
        startActivity(Intent(activity, MyLikeActivity::class.java))
    }

    //친구추가 목록 이동
    private fun clickMyFriend() {
        startActivity(Intent(activity, MyFriendActivity::class.java))
    }
    
    //작성글로 이동
    private fun clickWrite() {
        startActivity(Intent(activity, MyWriteActivity::class.java).putExtra("userNo",Common.getUserNo(requireActivity())))
    }

    //프로필 화면으로 이동
    private fun clickProfile() {
        startActivity(Intent(activity, ProfileActivity::class.java).putExtra("userNo",Common.getUserNo(requireActivity())))
    }

    //로그아웃
    private fun clickLogout() {
        Toast.makeText(requireActivity(), "기능구현중", Toast.LENGTH_SHORT).show()
    }

}