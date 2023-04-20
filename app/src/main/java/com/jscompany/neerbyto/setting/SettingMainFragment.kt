package com.jscompany.neerbyto.setting

import android.annotation.SuppressLint
import android.content.Intent
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
import com.google.android.gms.oss.licenses.OssLicensesMenuActivity
import com.jscompany.neerbyto.Common
import com.jscompany.neerbyto.R
import com.jscompany.neerbyto.RetrofitBaseUrl
import com.jscompany.neerbyto.databinding.FragmentSettingMainBinding
import com.jscompany.neerbyto.main.MainActivity
import com.jscompany.neerbyto.profile.MainMyZoneFragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //앱 타이틀
        val toolbar: Toolbar = view.findViewById(R.id.toolbar) // 상단바
        toolbar.setTitle(R.string.setting)
        toolbar.setNavigationIcon(R.drawable.ic_action_back)


        //뒤로가기
        toolbar.setNavigationOnClickListener {
            requireActivity().finish()
        }

        //클릭 이벤트
        binding.btnAlarm.setOnClickListener { clickAlarm() }
        binding.btnServiceTerms.setOnClickListener { clickTerms() }
        binding.btnOpenlicense.setOnClickListener { clickOpenlicense() }
        binding.btnWithdraw.setOnClickListener { clickWithdraw() }
        
        //버전 셋팅
        setVersionData()
        binding.btnVersion.setOnClickListener { clickVersion() }
        
    }

    private fun clickAlarm() {
        changFragment(R.id.btn_alarm)
    }

    private fun clickTerms() {
        changFragment(R.id.btn_service_terms)
    }

    private fun clickOpenlicense() {
        //changFragment(R.id.btn_openlicense)

        startActivity(Intent(requireActivity(), OssLicensesMenuActivity::class.java))

        // ActionBar의 title 변경
        OssLicensesMenuActivity.setActivityTitle("오픈소스 라이선스")
    }

    private fun clickVersion() {
        //구글 플레이로 가기
        Toast.makeText(activity, "구글플레이 열기", Toast.LENGTH_SHORT).show()
    }

    //탈퇴
    private fun clickWithdraw() {
        changFragment(R.id.btn_withdraw)
    }

    //플래그먼트 변경 함수
    private fun changFragment(tvid : Int) {
        val targetFragment = getFragment(tvid)
        val transaction =requireActivity().supportFragmentManager.beginTransaction()

        transaction.replace(R.id.container_fragment, targetFragment)
        transaction.addToBackStack(null);

        transaction.commit()
    }

    //플래그먼트 화면 반환 함수
    private fun getFragment(tvId : Int) : Fragment {

        val fragment = when(tvId) {
            R.id.btn_alarm -> AlarmFragment()
            R.id.btn_service_terms -> TermsFragment()
            //R.id.btn_openlicense -> OpenLicenseFragment()
            R.id.btn_withdraw -> WithdrawFragment()
            else -> throw IllegalArgumentException("not found menu item id")
        }
        return  fragment
    }

    private fun setVersionData(){
        RetrofitBaseUrl.getRetrofitInstance(Common.dotHomeUrl).create(SettingService::class.java)
            .loadVersionData().enqueue(object : Callback<String>{
                override fun onResponse(call: Call<String>, response: Response<String>) {
                    val result = response.body() ?: ""
                    binding.btnVersion.text = "${getString(R.string.app_version)} ${result}"
                }

                override fun onFailure(call: Call<String>, t: Throwable) {
                    Common.makeToast(requireActivity(),getString(R.string.response_server_error))
                }
            })
    }

}