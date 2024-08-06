package com.example.teamup.Activity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.teamup.Activity.Domain.CreareMeciDomain;
import com.example.teamup.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

public class CreareMeciActivity extends AppCompatActivity {

    private TextView textViewCreatorName, textViewSelectedDateTime;
    private Spinner spinnerLocation, spinnerPrice;
    private Button buttonSelectDateTime, buttonCreateMatch;
    private EditText editTextCreatorName;

    private String selectedLocation, selectedPrice;
    private String selectedDateTime;

    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creare_meci);

        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance("https://teamup-e21fc-default-rtdb.europe-west1.firebasedatabase.app/").getReference();

        textViewCreatorName = findViewById(R.id.creatorName);
        spinnerLocation = findViewById(R.id.locationSpinner);
        spinnerPrice = findViewById(R.id.priceSpinner);
        buttonSelectDateTime = findViewById(R.id.selectDateTimeButton);
        textViewSelectedDateTime = findViewById(R.id.selectedDateTime);
        buttonCreateMatch = findViewById(R.id.createMatchButton);

        editTextCreatorName = findViewById(R.id.creatorName);

        if (currentUser != null) {
            textViewCreatorName.setText(currentUser.getDisplayName());
        }

        spinnerLocation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedLocation = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });

        spinnerPrice.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedPrice = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });

        buttonSelectDateTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateTimePicker();
            }
        });

        buttonCreateMatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createMatch();
            }
        });

        LinearLayout backbtn = findViewById(R.id.backbtn);
        backbtn.setOnClickListener(v -> {
            Intent intent = new Intent(CreareMeciActivity.this, MeciuriActivity.class);
            startActivity(intent);
        });
    }

    private void showDateTimePicker() {
        final Calendar currentDate = Calendar.getInstance();
        Calendar date = Calendar.getInstance();
        new DatePickerDialog(CreareMeciActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                date.set(year, monthOfYear, dayOfMonth);
                new TimePickerDialog(CreareMeciActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        date.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        date.set(Calendar.MINUTE, minute);
                        selectedDateTime = date.getTime().toString();
                        textViewSelectedDateTime.setText(selectedDateTime);
                    }
                }, currentDate.get(Calendar.HOUR_OF_DAY), currentDate.get(Calendar.MINUTE), false).show();
            }
        }, currentDate.get(Calendar.YEAR), currentDate.get(Calendar.MONTH), currentDate.get(Calendar.DATE)).show();
    }

    private void createMatch() {
        String creatorName = textViewCreatorName.getText().toString();
        if (creatorName.isEmpty() || selectedLocation == null || selectedDateTime == null || selectedPrice == null) {
            Toast.makeText(this, "Te rugăm să completezi toate câmpurile", Toast.LENGTH_SHORT).show();
            return;
        }

        String matchId = mDatabase.child("crearemeci").push().getKey();
        if (matchId == null) {
            Toast.makeText(this, "Eroare la crearea meciului", Toast.LENGTH_SHORT).show();
            return;
        }

        CreareMeciDomain newMatch = new CreareMeciDomain(creatorName, selectedLocation, selectedDateTime, selectedPrice, 1, 12);

        mDatabase.child("crearemeci").child(matchId).setValue(newMatch)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(this, "Meciul a fost creat cu succes", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(this, "Eroare la crearea meciului: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
