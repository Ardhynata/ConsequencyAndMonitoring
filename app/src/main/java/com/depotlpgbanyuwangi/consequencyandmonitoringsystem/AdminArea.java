package com.depotlpgbanyuwangi.consequencyandmonitoringsystem;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
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

public class AdminArea extends Activity {

    private Handler mHandler = new Handler();
    private String refresher = "http://depotlpgbanyuwangi.com/refresher.php";
    Button notifNumber, notifNumberHistory, notifNumberBlock;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); //enable full screen
        setContentView(R.layout.admin_area);

        mHandler.postDelayed(refresh, 3500);
        notifNumber = (Button)findViewById(R.id.notifNumber);
        notifNumberHistory = (Button)findViewById(R.id.notifNumberHistory);
        notifNumberBlock = (Button)findViewById(R.id.notifNumberBlock);

    }

    public void tap (View v)
    {
        if(v.getId()== R.id.cardView2){
            Intent i = new Intent(AdminArea.this, Tambah.class);
            mHandler.removeCallbacksAndMessages(null);
            startActivity(i);
        }
    }

    public void tab (View v)
    {
        if(v.getId()== R.id.cardView4){
            Intent i = new Intent(AdminArea.this, GantiPassword.class);
            mHandler.removeCallbacksAndMessages(null);
            startActivity(i);
        }
    }

    public void go (View v)
    {
        if(v.getId()== R.id.cardView){
            Intent i = new Intent(AdminArea.this, Notifikasi.class);
            mHandler.removeCallbacksAndMessages(null);
            startActivity(i);
        }
    }

    public void history (View v)
    {
        if(v.getId()== R.id.cardView3){
            Intent i = new Intent(AdminArea.this, History.class);
            mHandler.removeCallbacksAndMessages(null);
            startActivity(i);
        }
    }

    public void unblock (View v){

        if(v.getId()== R.id.cardView5){
            Intent i = new Intent(AdminArea.this, NotifikasiIzinUnblock.class);
            mHandler.removeCallbacksAndMessages(null);
            startActivity(i);
        }

    }

    public void logout (View v){

        if(v.getId()== R.id.btnLogOut){
            Intent i = new Intent(AdminArea.this, MainActivity.class);
            mHandler.removeCallbacksAndMessages(null);
            startActivity(i);
        }

    }

    public void onBackPressed() {

    }

    private Runnable refresh = new Runnable() {
        @Override
        public void run() {

            //Put Code to refresh data here
            StringRequest stringRequest = new StringRequest(Request.Method.POST, refresher, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {

                        JSONArray jsonArray = new JSONArray(response);
                        JSONObject jsonObject = jsonArray.getJSONObject(0);
                        String code = jsonObject.getString("code");

                        if(code.equals("fetch_error")){
                            Toast.makeText(AdminArea.this,jsonObject.getString("message"),Toast.LENGTH_SHORT).show();
                        }else{
                            int integernum = jsonObject.getInt("number");
                            if(integernum > 99){
                                notifNumber.setText("99+");
                                notifNumber.setVisibility(View.VISIBLE);
                            } else if (integernum == 0){
                                notifNumber.setVisibility(View.INVISIBLE);
                            } else {
                                notifNumber.setText(Integer.toString(integernum));
                                notifNumber.setVisibility(View.VISIBLE);
                            }

                            int integernumhistory = jsonObject.getInt("numberhistory");
                            if(integernumhistory > 99){
                                notifNumberHistory.setText("99+");
                                notifNumberHistory.setVisibility(View.VISIBLE);
                            } else if (integernumhistory == 0){
                                notifNumberHistory.setVisibility(View.INVISIBLE);
                            } else {
                                notifNumberHistory.setText(Integer.toString(integernumhistory));
                                notifNumberHistory.setVisibility(View.VISIBLE);
                            }

                            int integernumblock = jsonObject.getInt("numberblock");
                            if(integernumblock > 99){
                                notifNumberBlock.setText("99+");
                                notifNumberBlock.setVisibility(View.VISIBLE);
                            } else if (integernumblock == 0){
                                notifNumberBlock.setVisibility(View.INVISIBLE);
                            } else {
                                notifNumberBlock.setText(Integer.toString(integernumblock));
                                notifNumberBlock.setVisibility(View.VISIBLE);
                            }

                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(AdminArea.this,"Device Disconnected. Check Your Internet Connection",Toast.LENGTH_SHORT).show();
                }
            }){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String,String> params = new HashMap<String, String>();
                    params.put("refresher","19941217love19940222");

                    return params;
                }
            };
            stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                    30000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            MySingleton.getmInstance(AdminArea.this).addToRequestQueue(stringRequest);

            mHandler.postDelayed(this, 3500);
        }
    };

}
