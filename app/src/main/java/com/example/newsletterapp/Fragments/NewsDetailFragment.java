package com.example.newsletterapp.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.bumptech.glide.Glide;
import com.example.newsletterapp.Models.News;
import com.example.newsletterapp.databinding.FragmentNewsDetailBinding;
import com.example.newsletterapp.databinding.FragmentNewsListBinding;

import java.util.ArrayList;

public class NewsDetailFragment extends Fragment {
    private FragmentNewsDetailBinding binding;
    private News news;

    public NewsDetailFragment(News news) {
        this.news = news;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentNewsDetailBinding.inflate(inflater, container, false);

        init();

        binding.swipeRefresh.setOnRefreshListener(
                this::init
        );
        return binding.getRoot();
    }

    public void init(){
        binding.newsTitle.setText(news.getTitle());
        binding.newsDescription.setText(news.getDescription());
        binding.newsAuthor.setText(news.getAuthor());
        binding.newsPublishedAt.setText(news.getPublishedAt());
        binding.newsSource.setText(news.getName());

        if(news.getUrlToImage() == "null"){
            Glide.with(getContext()).load("https://tacm.com/wp-content/uploads/2018/01/no-image-available.jpeg").centerInside().into(binding.newsImage);
        }else{
            Glide.with(getContext()).load(news.getUrlToImage()).centerCrop().into(binding.newsImage);
        }
    }

}
