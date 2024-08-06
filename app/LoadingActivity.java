import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.example.teamup.R;

public class LoadingActivity extends AppCompatActivity {

    private static final int LOADING_DELAY = 2000; // Adjust the delay time as needed

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);

        // Simulate loading process
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Start your main activity after loading delay
                Intent intent = new Intent(LoadingActivity.this, MainActivity.class);
                startActivity(intent);
                finish(); // Close the loading screen activity
            }
        }, LOADING_DELAY);
    }
}
