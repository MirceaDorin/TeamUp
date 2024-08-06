package com.example.teamup.Activity.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.teamup.R;

import java.util.ArrayList;
import java.util.List;

public class BazaSportivaAdapter extends RecyclerView.Adapter<BazaSportivaAdapter.ViewHolder> {

    private List<Integer> imageList;

    public BazaSportivaAdapter(List<Integer> imageList) {
        this.imageList = imageList != null ? imageList : new ArrayList<>();
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_image, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.imageView.setImageResource(imageList.get(position));
    }

    @Override
    public int getItemCount() {
        return imageList != null ? imageList.size() : 0;
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
        }
    }
}
