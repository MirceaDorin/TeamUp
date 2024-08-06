package com.example.teamup.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.teamup.Activity.Domain.PrieteniDomain;
import com.example.teamup.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class RegisterActivity extends AppCompatActivity {

    // Declararea câmpurilor UI și a Firebase
    private EditText editTextEmail, editTextPassword, editTextName;
    private Button buttonRegister;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        editTextName = findViewById(R.id.editTextName);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        buttonRegister = findViewById(R.id.buttonRegister);

        buttonRegister.setOnClickListener(v -> {
            String name = editTextName.getText().toString().trim();
            String email = editTextEmail.getText().toString().trim();
            String password = editTextPassword.getText().toString().trim();

            if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password) && !TextUtils.isEmpty(name)) {
                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(RegisterActivity.this, task -> {
                            if (task.isSuccessful()) {
                                String userId = Objects.requireNonNull(mAuth.getCurrentUser()).getUid();


                                mDatabase.child("users").child(userId).child("username").setValue(name);

                                // Setare imagine de profil default
                                PrieteniDomain prieten = new PrieteniDomain(String.valueOf(R.drawable.rounded_image_profile), name);

                                mDatabase.child("users").child(userId).setValue(prieten)
                                        .addOnCompleteListener(task1 -> {
                                            if (task1.isSuccessful()) {
                                                Toast.makeText(RegisterActivity.this, "Cont creat cu succes!", Toast.LENGTH_SHORT).show();
                                                startActivity(new Intent(RegisterActivity.this, HomeActivity.class));
                                                finish();
                                            } else {
                                                Toast.makeText(RegisterActivity.this, "Eroare la crearea contului", Toast.LENGTH_SHORT).show();
                                            }
                                        });
                            } else {
                                Toast.makeText(RegisterActivity.this, "Eroare la crearea contului: " + Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
            } else {
                Toast.makeText(RegisterActivity.this, "Introdu numele, emailul și parola", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
