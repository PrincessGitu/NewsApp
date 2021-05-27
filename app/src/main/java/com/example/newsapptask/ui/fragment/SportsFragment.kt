package com.example.newsapptask.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsapptask.R
import com.example.newsapptask.adpater.NewsAdapter
import com.example.newsapptask.ui.activity.WebNewsActivity
import com.example.newsapptask.utils.InternetCheck
import com.example.newsapptask.utils.Resource
import com.example.newsapptask.viewModel.NewsViewModel
import kotlinx.android.synthetic.main.fragmnet_sports.*


/**
 * Created by Gitanjali Ghangale on 5/24/2021.
 */
class SportsFragment:Fragment(R.layout.fragmnet_sports) {

    private val TAG = "SportsFragment"
    lateinit var newsViewModel: NewsViewModel
    lateinit var newsAdapter: NewsAdapter
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Log.e(TAG,"onViewCreated")
        super.onViewCreated(view, savedInstanceState)
        newsViewModel = ViewModelProvider(requireActivity()).get(NewsViewModel::class.java)
        setupRecyclerView()



        newsViewModel.topHealthNews.observe(
            viewLifecycleOwner, { newsResponse ->
                when (newsResponse) {
                    is Resource.Success -> {
                        hideProgressBar()
                        newsResponse.data?.let { response ->
                            newsAdapter.differ.submitList(response.articles)
                        }
                    }
                    is Resource.Error -> {
                        hideProgressBar()
                        newsResponse.message?.let { message ->
                            Log.e(TAG, "An error occured: $message")
                        }
                    }
                    is Resource.Loading -> {
                        showProgressBar()
                    }
                }
            })


        newsAdapter.setOnItemClickListener { article ->
            Intent(requireContext(), WebNewsActivity::class.java).also { intent ->
                intent.putExtra("news", article)
                activity?.startActivity(intent)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        Log.e(TAG,"onResume")
        if (InternetCheck.isNetworkAvailbale(requireContext())) {
            rvSports.visibility=View.VISIBLE
            tvNoRecordSport.visibility=View.GONE
            newsViewModel.getHealthNews("us", "sport")
        } else if (!InternetCheck.isNetworkAvailbale(requireContext())) {
            tvNoRecordSport.visibility=View.VISIBLE
        }
    }

    private fun hideProgressBar() {
        sportProgressBar.visibility = View.INVISIBLE

    }

    private fun showProgressBar() {
        sportProgressBar.visibility = View.VISIBLE

    }

    private fun setupRecyclerView() {
        newsAdapter = NewsAdapter(newsViewModel,"HealthFragment")
        rvSports.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(activity)
            rvSports.addItemDecoration(
                DividerItemDecoration(
                    rvSports.context, (rvSports.layoutManager as LinearLayoutManager).orientation)
            )
        }
    }
}