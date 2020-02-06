package com.codepolitan.tiketsaya;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class TicketCheckoutAct extends AppCompatActivity {
    Button btn_pay_now, btn_mines, btn_plus;
    TextView text_jumlahtiket, text_totalharga, text_mybalance;
    Integer value_jumlahtiket = 1;
    Integer mybalance = 200;
    Integer value_totalharga = 0;
    Integer value_hargatiket = 75;
    ImageView notice_uang;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_checkout);

        btn_pay_now = findViewById(R.id.btn_pay_now);
        btn_plus = findViewById(R.id.btn_plus);
        btn_mines = findViewById(R.id.btn_mines);
        text_jumlahtiket = findViewById(R.id.jumlah_tiket);
        notice_uang = findViewById(R.id.notice_uang);

        text_mybalance = findViewById(R.id.text_mybalance);
        text_totalharga = findViewById(R.id.text_totalharga);

        //set value baru untuk beberapa komponen
        text_jumlahtiket.setText(value_jumlahtiket.toString());
        text_mybalance.setText("US$"+mybalance.toString()+"");

        value_totalharga = value_hargatiket * value_jumlahtiket;
        text_totalharga.setText("US$"+value_totalharga.toString()+"");

        notice_uang.setVisibility(View.GONE);



        //secara default btn minus, di hide
        if(value_jumlahtiket<2){
            btn_mines.animate().alpha(0).setDuration(300).start();
            btn_mines.setEnabled(false);
        }

        btn_mines.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                value_jumlahtiket-=1;
                text_jumlahtiket.setText(value_jumlahtiket.toString());
                if(value_jumlahtiket<2){
                    btn_mines.animate().alpha(0).setDuration(300).start();
                    btn_mines.setEnabled(false);
                }
                value_totalharga = value_hargatiket * value_jumlahtiket;
                text_totalharga.setText("US$"+value_totalharga.toString()+"");

                if(value_totalharga<mybalance){
                    btn_pay_now.animate().translationY(0).alpha(1).setDuration(350).start();
                    btn_pay_now.setEnabled(true);
                    text_mybalance.setTextColor(Color.parseColor("#203DD1"));
                    notice_uang.setVisibility(View.GONE);
                }

            }
        });
        btn_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                value_jumlahtiket+=1;
                text_jumlahtiket.setText(value_jumlahtiket.toString());
                if(value_jumlahtiket>1){
                    btn_mines.animate().alpha(1).setDuration(300).start();
                    btn_mines.setEnabled(true);
                }
                value_totalharga = value_hargatiket * value_jumlahtiket;
                text_totalharga.setText("US$"+value_totalharga.toString()+"");
                if(value_totalharga>mybalance){
                    btn_pay_now.animate().translationY(250).alpha(0).setDuration(350).start();
                    btn_pay_now.setEnabled(false);
                    text_mybalance.setTextColor(Color.parseColor("#D1206B"));
                    notice_uang.setVisibility(View.VISIBLE);
                }

            }
        });


        btn_pay_now.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gotosuccessticket = new Intent(TicketCheckoutAct.this, SuccessBuyTicketAct.class);
                startActivity(gotosuccessticket);
            }
        });
    }
}
