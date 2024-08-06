package com.example.teamup.Activity.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.GranularRoundedCorners;
import com.example.teamup.Activity.Domain.PopularDomain;
import com.example.teamup.R;

import java.util.ArrayList;

public class PopularAdapter extends RecyclerView.Adapter<PopularAdapter.Viewholder> {

    private ArrayList<PopularDomain> items;
    private Context context;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onNavButtonClick(PopularDomain item);
    }

    public PopularAdapter(ArrayList<PopularDomain> items, OnItemClickListener listener) {
        this.items = items;
        this.listener = listener;
    }

    @NonNull
    @Override
    public PopularAdapter.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflator = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_popular_list, parent, false);
        context = parent.getContext();
        return new Viewholder(inflator);
    }

    @Override
    public void onBindViewHolder(@NonNull PopularAdapter.Viewholder holder, int position) {
        PopularDomain currentItem = items.get(position);

        holder.title.setText(currentItem.getTitle());
        holder.subtitle.setText(currentItem.getSubtitle());

        int drawableResourceId = holder.itemView.getResources().getIdentifier(currentItem.getPicAddress(), "drawable", holder.itemView.getContext().getPackageName());

        if (drawableResourceId != 0) {
            Glide.with(holder.itemView.getContext())
                    .load(drawableResourceId)
                    .transform(new GranularRoundedCorners(30, 30, 0, 0))
                    .into(holder.pic);
        } else {
            holder.pic.setImageResource(R.drawable.baza_sportiva_tineretului);
        }

        holder.subtitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onNavButtonClick(currentItem);
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class Viewholder extends RecyclerView.ViewHolder {
        TextView title;
        TextView subtitle;
        ImageView pic;

        public Viewholder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.titletxt);
            subtitle = itemView.findViewById(R.id.navbutton);
            pic = itemView.findViewById(R.id.pic);
        }
    }
}
