package com.example.newsapp.ui;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.OnBackPressedCallback;
import androidx.activity.OnBackPressedDispatcher;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newsapp.R;
import com.example.newsapp.adapter.NewsImageAdapter;
import com.example.newsapp.adapter.NewsListAdapter;
import com.example.newsapp.model.NewsRequestModel;
import com.example.newsapp.services.ApiClient;
import com.example.newsapp.services.ServiceApi;
import com.example.newsapp.utils.SharedPreferencesHelper;
import com.example.newsapp.utils.constants;
import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DashboardActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    public DrawerLayout drawerLayout;
    public ActionBarDrawerToggle actionBarDrawerToggle;
    Toolbar toolbar;
    NewsListAdapter newsListAdapter;
    NavigationView navigationView;
    ImageView clickMenu;
    RecyclerView recyclerView;
    RecyclerView recyclerViewImg;
    List<String> imageList = new ArrayList<>();
    NewsImageAdapter newsImageAdapter;

    List<NewsRequestModel.Articles> newsDataList = new ArrayList<NewsRequestModel.Articles>();
    private Spinner spinnerQuery;
    private Button buttonSearch;
    ProgressBar progressLoader;
    private final OnBackPressedDispatcher onBackPressedDispatcher = getOnBackPressedDispatcher();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_dashboard);
        //Spinner Data to get the articles
        List<String> queryOptions = new ArrayList<>();
        queryOptions.add("bitcoin");
        queryOptions.add("technology");
        queryOptions.add("healthcare");
        queryOptions.add("sports");
        queryOptions.add("climate change");
        queryOptions.add("politics");
        queryOptions.add("software industry");
        queryOptions.add("android");
        queryOptions.add("movies");
        queryOptions.add("space");
        queryOptions.add("artificial intelligence");
        queryOptions.add("super computer");
        spinnerQuery = findViewById(R.id.spinner_query);
        buttonSearch = findViewById(R.id.button_search);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.spinner_item, queryOptions) {
            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView textView = view.findViewById(R.id.spinner_item_text);
                textView.setTextColor(Color.BLACK);
                return view;
            }
        };
        onBackPressedDispatcher.addCallback(new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                AlertDialog.Builder builder = new AlertDialog.Builder(DashboardActivity.this);
                builder.setMessage("Do you want to exit?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", (dialog, id) -> {
                            Intent intent = new Intent(Intent.ACTION_MAIN);
                            intent.addCategory(Intent.CATEGORY_HOME);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                            finish();
                            System.exit(0);
                        })
                        .setNegativeButton("No", (dialog, id) -> dialog.cancel());
                AlertDialog alert = builder.create();
                alert.show();
            }
        });

        spinnerQuery.setAdapter(adapter);

        recyclerView = findViewById(R.id.news_rv);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        clickMenu = findViewById(R.id.iv_menu);
        drawerLayout = findViewById(R.id.my_drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        clickMenu = findViewById(R.id.iv_menu);
        toolbar = findViewById(R.id.toolbar);
        drawerLayout = findViewById(R.id.my_drawer_layout);
        recyclerView = findViewById(R.id.news_rv);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this,
                drawerLayout, R.string.nav_open, R.string.nav_close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        clickMenu.setOnClickListener(v -> {
            drawerLayout.openDrawer(GravityCompat.START);
        });
        navigationView.setNavigationItemSelectedListener(this);
        newsListAdapter = new NewsListAdapter(DashboardActivity.this, newsDataList);
//        newsImageAdapter = new NewsImageAdapter(DashboardActivity.this, imageList);
        recyclerView.setLayoutManager(new LinearLayoutManager(DashboardActivity.this, LinearLayoutManager.VERTICAL, false));
        //API_KEY="066af46afd694425837ed014234fb786"
        recyclerView.setAdapter(newsListAdapter);
        progressLoader=findViewById(R.id.progressBar);
        buttonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String selectedQuery = spinnerQuery.getSelectedItem().toString();
                getNews(selectedQuery);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        getNews("bitcoin");
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_profile) {
            startActivity(new Intent(DashboardActivity.this, ProfileActivity.class));
        } else if (id == R.id.nav_whats_new) {
            Toast.makeText(this, "What's new here !", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_category) {
            Toast.makeText(this, "Category is here !", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_trending) {
            Toast.makeText(this, "Trending !", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_logout) {
            AlertDialog alertDialog = new AlertDialog.Builder(DashboardActivity.this).create();
            alertDialog.setTitle("The Hindustan Times");
            alertDialog.setMessage("Do you want to logout ?");
            alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Cancel",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Confirm",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            alertDialog.show();
                            SharedPreferencesHelper sharedPreferencesHelper = new SharedPreferencesHelper(DashboardActivity.this);
                            sharedPreferencesHelper.clear();
//                            Toast.makeText(this, "Logged out successfully!", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(DashboardActivity.this, LoginActivity.class));
                            Toast.makeText(DashboardActivity.this, "Logout successfully !", Toast.LENGTH_SHORT).show();
                            finish();
                            dialog.dismiss();
                        }
                    });
            SharedPreferencesHelper sharedPreferencesHelper = new SharedPreferencesHelper(DashboardActivity.this);
            sharedPreferencesHelper.clear();
            startActivity(new Intent(DashboardActivity.this, LoginActivity.class));
            Toast.makeText(DashboardActivity.this, "Logged out successfully !", Toast.LENGTH_SHORT).show();
            finish();
        }

        DrawerLayout drawer = findViewById(R.id.my_drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;

    }

    public void getNews(String query) {
        newsDataList.clear();
        imageList.clear();
        progressLoader.setVisibility(View.VISIBLE);
        ServiceApi api = ApiClient.getClient().create(ServiceApi.class);
        Call<NewsRequestModel> call = api.getEverything(
                query,
//                "",
//                "relevancy",
                constants.API_KEY
        );
        call.enqueue(new Callback<NewsRequestModel>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onResponse(@NonNull Call<NewsRequestModel> call, @NonNull Response<NewsRequestModel> response) {
                Log.d("TAG", "onResponse: getCategory Details\t" + new Gson().toJson(response.body()));
                if (response.body() != null) {
                    progressLoader.setVisibility(View.GONE);
                    Log.d("TAG", "onResponse: Dashboard News Articles\t" + new Gson().toJson(response.body().getTotalResults()));
                    newsDataList.addAll(response.body().getArticles());
                    newsListAdapter.notifyDataSetChanged();
                }else{
                    progressLoader.setVisibility(View.GONE);
                    Toast.makeText(DashboardActivity.this, "Query doesn't exists !", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<NewsRequestModel> call, @NonNull Throwable t) {
                progressLoader.setVisibility(View.GONE);
                Log.e("TAG", "onFailure: " + t.getLocalizedMessage().toString());
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Do you want to exit?")
                .setCancelable(false)
                .setPositiveButton("Yes", (dialog, id) -> {
                    Intent intent = new Intent(Intent.ACTION_MAIN);
                    intent.addCategory(Intent.CATEGORY_HOME);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                    System.exit(0);
                })
                .setNegativeButton("No", (dialog, id) -> dialog.cancel());
        AlertDialog alert = builder.create();
        alert.show();
    }
}