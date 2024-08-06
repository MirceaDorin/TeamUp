package com.example.teamup.Activity.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.teamup.Activity.BazaSportivaActivity;
import com.example.teamup.Activity.Domain.PostariDomain;
import com.example.teamup.R;

import java.util.ArrayList;
import java.util.List;

public class PostariAdapter extends RecyclerView.Adapter<PostariAdapter.PostariViewHolder> implements Filterable {

    private List<PostariDomain> postList;
    private List<PostariDomain> fullPostList;
    private Context context;

    public PostariAdapter(Context context, List<PostariDomain> postList) {
        this.context = context;
        this.postList = postList;
        this.fullPostList = new ArrayList<>(postList);
    }

    @NonNull
    @Override
    public PostariViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_activity_postari, parent, false);
        return new PostariViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PostariViewHolder holder, int position) {
        PostariDomain post = postList.get(position);
        holder.postTitle.setText(post.getTitle());
        holder.postDescription.setText(post.getDescription());
        holder.cityTextView.setText(post.getCity());
        holder.postImage.setImageResource(post.getImageResIds().get(0)); // Show the first image as a thumbnail
        holder.ratingBar.setRating(post.getRating());
        holder.actionButton.setOnClickListener(v -> {
            Intent intent = new Intent(context, BazaSportivaActivity.class);
            intent.putExtra("title", post.getTitle());
            intent.putExtra("description", post.getDescription());
            intent.putExtra("city", post.getCity());
            intent.putExtra("imageResIds", new ArrayList<>(post.getImageResIds()));
            intent.putExtra("rating", post.getRating());
            intent.putExtra("fullAddress", post.getFullAddress());
            intent.putExtra("hourlyRate", post.getHourlyRate());
            intent.putExtra("phoneNumber", post.getPhoneNumber());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return postList.size();
    }

    @Override
    public Filter getFilter() {
        return postFilter;
    }

    private Filter postFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<PostariDomain> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(fullPostList);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (PostariDomain item : fullPostList) {
                    if (item.getTitle().toLowerCase().contains(filterPattern)) {
                        filteredList.add(item);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            postList.clear();
            postList.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };

    public void filterByCity(String city) {
        if (city.equals("Toate Orașele")) {
            postList = new ArrayList<>(fullPostList);
        } else {
            List<PostariDomain> filteredList = new ArrayList<>();
            for (PostariDomain post : fullPostList) {
                if (post.getCity().equalsIgnoreCase(city)) {
                    filteredList.add(post);
                }
            }
            postList = filteredList;
        }
        notifyDataSetChanged();
    }

    public static class PostariViewHolder extends RecyclerView.ViewHolder {
        TextView postTitle, postDescription, cityTextView;
        ImageView postImage;
        RatingBar ratingBar;
        Button actionButton;

        public PostariViewHolder(@NonNull View itemView) {
            super(itemView);
            postTitle = itemView.findViewById(R.id.postTitle);
            postDescription = itemView.findViewById(R.id.postDescription);
            cityTextView = itemView.findViewById(R.id.cityTextView);
            postImage = itemView.findViewById(R.id.postImage);
            ratingBar = itemView.findViewById(R.id.ratingBar);
            actionButton = itemView.findViewById(R.id.actionButton);
        }
    }
}
