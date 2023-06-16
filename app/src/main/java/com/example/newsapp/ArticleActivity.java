package com.example.newsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.newsapp.adapters.NewsRVAdapter;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ArticleActivity extends AppCompatActivity {
    private TextView title;
    private TextView content;
    private TextView source;
    private TextView date;
    private ImageView img;
    private Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);
        title = findViewById(R.id.articleTitle);
        source = findViewById(R.id.articleSource);
        content = findViewById(R.id.articleContent);
        date  = findViewById(R.id.articleDate);
        img = findViewById(R.id.articleImage);
        button = findViewById(R.id.openBrowserButton);
        Intent intent = getIntent();
        Bundle extra = getIntent().getExtras();
        title.setText(extra.getString(NewsRVAdapter.ARTICLE_TITLE));
        content.setText(extra.getString(NewsRVAdapter.ARTICLE_CONTENT));
        source.setText("Source: "+extra.getString(NewsRVAdapter.ARTICLE_SRC));
        Picasso.get().load(extra.getString(NewsRVAdapter.ARTICLE_IMG)).into(img);
        date.setText("Published on: "+getDate(extra.getString(NewsRVAdapter.ARTICLE_DATE)));
        String url = extra.getString(NewsRVAdapter.ARTICLE_URL);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent urlIntent = new Intent(ArticleActivity.this,WebActivity.class);
                urlIntent.putExtra(NewsRVAdapter.ARTICLE_URL,url);
                startActivity(urlIntent);
            }
        });
    }

    public String getDate(String ipDate){
        SimpleDateFormat input = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        SimpleDateFormat output = new SimpleDateFormat("dd/MM/yyyy");

        Date d = new Date();
        try
        {
            d = input.parse(ipDate);
        }
        catch ( ParseException e)
        {
            e.printStackTrace();
        }
        String formatted = output.format(d);
      return  formatted;
    }
}