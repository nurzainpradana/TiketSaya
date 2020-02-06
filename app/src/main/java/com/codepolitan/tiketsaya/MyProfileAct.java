package com.codepolitan.tiketsaya;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

public class MyProfileAct extends AppCompatActivity {
    LinearLayout item_my_ticket;
    Button btn_edit_profile;
    Button btn_sign_out;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);

        item_my_ticket = findViewById(R.id.item_my_ticket);
        item_my_ticket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gotomytickets = new Intent(MyProfileAct.this, MyTicketDetailAct.class);
                startActivity(gotomytickets);
            }
        });

        btn_edit_profile = findViewById(R.id.btn_edit_profile);
        btn_edit_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gotoeditprofile = new Intent(MyProfileAct.this, EditProfileAct.class);
                startActivity(gotoeditprofile);
            }
        });

        btn_sign_out = findViewById(R.id.btn_sign_out);
        btn_sign_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent signout = new Intent(MyProfileAct.this, SignInAct.class);
                startActivity(signout);
            }
        });
    }
}
