package com.jscompany.neerbyto.profile

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.jscompany.neerbyto.R
import com.jscompany.neerbyto.databinding.ActivityMannerEstimateGoodBinding

class MannerEstimateGoodActivity : AppCompatActivity() {

    private val binding : ActivityMannerEstimateGoodBinding by lazy { ActivityMannerEstimateGoodBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}