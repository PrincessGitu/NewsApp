package com.example.newsapptask.ui.activity

import android.os.Bundle
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import com.example.newsapptask.R
import com.example.newsapptask.model.Article
import kotlinx.android.synthetic.main.activity_web_news.*


class WebNewsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_news)
        val newData=intent.getSerializableExtra("news") as Article
        webView.apply {
            webViewClient = WebViewClient()
            loadUrl(newData.url!!)
        }
    }
}