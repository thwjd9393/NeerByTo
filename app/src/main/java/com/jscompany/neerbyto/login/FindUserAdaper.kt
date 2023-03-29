package com.jscompany.neerbyto.login

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

//FragmentStateAdapter 상속받은 플래그먼트는 FragmentActivity를 멤버변수로 받아야함
class FindUserAdaper(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {

    var fragments : ArrayList<Fragment> = ArrayList()
    override fun getItemCount(): Int {
        return fragments.size
    }

    override fun createFragment(position: Int): Fragment {
        return fragments[position]
    }

    fun addFragment(fragment: Fragment) { //플래그 먼트 등록
        fragments.add(fragment)
        notifyItemInserted(fragments.size-1)
    }
}