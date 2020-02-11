package com.codepolitan.tiketsaya;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class MyProfileAct extends AppCompatActivity {
    LinearLayout item_my_ticket;
    Button btn_edit_profile;
    Button btn_sign_out;

    TextView nama_lengkap, bio;
    ImageView photo_profile;

    DatabaseReference reference;

    String USERNAME_KEY = "usernamekey";
    String username_key = "";
    String username_key_new = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);

        getUsernameLocal();

        item_my_ticket = findViewById(R.id.item_my_ticket);
        btn_edit_profile = findViewById(R.id.btn_edit_profile);

        nama_lengkap = findViewById(R.id.nama_lengkap);
        bio = findViewById(R.id.bio);
        photo_profile = findViewById(R.id.photo_profile);

        reference = FirebaseDatabase.getInstance().getReference().child("Users").child(username_key_new);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                nama_lengkap.setText(dataSnapshot.child("nama_lengkap").getValue().toString());
                bio.setText(dataSnapshot.child("bio").getValue().toString());

                Picasso.with(MyProfileAct.this)
                        .load(dataSnapshot.child("url_photo_profile")
                        .getValue().toString()).centerCrop().fit()
                        .into(photo_profile);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        item_my_ticket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gotomytickets = new Intent(MyProfileAct.this, MyTicketDetailAct.class);
                startActivity(gotomytickets);
            }
        });

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

    public void getUsernameLocal(){
        SharedPreferences sharedPreferences = getSharedPreferences(USERNAME_KEY, MODE_PRIVATE);
        username_key_new = sharedPreferences.getString(username_key,"");
    }
}
