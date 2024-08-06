package com.example.teamup.Activity.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.teamup.Activity.Domain.PrieteniDomain;
import com.example.teamup.R;
import java.util.List;

public class PrieteniAdapter extends RecyclerView.Adapter<PrieteniAdapter.PrieteniViewHolder> {

    private List<PrieteniDomain> prieteni;
    private Context context;
    private boolean isSuggestion;
    private OnAddFriendClickListener onAddFriendClickListener;

    public interface OnAddFriendClickListener {
        void onAddFriendClick(PrieteniDomain prieten);
    }

    public PrieteniAdapter(Context context, List<PrieteniDomain> prieteni, boolean isSuggestion, OnAddFriendClickListener onAddFriendClickListener) {
        this.context = context;
        this.prieteni = prieteni;
        this.isSuggestion = isSuggestion;
        this.onAddFriendClickListener = onAddFriendClickListener;
    }

    @NonNull
    @Override
    public PrieteniViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.activity_fiecareprieten, parent, false);
        return new PrieteniViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PrieteniViewHolder holder, int position) {
        PrieteniDomain prieten = prieteni.get(position);
        holder.profileName.setText(prieten.getUsername());


        String imageResId = prieten.getImageResId();
        Log.d("PrieteniAdapter", "Loading image for user: " + prieten.getUsername() + ", imageResId: " + imageResId);

        // Load image with Glide
        if (imageResId != null && !imageResId.isEmpty()) {
            Glide.with(context)
                    .load(imageResId)
                    .placeholder(R.drawable.rounded_image_profile)
                    .error(R.drawable.rounded_image_profile)
                    .circleCrop()
                    .into(holder.profileImage);
        } else {
            // Handle case where imageResId is null or empty
            holder.profileImage.setImageResource(R.drawable.rounded_image_profile); // or set another default image
        }


        if (isSuggestion) {
            holder.addFriendButton.setVisibility(View.VISIBLE);
            holder.addFriendButton.setOnClickListener(v -> {
                if (onAddFriendClickListener != null) {
                    onAddFriendClickListener.onAddFriendClick(prieten);
                }
            });
        } else {
            holder.addFriendButton.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return prieteni.size();
    }

    public static class PrieteniViewHolder extends RecyclerView.ViewHolder {
        ImageView profileImage;
        TextView profileName;
        TextView profileCity;
        Button addFriendButton;

        public PrieteniViewHolder(@NonNull View itemView) {
            super(itemView);
            profileImage = itemView.findViewById(R.id.profileImage);
            profileName = itemView.findViewById(R.id.profileName);
            profileCity = itemView.findViewById(R.id.profileCity);
            addFriendButton = itemView.findViewById(R.id.addFriendButton);
        }
    }
}
