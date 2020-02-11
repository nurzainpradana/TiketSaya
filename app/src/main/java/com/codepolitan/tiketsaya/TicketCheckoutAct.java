package com.codepolitan.tiketsaya;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.renderscript.Sampler;
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

import java.util.Random;

public class TicketCheckoutAct extends AppCompatActivity {
    Button btn_pay_now, btn_mines, btn_plus;
    TextView textjumlahtiket, texttotalharga, textmybalance, namawisata, lokasi, ketentuan ;
    Integer value_jumlahtiket = 1;
    Integer mybalance = 0;
    Integer value_totalharga = 0;
    Integer value_hargatiket = 0;
    ImageView notice_uang;
    Integer sisa_balance = 0; // untuk sisa balance setelah checkout

    String USERNAME_KEY = "usernamekey";
    String username_key = "";
    //membuat string baru
    String username_key_new = "";

    String date_wisata = "";
    String time_wisata="";


    //membuat angka random
    Integer nomor_transaksi = new Random().nextInt();

    LinearLayout btn_back;

    DatabaseReference reference, reference2, reference3, reference4;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_checkout);

        getUsernameLocal();

        //mengambil data dari intent
        Bundle bundle = getIntent().getExtras();
        final String jenis_tiket_baru = bundle.getString("jenis_tiket");

        btn_pay_now = findViewById(R.id.btn_pay_now);
        btn_back = findViewById(R.id.btn_back);
        btn_plus = findViewById(R.id.btn_plus);
        btn_mines = findViewById(R.id.btn_mines);
        textjumlahtiket = findViewById(R.id.textjumlahtiket);
        notice_uang = findViewById(R.id.notice_uang);

        namawisata = findViewById(R.id.nama_wisata);
        lokasi = findViewById(R.id.lokasi);
        ketentuan = findViewById(R.id.ketentuan);

        textmybalance = findViewById(R.id.textmybalance);
        texttotalharga = findViewById(R.id.texttotalharga);

        //set value baru untuk beberapa komponen
        textjumlahtiket.setText(value_jumlahtiket.toString());

        notice_uang.setVisibility(View.GONE);

        //secara default btn minus, di hide
        if(value_jumlahtiket<2){
            btn_mines.animate().alpha(0).setDuration(300).start();
            btn_mines.setEnabled(false);
        }

        //mengambil data user dari firebase
        reference2 = FirebaseDatabase.getInstance().getReference().child("Users").child(username_key_new);
        reference2.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mybalance = Integer.valueOf(dataSnapshot.child("user_balance").getValue().toString());
                textmybalance.setText("US$"+mybalance.toString()+"");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
                //LAST EDIT

        reference = FirebaseDatabase.getInstance().getReference().child("Wisata").child(jenis_tiket_baru);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //menimpa data yang ada dengan yang baru
                namawisata.setText(dataSnapshot.child("nama_wisata").getValue().toString());
                lokasi.setText(dataSnapshot.child("lokasi").getValue().toString());
                ketentuan.setText(dataSnapshot.child("ketentuan").getValue().toString());
                value_hargatiket = Integer.valueOf(dataSnapshot.child("harga_tiket").getValue().toString());

                date_wisata = dataSnapshot.child("date_wisata").getValue().toString();
                time_wisata = dataSnapshot.child("time_wisata").getValue().toString();

                value_totalharga = value_hargatiket * value_jumlahtiket;
                texttotalharga.setText("US$"+value_totalharga.toString()+"");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



        btn_mines.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                value_jumlahtiket-=1;
                textjumlahtiket.setText(value_jumlahtiket.toString());
                if(value_jumlahtiket<2){
                    btn_mines.animate().alpha(0).setDuration(300).start();
                    btn_mines.setEnabled(false);
                }
                value_totalharga = value_hargatiket * value_jumlahtiket;
                texttotalharga.setText("US$"+value_totalharga.toString()+"");

                if(value_totalharga<mybalance){
                    btn_pay_now.animate().translationY(0).alpha(1).setDuration(350).start();
                    btn_pay_now.setEnabled(true);
                    textmybalance.setTextColor(Color.parseColor("#203DD1"));
                    notice_uang.setVisibility(View.GONE);
                }

            }
        });
        btn_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                value_jumlahtiket+=1;
                textjumlahtiket.setText(value_jumlahtiket.toString());
                if(value_jumlahtiket>1){
                    btn_mines.animate().alpha(1).setDuration(300).start();
                    btn_mines.setEnabled(true);
                }
                value_totalharga = value_hargatiket * value_jumlahtiket;
                texttotalharga.setText("US$"+value_totalharga.toString()+"");
                if(value_totalharga>mybalance){
                    btn_pay_now.animate().translationY(250).alpha(0).setDuration(350).start();
                    btn_pay_now.setEnabled(false);
                    textmybalance.setTextColor(Color.parseColor("#D1206B"));
                    notice_uang.setVisibility(View.VISIBLE);
                }

            }
        });


        btn_pay_now.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                //menyimpan data user ke firebase dan membuat tabel baru "MyTickets"

                //generate nomor integer secara random
                //karena kira ingin membuat transaksi secara unik
                reference3 = FirebaseDatabase.getInstance().getReference()
                        .child("MyTickets").child(username_key_new)
                        .child(namawisata.getText().toString() + nomor_transaksi);
                reference3.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        reference3.getRef().child("id_ticket").setValue(namawisata.getText().toString() + nomor_transaksi);
                        reference3.getRef().child("nama_wisata").setValue(namawisata.getText().toString());
                        reference3.getRef().child("lokasi").setValue(lokasi.getText().toString());
                        reference3.getRef().child("ketentuan").setValue(ketentuan.getText().toString());
                        reference3.getRef().child("jumlah_tiket").setValue(value_jumlahtiket);

                        reference3.getRef().child("date_wisata").setValue(date_wisata);
                        reference3.getRef().child("time_wisata").setValue(time_wisata);

                        Intent gotosuccessticket = new Intent(TicketCheckoutAct.this, SuccessBuyTicketAct.class);
                        startActivity(gotosuccessticket);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                });

                //update data balance kepada users (yang saat ini login)
                //mengambil data user dari firebase
                reference4= FirebaseDatabase.getInstance().getReference().child("Users").child(username_key_new);
                reference4.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        sisa_balance = mybalance - value_totalharga;
                        reference4.getRef().child("user_balance").setValue(sisa_balance);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });



            }
        });

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gototicketdetail = new Intent (TicketCheckoutAct.this, TicketDetailAct.class);
                startActivity(gototicketdetail);
            }
        });
    }

    //mendapatkan reference
    public void getUsernameLocal(){
        //hanya perlu mendapatkan username key
        SharedPreferences sharedPreferences = getSharedPreferences(USERNAME_KEY, MODE_PRIVATE);
        username_key_new = sharedPreferences.getString(username_key,"");
    }
}
