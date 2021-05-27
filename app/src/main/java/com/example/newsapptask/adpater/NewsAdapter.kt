package com.example.newsapptask.adpater

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.newsapptask.R
import com.example.newsapptask.model.Article
import com.example.newsapptask.viewModel.NewsViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.item_news.view.*


/**
 * Created by Gitanjali Ghangale on 5/22/2021.
 */
class NewsAdapter(private val newsViewModel: NewsViewModel, private val caller:String) : RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {

    inner class NewsViewHolder(item: View) : RecyclerView.ViewHolder(item)

    private val differCallback = object : DiffUtil.ItemCallback<Article>() {
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem.url == newItem.url
        }

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem == newItem
        }

    }
    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        return NewsViewHolder(LayoutInflater.from(parent.context).inflate(
                R.layout.item_news, parent, false))
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val article=differ.currentList[position]
        if(caller == "favorite"){
            holder.itemView.imgFavorite.visibility=View.GONE
        }else{
            holder.itemView.imgFavorite.visibility=View.VISIBLE
            if(article.favorite){
              holder.itemView.imgFavorite.setImageResource(R.drawable.ic_favorite_fill)
            }else{
                holder.itemView.imgFavorite.setImageResource(R.drawable.ic_favorite_border)
            }

        }
        holder.itemView.apply {
            Glide.with(this).load(article.urlToImage).into(ivArticleImage)
            tvTitle.text = article.title
            tvAuthor.text=article.author
            tvDescription.text = article.description
            setOnClickListener {
                onItemClickListener?.let { it(article) }
            }

            imgFavorite.setOnClickListener {
                article.favorite=true
                newsViewModel.addFavorite(article)
                Snackbar.make(it, "News saved successfully", Snackbar.LENGTH_SHORT).show()
            }
        }



    }

    override fun getItemCount(): Int {
        Log.e("size","=="+differ.currentList.size)
        return differ.currentList.size
    }


    private var onItemClickListener: ((Article) -> Unit)? = null

    fun setOnItemClickListener(listener: (Article) -> Unit) {
        onItemClickListener = listener
    }
}