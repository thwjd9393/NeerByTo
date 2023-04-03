package com.jscompany.neerbyto.profile

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import com.jscompany.neerbyto.R
import com.jscompany.neerbyto.databinding.ActivityMannerEstimateGoodBinding

class MannerEstimateGoodActivity : AppCompatActivity() {

    private val binding : ActivityMannerEstimateGoodBinding by lazy { ActivityMannerEstimateGoodBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        init()

    }

    private fun init() {

        //툴바 등록
        setSupportActionBar(findViewById(R.id.toolbar))
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setTitle(R.string.manner_good)

        binding.btnOk.setOnClickListener { clickOk() }
        binding.btnCancle.setOnClickListener { finish() }

    }

    private fun clickOk() {
        Toast.makeText(this, "클릭", Toast.LENGTH_SHORT).show()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) finish()
        return super.onOptionsItemSelected(item)
    }
}