package com.ibrahimmakashi.newsapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ProgressBar

class DetailActivity : AppCompatActivity() {

    lateinit var webView: WebView
     lateinit var progressBar : ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val url = intent.getStringExtra("URL")
        if (url != null){
            webView = findViewById(R.id.detailWebView)
            progressBar = findViewById(R.id.progressBar2)
            webView.settings.javaScriptEnabled = true
            webView.settings.userAgentString = "Mozilla/5.0 (Linux; Android 13; SM-A205U) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/105.0.5195.136 Mobile Safari/537.36"
            webView.webViewClient = object  : WebViewClient(){
                override fun onPageFinished(view: WebView?, url: String? ) {
                    super.onPageFinished(view, url)
                    progressBar.visibility = View.GONE
                    webView.visibility = View.VISIBLE
                }
            }
            webView.loadUrl(url)
        }
    }
}