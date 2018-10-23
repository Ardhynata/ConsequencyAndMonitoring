package com.depotlpgbanyuwangi.consequencyandmonitoringsystem;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Unblock extends AppCompatActivity {

    TextView txtNopol, txtSPPBE, txtStatus, txtDeadline;
    String strNopol, strSPPBE, strStatus, strDeadline, str_deadline_plus_dispensasi;
    Button btnUnblock;
    EditText etDispensasi;

    String unblock_url = "http://depotlpgbanyuwangi.com/unblock.php";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); //enable full screen
        setContentView(R.layout.unblock);

        txtNopol    = (TextView)findViewById(R.id.txtNopol);
        txtSPPBE    = (TextView)findViewById(R.id.txtSPPBE);
        txtStatus   = (TextView)findViewById(R.id.txtStatus);
        txtDeadline = (TextView)findViewById(R.id.txtDeadline);

        strNopol       = getIntent().getStringExtra("nomor_polisi");
        strSPPBE       = getIntent().getStringExtra("nama_sppbe");
        strStatus      = getIntent().getStringExtra("status_izin");
        strDeadline    = getIntent().getStringExtra("deadline");

        txtNopol.setText(strNopol);
        txtSPPBE.setText(strSPPBE);
        if(strStatus.equals("0")){
            txtStatus.setText("Belum Dicek");
        }else if (strStatus.equals("1")){
            txtStatus.setText("Diterima");
        }else if(strStatus.equals("2")){
            txtStatus.setText("Masuk Sementara");
        }else if(strStatus.equals("3")){
            txtStatus.setText("DITOLAK");
        }

        final Date date_deadline;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat dateView = new SimpleDateFormat("dd MMMM yyyy hh:mm aaa");
        try {
            date_deadline = dateFormat.parse(strDeadline);
            String dateTime = dateView.format(date_deadline);
            System.out.println(dateTime);
            txtDeadline.setText(dateTime);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        etDispensasi = (EditText)findViewById(R.id.etDispensasi);
        btnUnblock = (Button)findViewById(R.id.btnUnblock);

        btnUnblock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String dispensasi = etDispensasi.getText().toString();


                if(dispensasi.equals("")){
                    Toast.makeText(Unblock.this,"Anda belum memasukkan nilai pada kolom dispensasi",Toast.LENGTH_LONG).show();
                }else{

                    int lama_dispensasi = Integer.parseInt(dispensasi);

                    final Date date_deadline;
                    final Date plus_dispensasi;
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    try {
                        date_deadline = dateFormat.parse(strDeadline);
                        //System.out.println(dateFormat.format(date_deadline));

                        Calendar calendar = Calendar.getInstance();
                        calendar.setTime(date_deadline);
                        calendar.add(Calendar.DATE, lama_dispensasi);

                        plus_dispensasi = calendar.getTime();
                        //System.out.println(dateFormat.format(plus_dispensasi));
                        str_deadline_plus_dispensasi = dateFormat.format(plus_dispensasi);

                    } catch (ParseException e) {
                        e.printStackTrace();
                    }


                    StringRequest stringRequest = new StringRequest(Request.Method.POST, unblock_url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            try {
                                JSONArray jsonArray = new JSONArray(response);
                                JSONObject jsonObject = jsonArray.getJSONObject(0);
                                String code = jsonObject.getString("code");

                                if(code.equals("notifikasi_failed")){
                                    Toast.makeText(Unblock.this,jsonObject.getString("message"),Toast.LENGTH_LONG).show();
                                }else{
                                    Toast.makeText(Unblock.this,jsonObject.getString("message"),Toast.LENGTH_LONG).show();
                                    Intent intent = new Intent(Unblock.this, AdminArea.class);
                                    startActivity(intent);
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                            Toast.makeText(Unblock.this,"Connection to Server Interrupted",Toast.LENGTH_LONG).show();

                        }
                    }){
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String,String> params = new HashMap<String, String>();
                            params.put("unblock", "NataLovesAsma");
                            params.put("nomor_polisi", strNopol);
                            params.put("deadline", str_deadline_plus_dispensasi);
                            return params;
                        }
                    };

                    MySingleton.getmInstance(Unblock.this).addToRequestQueue(stringRequest);

                }


            }
        });

    }
}
