package com.example.teamup.Activity.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.teamup.Activity.MeciuriActivity;
import com.example.teamup.Activity.Domain.CreareMeciDomain;
import com.example.teamup.R;

import java.util.List;

public class MeciuriAdapter extends RecyclerView.Adapter<MeciuriAdapter.MeciuriViewHolder> {

    private Context context;
    private List<CreareMeciDomain> meciuriList;

    public MeciuriAdapter(Context context, List<CreareMeciDomain> meciuriList) {
        this.context = context;
        this.meciuriList = meciuriList;
    }

    @NonNull
    @Override
    public MeciuriViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_meciuri, parent, false);
        return new MeciuriViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MeciuriViewHolder holder, int position) {
        CreareMeciDomain meci = meciuriList.get(position);
        holder.textViewCreator.setText(meci.getCreatorName());
        holder.textViewLocation.setText(meci.getLocation());
        holder.textViewDateTime.setText(meci.getDateTime());
        holder.textViewPrice.setText(meci.getPrice());
        holder.textViewParticipants.setText(meci.getCurrentParticipants() + "/" + meci.getMaxParticipants() + " locuri");

        holder.buttonJoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (meci.getCurrentParticipants() < meci.getMaxParticipants()) {
                    meci.setCurrentParticipants(meci.getCurrentParticipants() + 1);
                    notifyDataSetChanged();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return meciuriList.size();
    }

    public class MeciuriViewHolder extends RecyclerView.ViewHolder {

        TextView textViewCreator, textViewLocation, textViewDateTime, textViewPrice, textViewParticipants;
        Button buttonJoin;

        public MeciuriViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewCreator = itemView.findViewById(R.id.textViewCreator);
            textViewLocation = itemView.findViewById(R.id.textViewLocation);
            textViewDateTime = itemView.findViewById(R.id.textViewDateTime);
            textViewPrice = itemView.findViewById(R.id.textViewPrice);
            textViewParticipants = itemView.findViewById(R.id.textViewParticipants);
            buttonJoin = itemView.findViewById(R.id.buttonJoin);
        }
    }
}
