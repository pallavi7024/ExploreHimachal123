package com.tourism.myapplication;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.tourism.myapplication.databinding.FragmentEventsBinding;
import com.tourism.myapplication.utils.Constants;

public class FragmentEvents extends Fragment {

    private FragmentEventsBinding binding;

    public FragmentEvents() {
        super(R.layout.fragment_events);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentEventsBinding.inflate(inflater, container, false);
        binding.llFairFestival.setOnClickListener(view -> {
            navigateToNextScreen(Constants.HotelType.FAIRS_AND_FESTIVALS);
        });
        binding.llHimachalFood.setOnClickListener(view -> {
            navigateToNextScreen(Constants.HotelType.HIMACHAL_FOOD);
        });
        binding.llHolyPlaces.setOnClickListener(view -> {
            navigateToNextScreen(Constants.HotelType.HOLY_PLACES);
        });
        return binding.getRoot();
    }

    private void navigateToNextScreen(Constants.HotelType type) {
        Intent intent = new Intent(getActivity(), ExplorerActivity.class);
        intent.putExtra("type", type.name());
        intent.putExtra("isEvent", true);
        startActivity(intent);
    }
}