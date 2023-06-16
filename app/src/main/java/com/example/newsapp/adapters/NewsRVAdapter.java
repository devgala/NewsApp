package com.example.newsapp.adapters;



import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.AsyncListDiffer;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newsapp.ArticleActivity;
import com.example.newsapp.R;
import com.example.newsapp.models.Article;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class NewsRVAdapter extends RecyclerView.Adapter<NewsRVAdapter.ViewHolder> {
    private final static  String defaultURL = "https://upload.wikimedia.org/wikipedia/commons/thumb/6/65/No-Image-Placeholder.svg/330px-No-Image-Placeholder.svg.png";
    private ArrayList<Article> articleArrayList;
    private Context context;
    public static final String ARTICLE_TITLE = "com.example.newsapp.adapters.article_title";
    public static final String ARTICLE_IMG = "com.example.newsapp.adapters.article_img";
    public static final String ARTICLE_SRC = "com.example.newsapp.adapters.article_src";
    public static final String ARTICLE_CONTENT = "com.example.newsapp.adapters.article_content";
    public static final String ARTICLE_DATE = "com.example.newsapp.adapters.article_date";
    public static final String ARTICLE_URL = "com.example.newsapp.adapters.article_url";

    public static final DiffUtil.ItemCallback<Article> DIFF_CALLBACK = new DiffUtil.ItemCallback<Article>() {
        @Override
        public boolean areItemsTheSame(@NonNull Article oldItem, @NonNull Article newItem) {
            return oldItem.getTitle().equals(newItem.getTitle());
        }

        @SuppressLint("DiffUtilEquals")
        @Override
        public boolean areContentsTheSame(@NonNull Article oldItem, @NonNull Article newItem) {
            return oldItem.equals(newItem);
        }
    };
    private AsyncListDiffer<Article> asyncListDiffer; //= new AsyncListDiffer<Article>(this,DIFF_CALLBACK);

    public NewsRVAdapter(ArrayList<Article> articleArrayList, Context context) {
        asyncListDiffer = new AsyncListDiffer<Article>(this,DIFF_CALLBACK);
        this.articleArrayList = articleArrayList;
        this.context = context;
       submitList(articleArrayList);
    }
    public void submitList(ArrayList<Article> articleArrayList){
        //Log.d("async","submitlist");
        asyncListDiffer.submitList(articleArrayList);
    }

    @NonNull
    @Override
    public NewsRVAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.article_layout,parent,false);

        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull NewsRVAdapter.ViewHolder holder, int position) {
        Article article = asyncListDiffer.getCurrentList().get(position);
        holder.titletext.setText(article.getTitle());
        holder.sourceName.setText("Source: "+article.getSource().getName());
        if(!article.getUrlToImage().equalsIgnoreCase("no image"))
        Picasso.get().load(article.getUrlToImage()).into(holder.image);
        else Picasso.get().load(defaultURL).into(holder.image);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, ArticleActivity.class);
                i.putExtra(ARTICLE_TITLE,article.getTitle());
                i.putExtra(ARTICLE_IMG,article.getUrlToImage());
                i.putExtra(ARTICLE_CONTENT,article.getDescription());
                i.putExtra(ARTICLE_SRC,article.getSource().getName());
                i.putExtra(ARTICLE_DATE,article.getPublishedAt());
                i.putExtra(ARTICLE_URL,article.getUrl());

               context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return asyncListDiffer.getCurrentList().size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView titletext;
        private ImageView image;
        private TextView sourceName;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            titletext = itemView.findViewById(R.id.articleHeading);
            image = itemView.findViewById(R.id.articleIV);
            sourceName = itemView.findViewById(R.id.sourceName);
        }
    }
}
