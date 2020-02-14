package com.codepolitan.tiketsaya;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MyTicketDetailAct extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_ticket_detail);

        //Mengambil data dari intent
        Bundle bundle = getIntent().getExtras();
        final String jenis_tiket_baru = bundle.getString("jenis_tiket");
        
    }
}
