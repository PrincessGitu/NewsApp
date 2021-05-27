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
import com.example.newsapptask.utils.InternetCheck.Companion.isNetworkAvailbale
import com.example.newsapptask.viewModel.NewsViewModel
import kotlinx.android.synthetic.main.fragment_technology.*

/**
 * Created by Gitanjali Ghangale on 5/22/2021.
 */
class TechnologyFragment : Fragment(R.layout.fragment_technology) {

    private val TAG = "TechnologyFragment"
    lateinit var newsViewModel: NewsViewModel
    lateinit var newsAdapter: NewsAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.e(TAG,"onViewCreated")

        newsViewModel = ViewModelProvider(requireActivity()).get(NewsViewModel::class.java)
        setupRecyclerView()

        if (isNetworkAvailbale(requireContext())) {
            newsViewModel.getTechnologyNews("us", "Technology")

        } else if (!isNetworkAvailbale(requireContext())) {
            Toast.makeText(requireContext(), getString(R.string.no_internet), Toast.LENGTH_SHORT).show()
        }


        newsViewModel.getAllNews().observe(viewLifecycleOwner,{
          newsAdapter.differ.submitList(it)
        })

        newsAdapter.setOnItemClickListener { article ->
            val bundle = Bundle().apply {
                putSerializable("news", article)
            }
            Intent(requireContext(), WebNewsActivity::class.java).also { intent ->
                intent.putExtra("news", article)
                activity?.startActivity(intent)
            }

            //  val fragmentObj = WebViewNewsFragment()
            //  fragmentObj.arguments = bundle
            // activity?.supportFragmentManager?.beginTransaction()?.replace(R.id.fragment_frame,fragmentObj)?.commit()
        }

    }//onViewCreated


    override fun onResume() {
        super.onResume()
        Log.e(TAG,"onResume")
    }

    private fun setupRecyclerView() {
        newsAdapter = NewsAdapter(newsViewModel, "TechnologyFragment")
        rvTechnologyNews.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(activity)
            rvTechnologyNews.addItemDecoration(DividerItemDecoration(
                    rvTechnologyNews.context, (rvTechnologyNews.layoutManager as LinearLayoutManager).orientation))
        }
    }
}