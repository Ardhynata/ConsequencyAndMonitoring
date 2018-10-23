package com.depotlpgbanyuwangi.consequencyandmonitoringsystem;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.asus.consquencyandmonitoringsystem.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class DetailSkidTank extends Activity {

    TextView Nopol, NamaSPPBE, StatusIzin, WaktuMasuk, Blokir;
    EditText NamaSopir;
    Button BtnNext,BtnUnblock;
    String nama_sopir, ID_pengecekan;
    AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); //enable full screen
        setContentView(R.layout.detail_skid_tank);


        System.out.println("Masuk Pak Eko");
        final String nomor_polisi = SharedPrefs.getDefaults("nomor_polisi",DetailSkidTank.this);
        String nama_sppbe = SharedPrefs.getDefaults("nama_sppbe",DetailSkidTank.this);
        String status_izin = SharedPrefs.getDefaults("status_izin",DetailSkidTank.this);
        String tanggal_jam_pengecekan = SharedPrefs.getDefaults("time_view",DetailSkidTank.this);
        ID_pengecekan = SharedPrefs.getDefaults("ID_pengecekan",DetailSkidTank.this);

        final String sopir_url = "http://depotlpgbanyuwangi.com/adddriver.php";
        final String izin_unblock_url = "http://depotlpgbanyuwangi.com/izin_unblock.php";

        Nopol = (TextView)findViewById(R.id.txtNopol);
        NamaSPPBE = (TextView)findViewById(R.id.txtSPPBE);
        StatusIzin = (TextView)findViewById(R.id.txtStatus);
        WaktuMasuk = (TextView)findViewById(R.id.txtDeadline);
        Blokir = (TextView)findViewById(R.id.txtBlokir);
        NamaSopir = (EditText)findViewById(R.id.etnamaSopir);

        if (status_izin.equals("DITOLAK")){
            NamaSopir.setEnabled(false);
        }else{
            NamaSopir.setEnabled(true);
        }

        BtnNext = (Button)findViewById(R.id.btnTruck);
        BtnUnblock = (Button)findViewById(R.id.btnUnblock);
        builder = new AlertDialog.Builder(DetailSkidTank.this);

        Nopol.setText(nomor_polisi);
        NamaSPPBE.setText(nama_sppbe);
        StatusIzin.setText(status_izin);
        WaktuMasuk.setText(tanggal_jam_pengecekan);

        if(status_izin.equals("DITOLAK")){

            BtnNext.setVisibility(View.GONE);
            BtnNext.setEnabled(false);
            BtnUnblock.setVisibility(View.VISIBLE);
            BtnUnblock.setEnabled(true);
            Blokir.setVisibility(View.VISIBLE);

            BtnUnblock.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    StringRequest stringRequest = new StringRequest(Request.Method.POST, izin_unblock_url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            try {
                                JSONArray jsonArray = new JSONArray(response);
                                JSONObject jsonObject = jsonArray.getJSONObject(0);
                                String code = jsonObject.getString("code");

                                if(code.equals("notifikasi_success")){

                                    Toast.makeText(DetailSkidTank.this,jsonObject.getString("message"),Toast.LENGTH_LONG).show();

                                }else{

                                    Toast.makeText(DetailSkidTank.this,jsonObject.getString("message"),Toast.LENGTH_LONG).show();

                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }


                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                            Toast.makeText(DetailSkidTank.this,"Connection to Server Lost",Toast.LENGTH_LONG).show();

                        }
                    }){
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String,String> params = new HashMap<String, String>();
                            params.put("izin_unblock","NataLovesAsma");
                            params.put("nomor_polisi", nomor_polisi);
                            params.put("unblock_requester", SharedPrefs.getDefaults("user_id",DetailSkidTank.this));
                            return params;
                        }
                    };

                    MySingleton.getmInstance(DetailSkidTank.this).addToRequestQueue(stringRequest);

                    Intent intent = new Intent(DetailSkidTank.this, CheckerArea.class);
                    startActivity(intent);

                }
            });

        }else{

            BtnUnblock.setVisibility(View.GONE);
            BtnUnblock.setEnabled(false);
            BtnNext.setVisibility(View.VISIBLE);
            BtnNext.setEnabled(true);
            Blokir.setVisibility(View.INVISIBLE);


            BtnNext.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    nama_sopir = NamaSopir.getText().toString();

                    if(nama_sopir.equals("")){
                        builder.setTitle("Something went wrong");
                        displayAlert("Masukkan nama sopir.");
                    } else {

                        StringRequest stringRequest = new StringRequest(Request.Method.POST, sopir_url, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    JSONArray jsonArray = new JSONArray(response);
                                    JSONObject jsonObject = jsonArray.getJSONObject(0);
                                    String code = jsonObject.getString("code");

                                    if(code.equals("update_failed")){
                                        builder.setTitle("Error...");
                                        displayAlert(jsonObject.getString("message"));
                                    } else {

                                        SharedPrefs.setDefaults("skid_cek","default",DetailSkidTank.this);
                                        SharedPrefs.setDefaults("driver_cek","default",DetailSkidTank.this);

                                        Toast.makeText(DetailSkidTank.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(DetailSkidTank.this, CekArea.class);
                                        startActivity(intent);
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                            }
                        }){
                            @Override
                            protected Map<String, String> getParams() throws AuthFailureError {
                                Map<String,String> params = new HashMap<String, String>();
                                params.put("ID_pengecekan",ID_pengecekan);
                                params.put("nama_sopir",nama_sopir);
                                return params;
                            }
                        };
                        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                                30000,
                                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                        MySingleton.getmInstance(DetailSkidTank.this).addToRequestQueue(stringRequest);
                    }

                }
            });

        }
    }

    public void displayAlert(String message){

        builder.setMessage(message);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                NamaSopir.setText("");
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();

    }

}

