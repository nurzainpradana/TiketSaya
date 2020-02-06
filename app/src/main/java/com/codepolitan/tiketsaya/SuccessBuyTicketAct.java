package com.codepolitan.tiketsaya;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class SuccessBuyTicketAct extends AppCompatActivity {
    Animation app_splash, btt, ttb;
    Button btn_view_ticket, btn_my_dashboard;
    TextView app_tittle, app_subtittle;
    ImageView icon_success_ticket;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success_buy_ticket);

        //inisiasi
        btn_view_ticket = findViewById(R.id.btn_view_ticket);
        btn_my_dashboard = findViewById(R.id.btn_my_dashboard);
        app_tittle = findViewById(R.id.app_tittle);
        app_subtittle = findViewById(R.id.app_subtitle);
        icon_success_ticket = findViewById(R.id.icon_success_ticket);


        //load animation
        app_splash = AnimationUtils.loadAnimation(this, R.anim.app_splash);
        btt = AnimationUtils.loadAnimation(this, R.anim.btt);
        ttb = AnimationUtils.loadAnimation(this, R.anim.ttb);

        //run animation
        icon_success_ticket.startAnimation(app_splash);

        app_tittle.startAnimation(ttb);
        app_subtittle.startAnimation(ttb);

        btn_my_dashboard.startAnimation(btt);
        btn_view_ticket.startAnimation(btt);



    }
}
