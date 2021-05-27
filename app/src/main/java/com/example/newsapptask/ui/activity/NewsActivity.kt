package com.example.newsapptask.ui.activity


import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.newsapptask.R
import com.example.newsapptask.adpater.ViewPagerAdapter
import com.example.newsapptask.database.ArticleDatabase
import com.example.newsapptask.providerFactory.NewsProviderFactory
import com.example.newsapptask.repository.NewsRepository
import com.example.newsapptask.ui.fragment.HealthFragment
import com.example.newsapptask.ui.fragment.SportsFragment
import com.example.newsapptask.ui.fragment.TechnologyFragment
import com.example.newsapptask.viewModel.NewsViewModel
import kotlinx.android.synthetic.main.activity_news.*

class NewsActivity : AppCompatActivity() {
    lateinit var newsViewModel: NewsViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news)

        val newsRepository = NewsRepository(ArticleDatabase(this))
        val newsProviderFactory = NewsProviderFactory(newsRepository)
        newsViewModel = ViewModelProvider(this, newsProviderFactory).get(NewsViewModel::class.java)


        val adapter = ViewPagerAdapter(supportFragmentManager)
        adapter.addFragment(TechnologyFragment(), "Technology")
        adapter.addFragment(HealthFragment(), "Health")
        adapter.addFragment(SportsFragment(), "Sports")
        viewPager.adapter = adapter
        tabs.setupWithViewPager(viewPager)

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_favorite, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        return when (item.itemId) {
            R.id.action_favorite -> {
                Intent(this, FavoriteActivity::class.java).also {
                    startActivity(it)
                }
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}