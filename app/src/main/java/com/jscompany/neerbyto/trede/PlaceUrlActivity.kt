package com.jscompany.neerbyto.trede

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.webkit.WebChromeClient
import android.webkit.WebViewClient
import com.jscompany.neerbyto.R
import com.jscompany.neerbyto.databinding.ActivityPlaceUrlBinding

class PlaceUrlActivity : AppCompatActivity() {

    val binding : ActivityPlaceUrlBinding by lazy { ActivityPlaceUrlBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        //웹뷰
        binding.webView.webViewClient = WebViewClient()

        binding.webView.webChromeClient = WebChromeClient()

        binding.webView.settings.javaScriptEnabled = true

        val place_url : String = intent.getStringExtra("place_url") ?: ""
        binding.webView.loadUrl(place_url)
    }
    override fun onBackPressed() {

        if (binding.webView.canGoBack()) binding.webView.goBack() //돌아갈게 있으면 뒤로가
        else super.onBackPressed()

    }

}