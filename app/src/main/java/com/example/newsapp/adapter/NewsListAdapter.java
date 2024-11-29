package com.example.newsapp.adapter;

import static java.security.AccessController.getContext;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.newsapp.R;
import com.example.newsapp.model.NewsRequestModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class NewsListAdapter extends RecyclerView.Adapter<NewsListAdapter.ViewHolder> {
    Context context;
    List<NewsRequestModel.Articles> newsRequestModelsList;
    List<String> imageList = new ArrayList<String>();


    public NewsListAdapter(Context context, List<NewsRequestModel.Articles> newsRequestModelsList) {
        this.context = context;
        this.newsRequestModelsList = newsRequestModelsList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_news_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        NewsRequestModel.Articles newsData=newsRequestModelsList.get(position);
        Log.d("TAG", "onBindViewHolder: "+newsData.getTitle());
        holder.txtTitle.setText(newsData.getTitle());
        holder.txtNewsSum.setText(newsData.getDescription());
        String image = newsData.getUrlToImage();
        Log.d("TAG", "onBindViewHolder: ArticleImage List\t"+newsData.getUrlToImage());
        /*Glide.with(context)
                .load(image)
                .placeholder(R.drawable.logosmall)
                .into(holder.imgURL);*/
        Picasso.get().load(image).fit().into(holder.imgURL);
    }
    public int getItemCount() {
        Log.d("TAG", "getItemCount: "+newsRequestModelsList.size());
        return newsRequestModelsList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView txtTitle, txtNewsSum;
        ImageView imgURL;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTitle = itemView.findViewById(R.id.title);
            txtNewsSum = itemView.findViewById(R.id.tv_news_desc);
            imgURL=itemView.findViewById(R.id.img);
        }
    }
}