package com.example.newsletterapp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.newsletterapp.Fragments.NewsDetailFragment;
import com.example.newsletterapp.Models.News;
import com.example.newsletterapp.R;
import com.example.newsletterapp.databinding.NewsCardLayoutBinding;

import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsAdapterViewHolder> {
    private List<News> newsList;
    private Context context;
    private View view;
    private AppCompatActivity activity;


    public NewsAdapter(Context context, List<News> newsList) {
        this.context = context;
        this.newsList = newsList;
    }

    @NonNull
    @Override
    public NewsAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        NewsCardLayoutBinding binding = NewsCardLayoutBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        NewsAdapterViewHolder newsAdapterViewHolder = new NewsAdapterViewHolder(binding);
        activity = (AppCompatActivity) context;

        newsAdapterViewHolder.binding.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NewsDetailFragment newsDetailFragment = new NewsDetailFragment(newsList.get(newsAdapterViewHolder.getAdapterPosition()));

                loadFragment(newsDetailFragment, "newsDetailFragment");
            }
        });
        return newsAdapterViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull NewsAdapterViewHolder holder, int position) {
        News news = newsList.get(position);
        holder.binding.newsTitle.setText(news.getTitle());
        holder.binding.newsDesc.setText(news.getDescription());
        holder.binding.newsPublishedAt.setText(news.getFormattedDate());

        if(news.getUrlToImage() == "null"){
            Glide.with(context).load("https://tacm.com/wp-content/uploads/2018/01/no-image-available.jpeg").centerInside().into(holder.binding.newsImage);
        }else{
            Glide.with(context).load(news.getUrlToImage()).centerCrop().into(holder.binding.newsImage);
        }

    }

    @Override
    public int getItemCount() {
        return newsList.size();
    }

    public class NewsAdapterViewHolder extends RecyclerView.ViewHolder {
        public NewsCardLayoutBinding binding;

        public NewsAdapterViewHolder(NewsCardLayoutBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    private void loadFragment(Fragment fragment, String tag) {
        FragmentTransaction transaction;
        transaction = activity.getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frameLayoutMain, fragment, tag);
        transaction.addToBackStack(null);
        transaction.commit();
    }

}
