package com.example.teamup.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.teamup.Activity.Adapter.ChatAdapter;
import com.example.teamup.R;
import com.example.teamup.Activity.Domain.ChatDomain;

import java.util.ArrayList;
import java.util.List;

public class ChatActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ChatAdapter chatAdapter;
    private List<ChatDomain> chatList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        // Initialize the RecyclerView and SearchView
        recyclerView = findViewById(R.id.recyclerView);
        SearchView searchView = findViewById(R.id.searchBar);

        // Set up RecyclerView
        chatList = new ArrayList<>();
        chatAdapter = new ChatAdapter(chatList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(chatAdapter);

        // Initialize chat list with dummy data (replace with real data)
        populateChatList();

        // Set up search functionality
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                chatAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                chatAdapter.getFilter().filter(newText);
                return false;
            }
        });

        // Bottom navigation buttons
        LinearLayout mesajebtn = findViewById(R.id.mesajebtn);
        LinearLayout profilebtn = findViewById(R.id.profilebtn);
        LinearLayout settingsbtn = findViewById(R.id.settingsbtn);
        LinearLayout homebtn = findViewById(R.id.homebtn);

        mesajebtn.setOnClickListener(v -> {
            // Already in ChatActivity, no need to do anything
        });

        profilebtn.setOnClickListener(v -> {
            Intent intent = new Intent(ChatActivity.this, ProfileActivity.class);
            startActivity(intent);
        });

        settingsbtn.setOnClickListener(v -> {
            Intent intent = new Intent(ChatActivity.this, SettingsActivity.class);
            startActivity(intent);
        });

        homebtn.setOnClickListener(v -> {
            Intent intent = new Intent(ChatActivity.this, HomeActivity.class);
            startActivity(intent);
        });
    }

    private void populateChatList() {
        // Dummy data
        chatList.add(new ChatDomain("Radu Drăgușin", "Salut!", "11:00 AM"));
        chatList.add(new ChatDomain("Ianis Hagi", "Ne vedem la ora 8", "12:30 PM"));
        chatList.add(new ChatDomain("Florinel Coman", "Să-ți aduci mânuși că stai în poartă", "12:45 PM"));

        chatAdapter.notifyDataSetChanged();
    }
}
