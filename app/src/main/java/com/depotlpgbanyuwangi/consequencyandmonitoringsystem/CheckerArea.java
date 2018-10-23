package com.depotlpgbanyuwangi.consequencyandmonitoringsystem;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.example.asus.consquencyandmonitoringsystem.R;

public class CheckerArea extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); //enable full screen
        setContentView(R.layout.checker_area);
    }

    public void tanksearch (View v)
    {
        if(v.getId()== R.id.cardView2){
            Intent i = new Intent(CheckerArea.this, TankSearch.class);
            i.putExtra("user_id",SharedPrefs.getDefaults("user_id",CheckerArea.this));
            startActivity(i);
        }
    }

    public void notifikasichecker (View v)
    {
        if(v.getId()== R.id.cardView){
            Intent i = new Intent(CheckerArea.this, NotifikasiChecker.class);
            startActivity(i);
        }
    }

    public void historychecker (View v)
    {
        if(v.getId()== R.id.cardView3){
            Intent i = new Intent(CheckerArea.this, HistoryChecker.class);
            startActivity(i);
        }
    }

    public void unblocked (View v){

        if(v.getId()== R.id.cardView4){
            Intent i = new Intent(CheckerArea.this, NotifikasiUnblocked.class);
            startActivity(i);
        }

    }

    public void logout (View v){

        if(v.getId()==R.id.btnLogOut){
            Intent i = new Intent(CheckerArea.this, MainActivity.class);
            startActivity(i);
        }

    }

    public void onBackPressed() {

    }

}
