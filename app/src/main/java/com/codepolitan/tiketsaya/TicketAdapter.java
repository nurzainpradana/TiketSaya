package com.codepolitan.tiketsaya;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

//untuk melisting data dari adapter
public class TicketAdapter extends RecyclerView.Adapter<TicketAdapter.MyViewHolder> {

    Context context;
    ArrayList<MyTicket> myTicket;

    public TicketAdapter(Context c, ArrayList<MyTicket> p){
        context = c;
        myTicket = p;

        //menginflate data

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_myticket, viewGroup, false ));
        //menginflasi / mereplace list yang sudah ada
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        TextView xnama_wisata, xlokasi, xjumlah_tiket;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            xnama_wisata = itemView.findViewById(R.id.xnama_wisata);
            xlokasi = itemView.findViewById(R.id.xlokasi);
            xjumlah_tiket = itemView.findViewById(R.id.xjumlah_tiket);
        }
    }

}
