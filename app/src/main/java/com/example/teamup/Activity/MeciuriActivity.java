package com.example.teamup.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.teamup.Activity.Adapter.MeciuriAdapter;
import com.example.teamup.Activity.Domain.CreareMeciDomain;
import com.example.teamup.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MeciuriActivity extends AppCompatActivity {

    private RecyclerView meciuriRecyclerView;
    private MeciuriAdapter meciuriAdapter;
    private List<CreareMeciDomain> meciuriList;
    private DatabaseReference mDatabase;
    private LinearLayout backbtn;
     private Button buttonCreateMatch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meciuri);

        mDatabase = FirebaseDatabase.getInstance("https://teamup-e21fc-default-rtdb.europe-west1.firebasedatabase.app/").getReference();
        loadMeciuri();

        meciuriRecyclerView = findViewById(R.id.recyclerViewMeciuri);
        meciuriList = new ArrayList<>();
        meciuriAdapter = new MeciuriAdapter(this, meciuriList);
        meciuriRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        meciuriRecyclerView.setAdapter(meciuriAdapter);
        backbtn = findViewById(R.id.backbtn);
        buttonCreateMatch = findViewById(R.id.buttonCreateMatch);

        backbtn.setOnClickListener(v -> {
            Intent intent = new Intent(MeciuriActivity.this, HomeActivity.class);
            startActivity(intent);
        });

        buttonCreateMatch.setOnClickListener(v -> {
            Intent intent = new Intent(MeciuriActivity.this, CreareMeciActivity.class);
            startActivity(intent);
        });

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(meciuriRecyclerView.getContext(),
                DividerItemDecoration.VERTICAL);
        dividerItemDecoration.setDrawable(Objects.requireNonNull(ContextCompat.getDrawable(this, R.drawable.custom_divider)));
        meciuriRecyclerView.addItemDecoration(dividerItemDecoration);
    }

    private void loadMeciuri() {
        mDatabase.child("crearemeci").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                meciuriList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    CreareMeciDomain meci = dataSnapshot.getValue(CreareMeciDomain.class);
                    if (meci != null) {
                        meciuriList.add(meci);
                    }
                }
                meciuriAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MeciuriActivity.this, "Eroare încărcare meciuri din baza de date: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
