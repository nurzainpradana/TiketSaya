package com.codepolitan.tiketsaya;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class SuccessRegisterAct extends AppCompatActivity {
    Button btn_explore;
    Animation app_splash, btt, ttb;
    ImageView icon_success;
    TextView app_tittle, app_subtittle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success_register);

        icon_success = findViewById(R.id.icon_success);
        app_tittle = findViewById(R.id.app_tittle);
        app_subtittle = findViewById(R.id.app_subtitle);
        btn_explore = findViewById(R.id.btn_explore);

        //load animation
        app_splash = AnimationUtils.loadAnimation(this, R.anim.app_splash);
        btt = AnimationUtils.loadAnimation(this, R.anim.btt);
        ttb = AnimationUtils.loadAnimation(this, R.anim.ttb);

        //run animation
        btn_explore.setAnimation(btt);
        icon_success.setAnimation(app_splash);
        app_tittle.setAnimation(ttb);
        app_subtittle.setAnimation(ttb);

        btn_explore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent gotohome = new Intent(SuccessRegisterAct.this, HomeAct.class);
                startActivity(gotohome);
            }
        });
    }

}
