package com.depotlpgbanyuwangi.consequencyandmonitoringsystem;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.asus.consquencyandmonitoringsystem.R;

public class Tambah extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tambah);
    }

    public void tambahChecker(View v){
        if(v.getId()== R.id.btChecker){
            Intent i = new Intent(Tambah.this, TambahChecker.class);
            startActivity(i);
        }
    }

    public void tambahTruck(View v){
        if(v.getId()== R.id.btTruck){
            Intent i = new Intent(Tambah.this, TambahTruck.class);
            startActivity(i);
        }
    }
}
