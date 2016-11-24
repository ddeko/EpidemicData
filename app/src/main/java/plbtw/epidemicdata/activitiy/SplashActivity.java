package plbtw.epidemicdata.activitiy;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;


import plbtw.epidemicdata.R;


/**
 * Created by 高橋六羽 on 2016/03/10.
 */
public class SplashActivity extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 5000;

    private SharedPreferences sharedPreferences;
    private final String name = "loginUser";
    private static final int mode = Activity.MODE_PRIVATE;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        sharedPreferences = getSharedPreferences(name, mode);

        new Handler().postDelayed(new Runnable() {

            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */

            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity
                finish();
                if(!sharedPreferences.getString("id_user","").equalsIgnoreCase(""))
                {
                    Intent i = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(i);
                }
                else {
                    Intent i = new Intent(SplashActivity.this, LoginActivity.class);
                    startActivity(i);
                }
            }
        }, SPLASH_TIME_OUT);
    }

}
