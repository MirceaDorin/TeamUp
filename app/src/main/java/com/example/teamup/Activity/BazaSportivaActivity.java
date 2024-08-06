package com.example.teamup.Activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager2.widget.ViewPager2;

import com.example.teamup.Activity.Adapter.BazaSportivaAdapter;
import com.example.teamup.R;

import java.util.List;
import java.util.Objects;

public class BazaSportivaActivity extends AppCompatActivity {

    private TextView baseName, description, address, ratingText, hourlyRate;
    private ViewPager2 viewPager;
    private LinearLayout indicatorLayout;
    private BazaSportivaAdapter adapter;
    private Button rentButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bazasportiva);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);

        baseName = findViewById(R.id.baseName);
        description = findViewById(R.id.description);
        address = findViewById(R.id.address);
        ratingText = findViewById(R.id.ratingText);
        hourlyRate = findViewById(R.id.hourlyRate);
        viewPager = findViewById(R.id.viewPager);
        indicatorLayout = findViewById(R.id.indicatorLayout);
        rentButton = findViewById(R.id.rentButton);

        Intent intent = getIntent();
        String title = intent.getStringExtra("title");
        String desc = intent.getStringExtra("description");
        String city = intent.getStringExtra("city");
        List<Integer> imageResIds = intent.getIntegerArrayListExtra("imageResIds");
        float rating = intent.getFloatExtra("rating", 0);
        String fullAddress = intent.getStringExtra("fullAddress");
        String rate = intent.getStringExtra("hourlyRate");
        String phoneNumber = intent.getStringExtra("phoneNumber");

        baseName.setText(title);
        description.setText(desc);
        address.setText(fullAddress);
        ratingText.setText(String.valueOf(rating));
        hourlyRate.setText(rate);

        adapter = new BazaSportivaAdapter(imageResIds);
        viewPager.setAdapter(adapter);

        assert imageResIds != null;
        setupIndicators(imageResIds.size());
        setCurrentIndicator(0);

        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                setCurrentIndicator(position);
            }
        });

        rentButton.setOnClickListener(v -> {
            if (phoneNumber != null && !phoneNumber.isEmpty()) {
                Intent dialIntent = new Intent(Intent.ACTION_DIAL);
                dialIntent.setData(Uri.parse("tel:" + phoneNumber));
                startActivity(dialIntent);
            } else {
                Toast.makeText(BazaSportivaActivity.this, "Numărul de telefon nu este disponibil", Toast.LENGTH_SHORT).show();
            }
        });

        LinearLayout mesajebtn = findViewById(R.id.mesajebtn);
        LinearLayout profilebtn = findViewById(R.id.profilebtn);
        LinearLayout settingsbtn = findViewById(R.id.settingsbtn);
        LinearLayout homebtn = findViewById(R.id.homebtn);
        LinearLayout backbtn = findViewById(R.id.backbtn);

        mesajebtn.setOnClickListener(v -> {
            Intent intent1 = new Intent(BazaSportivaActivity.this, ChatActivity.class);
            startActivity(intent1);
        });

        profilebtn.setOnClickListener(v -> {
            Intent intent1 = new Intent(BazaSportivaActivity.this, ProfileActivity.class);
            startActivity(intent1);
        });

        settingsbtn.setOnClickListener(v -> {
            Intent intent1 = new Intent(BazaSportivaActivity.this, SettingsActivity.class);
            startActivity(intent1);
        });

        homebtn.setOnClickListener(v -> {
            Intent intent1 = new Intent(BazaSportivaActivity.this, HomeActivity.class);
            startActivity(intent1);
        });

        backbtn.setOnClickListener(v -> {
            Intent intent1 = new Intent(BazaSportivaActivity.this, PostariTerenuriActivity.class);
            startActivity(intent1);
        });
    }

    private void setupIndicators(int count) {
        ImageView[] indicators = new ImageView[count];
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        params.setMargins(8, 0, 8, 0);

        for (int i = 0; i < indicators.length; i++) {
            indicators[i] = new ImageView(this);
            indicators[i].setLayoutParams(params);
            indicators[i].setImageDrawable(getDrawable(R.drawable.indicator_inactive));
            indicatorLayout.addView(indicators[i]);
        }

        setCurrentIndicator(0);
    }

    private void setCurrentIndicator(int index) {
        int childCount = indicatorLayout.getChildCount();
        for (int i = 0; i < childCount; i++) {
            ImageView imageView = (ImageView) indicatorLayout.getChildAt(i);
            if (i == index) {
                imageView.setImageDrawable(getDrawable(R.drawable.indicator_active));
            } else {
                imageView.setImageDrawable(getDrawable(R.drawable.indicator_inactive));
            }
        }
    }
}
