package com.jscompany.neerbyto.setting

import android.os.Bundle
import android.provider.DocumentsContract.Root
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import com.jscompany.neerbyto.R
import com.jscompany.neerbyto.databinding.FragmentSettingWithdrawBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [WithdrawFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class WithdrawFragment : Fragment() {

    private val binding : FragmentSettingWithdrawBinding by lazy { FragmentSettingWithdrawBinding.inflate(layoutInflater) }

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

        //툴바
        val toolbar: Toolbar = view.findViewById(R.id.toolbar) // 상단바
        toolbar.setTitle(R.string.withdraw)
        toolbar.setNavigationIcon(R.drawable.ic_action_back)

        //뒤로가기
        toolbar.setNavigationOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }

        binding.btnCancle.setOnClickListener { requireActivity().supportFragmentManager.popBackStack() }
        binding.btnWithdraw.setOnClickListener {  }
    }

}