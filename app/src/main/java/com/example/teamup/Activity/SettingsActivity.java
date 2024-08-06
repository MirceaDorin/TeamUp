package com.example.teamup.Activity;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.teamup.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class SettingsActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_GALLERY = 100;
    private static final int REQUEST_CODE_PERMISSION = 200;

    private ImageView profileImageView;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private TextView usernameTextView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);


        usernameTextView = findViewById(R.id.textView4);
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        loadUsername();


        //Selectarea date nasterii:
        LinearLayout selectAgeLayout = findViewById(R.id.select_age);
        selectAgeLayout.setOnClickListener(view -> showDatePickerDialog());

        // Spinner pentru dropdownbutton
        Spinner spinnerCity = findViewById(R.id.spinner_city);
        Spinner spinnerAge = findViewById(R.id.spinner_age);

// Create ArrayAdapter using the string array and default spinner layout
        ArrayAdapter<CharSequence> adapterCity = ArrayAdapter.createFromResource(this,
                R.array.cities_array, android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence> adapterAge = ArrayAdapter.createFromResource(this,
                R.array.ages_array, android.R.layout.simple_spinner_item);

// Specify the layout to use when the list of choices appears
        adapterCity.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapterAge.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

// Apply the adapter to the spinner
        spinnerCity.setAdapter(adapterCity);
        spinnerAge.setAdapter(adapterAge);


        // Initialize elements from the layout
        ImageView backBtn = findViewById(R.id.imageView8);
        TextView profileName = findViewById(R.id.textView4);
        TextView profileEmail = findViewById(R.id.email);
        Switch notificationSwitch = findViewById(R.id.switch_notifications);

        // Initialize LinearLayouts for each setting option
        LinearLayout selectCity = findViewById(R.id.select_city);
        LinearLayout changeEmail = findViewById(R.id.change_email);
        LinearLayout changePassword = findViewById(R.id.change_password);
        LinearLayout toggleNotifications = findViewById(R.id.toggle_notifications);
        LinearLayout selectAge = findViewById(R.id.select_age);
        LinearLayout privacyPolicy = findViewById(R.id.privacy_policy);
        LinearLayout reportProblem = findViewById(R.id.report_problem);
        LinearLayout help = findViewById(R.id.help);
        LinearLayout deleteAccount = findViewById(R.id.delete_account);

        backBtn.setOnClickListener(view -> {
            // Handle back button click to go to ActivityHome
            Intent intent = new Intent(SettingsActivity.this, HomeActivity.class);
            startActivity(intent);
        });

        selectCity.setOnClickListener(view -> {
            // Handle select city option
            // Start another activity or show a dialog
            Toast.makeText(SettingsActivity.this, "Alege Orasul clicked", Toast.LENGTH_SHORT).show();
        });

        changeEmail.setOnClickListener(view -> {
            // Handle change email option
            // Start another activity or show a dialog
            Toast.makeText(SettingsActivity.this, "Schimbă email clicked", Toast.LENGTH_SHORT).show();
        });

        changePassword.setOnClickListener(view -> {
            // Handle change password option
            // Start another activity or show a dialog
            Toast.makeText(SettingsActivity.this, "Schimbă parola clicked", Toast.LENGTH_SHORT).show();
        });

        toggleNotifications.setOnClickListener(view -> {
            // Handle toggle notifications option
            // This can be managed through the Switch directly
        });

        selectAge.setOnClickListener(view -> {
            // Handle select age option
            // Start another activity or show a dialog
            Toast.makeText(SettingsActivity.this, "Selectează vârsta clicked", Toast.LENGTH_SHORT).show();
        });

        privacyPolicy.setOnClickListener(view -> {
            // Handle privacy policy option
            // Start another activity or show a dialog
            Toast.makeText(SettingsActivity.this, "Politică de confidențialitate clicked", Toast.LENGTH_SHORT).show();
        });

        reportProblem.setOnClickListener(view -> {
            // Handle report a problem option
            // Start another activity or show a dialog
            Toast.makeText(SettingsActivity.this, "Raportează o problemă clicked", Toast.LENGTH_SHORT).show();
        });

        help.setOnClickListener(view -> {
            // Handle help option
            // Start another activity or show a dialog
            Toast.makeText(SettingsActivity.this, "Ajutor clicked", Toast.LENGTH_SHORT).show();
        });

        deleteAccount.setOnClickListener(view -> {
            // Handle delete account option
            // Start another activity or show a dialog
            Toast.makeText(SettingsActivity.this, "Șterge contul clicked", Toast.LENGTH_SHORT).show();
        });

        // Optionally, handle the switch state change directly
        notificationSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            // Handle the notification switch state change
            if (isChecked) {
                Toast.makeText(SettingsActivity.this, "Notificări pornite", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(SettingsActivity.this, "Notificări oprite", Toast.LENGTH_SHORT).show();
            }
        });

        // Inițializarea ImageView pentru a permite selectarea imaginii
        profileImageView = findViewById(R.id.imageView7);
        profileImageView.setOnClickListener(v -> {
            if (ContextCompat.checkSelfPermission(SettingsActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(SettingsActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_CODE_PERMISSION);
            } else {
                openGallery();
            }
        });
    }
    private void showDatePickerDialog() {
        DatePickerDialog.OnDateSetListener dateSetListener = (view, year, month, dayOfMonth) -> {
            // Calendar pentru data curentă
            Calendar current = Calendar.getInstance();
            // Calendar pentru data nașterii
            Calendar selected = Calendar.getInstance();
            selected.set(year, month, dayOfMonth);

            // Calculul vârstei
            int age = current.get(Calendar.YEAR) - selected.get(Calendar.YEAR);
            if (current.get(Calendar.DAY_OF_YEAR) < selected.get(Calendar.DAY_OF_YEAR)) {
                age--;
            }

            // Setarea varstei sau afisarea in alt view
            // De exemplu:
            // textViewAge.setText(String.valueOf(age));
            // Sau poți face orice altceva cu valoarea vârstei
        };

        // Obțineți data curentă pentru a seta ca valoare implicită în dialogul DatePicker
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        // Afișează dialogul DatePicker
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, dateSetListener, year, month, day);
        datePickerDialog.show();
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openGallery();
            } else {
                Toast.makeText(this, "Permisiunea este necesară pentru a accesa galeria", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, REQUEST_CODE_GALLERY);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_GALLERY && resultCode == RESULT_OK && data != null) {
            Uri selectedImage = data.getData();
            try {
                profileImageView.setImageURI(selectedImage);

                saveImageToDatabase(selectedImage);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    // Save image to Firebase Realtime Database
    private void saveImageToDatabase(Uri imageUri) {
        try {
            String base64Image = convertImageToBase64(imageUri);

            String userId = mAuth.getCurrentUser().getUid();
            Map<String, Object> imageMap = new HashMap<>();
            imageMap.put("profileImage", base64Image);
            mDatabase.child("users").child(userId).updateChildren(imageMap);

            Toast.makeText(this, "Imaginea de profil a fost salvată cu succes", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Eroare la salvarea imaginii de profil", Toast.LENGTH_SHORT).show();
        }
    }

    private String convertImageToBase64(Uri imageUri) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] imageBytes;
        try {
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);

            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            imageBytes = baos.toByteArray();
        } finally {
            baos.close();
        }

        return Base64.encodeToString(imageBytes, Base64.DEFAULT);
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
                    Toast.makeText(SettingsActivity.this, "Utilizatorul nu a fost găsit în baza de date", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle error
                Toast.makeText(SettingsActivity.this, "Eroare la încărcarea utilizatorului " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
