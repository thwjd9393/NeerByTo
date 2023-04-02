package com.jscompany.neerbyto.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jscompany.neerbyto.databinding.FragmentMannerGoodBinding
import com.jscompany.neerbyto.databinding.FragmentMyWriteIngBinding


class MyWriteIngFragment : Fragment() {

    private val binding : FragmentMyWriteIngBinding by lazy { FragmentMyWriteIngBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return binding.root
    }

}