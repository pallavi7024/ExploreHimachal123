
package com.tourism.myapplication.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.tourism.myapplication.api.model.GetModel;
import com.tourism.myapplication.databinding.RowTopDestinationBinding;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class TopDestinationAdapter extends RecyclerView.Adapter<TopDestinationAdapter.OuterViewHolder> {

    private Consumer<GetModel> onClickListener;

    public TopDestinationAdapter(Consumer<GetModel> onClickListener) {
        this.onClickListener = onClickListener;
    }

    private List<GetModel> list = new ArrayList<>();

    @SuppressLint("NotifyDataSetChanged")
    public void submitList(List<GetModel> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public OuterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new OuterViewHolder(RowTopDestinationBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull OuterViewHolder holder, int position) {
        holder.bind(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class OuterViewHolder extends RecyclerView.ViewHolder {
        private RowTopDestinationBinding binding;

        public OuterViewHolder(RowTopDestinationBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            binding.getRoot().setOnClickListener(v -> onClickListener.accept(
                    list.get(getAdapterPosition())
            ));
        }

        public void bind(GetModel model) {
            binding.tvHotelName.setText(model.getName());
            binding.tvHotelDescription.setText(model.getDescription());
            Glide.with(binding.getRoot()).load(model.getImage()).into(binding.ivImage);
        }
    }
}
