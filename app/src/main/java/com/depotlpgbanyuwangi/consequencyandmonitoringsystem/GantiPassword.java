package com.depotlpgbanyuwangi.consequencyandmonitoringsystem;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

import com.example.asus.consquencyandmonitoringsystem.R;

public class GantiPassword extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.ganti_password);
    }

    public void passChecker(View v){
        if(v.getId()== R.id.cardViewCek){
            Intent i = new Intent(GantiPassword.this, PassChecker.class);
            startActivity(i);
        }
    }

    public void passVerificator(View v){
        if(v.getId()== R.id.cardViewVr){
            Intent i = new Intent(GantiPassword.this, PassVerificator.class);
            startActivity(i);
        }
    }
}
