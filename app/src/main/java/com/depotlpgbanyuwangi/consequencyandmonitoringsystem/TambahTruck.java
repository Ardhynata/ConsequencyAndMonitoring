package com.depotlpgbanyuwangi.consequencyandmonitoringsystem;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
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

import java.util.HashMap;
import java.util.Map;

public class TambahTruck extends Activity {
    EditText etSPPBE, etPlat, etKapasitas, etAlamat;
    String sppbe, plat, kapasitas, alamat;
    Button btSave;

    String tambah_truck = "http://depotlpgbanyuwangi.com/tambahtruck.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.tambah_truck);

        etSPPBE     = (EditText)findViewById(R.id.etSPPBE);
        etPlat      = (EditText)findViewById(R.id.etPlat);
        etKapasitas = (EditText)findViewById(R.id.etKapasitas);
        etAlamat    = (EditText)findViewById(R.id.etAlamat);
        btSave      = (Button)findViewById(R.id.btSave);

        btSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sppbe = etSPPBE.getText().toString();
                plat = etPlat.getText().toString();
                kapasitas = etKapasitas.getText().toString();
                alamat = etAlamat.getText().toString();

                StringRequest stringRequest = new StringRequest(Request.Method.POST, tambah_truck, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            JSONObject jsonObject = jsonArray.getJSONObject(0);
                            String code = jsonObject.getString("code");

                            Toast.makeText(TambahTruck.this,jsonObject.getString("message"),Toast.LENGTH_SHORT).show();
                            etSPPBE.setText("");
                            etPlat.setText("");
                            etKapasitas.setText("");
                            etAlamat.setText("");

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
                        params.put("sppbe",sppbe);
                        params.put("plat",plat);
                        params.put("kapasitas",kapasitas);
                        params.put("alamat",alamat);
                        return params;
                    }
                };
                MySingleton.getmInstance(TambahTruck.this).addToRequestQueue(stringRequest);
            }
        });


    }
}
