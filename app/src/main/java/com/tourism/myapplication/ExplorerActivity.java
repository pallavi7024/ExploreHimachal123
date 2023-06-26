package com.tourism.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.tourism.myapplication.adapter.OuterAdapter;
import com.tourism.myapplication.api.RetrofitModule;
import com.tourism.myapplication.api.model.GetModel;
import com.tourism.myapplication.databinding.ActivityExplorerBinding;
import com.tourism.myapplication.utils.Constants;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ExplorerActivity extends AppCompatActivity {
    private ActivityExplorerBinding binding;

    private OuterAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityExplorerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getDataFromIntent();
    }

    private void getDataFromIntent() {
        Constants.HotelType type = Constants.HotelType.valueOf(getIntent().getStringExtra("type"));
        getSupportActionBar().setTitle(type.name().replace("_", " "));
        setRecyclerView(type);
    }

    private void setRecyclerView(Constants.HotelType type) {
        adapter = new OuterAdapter(hotelName -> {
            navigateToViewHotelsActivity(type, hotelName);
        });
        binding.rvThingsToDo.setLayoutManager(new LinearLayoutManager(this));
        binding.rvThingsToDo.setAdapter(adapter);
        binding.rvThingsToDo.setHasFixedSize(true);
        RetrofitModule.getHotelApi().getThingsToDo(type).enqueue(new Callback<>() {
            @Override
            public void onResponse(@NonNull Call<List<GetModel>> call, @NonNull Response<List<GetModel>> response) {
                if (response.isSuccessful()) {
                    adapter.submitList(response.body());
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<GetModel>> call, @NonNull Throwable t) {
                Toast.makeText(ExplorerActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void navigateToViewHotelsActivity(Constants.HotelType type, GetModel hotelName) {
        if (getIntent().getBooleanExtra("isEvent", false)) {
            return;
        }
        var intent = new Intent(this, HotelListActivity.class);
        intent.putExtra("type", type.name());
        intent.putExtra("hotelName", hotelName);
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }
}