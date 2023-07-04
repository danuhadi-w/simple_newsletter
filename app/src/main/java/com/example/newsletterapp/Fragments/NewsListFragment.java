package com.example.newsletterapp.Fragments;

import static com.android.volley.Request.Method.GET;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.newsletterapp.API.NewsAPI;
import com.example.newsletterapp.Adapters.NewsAdapter;
import com.example.newsletterapp.Models.News;
import com.example.newsletterapp.R;
import com.example.newsletterapp.databinding.FragmentNewsListBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;


public class NewsListFragment extends Fragment {

    private FragmentNewsListBinding binding;
    private GridLayoutManager gridLayoutManager;
    private NewsAdapter newsAdapter;
    private List<News> newsList;
    private Boolean isExecuting = false;

    public NewsListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        newsList = new ArrayList<>();
        binding = FragmentNewsListBinding.inflate(inflater, container, false);
        gridLayoutManager = new GridLayoutManager(
                binding.getRoot().getContext(),
                1,
                LinearLayoutManager.VERTICAL, false
        );
        binding.newsRecyclerView.setLayoutManager(gridLayoutManager);
        binding.newsRecyclerView.setHasFixedSize(true);

        init();

        binding.swipeRefresh.setOnRefreshListener(
                this::init
        );
        return binding.getRoot();
    }

    private void init() {
        try {
            binding.nestedSV.scrollTo(0, 0);
            setLoadingShimmer(true);
            newsList.clear();
            newsAdapter = new NewsAdapter(binding.getRoot().getContext(), newsList);
            binding.newsRecyclerView.setAdapter(newsAdapter);
        } catch (Exception e) {
            //preventing crash when back is pressed while api load not finished
        }
        getData();
    }

    private void setLoadingShimmer(Boolean bool) {
        if (bool) {
            binding.shimmerLayout.startShimmer();
            binding.shimmerLayout.setVisibility(View.VISIBLE);
        } else {
            binding.shimmerLayout.stopShimmer();
            binding.shimmerLayout.setVisibility(View.GONE);
        }
    }

    private void getData() {

        if (isExecuting) {
            binding.swipeRefresh.setRefreshing(false);
            return;
        } else isExecuting = true;

        int method = GET;
        String url = NewsAPI.api;

        RequestQueue queue = Volley.newRequestQueue(getContext());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(method, url + getContext().getString(R.string.NEWS_API), null,
                response -> {
                    try {
                        JSONArray jsonArray = response.getJSONArray("articles");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);

                            News newData = new News(
                                    jsonObject.optJSONObject("source").optString("name"),
                                    jsonObject.optString("author"),
                                    jsonObject.optString("title"),
                                    jsonObject.optString("description"),
                                    jsonObject.optString("url"),
                                    jsonObject.optString("urlToImage"),
                                    jsonObject.optString("publishedAt")
                            );
                            if (i == 0) {

                                System.out.println(jsonObject);
                            }
                            newsList.add(newData);
                        }

                        try {
                            newsAdapter = new NewsAdapter(binding.getRoot().getContext(), newsList);
                            binding.newsRecyclerView.setAdapter(newsAdapter);
                        } catch (Exception e) {
                            //preventing crash when back is pressed while api load not finished
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    try {
                        setLoadingShimmer(false);
                        isExecuting = false;
                    } catch (Exception e) {
                        //preventing crash when back is pressed while api load not finished
                    }
                },
                error -> {
                    String errorText = error.toString();
                    try {
                        Toast.makeText(getContext(), errorText, Toast.LENGTH_SHORT).show();
                    } catch (Exception err) {
                        //preventing crash when back is pressed while api load not finished
                    } finally {
                        try {
                            setLoadingShimmer(false);
                            isExecuting = false;
                        } catch (Exception e) {
                            //preventing crash when back is pressed while api load not finished
                        }
                    }
                }

        ) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("User-Agent", "Mozilla/5.0");

                return headers;
            }
        };
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(
                (int) TimeUnit.SECONDS.toMillis(30),
                0,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        ));

        queue.add(jsonObjectRequest);
        binding.swipeRefresh.setRefreshing(false);

    }
}