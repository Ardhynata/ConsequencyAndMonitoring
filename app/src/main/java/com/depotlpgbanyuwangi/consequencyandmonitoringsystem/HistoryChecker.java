package com.depotlpgbanyuwangi.consequencyandmonitoringsystem;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Window;
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

public class HistoryChecker extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;
    String [] checkername, waktucek,nopolcek,milikcek,IDcek;
    ArrayList<DataProviderHistoryChecker> arrayList = new ArrayList<DataProviderHistoryChecker>();
    private Handler mHandler = new Handler();

    String history_checker_url = "http://depotlpgbanyuwangi.com/historychecker.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); //enable full screen
        setContentView(R.layout.history_checker);

        mHandler.postDelayed(refresh, 250);
        //mHandler.postDelayed(refresh, 250);

    }

    public void onBackPressed() {
        Intent intent = new Intent(HistoryChecker.this, CheckerArea.class);
        startActivity(intent);
    }

    private Runnable refresh = new Runnable() {
        @Override
        public void run() {

            final String user_id  = SharedPrefs.getDefaults("user_id",HistoryChecker.this);

            StringRequest stringRequest = new StringRequest(Request.Method.POST, history_checker_url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        JSONObject jsonObject = jsonArray.getJSONObject(0);
                        String code = jsonObject.getString("code");
                        int jumlah = jsonArray.length()-1;

                        if(code.equals("notifikasi_failed")){
                            Toast.makeText(HistoryChecker.this,jsonObject.getString("message"),Toast.LENGTH_SHORT).show();
                        }else{
                            //Toast.makeText(Notifikasi.this,jsonObject.getString("message"),Toast.LENGTH_SHORT).show();

                            checkername = new String[jumlah];
                            IDcek = new String[jumlah];
                            waktucek = new String[jumlah];
                            nopolcek = new String[jumlah];
                            milikcek = new String[jumlah];

                            int i;
                            String checker, nopol, milik, waktu,id_pengecekan;

                            for(i=0;i<jumlah;i++){

                                jsonObject = jsonArray.getJSONObject(i+1);
                                checker = jsonObject.getString("username");
                                id_pengecekan = jsonObject.getString("ID_pengecekan");
                                nopol = jsonObject.getString("nomor_polisi");
                                milik = jsonObject.getString("nama_sppbe");
                                waktu = jsonObject.getString("tanggal_jam_pengecekan");

                                System.out.println(waktu);

                                checkername[jumlah-i-1] = checker;
                                IDcek[jumlah-i-1] = id_pengecekan;
                                waktucek[jumlah-i-1] = waktu;
                                nopolcek[jumlah-i-1] = nopol;
                                milikcek[jumlah-i-1] = milik;

                            }
                            recyclerView = (RecyclerView) findViewById(R.id.recycler);
                            i = 0;
                            for(String name : checkername){

                                //System.out.println(name+" "+IDcek[i]+" "+waktucek[i]+" "+nopolcek[i]+" "+milikcek[i]);
                                DataProviderHistoryChecker dataProviderHistoryChecker = new DataProviderHistoryChecker(name,IDcek[i],waktucek[i],nopolcek[i],milikcek[i]);
                                arrayList.add(dataProviderHistoryChecker);
                                i++;

                            }

                            adapter = new RecyclerAdapterHistoryChecker(arrayList,HistoryChecker.this);
                            recyclerView.setHasFixedSize(true);
                            layoutManager = new LinearLayoutManager(HistoryChecker.this);
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
                    params.put("userID",user_id);
                    return params;
                }
            };

            MySingleton.getmInstance(HistoryChecker.this).addToRequestQueue(stringRequest);

            //mHandler.postDelayed(this, 1000);

        }
    };
}
