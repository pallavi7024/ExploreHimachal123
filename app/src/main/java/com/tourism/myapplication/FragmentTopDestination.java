package com.tourism.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.tourism.myapplication.databinding.FragmentDestinationBinding;

public class FragmentTopDestination extends Fragment {
    public FragmentTopDestination() {
        super(R.layout.fragment_destination);
    }

    private FragmentDestinationBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentDestinationBinding.inflate(inflater, container, false);
        binding.cardTopDestination.setOnClickListener(v -> {
            startActivity(
                    new Intent(getActivity(), TopDestinationActivity.class)
            );
        });
        return binding.getRoot();
    }
}
