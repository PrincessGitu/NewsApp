package com.example.newsapptask.ui.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapptask.R
import com.example.newsapptask.adpater.NewsAdapter
import com.example.newsapptask.database.ArticleDatabase
import com.example.newsapptask.providerFactory.NewsProviderFactory
import com.example.newsapptask.repository.NewsRepository
import com.example.newsapptask.viewModel.NewsViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_favorite.*

class FavoriteActivity : AppCompatActivity() {

    lateinit var newsViewModel: NewsViewModel
    lateinit var newsAdapter: NewsAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite)

        val newsRepository = NewsRepository(ArticleDatabase(this))
        val newsProviderFactory = NewsProviderFactory(newsRepository)
        newsViewModel = ViewModelProvider(this, newsProviderFactory).get(NewsViewModel::class.java)

        setupRecyclerView()

        newsViewModel.getAllFavoriteNews().observe(this,{articles->
            if(articles.isEmpty()){
                tvNoRecord.visibility=View.VISIBLE
            }else{
                tvNoRecord.visibility=View.GONE
            }
            newsAdapter.differ.submitList(articles)

        })

        newsAdapter.setOnItemClickListener { article ->
            Intent(this@FavoriteActivity, WebNewsActivity::class.java).also { intent ->
                intent.putExtra("news", article)
                startActivity(intent)
            }
        }
        val itemTouchHelperCallback=object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT){
            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder,
                                target: RecyclerView.ViewHolder): Boolean {
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position=viewHolder.adapterPosition
                val article=newsAdapter.differ.currentList[position]
                newsViewModel.deleteFavoriteArticle(article)
                Snackbar.make(findViewById(android.R.id.content),"Article Deleted Successfully", Snackbar.LENGTH_LONG).apply {
                    setAction("UNDO"){
                        article.favorite=true
                        newsViewModel.addFavorite(article)
                    }
                    show()
                }
            }
        }

        ItemTouchHelper(itemTouchHelperCallback).apply {
            attachToRecyclerView(rvFavorite)
        }
    }


    private fun setupRecyclerView() {
        newsAdapter = NewsAdapter(newsViewModel,"favorite")
        rvFavorite.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(this@FavoriteActivity)
            rvFavorite.addItemDecoration(DividerItemDecoration(
                    rvFavorite.context, (rvFavorite.layoutManager as LinearLayoutManager).orientation))
        }
    }
}