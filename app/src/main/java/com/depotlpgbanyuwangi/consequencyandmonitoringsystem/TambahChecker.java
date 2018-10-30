package com.depotlpgbanyuwangi.consequencyandmonitoringsystem;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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

public class TambahChecker extends AppCompatActivity {

    EditText etUser, etPass;
    Button btSave;
    String username, password;

    String tambah_checker = "http://depotlpgbanyuwangi.com/tambahchecker.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.tambah_checker);

        etUser = (EditText)findViewById(R.id.checkerID);
        etPass = (EditText)findViewById(R.id.passCek);
        btSave = (Button)findViewById(R.id.btnSave);

        btSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username = etUser.getText().toString();
                password = etPass.getText().toString();

                StringRequest stringRequest = new StringRequest(Request.Method.POST, tambah_checker, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            JSONObject jsonObject = jsonArray.getJSONObject(0);
                            String code = jsonObject.getString("code");

                            Toast.makeText(TambahChecker.this,jsonObject.getString("message"),Toast.LENGTH_SHORT).show();
                            etUser.setText("");
                            etPass.setText("");

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
                        params.put("username",username);
                        params.put("password",password);
                        return params;
                    }
                };
                MySingleton.getmInstance(TambahChecker.this).addToRequestQueue(stringRequest);
            }
        });

    }
}
