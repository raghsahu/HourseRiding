package com.riding.hourseriding.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.riding.hourseriding.MainActivity;
import com.riding.hourseriding.R;

public class Splash_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

              //  if (session.isLoggedIn()) {

                    Intent mainIntent = new Intent(Splash_Activity.this, MainActivity.class);
                    startActivity(mainIntent);
                    finish();

             //   } else {
             //       Intent mainIntent = new Intent(Splash_Activity.this, Login_Activity.class);
             //       startActivity(mainIntent);
             //       finish();

              //  }


            }
        }, 3000);
    }
}
