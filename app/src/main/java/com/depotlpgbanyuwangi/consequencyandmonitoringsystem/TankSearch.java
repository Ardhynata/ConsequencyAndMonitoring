package com.depotlpgbanyuwangi.consequencyandmonitoringsystem;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
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

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class TankSearch extends Activity {

    AlertDialog.Builder builder;
    EditText et;
    Button button;
    String etPlat, txtwelcome;
    TextView TxtWelcome;
    private Handler mHandler = new Handler();

    Date tanggal_jam_pengecekan;
    SimpleDateFormat databaseformat,filenameformat,viewformat;
    String timedata,timeview,imgname;

    String searchtruck_url = "http://depotlpgbanyuwangi.com/searchtruck.php";

    String redirect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); //enable full screen
        setContentView(R.layout.tank_search);

        resetSharedPrefs();

        String ID_Login = SharedPrefs.getDefaults("id_login",TankSearch.this);
        final String UserID = SharedPrefs.getDefaults("user_id",TankSearch.this);

        tanggal_jam_pengecekan = new Date();
        databaseformat  = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        filenameformat  = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss-SSS");
        viewformat      = new SimpleDateFormat("dd MMMM yyyy hh:mm aaa");

        timedata = databaseformat.format(tanggal_jam_pengecekan);
        imgname = filenameformat.format(tanggal_jam_pengecekan);
        timeview = viewformat.format(tanggal_jam_pengecekan);

        SharedPrefs.setDefaults("time_view",timeview,TankSearch.this);
        SharedPrefs.setDefaults("time_data",timedata,TankSearch.this);
        SharedPrefs.setDefaults("img_name",imgname,TankSearch.this);

        TxtWelcome = (TextView)findViewById(R.id.txtWelcome);
        TxtWelcome.setText(getText(R.string.welcome_checker) + ID_Login);

        builder = new AlertDialog.Builder(TankSearch.this);
        et = (EditText)findViewById(R.id.etPlat);
        button = (Button)findViewById(R.id.button2);

        redirect = SharedPrefs.getDefaults("block_redirect",TankSearch.this);

        System.out.println(redirect);

        if(redirect.equals("true")){

            etPlat = SharedPrefs.getDefaults("nomor_polisi",TankSearch.this);

            StringRequest stringRequest = new StringRequest(Request.Method.POST, searchtruck_url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        JSONObject jsonObject = jsonArray.getJSONObject(0);
                        String code = jsonObject.getString("code");

                        if(code.equals("search_failed")){
                            builder.setTitle("Error...");
                            displayAlert(jsonObject.getString("message"));
                        } else {
                            String ID_pengecekan = jsonObject.getString("ID_pengecekan");
                            String nomor_polisi = jsonObject.getString("nomor_polisi");
                            String nama_sppbe = jsonObject.getString("nama_sppbe");
                            String status_izin = jsonObject.getString("status_izin");

                            SharedPrefs.setDefaults("ID_pengecekan",ID_pengecekan,TankSearch.this);
                            SharedPrefs.setDefaults("nomor_polisi",nomor_polisi,TankSearch.this);
                            SharedPrefs.setDefaults("nama_sppbe",nama_sppbe,TankSearch.this);
                            SharedPrefs.setDefaults("status_izin",status_izin,TankSearch.this);

                            Intent intent = new Intent(TankSearch.this,DetailSkidTank.class);
                            startActivity(intent);
                            et.setText("");

                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(TankSearch.this,"Connection to Server Lost",Toast.LENGTH_SHORT).show();
                    error.printStackTrace();

                }
            }){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String,String> params = new HashMap<String,String>();
                    params.put("userID",UserID);
                    params.put("nomor_polisi",etPlat);
                    params.put("tanggal_jam_pengecekan",timedata);
                    return params;
                }
            };
            stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                    30000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            MySingleton.getmInstance(TankSearch.this).addToRequestQueue(stringRequest);

        }else{

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    etPlat = et.getText().toString();

                    if(etPlat.equals("")){
                        builder.setTitle("Something went wrong");
                        displayAlert("Masukkan nomor polisi yang benar");
                    } else {
                        StringRequest stringRequest = new StringRequest(Request.Method.POST, searchtruck_url, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                                try {
                                    JSONArray jsonArray = new JSONArray(response);
                                    JSONObject jsonObject = jsonArray.getJSONObject(0);
                                    String code = jsonObject.getString("code");

                                    if(code.equals("search_failed")){
                                        builder.setTitle("Error...");
                                        displayAlert(jsonObject.getString("message"));
                                    } else {
                                        String ID_pengecekan = jsonObject.getString("ID_pengecekan");
                                        String nomor_polisi = jsonObject.getString("nomor_polisi");
                                        String nama_sppbe = jsonObject.getString("nama_sppbe");
                                        String status_izin = jsonObject.getString("status_izin");

                                        SharedPrefs.setDefaults("ID_pengecekan",ID_pengecekan,TankSearch.this);
                                        SharedPrefs.setDefaults("nomor_polisi",nomor_polisi,TankSearch.this);
                                        SharedPrefs.setDefaults("nama_sppbe",nama_sppbe,TankSearch.this);
                                        SharedPrefs.setDefaults("status_izin",status_izin,TankSearch.this);

                                        Intent intent = new Intent(TankSearch.this,DetailSkidTank.class);
                                        startActivity(intent);
                                        et.setText("");

                                    }

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(TankSearch.this,"Connection to Server Lost",Toast.LENGTH_SHORT).show();
                                error.printStackTrace();

                            }
                        }){
                            @Override
                            protected Map<String, String> getParams() throws AuthFailureError {
                                Map<String,String> params = new HashMap<String,String>();
                                params.put("userID",UserID);
                                params.put("nomor_polisi",etPlat);
                                params.put("tanggal_jam_pengecekan",timedata);
                                return params;
                            }
                        };
                        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                                30000,
                                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                        MySingleton.getmInstance(TankSearch.this).addToRequestQueue(stringRequest);
                    }


                }
            });

        }

       // mHandler.postDelayed(refresh, 30050);

    }

    public void tap (View v)
    {
        if(v.getId()== R.id.cardView2){
            Intent i = new Intent(TankSearch.this, TambahTruck.class);
            mHandler.removeCallbacksAndMessages(null);
            startActivity(i);
        }
    }

    public void displayAlert(String message){

        builder.setMessage(message);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                et.setText("");
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();

    }

    public void resetSharedPrefs(){

        if (SharedPrefs.getDefaults("check_done",TankSearch.this)!=null){

            String res_string = "";
            String key = SharedPrefs.getDefaults("check_done",TankSearch.this);

            if(key.equals("reset")){
                SharedPrefs.setDefaults("time_view",res_string,TankSearch.this);
                SharedPrefs.setDefaults("time_data",res_string,TankSearch.this);
                SharedPrefs.setDefaults("img_name",res_string,TankSearch.this);
                SharedPrefs.setDefaults("ID_pengecekan",res_string,TankSearch.this);
                SharedPrefs.setDefaults("nomor_polisi",res_string,TankSearch.this);
                SharedPrefs.setDefaults("nama_sppbe",res_string,TankSearch.this);
                SharedPrefs.setDefaults("status_izin",res_string,TankSearch.this);

                SharedPrefs.setDefaults("skid_cek",res_string,TankSearch.this);
                SharedPrefs.setDefaults("driver_cek",res_string,TankSearch.this);

                SharedPrefs.setDefaults("skid_cek",res_string,TankSearch.this);
                SharedPrefs.setDefaults("driver_cek",res_string,TankSearch.this);
                SharedPrefs.setDefaults("check_done",res_string,TankSearch.this);
            }


        }
    }

    public void onBackPressed() {
        Intent i = new Intent(TankSearch.this, CheckerArea.class);
        startActivity(i);
    }

}
