package bluetowel.com.laundryhelper.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;

import bluetowel.com.laundryhelper.MainActivity;
import bluetowel.com.laundryhelper.R;
import bluetowel.com.laundryhelper.util.SwitchHelper;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getSupportActionBar().hide();
        setContentView(R.layout.activity_splash);

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent myIntent = new Intent(SplashActivity.this, MainActivity.class);
                SwitchHelper.animReq = false;
                SplashActivity.this.startActivity(myIntent);
                finish();
            }
        }, 1400);

    }
}
