package com.jscompany.neerbyto.profile

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.jscompany.neerbyto.R
import com.jscompany.neerbyto.databinding.ActivityMannerEstimateBadBinding

class MannerEstimateBadActivity : AppCompatActivity() {

    private val binding : ActivityMannerEstimateBadBinding by lazy { ActivityMannerEstimateBadBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}