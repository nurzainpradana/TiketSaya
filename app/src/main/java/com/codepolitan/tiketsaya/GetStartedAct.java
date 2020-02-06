package com.codepolitan.tiketsaya;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.ActionMode;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class GetStartedAct extends AppCompatActivity {

    Animation btt, ttb;
    Button btn_sign_in, btn_new_account_create;
    ImageView emblem;
    TextView intro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_started);

        //laod animation
        btt = AnimationUtils.loadAnimation(this, R.anim.btt);
        ttb = AnimationUtils.loadAnimation(this, R.anim.ttb);

        emblem = findViewById(R.id.emblem);
        intro = findViewById(R.id.intro);
        btn_sign_in = findViewById(R.id.button_sign_in);
        btn_new_account_create = findViewById(R.id.btn_new_account_create);

        //run animation
        emblem.startAnimation(ttb);
        intro.startAnimation(ttb);

        btn_new_account_create.startAnimation(btt);
        btn_sign_in.startAnimation(btt);

        btn_sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gotosign = new Intent(GetStartedAct.this, SignInAct.class);
                startActivity(gotosign);
            }
        });


        btn_new_account_create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gotoregisterone = new Intent(GetStartedAct.this, RegisterOneAct.class);
                startActivity(gotoregisterone);
            }
        });
    }
}
