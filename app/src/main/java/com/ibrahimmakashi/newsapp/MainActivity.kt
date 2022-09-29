package com.ibrahimmakashi.newsapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SnapHelper
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var adapter: NewsAdapter
    private lateinit var newsList: RecyclerView
     var article =  mutableListOf<Article>()
    lateinit var progressBar: ProgressBar
    var pageNum = 0
    var totalResult = -1
    var TAG = "MainActivity"
   private lateinit var refresh : SwipeRefreshLayout
   private lateinit var news : News


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        progressBar = findViewById(R.id.progressBar)
        newsList = findViewById(R.id.newsList)
        refresh = findViewById(R.id.swiperefresh)

        progressBar.visibility = View.VISIBLE
        adapter = NewsAdapter(this@MainActivity ,article)
        newsList.adapter = adapter
        var layoutManager = LinearLayoutManager(this)
        newsList.layoutManager = layoutManager
//        val snapHelper: SnapHelper = LinearSnapHelper()
//        snapHelper.attachToRecyclerView(newsList)
        refresh.setOnRefreshListener{
            refresh.isRefreshing = false
            pageNum++
            getNews()
            adapter.notifyDataSetChanged()
        }

        getNews()
    }

    private fun getNews() {
        val news = NewsService.newsInstance.getHeadlines("in",pageNum)
        news.enqueue(object : Callback<News> {
            override fun onFailure(call: Call<News>, t: Throwable) {
                Log.d("IBRAHIM","ERROR IN FETCHING IN NEWS DATA...!!!")
            }

            override fun onResponse(call: Call<News>, response: Response<News>) {
                val news = response.body()
                if (news!=null){
                   totalResult = news.totalResults
                    progressBar.visibility = View.GONE
                    article.addAll(news.articles)
                    adapter.notifyDataSetChanged()
                }
            }
        })


    }
}