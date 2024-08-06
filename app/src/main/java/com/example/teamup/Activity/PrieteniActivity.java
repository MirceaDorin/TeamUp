package com.example.teamup.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.teamup.Activity.Adapter.PrieteniAdapter;
import com.example.teamup.Activity.Domain.PrieteniDomain;
import com.example.teamup.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PrieteniActivity extends AppCompatActivity {
    private static final String TAG = "PrieteniActivity";

    private RecyclerView friendsRecyclerView;
    private RecyclerView suggestionsRecyclerView;
    private PrieteniAdapter friendsAdapter;
    private PrieteniAdapter suggestionsAdapter;
    private List<PrieteniDomain> friendsList;
    private List<PrieteniDomain> suggestionsList;

    private DatabaseReference usersRef;
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prieteni);

        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

        FirebaseDatabase database = FirebaseDatabase.getInstance("https://teamup-e21fc-default-rtdb.europe-west1.firebasedatabase.app/");
        usersRef = database.getReference().child("users");
        loadExistingUsers();

        friendsRecyclerView = findViewById(R.id.friendsRecyclerView);
        suggestionsRecyclerView = findViewById(R.id.suggestionsRecyclerView);

        friendsList = new ArrayList<>();
        suggestionsList = new ArrayList<>();

        setupRecyclerViews();

        LinearLayout mesajebtn = findViewById(R.id.mesajebtn);
        LinearLayout profilebtn = findViewById(R.id.profilebtn);
        LinearLayout settingsbtn = findViewById(R.id.settingsbtn);
        LinearLayout homebtn = findViewById(R.id.homebtn);

        mesajebtn.setOnClickListener(v -> {
            Intent intent = new Intent(PrieteniActivity.this, ChatActivity.class);
            startActivity(intent);
        });

        profilebtn.setOnClickListener(v -> {
            Intent intent = new Intent(PrieteniActivity.this, ProfileActivity.class);
            startActivity(intent);
        });

        settingsbtn.setOnClickListener(v -> {
            Intent intent = new Intent(PrieteniActivity.this, SettingsActivity.class);
            startActivity(intent);
        });

        homebtn.setOnClickListener(v -> {
            Intent intent = new Intent(PrieteniActivity.this, HomeActivity.class);
            startActivity(intent);
        });
    }

    private void setupRecyclerViews() {
        friendsAdapter = new PrieteniAdapter(this, friendsList, false, null);
        friendsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        friendsRecyclerView.setAdapter(friendsAdapter);

        suggestionsAdapter = new PrieteniAdapter(this, suggestionsList, true, new PrieteniAdapter.OnAddFriendClickListener() {
            @Override
            public void onAddFriendClick(PrieteniDomain prieten) {
                friendsList.add(prieten);
                suggestionsList.remove(prieten);

                friendsAdapter.notifyDataSetChanged();
                suggestionsAdapter.notifyDataSetChanged();
            }
        });
        suggestionsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        suggestionsRecyclerView.setAdapter(suggestionsAdapter);

    }

    private void loadExistingUsers() {
        usersRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                suggestionsList.clear();

                for (DataSnapshot userSnapshot : snapshot.getChildren()) {
                    if (currentUser != null && Objects.equals(userSnapshot.getKey(), currentUser.getUid())) {
                        continue;
                    }

                    String username = userSnapshot.child("username").getValue(String.class);
                    String imageResId = String.valueOf(userSnapshot.child("profileImage").getValue());

                    Log.d(TAG, "User: " + username + ", ImageResId: " + imageResId);

                    PrieteniDomain prieten = new PrieteniDomain(imageResId, username);
                    suggestionsList.add(prieten);
                }

                suggestionsAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(PrieteniActivity.this, "Failed to load users: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e(TAG, "Failed to load users: " + error.getMessage());
            }
        });
    }
}
