package com.example.teamup.Activity.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.teamup.Activity.Domain.ChatDomain;
import com.example.teamup.R;

import java.util.ArrayList;
import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ChatViewHolder> implements Filterable {

    private List<ChatDomain> chatList;
    private List<ChatDomain> chatListFull;

    public ChatAdapter(List<ChatDomain> chatList) {
        this.chatList = chatList;
        chatListFull = new ArrayList<>(chatList);
    }

    @NonNull
    @Override
    public ChatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chat_message, parent, false);
        return new ChatViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatViewHolder holder, int position) {
        ChatDomain chatDomain = chatList.get(position);
        holder.nameTextView.setText(chatDomain.getName());
        holder.messageTextView.setText(chatDomain.getLastMessage());
        holder.timestampTextView.setText(chatDomain.getTimestamp());
    }

    @Override
    public int getItemCount() {
        return chatList.size();
    }

    @Override
    public Filter getFilter() {
        return chatFilter;
    }

    private Filter chatFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<ChatDomain> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(chatListFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (ChatDomain item : chatListFull) {
                    if (item.getName().toLowerCase().contains(filterPattern)) {
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
            chatList.clear();
            chatList.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };

    static class ChatViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView;
        TextView messageTextView;
        TextView timestampTextView;

        public ChatViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.nameTextView);
            messageTextView = itemView.findViewById(R.id.messageTextView);
            timestampTextView = itemView.findViewById(R.id.timestampTextView);
        }
    }
}
