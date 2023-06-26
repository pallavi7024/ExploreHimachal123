package com.tourism.myapplication;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.denzcoskun.imageslider.models.SlideModel;
import com.tourism.myapplication.adapter.HotelAdapter;
import com.tourism.myapplication.api.RetrofitModule;
import com.tourism.myapplication.api.model.GetModel;
import com.tourism.myapplication.api.model.HotelModel;
import com.tourism.myapplication.databinding.ActivityHotelListBinding;
import com.tourism.myapplication.utils.Constants;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HotelListActivity extends AppCompatActivity {

    private ActivityHotelListBinding binding;
    private HotelAdapter adapter;
    private Constants.HotelType type = null;
    private GetModel hotelModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHotelListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getDataFromIntent();
        setRecyclerView();
        setLongDescription();
    }

    private void setLongDescription() {
        if (hotelModel.getLongDescription() != null && !hotelModel.getLongDescription().isBlank()) {
            binding.textViewLongDesc.setVisibility(android.view.View.VISIBLE);
            binding.textViewName.setVisibility(android.view.View.VISIBLE);
            binding.textViewLongDesc.setText(hotelModel.getLongDescription());
        }
        if (hotelModel.getImages() != null && !hotelModel.getImages().isEmpty()) {
            binding.imageSlider.setVisibility(android.view.View.VISIBLE);
            var imageList = hotelModel.getImages().stream().map(v -> new SlideModel(v, null))
                    .collect(Collectors.toList());
            binding.imageSlider.setImageList(imageList);
        }
    }

    private void getDataFromIntent() {
        if (getIntent().hasExtra("type"))
            type = Constants.HotelType.valueOf(getIntent().getStringExtra("type"));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
            hotelModel = getIntent().getSerializableExtra("hotelName", GetModel.class);
        else
            hotelModel = (GetModel) getIntent().getSerializableExtra("hotelName");
        getSupportActionBar().setTitle(hotelModel.getName());
    }

    private void setRecyclerView() {
        adapter = new HotelAdapter(this::navigateToPaymentActivity);
        binding.rvHotels.setLayoutManager(new LinearLayoutManager(this));
        binding.rvHotels.setAdapter(adapter);
        binding.rvHotels.setHasFixedSize(true);
        getData();
    }

    private void getData() {
        if (type != null)
            RetrofitModule.getHotelApi()
                    .getHotelsData(type.name().toLowerCase(Locale.getDefault()), hotelModel.getName().trim())
                    .enqueue(new Callback<>() {
                        @Override
                        public void onResponse(@NonNull Call<List<HotelModel>> call, @NonNull Response<List<HotelModel>> response) {
                            Log.d("AAA", "onResponse: " + call.request().url());
                            if (response.isSuccessful()) {
                                adapter.submitList(response.body());
                            } else
                                Toast.makeText(HotelListActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onFailure(@NonNull Call<List<HotelModel>> call, @NonNull Throwable t) {
                            Toast.makeText(HotelListActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                        }
                    });
        else
            RetrofitModule.getHotelApi().getTopDestinationHotels(
                            hotelModel.getName().trim())
                    .enqueue(new Callback<>() {
                                 @Override
                                 public void onResponse(@NonNull Call<List<HotelModel>> call, @NonNull Response<List<HotelModel>> response) {
                                     Log.d("AAA", "onResponse: " + call.request().url());
                                     if (response.isSuccessful()) {
                                         adapter.submitList(response.body());
                                     } else
                                         Toast.makeText(HotelListActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                                 }

                                 @Override
                                 public void onFailure(@NonNull Call<List<HotelModel>> call, @NonNull Throwable t) {
                                     Toast.makeText(HotelListActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                                 }
                             }
                    );
    }

    private void navigateToPaymentActivity(HotelModel hotelModel) {
        var intent = new Intent(this, PaymentActivity.class);
        intent.putExtra("hotelModel", hotelModel);
        startActivity(intent);
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }
}