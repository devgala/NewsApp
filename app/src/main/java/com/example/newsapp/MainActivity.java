package com.example.newsapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.newsapp.adapters.CategoryAdapter;
import com.example.newsapp.adapters.NewsRVAdapter;
import com.example.newsapp.apis.NewsAPI;
import com.example.newsapp.models.Article;
import com.example.newsapp.models.Category;
import com.example.newsapp.models.newsModel;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements  CategoryAdapter.categoryClickInterface{
    //2b8fdd48f01a4a078cc29266b66626b2
    private static final String API_KEY = "2b8fdd48f01a4a078cc29266b66626b2";
    private  static  final  String BASE_URL = "https://newsapi.org/";
    private  static  final  String URL = "https://newsapi.org/v2/top-headlines?country=in&excludeDomains=stackoverflow.com&sortBy=publishedAt&language=en&apiKey=";
    private static  String cat="All";
    public static ArrayList<Category> categoryList;
    public ArrayList<Article> articleArrayList;
    private  RecyclerView articleRV;
    private RecyclerView categoryRV;
    private CategoryAdapter categoryAdapter;
    private NewsRVAdapter newsRVAdapter;
    private ProgressBar progressBar;
    private ImageButton refreshButton;
    TextView header;
    @SuppressLint("NotifyDataSetChanged")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        categoryList = new ArrayList<Category>();
        articleArrayList = new ArrayList<Article>();
        articleRV = findViewById(R.id.articleRV);
        categoryRV = findViewById(R.id.categoryRV);
        progressBar = findViewById(R.id.progressbar);
        refreshButton = findViewById(R.id.refresh);
        header = findViewById(R.id.header);
        header.setText("News App");
        articleRV.setLayoutManager(new LinearLayoutManager(this));
        articleRV.setLayoutManager(new LinearLayoutManager(this));
        categoryAdapter = new CategoryAdapter(categoryList,this,this::onCategoryClick);
        newsRVAdapter = new NewsRVAdapter(articleArrayList,this);
        articleRV.setAdapter(newsRVAdapter);
        categoryRV.setAdapter(categoryAdapter);
        getCategories();
        categoryAdapter.notifyDataSetChanged();
        getNews("All");
        newsRVAdapter.notifyDataSetChanged();
        refreshButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getNews(cat);
            }
        });

    }
    public void getCategories(){
        categoryList.add(new Category("All","https://images.unsplash.com/photo-1508612761958-e931d843bdd5?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=415&q=80"));
        categoryList.add(new Category("Science","https://images.unsplash.com/photo-1507413245164-6160d8298b31?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=870&q=80"));
        categoryList.add(new Category("Business","https://images.unsplash.com/photo-1611974789855-9c2a0a7236a3?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=870&q=80"));
        categoryList.add(new Category("Health","https://images.unsplash.com/photo-1511688878353-3a2f5be94cd7?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=387&q=80"));
        categoryList.add(new Category("Sports","https://images.unsplash.com/photo-1558365849-6ebd8b0454b2?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=388&q=80"));
        categoryList.add(new Category("Technology","https://images.unsplash.com/photo-1597733336794-12d05021d510?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=774&q=80"));


    }
    public void getNews(String category){
        progressBar.setVisibility(View.VISIBLE);
        articleArrayList.clear();
        cat = category;
        Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        NewsAPI newsAPI = retrofit.create(NewsAPI.class);
        Call<newsModel> call;
        if(category.equalsIgnoreCase("All")){
            call = newsAPI.getAllArticles(URL+API_KEY);

            header.setText("News App");
        }else {

            call = newsAPI.getAllArticles(URL+API_KEY+"&category="+category);

            header.setText(category.toUpperCase());
        }
        call.enqueue(new Callback<newsModel>() {
            @Override
            public void onResponse(Call<newsModel> call, Response<newsModel> response) {
                newsModel nm = response.body();
                progressBar.setVisibility(View.GONE);
                ArrayList<Article> list = nm.getArticles();
                for(Article a : list){
                    Article article = new Article(a.getTitle(),a.getDescription(),a.getUrl(),a.getUrlToImage(),a.getPublishedAt(),a.getContent(),a.getSource());
                    articleArrayList.add(article);
                }
                newsRVAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<newsModel> call, Throwable t) {

                Toast.makeText(MainActivity.this,"Failed to get News",Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
                if(!category.equalsIgnoreCase("all"))
                getNews("All");
                else Toast.makeText(MainActivity.this,"Please check internet or try again later",Toast.LENGTH_LONG).show();

            }
        });
    }
    @Override
    public void onCategoryClick(int position) {
        Log.d("category",categoryList.get(position).getCategoryName());
        getNews(categoryList.get(position).getCategoryName());
    }
}