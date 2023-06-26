package com.tourism.myapplication;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.denzcoskun.imageslider.models.SlideModel;
import com.tourism.myapplication.databinding.FragmentHomefrag2Binding;

import java.util.ArrayList;


public class HomeFrag extends Fragment {
    public HomeFrag() {
        super(R.layout.fragment_homefrag2);
    }

    private FragmentHomefrag2Binding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentHomefrag2Binding.inflate(inflater, container, false);
        ArrayList<SlideModel> imageList = new ArrayList<>();
        imageList.add(new SlideModel(R.drawable.slider1, null));
        imageList.add(new SlideModel(R.drawable.slider2, null));
        imageList.add(new SlideModel(R.drawable.slider3, null));
        imageList.add(new SlideModel(R.drawable.slider4, null));
        imageList.add(new SlideModel(R.drawable.slider5, null));
        imageList.add(new SlideModel(R.drawable.slider6, null));

        binding.imageSlider.setImageList(imageList);

        return binding.getRoot();
    }
}
