package com.codepolitan.tiketsaya;

import android.content.Intent;

public class MyTicket {

    String nama_wisata, lokasi;
    Integer jumlah_tiket;

    public MyTicket(String nama_wisata, String lokasi, Integer jumlah_tiket) {
        this.nama_wisata = nama_wisata;
        this.lokasi = lokasi;
        this.jumlah_tiket = jumlah_tiket;
    }

    public String getNama_wisata() {
        return nama_wisata;
    }

    public void setNama_wisata(String nama_wisata) {
        this.nama_wisata = nama_wisata;
    }

    public String getLokasi() {
        return lokasi;
    }

    public void setLokasi(String lokasi) {
        this.lokasi = lokasi;
    }

    public Integer getJumlah_tiket() {
        return jumlah_tiket;
    }

    public void setJumlah_tiket(Integer jumlah_tiket) {
        this.jumlah_tiket = jumlah_tiket;
    }
}
