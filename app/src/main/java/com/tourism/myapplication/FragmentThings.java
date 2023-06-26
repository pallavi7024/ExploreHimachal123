package com.tourism.myapplication;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.tourism.myapplication.databinding.FragmentThingsBinding;
import com.tourism.myapplication.utils.Constants;

public class FragmentThings extends Fragment {

    private FragmentThingsBinding binding;

    public FragmentThings() {
        super(R.layout.fragment_things);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentThingsBinding.inflate(inflater, container, false);
        binding.llCamping.setOnClickListener(view -> {
            navigateToNextScreen(Constants.HotelType.CAMPING);
        });
        binding.llParagliding.setOnClickListener(view -> {
            navigateToNextScreen(Constants.HotelType.PARAGLIDING);
        });

        binding.llTrekking.setOnClickListener(view -> {
            navigateToNextScreen(Constants.HotelType.TREKKING);
        });

        return binding.getRoot();
    }

    private void navigateToNextScreen(Constants.HotelType type) {
        Intent intent = new Intent(getActivity(), ExplorerActivity.class);
        intent.putExtra("type", type.name());
        startActivity(intent);
    }
}