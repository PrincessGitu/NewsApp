package com.example.newsapptask.ui.fragment

import android.os.Bundle
import android.view.View
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.newsapptask.R
import kotlinx.android.synthetic.main.fragmnet_web_view_news.*

/**
 * Created by Gitanjali Ghangale on 5/24/2021.
 */

class WebViewNewsFragment:Fragment(R.layout.fragmnet_web_view_news) {

    private val args: WebViewNewsFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val getNews = args.news
        webView.apply {
            webViewClient = WebViewClient()
            loadUrl(getNews.url!!)
        }
    }

}