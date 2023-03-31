package com.jscompany.neerbyto.trede

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import com.jscompany.neerbyto.R
import com.jscompany.neerbyto.databinding.FragmentReportUserBinding

class ReportUserFragment : Fragment() {

    private val binding : FragmentReportUserBinding by lazy { FragmentReportUserBinding.inflate(layoutInflater) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //툴바 적용
        var toolbar = view.findViewById<Toolbar>(R.id.toolbar)
        toolbar.title = getString(R.string.report_btn)
        
    }
}