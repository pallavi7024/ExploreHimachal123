package com.tourism.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.tourism.myapplication.adapter.TopDestinationAdapter;
import com.tourism.myapplication.api.RetrofitModule;
import com.tourism.myapplication.api.model.GetModel;
import com.tourism.myapplication.databinding.ActivityTopdestinationBinding;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TopDestinationActivity extends AppCompatActivity {

    private ActivityTopdestinationBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTopdestinationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setUpToolbar();
        setRecyclerView();
    }

    private void setRecyclerView() {
        var adapter = new TopDestinationAdapter(this::navigateToViewHotelsActivity);
        binding.rvTopDestination.setAdapter(adapter);
        binding.rvTopDestination.setLayoutManager(new LinearLayoutManager(this));

        RetrofitModule.getHotelApi()
                .getTopDestination()
                .enqueue(new Callback<>() {
                    @Override
                    public void onResponse(@NonNull Call<List<GetModel>> call, @NonNull Response<List<GetModel>> response) {
                        if (response.isSuccessful())
                            adapter.submitList(response.body());
                        else
                            Toast.makeText(TopDestinationActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(@NonNull Call<List<GetModel>> call, @NonNull Throwable t) {
                        Toast.makeText(TopDestinationActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                    }
                });

    }

    private void setUpToolbar() {
        setSupportActionBar(binding.toolbarseven);
        getSupportActionBar().setTitle("Top Destination");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void navigateToViewHotelsActivity(GetModel hotelName) {
        var intent = new Intent(this, HotelListActivity.class);
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