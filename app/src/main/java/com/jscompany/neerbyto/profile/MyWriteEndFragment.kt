package com.jscompany.neerbyto.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jscompany.neerbyto.databinding.FragmentMannerBadBinding
import com.jscompany.neerbyto.databinding.FragmentMyWriteEndBinding


class MyWriteEndFragment : Fragment() {

    private val binding : FragmentMyWriteEndBinding by lazy { FragmentMyWriteEndBinding.inflate(layoutInflater) }

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