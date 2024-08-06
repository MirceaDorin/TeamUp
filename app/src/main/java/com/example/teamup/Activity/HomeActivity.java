package com.example.teamup.Activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Base64;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.teamup.Activity.Adapter.PopularAdapter;
import com.example.teamup.Activity.Domain.PopularDomain;
import com.example.teamup.Activity.Domain.PostariDomain;
import com.example.teamup.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {
    private RecyclerView.Adapter adapterPopularList;
    private RecyclerView recyclerViewPopular;
    private LinearLayout profilebtn;
    private LinearLayout settingsbtn;
    private LinearLayout mesajebtn;
    private LinearLayout postaribtn;
    private LinearLayout prietenibtn;
    private LinearLayout meciuribtn;
    private TextView vezimmbtn;
    private ImageView profileImageView;

    private TextView usernameTextView;

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        profileImageView = findViewById(R.id.imageView7);
        usernameTextView = findViewById(R.id.textView4);

        initRecyclerView();
        BottomNavigation();
        UpperNavigation();
        loadProfileImage();
        loadUsername();
    }

    private void BottomNavigation() {
        profilebtn = findViewById(R.id.profilebtn);
        settingsbtn = findViewById(R.id.settingsbtn);
        mesajebtn = findViewById(R.id.mesajebtn);

        profilebtn.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, ProfileActivity.class);
            startActivity(intent);
            finish();
        });

        settingsbtn.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, SettingsActivity.class);
            startActivity(intent);
            finish();
        });

        mesajebtn.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, ChatActivity.class);
            startActivity(intent);
            finish();
        });
    }

    private void UpperNavigation() {
        postaribtn = findViewById(R.id.postaribtn);
        prietenibtn = findViewById(R.id.prietenibtn);
        meciuribtn = findViewById(R.id.meciuribtn);
        vezimmbtn = findViewById(R.id.vezimmbtn);

        postaribtn.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, PostariTerenuriActivity.class);
            startActivity(intent);
            finish();
        });

        prietenibtn.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, PrieteniActivity.class);
            startActivity(intent);
            finish();
        });

        meciuribtn.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, MeciuriActivity.class);
            startActivity(intent);
            finish();
        });

        vezimmbtn.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, PostariTerenuriActivity.class);
            startActivity(intent);
            finish();
        });
    }

    private void initRecyclerView() {
        ArrayList<PopularDomain> items = new ArrayList<>();
        items.add(new PopularDomain("Baza Sportiva Tineretului", "+INCHIRIAZĂ", "baza_sportiva_tineretului"));
        items.add(new PopularDomain("Baza Sportiva Universitate", "+INCHIRIAZĂ", "sintetic_univ"));
        items.add(new PopularDomain("Baza Sportiva Gheorgheni", "+INCHIRIAZĂ", "baza_sportiva_gheorgheni"));

        recyclerViewPopular = findViewById(R.id.view1);
        recyclerViewPopular.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        adapterPopularList = new PopularAdapter(items, new PopularAdapter.OnItemClickListener() {
            @Override
            public void onNavButtonClick(PopularDomain item) {
                PostariDomain postDetails = getPostDetails(item.getTitle());

                Intent intent = new Intent(HomeActivity.this, BazaSportivaActivity.class);
                intent.putExtra("title", postDetails.getTitle());
                intent.putExtra("description", postDetails.getDescription());
                intent.putExtra("city", postDetails.getCity());
                intent.putExtra("imageResIds", new ArrayList<>(postDetails.getImageResIds()));
                intent.putExtra("rating", postDetails.getRating());
                intent.putExtra("fullAddress", postDetails.getFullAddress());
                intent.putExtra("hourlyRate", postDetails.getHourlyRate());
                intent.putExtra("phoneNumber", postDetails.getPhoneNumber());
                startActivity(intent);
            }
        });

        recyclerViewPopular.setAdapter(adapterPopularList);
    }

    private PostariDomain getPostDetails(String title) {
        List<Integer> images1 = new ArrayList<>();
        images1.add(R.drawable.baza_sportiva_tineretului);
        images1.add(R.drawable.baza_sportiva_tineretului);
        images1.add(R.drawable.baza_sportiva_tineretului);

        List<Integer> images2 = new ArrayList<>();
        images2.add(R.drawable.baza_sportiva_gheorgheni);
        images2.add(R.drawable.baza_sportiva_gheorgheni);
        images2.add(R.drawable.baza_sportiva_gheorgheni);

        List<Integer> images3 = new ArrayList<>();
        images3.add(R.drawable.sintetic_univ);
        images3.add(R.drawable.sintetic_univ);
        images3.add(R.drawable.sintetic_univ);

        switch (title) {
            case "Baza Sportiva Tineretului":
                return new PostariDomain("Baza Sportivă Tineretului", "La a doua închiriere ai 10% reducere", "Oradea", images1, 4.5f,"Str. Tineretului nr. 10, Oradea", "150 RON/oră", "1234567890");

            case "Baza Sportiva Gheorgheni":
                return new PostariDomain("Baza Sportivă Gheorgheni", "Avem cel mai nou tip de gazon", "Cluj Napoca", images2, 4.0f,  "Str. Gheorgheni nr. 20, Cluj Napoca", "120 RON/oră","9876543210");
            case "Baza Sportiva Universitate":
                return new PostariDomain("Baza Sportivă Universitate", "Locuri de parcare inclus în preț", "Oradea", images3, 3.5f,  "Str. Universității nr. 1, Oradea", "100 RON/oră","0246813579");
            default:
                return null;
        }
    }

    private void loadProfileImage() {
        String userId = mAuth.getCurrentUser().getUid();
        mDatabase.child("users").child(userId).child("profileImage").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String profileImageValue = String.valueOf(dataSnapshot.getValue());

                    if (!TextUtils.isEmpty(profileImageValue)) {
                        byte[] decodedString = Base64.decode(profileImageValue, Base64.DEFAULT);
                        Bitmap decodedBitmap = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);

                        profileImageView.setImageBitmap(decodedBitmap);
                    } else {
                        Toast.makeText(HomeActivity.this, "Eroare încărcare imagine de profil", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    //Toast.makeText(HomeActivity.this, "Imaginea de profil nu există în baza de date", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle error
                Toast.makeText(HomeActivity.this, "Nu s-a putut încărca imaginea de profil " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
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
                    usernameTextView.setText(username);
                } else {
                    Toast.makeText(HomeActivity.this, "Nu a fost gasit niciun utilizator cu numele acesta în baza de date", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(HomeActivity.this, "Eroare la încărcarea utilizatorilor " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
