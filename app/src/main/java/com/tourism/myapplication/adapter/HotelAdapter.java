package com.tourism.myapplication.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.tourism.myapplication.api.model.HotelModel;
import com.tourism.myapplication.databinding.RowHotelsBinding;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class HotelAdapter extends RecyclerView.Adapter<HotelAdapter.HotelViewHolder> {

    private List<HotelModel> list = new ArrayList<>();

    private Consumer<HotelModel> listener;

    public HotelAdapter(Consumer<HotelModel> listener) {
        this.listener = listener;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void submitList(List<HotelModel> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public HotelViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new HotelViewHolder(RowHotelsBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull HotelViewHolder holder, int position) {
        holder.bind(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class HotelViewHolder extends RecyclerView.ViewHolder {
        private RowHotelsBinding binding;

        public HotelViewHolder(RowHotelsBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(HotelModel model) {
            binding.textViewHotelName.setText(model.getName().trim());
            binding.textViewRating.setText(model.getRating().trim());
            binding.textViewLocation.setText(model.getLocation().trim());
            binding.textViewPrice.setText(model.getPrice().trim());
            Glide.with(binding.getRoot()).load(model.getImage()).into(binding.imageView);
            binding.buttonBookNow.setOnClickListener(v -> listener.accept(model));
        }
    }
}
