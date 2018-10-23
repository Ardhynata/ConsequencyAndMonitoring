package com.depotlpgbanyuwangi.consequencyandmonitoringsystem;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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

public class MainActivity extends Activity {

    //private int CAMERA_PERMISSION_CODE = 1, R_STORAGE_PERMISSION_CODE=2, W_STORAGE_PERMISSION_CODE=3;

    Button BnLogin;
    EditText IDLogin, passLogin;
    String idlogin,access_token;
    AlertDialog.Builder builder;
    String login_url = "http://depotlpgbanyuwangi.com/login.php";
    String[] imagefilename = new String[4];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); //enable full screen
        setContentView(R.layout.activity_main);

        ClearDataApplication.getInstance().clearApplicationData();

        if(!Permission.hasPermissions(this,Permission.getPERMISSIONS())){
            ActivityCompat.requestPermissions(this, Permission.getPERMISSIONS(), Permission.getPERMISSION_ALL());
        }

        SharedPrefs.resDefaults(MainActivity.this);
        String user_id = SharedPrefs.getDefaults("user_id",MainActivity.this);
        System.out.println("user_id is: " + user_id);
        System.out.println("Masookk Pak eko");

        SharedPrefs.setDefaults("block_redirect","false",MainActivity.this);

        ImageView myImageView = (ImageView) findViewById(R.id.imageView);
        myImageView.setImageResource(R.drawable.pertamina_logo);


        builder = new AlertDialog.Builder(MainActivity.this);
        IDLogin = (EditText) findViewById(R.id.IDLogin);
        passLogin = (EditText) findViewById(R.id.passLogin);
        BnLogin = (Button)findViewById(R.id.btnLogin);

        BnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                idlogin = IDLogin.getText().toString();
                access_token = passLogin.getText().toString();

                SharedPrefs.setDefaults("id_login",idlogin,MainActivity.this);

                if(idlogin.equals("")||access_token.equals("")){
                    builder.setTitle("Something went wrong");
                    displayAlert("Enter a valid username and password...");
                } else {
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, login_url,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    try {

                                        JSONArray jsonArray = new JSONArray(response);
                                        JSONObject jsonObject = jsonArray.getJSONObject(0);
                                        String code = jsonObject.getString("code");

                                        if (code.equals("login_failed")){
                                            builder.setTitle("Login Error...");
                                            displayAlert(jsonObject.getString("message"));
                                        }else{

                                            String ID_auth = jsonObject.getString("ID_auth");
                                            String userID = jsonObject.getString("userID");

                                            SharedPrefs.setDefaults("user_id",userID,MainActivity.this);

                                            if(ID_auth.equals("2")) {
                                                Intent intent = new Intent(MainActivity.this, AdminArea.class);
                                                startActivity(intent);
                                                IDLogin.setText("");
                                                passLogin.setText("");
                                            } else if(ID_auth.equals("3")) {
                                                Intent intent = new Intent(MainActivity.this, CheckerArea.class);
                                                startActivity(intent);
                                                IDLogin.setText("");
                                                passLogin.setText("");
                                            }
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(MainActivity.this,"Connection to Server Lost",Toast.LENGTH_SHORT).show();
                            error.printStackTrace();
                        }
                    }){
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String,String> params = new HashMap<String, String>();
                            params.put("username",idlogin);
                            params.put("access_token",access_token);
                            return params;
                        }
                    };
                    stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                            30000,
                            DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                    MySingleton.getmInstance(MainActivity.this).addToRequestQueue(stringRequest);
                }

            }
        });

    }
    public void displayAlert(String message){

        builder.setMessage(message);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                IDLogin.setText("");
                passLogin.setText("");
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();

    }
}
