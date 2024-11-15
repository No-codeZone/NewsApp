package com.example.newsapp.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.newsapp.R;

import java.util.List;

public class NewsImageAdapter extends RecyclerView.Adapter<NewsImageAdapter.ViewHolder> {
    Context context;
    private List<String> imageList;

    public NewsImageAdapter(Context context, List<String> imageList) {
        this.imageList = imageList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_news_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String articleImg=imageList.get(position);
        Log.d("TAG", "onBindViewHolder: ImageURL 1.0\t"+articleImg);
        Glide.with(context)
                .load(articleImg)
                .placeholder(R.drawable.logosmall)
                .into(holder.articleImage);
    }

    @Override
    public int getItemCount() {
        Log.d("TAG", "onBindViewHolder: ImageURL 2.0\t"+imageList.size());
        return imageList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView articleImage;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            articleImage=itemView.findViewById(R.id.img);
        }
    }
}
