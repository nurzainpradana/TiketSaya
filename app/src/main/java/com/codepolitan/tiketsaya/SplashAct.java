package com.codepolitan.tiketsaya;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import static com.codepolitan.tiketsaya.R.layout.activity_splash;

public class SplashAct extends AppCompatActivity {

    Animation app_splash, btt;
    ImageView app_logo;
    TextView app_tagline;

    String USERNAME_KEY = "usernamekey";
    String username_key = "";
    String username_key_new = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        getUsernameLocal();

        //load animation
        app_splash = AnimationUtils.loadAnimation(this, R.anim.app_splash);
        btt = AnimationUtils.loadAnimation(this, R.anim.btt);

        //load element
        app_logo = findViewById(R.id.app_logo);
        app_tagline = findViewById(R.id.app_tagline);

        //run animation
        app_logo.startAnimation(app_splash);
        app_tagline.startAnimation(btt);

        if(username_key_new.isEmpty()){
            //setting timer untuk 2 detik (2000 milidetik)
            //setelah menjalankan animasi splash screen
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    //merubah activity ke activity lain
                    Intent gogetstarted = new Intent(SplashAct.this, GetStartedAct.class);
                    startActivity(gogetstarted);
                    finish();
                }
            }, 2000); //1000 milis = 1 detik
        }else{
            //setting timer untuk 2 detik (2000 milidetik)
            //setelah menjalankan animasi splash screen
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    //merubah activity ke activity lain
                    Intent gogethome = new Intent(SplashAct.this, HomeAct.class);
                    startActivity(gogethome);
                    finish();
                }
            }, 2000); //1000 milis = 1 detik
        }

    }

    public void getUsernameLocal() {
        SharedPreferences sharedPreferences = getSharedPreferences(USERNAME_KEY, MODE_PRIVATE);
        username_key_new = sharedPreferences.getString(username_key, "");
    }

}
