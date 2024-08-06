package com.example.teamup.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.AdapterView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.teamup.Activity.Adapter.PostariAdapter;
import com.example.teamup.Activity.Domain.PostariDomain;
import com.example.teamup.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PostariTerenuriActivity extends AppCompatActivity {

    private PostariAdapter postariAdapter;
    private List<PostariDomain> postList;
    private SearchView searchView;
    private Spinner cityFilter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terenuri);


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);


        searchView = findViewById(R.id.searchBar);
        cityFilter = findViewById(R.id.cityFilter);
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Adăugăm DividerItemDecoration
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                DividerItemDecoration.VERTICAL);
        dividerItemDecoration.setDrawable(Objects.requireNonNull(ContextCompat.getDrawable(this, R.drawable.custom_divider)));
        recyclerView.addItemDecoration(dividerItemDecoration);

        postList = new ArrayList<>();
        populatePostList();

        postariAdapter = new PostariAdapter(this, postList);
        recyclerView.setAdapter(postariAdapter);

        setupSearch();
        setupCityFilter();


        //BUTOANELE DE NAVIGARE !
        LinearLayout mesajebtn = findViewById(R.id.mesajebtn);
        LinearLayout profilebtn = findViewById(R.id.profilebtn);
        LinearLayout settingsbtn = findViewById(R.id.settingsbtn);
        LinearLayout homebtn = findViewById(R.id.homebtn);

        mesajebtn.setOnClickListener(v -> {
            Intent intent = new Intent(PostariTerenuriActivity.this, ChatActivity.class);
            startActivity(intent);
        });

        profilebtn.setOnClickListener(v -> {
            Intent intent = new Intent(PostariTerenuriActivity.this, ProfileActivity.class);
            startActivity(intent);
        });

        settingsbtn.setOnClickListener(v -> {
            Intent intent = new Intent(PostariTerenuriActivity.this, SettingsActivity.class);
            startActivity(intent);
        });

        homebtn.setOnClickListener(v -> {
            Intent intent = new Intent(PostariTerenuriActivity.this, HomeActivity.class);
            startActivity(intent);
        });

    }

    private void populatePostList() {
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

        postList.add(new PostariDomain("Baza Sportivă Tineretului", "La a doua închiriere ai 10% reducere", "Oradea", images1, 4.5f,"Str. Tineretului nr. 10, Oradea", "150 RON/oră","1234567890"));
        postList.add(new PostariDomain("Baza Sportivă Gheorgheni", "Avem cel mai nou tip de gazon", "Cluj Napoca", images2, 4.0f,  "Str. Gheorgheni nr. 20, Cluj Napoca", "120 RON/oră","9876543210"));
        postList.add(new PostariDomain("Baza Sportivă Universitate", "Locuri de parcare inclus în preț", "Oradea", images3, 3.5f,  "Str. Universității nr. 1, Oradea", "100 RON/oră", "0246813579"));
    }



    private void setupSearch() {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                postariAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                postariAdapter.getFilter().filter(newText);
                return false;
            }
        });
    }

    private void setupCityFilter() {
        List<String> cities = new ArrayList<>();
        cities.add("Toate Orașele");
        cities.add("Oradea");
        cities.add("Cluj Napoca");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, cities);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cityFilter.setAdapter(adapter);

        cityFilter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedCity = parent.getItemAtPosition(position).toString();
                postariAdapter.filterByCity(selectedCity);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });
    }
}
