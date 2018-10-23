package com.depotlpgbanyuwangi.consequencyandmonitoringsystem;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.WindowManager;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.asus.consquencyandmonitoringsystem.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class NotifikasiUnblocked extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;
    String [] nomor_polisi, nama_sppbe, status_izin, deadline, unblock_requester;
    ArrayList<DataProviderUnblocked> arrayList = new ArrayList<DataProviderUnblocked>();
    private Handler mHandler = new Handler();
    String notifikasi_url = "http://depotlpgbanyuwangi.com/notifikasi_unblocked.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); //enable full screen
        setContentView(R.layout.notifikasi_unblocked);
        mHandler.postDelayed(refresh,250);

    }

    public void onBackPressed(){
        Intent intent = new Intent(NotifikasiUnblocked.this, CheckerArea.class);
        startActivity(intent);
    }

    private Runnable refresh = new Runnable() {
        @Override
        public void run() {
            StringRequest stringRequest = new StringRequest(Request.Method.POST, notifikasi_url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        JSONObject jsonObject = jsonArray.getJSONObject(0);
                        String code = jsonObject.getString("code");
                        int jumlah = jsonArray.length()-1;

                        if(code.equals("notifikasi_failed")){
                            Toast.makeText(NotifikasiUnblocked.this,jsonObject.getString("message"),Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(NotifikasiUnblocked.this,jsonObject.getString("message"),Toast.LENGTH_SHORT).show();

                            nomor_polisi = new String[jumlah];
                            nama_sppbe = new String[jumlah];
                            status_izin = new String[jumlah];
                            deadline = new String[jumlah];
                            unblock_requester = new String[jumlah];

                            int i;
                            String nomorpolisi, statusizin, tenggat, unblockrequester, namasppbe;

                            for(i=0;i<jumlah;i++){

                                jsonObject = jsonArray.getJSONObject(i+1);
                                nomorpolisi = jsonObject.getString("nomor_polisi");
                                namasppbe = jsonObject.getString("nama_sppbe");
                                statusizin = jsonObject.getString("status_izin");
                                tenggat = jsonObject.getString("deadline");
                                unblockrequester = jsonObject.getString("unblock_requester");

                                nomor_polisi[jumlah-i-1] = nomorpolisi;
                                nama_sppbe[jumlah-i-1] = namasppbe;
                                status_izin[jumlah-i-1] = statusizin;
                                deadline[jumlah-i-1] = tenggat;
                                unblock_requester[jumlah-i-1] = unblockrequester;

                            }
                            recyclerView = (RecyclerView) findViewById(R.id.recycler);
                            i = 0;
                            for(String name : nomor_polisi){

                                //System.out.println(name+" "+nama_sppbe[i]+" "+status_izin[i]+" "+deadline[i]+" "+unblock_requester[i]);
                                DataProviderUnblocked dataProviderUnblocked = new DataProviderUnblocked(name,nama_sppbe[i],status_izin[i],deadline[i],unblock_requester[i]);
                                arrayList.add(dataProviderUnblocked);
                                i++;

                            }

                            adapter = new RecyclerAdapterUnblocked(arrayList,NotifikasiUnblocked.this);
                            recyclerView.setHasFixedSize(true);
                            //System.out.println("Masuk Pak Eko");
                            layoutManager = new LinearLayoutManager(NotifikasiUnblocked.this);
                            recyclerView.setLayoutManager(layoutManager);
                            recyclerView.setAdapter(adapter);

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
                    params.put("notifikasi","NataLovesAsma");
                    return params;
                }
            };

            MySingleton.getmInstance(NotifikasiUnblocked.this).addToRequestQueue(stringRequest);

        }
    };

}
