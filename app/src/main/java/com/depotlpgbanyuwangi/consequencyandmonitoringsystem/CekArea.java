package com.depotlpgbanyuwangi.consequencyandmonitoringsystem;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
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

public class CekArea extends Activity {

    String skid_cek_status, driver_cek_status;
    Button BtSkid, BtDriver, BtDone;

    String ID_pengecekan;
    String checkdone_url = "http://depotlpgbanyuwangi.com/checkdone.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); //enable full screen
        setContentView(R.layout.cek_area);

        ID_pengecekan = SharedPrefs.getDefaults("ID_pengecekan",CekArea.this);

        BtSkid = (Button)findViewById(R.id.btSkid);
        BtDriver = (Button)findViewById(R.id.btDriver);
        BtDone = (Button)findViewById(R.id.btCheckDone);

        skid_cek_status = SharedPrefs.getDefaults("skid_cek",CekArea.this);
        driver_cek_status = SharedPrefs.getDefaults("driver_cek",CekArea.this);

        if(driver_cek_status.equals("done")&&skid_cek_status.equals("done")) {
            BtDone.setEnabled(true);
            BtDone.setVisibility(View.VISIBLE);
        }

        if(skid_cek_status.equals("done")){
            BtSkid.setBackgroundResource(R.drawable.roundedbtngrey);
            BtSkid.setEnabled(false);
            BtSkid.setText("Selesai");
        }

        if(driver_cek_status.equals("done")){
            BtDriver.setBackgroundResource(R.drawable.roundedbtngrey);
            BtDriver.setEnabled(false);
            BtDriver.setText("Selesai");
        }

        BtSkid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CekArea.this,CekTruck.class);
                startActivity(intent);
            }
        });

        BtDriver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CekArea.this,CekDriver.class);
                startActivity(intent);
            }
        });

        BtDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringRequest stringRequest = new StringRequest(Request.Method.POST, checkdone_url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            JSONObject jsonObject = jsonArray.getJSONObject(0);
                            String code = jsonObject.getString("code");

                            if(code.equals("done_failed")){
                                Toast.makeText(CekArea.this, "Connection disrupted. Try again",Toast.LENGTH_SHORT).show();
                            }else {

                                Toast.makeText(CekArea.this,jsonObject.getString("message"),Toast.LENGTH_SHORT).show();
                                SharedPrefs.setDefaults("check_done","reset",CekArea.this);
                                ClearDataApplication.getInstance().clearApplicationData();
                                Intent intent = new Intent(CekArea.this,TankSearch.class);
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
                        Map<String,String> params = new HashMap<String,String>();
                        params.put("complete","1");
                        params.put("ID_pengecekan", ID_pengecekan);
                        return params;
                    }
                };
                stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                        30000,
                        DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                MySingleton.getmInstance(CekArea.this).addToRequestQueue(stringRequest);

            }
        });
    }
}
