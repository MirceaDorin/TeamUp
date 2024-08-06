package com.example.teamup.Activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Base64;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.teamup.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileActivity extends AppCompatActivity {

    private ImageView profileImageView;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private TextView usernameTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // Initialize elements from the layout
        ConstraintLayout backBtn = findViewById(R.id.backbtn);
        ImageView backBtnIcon = findViewById(R.id.imageView8);
        profileImageView = findViewById(R.id.imageView7);
        usernameTextView = findViewById(R.id.textView4);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        loadProfileImage();
        loadUsername();

        // Initialize sections
        ConstraintLayout notificationsSection = findViewById(R.id.notifications);
        ConstraintLayout matchesSection = findViewById(R.id.meciuri);
        ConstraintLayout calendarSection = findViewById(R.id.calendar);
        ConstraintLayout friendsSection = findViewById(R.id.friends);
        ConstraintLayout rankSection = findViewById(R.id.rank);
        ConstraintLayout settingsSection = findViewById(R.id.settings);
        ConstraintLayout logoutSection = findViewById(R.id.logout);

        backBtn.setOnClickListener(view -> {
            // Handle back button click to go to ActivityHome
            Intent intent = new Intent(ProfileActivity.this, HomeActivity.class);
            startActivity(intent);
            finish(); // Optional: Close the current activity
        });

        notificationsSection.setOnClickListener(view -> {
            // Handle notifications section click
            Toast.makeText(ProfileActivity.this, "Notificari clicked", Toast.LENGTH_SHORT).show();
        });

        matchesSection.setOnClickListener(view -> {
            // Handle matches section click
            Toast.makeText(ProfileActivity.this, "Meciurile Tale clicked", Toast.LENGTH_SHORT).show();
        });

        calendarSection.setOnClickListener(view -> {
            // Handle calendar section click
            Toast.makeText(ProfileActivity.this, "Calendar clicked", Toast.LENGTH_SHORT).show();
        });

        friendsSection.setOnClickListener(view -> {
            // Handle friends section click
            Intent intent = new Intent(ProfileActivity.this, PrieteniActivity.class);
            startActivity(intent);
            finish(); // Optional: Close the current activity
        });

        rankSection.setOnClickListener(view -> {
            // Handle rank section click
            Toast.makeText(ProfileActivity.this, "Grad clicked", Toast.LENGTH_SHORT).show();
        });

        settingsSection.setOnClickListener(view -> {
            // Handle settings section click
            Intent intent = new Intent(ProfileActivity.this, SettingsActivity.class);
            startActivity(intent);
            finish(); // Optional: Close the current activity
        });

        logoutSection.setOnClickListener(view -> {
            new AlertDialog.Builder(ProfileActivity.this)
                    .setTitle("Confirmare Logout")
                    .setMessage("Ești sigur că vrei să te deconectezi?")
                    .setPositiveButton("Da", (dialog, which) -> {
                        // Handle logout action
                        mAuth.signOut();
                        Intent intent = new Intent(ProfileActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    })
                    .setNegativeButton("Nu", null)
                    .show();
        });
    }

    private void loadProfileImage() {
        String userId = mAuth.getCurrentUser().getUid();
        mDatabase.child("users").child(userId).child("profileImage").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // Convert Long to String
                    String profileImageValue = String.valueOf(dataSnapshot.getValue());

                    // Check if profileImageValue is not empty
                    if (!TextUtils.isEmpty(profileImageValue)) {
                        // Convert base64 string to Bitmap
                        byte[] decodedString = Base64.decode(profileImageValue, Base64.DEFAULT);
                        Bitmap decodedBitmap = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);

                        // Set profile image
                        profileImageView.setImageBitmap(decodedBitmap);
                    } else {
                        // Handle case where profileImageValue is empty
                        Toast.makeText(ProfileActivity.this, "Nu ai ales nicio imagine de profil", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    //Toast.makeText(ProfileActivity.this, "Imaginea de profil nu există", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle error
                Toast.makeText(ProfileActivity.this, "Eroare la încărcarea imaginii de profil: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void loadUsername() {
        String userId = mAuth.getCurrentUser().getUid();
        mDatabase.child("users").child(userId).child("username").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String username = dataSnapshot.getValue(String.class);
                    // Set username in TextView
                    usernameTextView.setText(username);
                } else {
                    // Handle case where dataSnapshot doesn't exist
                    Toast.makeText(ProfileActivity.this, "Acest nume nu există în baza de date", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle error
                Toast.makeText(ProfileActivity.this, "Eroarea la incărcarea utilizatorilor: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
