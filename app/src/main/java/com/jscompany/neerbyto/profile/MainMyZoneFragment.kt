package com.jscompany.neerbyto.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.jscompany.neerbyto.R
import com.jscompany.neerbyto.databinding.FragmentMainMyZoneBinding
import com.jscompany.neerbyto.databinding.FragmentMainTredeBinding

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


    }



}