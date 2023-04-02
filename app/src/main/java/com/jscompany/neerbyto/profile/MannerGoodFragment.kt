package com.jscompany.neerbyto.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jscompany.neerbyto.R
import com.jscompany.neerbyto.databinding.FragmentMannerGoodBinding


class MannerGoodFragment : Fragment() {

    private val binding : FragmentMannerGoodBinding by lazy { FragmentMannerGoodBinding.inflate(layoutInflater) }

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

}