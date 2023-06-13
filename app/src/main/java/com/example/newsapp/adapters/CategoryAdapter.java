package com.example.newsapp.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newsapp.R;
import com.example.newsapp.models.Category;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {

    private ArrayList<Category> categoryArrayList;
    private Context context;
private categoryClickInterface _categoryClickInterface;
    public CategoryAdapter(ArrayList<Category> categoryArrayList, Context context, CategoryAdapter.categoryClickInterface categoryClickInterface) {
        this.categoryArrayList = categoryArrayList;
        this.context = context;
        this._categoryClickInterface = categoryClickInterface;
    }

    @NonNull
    @Override
    public CategoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.category_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
    Category category = categoryArrayList.get(position);
    holder.tv.setText(category.getCategoryName());
        Picasso.get().load(category.getCategoryImg()).into(holder.iv);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                _categoryClickInterface.onCategoryClick(position);
            }
        });
    }


    @Override
    public int getItemCount() {
        return categoryArrayList.size();
    }

    public class ViewHolder extends  RecyclerView.ViewHolder{
        private TextView tv;
        private ImageView iv;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            iv = itemView.findViewById(R.id.categoryIV);
            tv = itemView.findViewById(R.id.categoryTV);
        }
    }
    public interface categoryClickInterface
    {
        public void onCategoryClick(int position);
    }

}
