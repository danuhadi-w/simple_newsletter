package com.example.newsletterapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;

import com.example.newsletterapp.API.NewsAPI;
import com.example.newsletterapp.Fragments.NewsListFragment;
import com.example.newsletterapp.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Fragment fragment = new NewsListFragment();
        loadFragment(fragment, "News List Fragment");

    }

    private void loadFragment(Fragment fragment, String tag) {
        FragmentManager fragmentManager;
        fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(binding.frameLayoutMain.getId(), fragment, tag).commit();
    }
}