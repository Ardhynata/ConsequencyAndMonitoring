package com.depotlpgbanyuwangi.consequencyandmonitoringsystem;

import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.StringRequest;
import com.example.asus.consquencyandmonitoringsystem.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class HistoryPageChecker extends AppCompatActivity {

    String ID_pengecekan,nopol;
    DatabaseFetcher databaseFetcher;
    String selectall_url = "http://depotlpgbanyuwangi.com/selectall.php";
    private Handler mHandler = new Handler();
    TextView txtSPPBE, txtKim, txtNopol,txtStatus, txtSopir,txtAPARCO2, txtAPARDCP, txtHT, txtV, txtEP, txtLamp,txtBan, txtSH, txtUni,txtSS,txtBT, txtViewDispensasi, txtViewKomentar;
    Button btTerima, btTolak, btPeringatan;
    EditText etDispensasi, etComment;
    String dispensasi,comment;
    ImageView imageView;

    AlertDialog.Builder builder;
    Button[] bfoto = new Button[12];

    String finallydone = "http://depotlpgbanyuwangi.com/finallydone.php";
    String laporan     = "http://depotlpgbanyuwangi.com/laporan/lap.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); //enable full screen
        setContentView(R.layout.history_page_checker);

        final String user_id     = SharedPrefs.getDefaults("user_id",HistoryPageChecker.this);
        ID_pengecekan = getIntent().getStringExtra("ID_pengecekan");
        nopol = getIntent().getStringExtra("nopol");

        txtViewDispensasi = (TextView)findViewById(R.id.txtViewDispensasi);
        txtViewKomentar = (TextView)findViewById(R.id.txtViewKomentar);
        etComment = (EditText)findViewById(R.id.etComment);
        etDispensasi = (EditText)findViewById(R.id.etDispensasi);

        txtSPPBE = (TextView)findViewById(R.id.valSPPBE);
        txtNopol = (TextView)findViewById(R.id.valNopol);
        txtSopir = (TextView)findViewById(R.id.valSopir);
        txtStatus = (TextView)findViewById(R.id.valStatus);

        txtKim = (TextView)findViewById(R.id.valKIM);
        txtAPARCO2 = (TextView)findViewById(R.id.valAPARCO2);
        txtAPARDCP = (TextView)findViewById(R.id.valAPARDCP);
        txtHT = (TextView)findViewById(R.id.valHT);
        txtV = (TextView)findViewById(R.id.valVessel);
        txtEP = (TextView)findViewById(R.id.valEP);
        txtLamp = (TextView)findViewById(R.id.valLamp);
        txtBan = (TextView)findViewById(R.id.valBan);

        txtSH = (TextView)findViewById(R.id.valSH);
        txtUni = (TextView)findViewById(R.id.valUni);
        txtSS = (TextView)findViewById(R.id.valSS);
        txtBT = (TextView)findViewById(R.id.valBT);

        btTerima = (Button)findViewById(R.id.btTerima);
        btTolak = (Button)findViewById(R.id.btTolak);
        btPeringatan = (Button)findViewById(R.id.btPeringatan);

        bfoto[0] = (Button)findViewById(R.id.btnKim);
        bfoto[1] = (Button)findViewById(R.id.btnAPARCO2);
        bfoto[2] = (Button)findViewById(R.id.btnAPARDCP);
        bfoto[3] = (Button)findViewById(R.id.btnHT);
        bfoto[4] = (Button)findViewById(R.id.btnVessel);
        bfoto[5] = (Button)findViewById(R.id.btnEP);
        bfoto[6] = (Button)findViewById(R.id.btnLamp);
        bfoto[7] = (Button)findViewById(R.id.btnBan);

        bfoto[8] = (Button)findViewById(R.id.btnSH);
        bfoto[9] = (Button)findViewById(R.id.btnUni);
        bfoto[10] = (Button)findViewById(R.id.btnSS);
        bfoto[11] = (Button)findViewById(R.id.btnBT);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, selectall_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONArray jsonArray = new JSONArray(response);
                    JSONObject jsonObject = jsonArray.getJSONObject(0);
                    String code = jsonObject.getString("code");

                    if(code.equals("selectall_failed")){
                        Toast.makeText(HistoryPageChecker.this,"CONNECTION PROBLEM", Toast.LENGTH_SHORT).show();
                    } else {

                        jsonObject = jsonArray.getJSONObject(1);

                        databaseFetcher = new DatabaseFetcher(
                                jsonObject.getString("nama_sppbe"),
                                jsonObject.getString("nomor_polisi"),
                                jsonObject.getString("nama_sopir"),
                                jsonObject.getString("status_izin"),
                                jsonObject.getString("kim_status"),
                                jsonObject.getString("apar_co2_status"),
                                jsonObject.getString("apar_dcp_status"),
                                jsonObject.getString("head_truck_status"),
                                jsonObject.getString("vessel_status"),
                                jsonObject.getString("electrical_part_status"),
                                jsonObject.getString("lamp_status"),
                                jsonObject.getString("ban_roda_status"),
                                jsonObject.getString("safety_helmet_status"),
                                jsonObject.getString("uniform_status"),
                                jsonObject.getString("safety_shoes_status"),
                                jsonObject.getString("barang_terlarang_status"),
                                jsonObject.getString("tanggal_jam_pengecekan"),
                                jsonObject.getString("username"),
                                jsonObject.getString("deadline")
                        );

                        txtSPPBE.setText(databaseFetcher.getNama_sppbe());
                        txtNopol.setText(databaseFetcher.getNomor_polisi());
                        txtSopir.setText(databaseFetcher.getNama_sopir());

                        if(databaseFetcher.getStatus_izin().equals("0")){
                            txtStatus.setText("Belum Dicek");
                        }else if(databaseFetcher.getStatus_izin().equals("1")){
                            txtStatus.setText("Diterima");
                        }else if(databaseFetcher.getStatus_izin().equals("2")){
                            txtStatus.setText("Peringatan");
                        }else if(databaseFetcher.getStatus_izin().equals("3")){
                            txtStatus.setText("Ditolak");
                        }

                        txtKim.setText(databaseFetcher.statusstring(databaseFetcher.getKim_status()));
                        txtAPARCO2.setText(databaseFetcher.statusstring(databaseFetcher.getApar_co2_status()));
                        txtAPARDCP.setText(databaseFetcher.statusstring(databaseFetcher.getApar_dcp_status()));
                        txtHT.setText(databaseFetcher.statusstring(databaseFetcher.getHead_truck_status()));
                        txtV.setText(databaseFetcher.statusstring(databaseFetcher.getVessel_status()));
                        txtEP.setText(databaseFetcher.statusstring(databaseFetcher.getElectrical_part_status()));
                        txtLamp.setText(databaseFetcher.statusstring(databaseFetcher.getLamp_status()));
                        txtBan.setText(databaseFetcher.statusstring(databaseFetcher.getBan_roda_status()));
                        txtSH.setText(databaseFetcher.statusstring(databaseFetcher.getSafety_helmet_status()));
                        txtUni.setText(databaseFetcher.statusstring(databaseFetcher.getUniform_status()));
                        txtSS.setText(databaseFetcher.statusstring(databaseFetcher.getSafety_shoes_status()));
                        txtBT.setText(databaseFetcher.statusstring(databaseFetcher.getBarang_terlarang_status()));

                        final String waktu = databaseFetcher.getTanggal_jam_pengecekan();
                        final String username = databaseFetcher.getUsername();

                        bfoto[0].setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                showPicture(waktu,username,"truckskidtank/KIM.jpg");
                            }
                        });

                        bfoto[1].setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                showPicture(waktu,username,"truckskidtank/APARCO2.jpg");
                            }
                        });

                        bfoto[2].setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                showPicture(waktu,username,"truckskidtank/APARDCP.jpg");
                            }
                        });

                        bfoto[3].setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                showPicture(waktu,username,"truckskidtank/HeadTruck.jpg");
                            }
                        });

                        bfoto[4].setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                showPicture(waktu,username,"truckskidtank/Vessel.jpg");
                            }
                        });

                        bfoto[5].setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                showPicture(waktu,username,"truckskidtank/ElectricalPart.jpg");
                            }
                        });

                        bfoto[6].setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                showPicture(waktu,username,"truckskidtank/Lamp.jpg");
                            }
                        });

                        bfoto[7].setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                showPicture(waktu,username,"truckskidtank/Ban.jpg");
                            }
                        });

                        bfoto[8].setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                showPicture(waktu,username,"driver/SafetyHelmet.jpg");
                            }
                        });

                        bfoto[9].setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                showPicture(waktu,username,"driver/Uniform.jpg");
                            }
                        });

                        bfoto[10].setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                showPicture(waktu,username,"driver/SafetyShoes.jpg");
                            }
                        });

                        if(databaseFetcher.statusstring(databaseFetcher.getBarang_terlarang_status()).equals(": Sesuai")){

                            bfoto[11].setEnabled(false);
                            bfoto[11].setBackgroundResource(R.drawable.roundedbtngrey);
                            bfoto[11].setText("");

                        } else {
                            bfoto[11].setEnabled(true);
                            bfoto[11].setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    showPicture(waktu,username,"driver/BarangTerlarang.jpg");
                                }
                            });
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(HistoryPageChecker.this,"CONNECTION ERROR", Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<String, String>();
                params.put("ID_pengecekan",ID_pengecekan);
                return params;
            }
        };
        MySingleton.getmInstance(HistoryPageChecker.this).addToRequestQueue(stringRequest);

    }

    public void showPicture (String waktu, String username, String filename){
            String server_url = "http://www.depotlpgbanyuwangi.com/images/"+ waktu + "%28" + username + "%29/"+ filename;

            ImageRequest imageRequest = new ImageRequest(server_url,
                new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(final Bitmap response) {

                        AlertDialog.Builder alertadd = new AlertDialog.Builder(HistoryPageChecker.this);
                        LayoutInflater factory = LayoutInflater.from(HistoryPageChecker.this);
                        final View view = factory.inflate(R.layout.alertimage, null);
                        imageView = (ImageView) view.findViewById(R.id.dialog_imageview);
                        imageView.setImageBitmap(response);
                        alertadd.setView(view);
                        alertadd.setNeutralButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dlg, int sumthin) {
                                response.recycle();
                            }
                        });
                        alertadd.show();
                    }
                }, 0, 0, ImageView.ScaleType.CENTER_CROP, null, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(HistoryPageChecker.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                    error.printStackTrace();
                }
            });
            MySingleton.getmInstance(HistoryPageChecker.this).addToRequestQueue(imageRequest);
    }

}
