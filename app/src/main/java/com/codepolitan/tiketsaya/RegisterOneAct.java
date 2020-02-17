package com.codepolitan.tiketsaya;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

public class RegisterOneAct extends AppCompatActivity {
    LinearLayout btn_back;
    Button btn_continue;
    EditText username, password, email_address;

    //untuk mendapatkan sebuah reference, untuk digunakan menyimpan sebuah data
    DatabaseReference reference, reference_username;



    //untuk menyimpan data lokal
    String USERNAME_KEY = "usernamekey";
    String username_key = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_one);

        btn_back=findViewById(R.id.btn_back);
        btn_continue = findViewById(R.id.btn_continue);

        username=findViewById(R.id.username);
        password=findViewById(R.id.password);
        email_address=findViewById(R.id.email_address);



        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });


        btn_continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //merubah state menjadi loading
                btn_continue.setEnabled(false);
                btn_continue.setText("Loading ...");

                //mengambil username pada firebase
                reference_username = FirebaseDatabase.getInstance().getReference().child("Users").child(username.getText().toString());
                reference_username.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.exists()){
                            Toast.makeText(getApplicationContext(), "Username Sudah Tersedia", Toast.LENGTH_SHORT).show();
                            //merubah state menjadi loading
                            btn_continue.setEnabled(true);
                            btn_continue.setText("CONTINUE");

                        }else{
                            //Menyimpan data kepada local storage (HP)
                            SharedPreferences sharedPreferences = getSharedPreferences(USERNAME_KEY, MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            Toast.makeText(RegisterOneAct.this, username.getText().toString(), Toast.LENGTH_SHORT).show();
                            editor.putString(username_key, username.getText().toString());
                            editor.apply();

                            Toast.makeText(RegisterOneAct.this, sharedPreferences.getString(username_key,""), Toast.LENGTH_SHORT).show();

                            //saat ini kita punya username
                            //tes apakah username udah masuk
                            //Toast.makeText(getApplicationContext(), "username " + username.getText().toString(), Toast.LENGTH_SHORT).show();

                            //simpan pada database
                            reference = FirebaseDatabase.getInstance().getReference().child("Users").child(username.getText().toString());
                            //jadi ini kita akan simpan ke pada firebase, dimana tempatnya users lalu menunju zain berdasarkan usernamenya
                            reference.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    //memasukkan data pada database
                                    //value didapatkan dari child
                                    dataSnapshot.getRef().child("username").setValue(username.getText().toString());
                                    dataSnapshot.getRef().child("password").setValue(password.getText().toString());
                                    dataSnapshot.getRef().child("email_address").setValue(email_address.getText().toString());
                                    dataSnapshot.getRef().child("user_balance").setValue(800);

                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });

                            //berpindah activity, letkkan di akhir
                            Intent gotonextregister = new Intent(RegisterOneAct.this, RegisterTwoAct.class);
                            startActivity(gotonextregister);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });



            }
        });
    }
}
