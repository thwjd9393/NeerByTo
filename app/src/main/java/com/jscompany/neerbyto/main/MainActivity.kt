package com.jscompany.neerbyto.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import com.jscompany.neerbyto.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val binding:ActivityMainBinding = ActivityMainBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)



    }
}