package com.depotlpgbanyuwangi.consequencyandmonitoringsystem;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
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

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class CekTruck extends Activity {

    AlertDialog.Builder builder;

    RadioGroup rGKIM, rGAPARCO2, rGAPARDCP, rGHT, rGV, rGEP, rGLamp, rgBan;
    int selectedKIM, selectedAPARCO2, selectedAPARDCP, selectedHT, selectedV, selectedEP, selectedLamp, selectedBan;

    Button BtSaveTruck;
    Button[] bfoto = new Button[8];
    Integer REQUEST_CAMERA=1, SELECT_FILE=0;
    private Bitmap bitmap;
    private ImageView[] imgView = new ImageView[8];
    int idKIM=0, idAPARCO2=0, idAPARDCP=0, idHT=0, idV=0, idEP=0, idLamp=0, idBan=0;
    int now;

    String mCurrentPhotoPath;
    String img_name,checker,ID_pengecekan;
    String truckcek_url = "http://depotlpgbanyuwangi.com/truckcek.php";
    String[] str_img = new String[8];


    public void checkButton(View view) {

        switch(rGKIM.getCheckedRadioButtonId()){
            case 0:
                break;
            case R.id.rdKIM1:
                selectedKIM = 1;
                idKIM = 1;
                break;
            case R.id.rdKIM2:
                selectedKIM = 0;
                idKIM = 1;
                break;
        }

        switch(rGAPARCO2.getCheckedRadioButtonId()){
            case 0:
                break;
            case R.id.rdAPARCO21:
                selectedAPARCO2 = 1;
                idAPARCO2 = 1;
                break;
            case R.id.rdAPARCO22:
                selectedAPARCO2 = 0;
                idAPARCO2 = 1;
                break;
        }

        switch(rGAPARDCP.getCheckedRadioButtonId()){
            case 0:
                break;
            case R.id.rdAPARDCP1:
                selectedAPARDCP = 1;
                idAPARDCP = 1;
                break;
            case R.id.rdAPARDCP2:
                selectedAPARDCP = 0;
                idAPARDCP = 1;
                break;
        }

        switch(rGHT.getCheckedRadioButtonId()){
            case 0:
                break;
            case R.id.rdHT1:
                selectedHT = 1;
                idHT = 1;
                break;
            case R.id.rdHT2:
                selectedHT = 0;
                idHT = 1;
                break;
        }

        switch(rGV.getCheckedRadioButtonId()){
            case 0:
                break;
            case R.id.rdV1:
                selectedV = 1;
                idV = 1;
                break;
            case R.id.rdV2:
                selectedV = 0;
                idV = 1;
                break;
        }

        switch(rGEP.getCheckedRadioButtonId()){
            case 0:
                break;
            case R.id.rdEP1:
                selectedEP = 1;
                idEP = 1;
                break;
            case R.id.rdEP2:
                selectedEP = 0;
                idEP = 1;
                break;
        }

        switch(rGLamp.getCheckedRadioButtonId()){
            case 0:
                break;
            case R.id.rdLamp1:
                selectedLamp = 1;
                idLamp = 1;
                break;
            case R.id.rdLamp2:
                selectedLamp = 0;
                idLamp = 1;
                break;
        }

        switch(rgBan.getCheckedRadioButtonId()){
            case 0:
                break;
            case R.id.rdBan1:
                selectedBan = 1;
                idBan = 1;
                break;
            case R.id.rdBan2:
                selectedBan = 0;
                idBan = 1;
                break;
        }
        globalListener();
    }

    private ImageView bantuan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); //enable full screen
        setContentView(R.layout.cek_truck);

        bantuan = findViewById(R.id.bantuan);


        BtSaveTruck = (Button)findViewById(R.id.btSaveTruck);
        BtSaveTruck.setEnabled(false);
        BtSaveTruck.setBackgroundResource(R.drawable.roundedbtngrey);

        img_name = SharedPrefs.getDefaults("img_name",CekTruck.this);
        checker = SharedPrefs.getDefaults("id_login",CekTruck.this);
        ID_pengecekan = SharedPrefs.getDefaults("ID_pengecekan",CekTruck.this);

        rGKIM		= (RadioGroup)findViewById(R.id.rgKIM);
        rGAPARCO2	= (RadioGroup)findViewById(R.id.rgAPARCO2);
        rGAPARDCP	= (RadioGroup)findViewById(R.id.rgAPARDCP);
        rGHT		= (RadioGroup)findViewById(R.id.rgHT);
        rGV			= (RadioGroup)findViewById(R.id.rgV);
        rGEP		= (RadioGroup)findViewById(R.id.rgEP);
        rGLamp		= (RadioGroup)findViewById(R.id.rgLamp);
        rgBan		= (RadioGroup)findViewById(R.id.rgBan);

        imgView[0]  = (ImageView)findViewById(R.id.imgKIM);
        imgView[1]  = (ImageView)findViewById(R.id.imgAPARCO2);
        imgView[2]  = (ImageView)findViewById(R.id.imgAPARDCP);
        imgView[3]  = (ImageView)findViewById(R.id.imgHT);
        imgView[4]  = (ImageView)findViewById(R.id.imgV);
        imgView[5]  = (ImageView)findViewById(R.id.imgEP);
        imgView[6]  = (ImageView)findViewById(R.id.imgLamp);
        imgView[7]  = (ImageView)findViewById(R.id.imgBan);

        bfoto[0] = (Button) findViewById(R.id.btFotoKIM);
        bfoto[0].setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick (View view){
                now = 0;
                selectImage();
            }
        });

        bfoto[1] = (Button) findViewById(R.id.btFotoAPARCO2);
        bfoto[1].setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick (View view){
                now = 1;
                selectImage();
            }
        });

        bfoto[2] = (Button) findViewById(R.id.btFotoAPARDCP);
        bfoto[2].setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick (View view){
                now = 2;
                selectImage();
            }
        });

        bfoto[3] = (Button) findViewById(R.id.btFotoHT);
        bfoto[3].setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick (View view){
                now = 3;
                selectImage();
            }
        });

        bfoto[4] = (Button) findViewById(R.id.btFotoVessel);
        bfoto[4].setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick (View view){
                now = 4;
                selectImage();
            }
        });

        bfoto[5] = (Button) findViewById(R.id.btFotoEP);
        bfoto[5].setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick (View view){
                now = 5;
                selectImage();
            }
        });

        bfoto[6] = (Button) findViewById(R.id.btFotoLamp);
        bfoto[6].setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick (View view){
                now = 6;
                selectImage();
            }
        });

        bfoto[7] = (Button) findViewById(R.id.btFotoBan);
        bfoto[7].setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick (View view){
                now = 7;
                selectImage();
            }
        });

        builder = new AlertDialog.Builder(CekTruck.this);

        BtSaveTruck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(CekTruck.this,"Saving Data...",Toast.LENGTH_LONG).show();

                    StringRequest stringRequest = new StringRequest(Request.Method.POST, truckcek_url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONArray jsonArray = new JSONArray(response);
                                JSONObject jsonObject = jsonArray.getJSONObject(0);
                                String code = jsonObject.getString("code");

                                if(code.equals("insert_failed")){
                                    builder.setTitle("Error while sending data...");
                                    displayAlert(jsonObject.getString("message"));
                                } else {
                                    Toast.makeText(CekTruck.this,jsonObject.getString("message"),Toast.LENGTH_SHORT).show();

                                    SharedPrefs.setDefaults("skid_cek","done",CekTruck.this);
                                    Intent intent = new Intent(CekTruck.this,CekArea.class);
                                    startActivity(intent);

                                }

                                str_img = null;

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                            Toast.makeText(CekTruck.this,"Connection to Server Lost",Toast.LENGTH_SHORT).show();
                            error.printStackTrace();

                        }
                    }){
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String,String> params = new HashMap<String, String>();
                            params.put("kim_status",Integer.toString(selectedKIM));
                            params.put("apar_co2_status",Integer.toString(selectedAPARCO2));
                            params.put("apar_dcp_status",Integer.toString(selectedAPARDCP));
                            params.put("head_truck_status",Integer.toString(selectedHT));
                            params.put("vessel_status",Integer.toString(selectedV));
                            params.put("electrical_part_status",Integer.toString(selectedEP));
                            params.put("lamp_status",Integer.toString(selectedLamp));
                            params.put("ban_roda_status",Integer.toString(selectedBan));
                            params.put("ID_pengecekan", ID_pengecekan);

                            params.put("foldername",img_name+"("+checker+")");
                            params.put("imgKIM",str_img[0]);
                            params.put("imgAPARCO2",str_img[1]);
                            params.put("imgAPARDCP",str_img[2]);
                            params.put("imgHT",str_img[3]);
                            params.put("imgV",str_img[4]);
                            params.put("imgEP",str_img[5]);
                            params.put("imgLamp",str_img[6]);
                            params.put("imgBan",str_img[7]);

                            return params;
                        }
                    };

                stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                        30000,
                        DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

                    MySingleton.getmInstance(CekTruck.this).addToRequestQueue(stringRequest);

            }
        });
    }

    public void panduan (View v)
    {
        startActivity(new Intent(CekTruck.this,PetunjukTeknis.class));
    }

    private void globalListener(){
        if(idKIM==1 && idAPARCO2==1 && idAPARDCP==1 && idHT==1 && idV==1 && idEP==1 && idLamp==1 && idBan==1
                && str_img[0]!=null && str_img[1]!=null && str_img[2]!=null && str_img[3]!=null && str_img[4]!=null && str_img[5]!=null && str_img[6]!=null && str_img[7]!=null){
            BtSaveTruck.setEnabled(true);
            BtSaveTruck.setBackgroundResource(R.drawable.roundedbtngreen);
        } else {
            BtSaveTruck.setEnabled(false);
            BtSaveTruck.setBackgroundResource(R.drawable.roundedbtngrey);
        }
    }

    private File createImageFile() throws IOException{
        //Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",  /* suffix */
                storageDir     /* directory */
        );
        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;

    }

    private void selectImage(){
        final CharSequence[] items={"Camera","Gallery","Cancel"};

        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(CekTruck.this);
        builder.setTitle("Add Image");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                if (items[i].equals("Camera")){

                    Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                    if(takePictureIntent.resolveActivity(getPackageManager())!=null){
                        File photoFile = null;
                        try {
                            photoFile = createImageFile();
                        } catch (IOException e) {
                            // Error occurred while creating the File
                            e.printStackTrace();
                        }
                        // Continue only if the File was successfully created
                        if(photoFile !=null){

                            Uri photoURI = FileProvider.getUriForFile(CekTruck.this,"com.depotlpgbanyuwangi.consquencyandmonitoringsystem",photoFile);
                            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                            startActivityForResult(takePictureIntent,REQUEST_CAMERA);
                        }
                    }

                }else if (items[i].equals("Gallery")){

                    Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    intent.setType("image/*");
                    startActivityForResult(intent.createChooser(intent,"Select File"), SELECT_FILE);

                }else if (items[i].equals("Cancel")){
                    dialog.dismiss();
                }

            }
        });
        builder.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {

            if (requestCode == REQUEST_CAMERA) {

                File file = new File(mCurrentPhotoPath);
                bitmap = null;
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(CekTruck.this.getContentResolver(), Uri.fromFile(file));
                    str_img[now]=imageToString(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                if (str_img[now]!=null){
                    //Do your stuff here.
                    //imgView[now].setImageBitmap(bitmap[now]);
                    //imgView[now].setVisibility(View.GONE);
                    bitmap.recycle();
                    bfoto[now].setBackgroundResource(R.drawable.roundedbtngreen);
                    bfoto[now].setText(R.string.editfoto);
                    globalListener();
                    now = 0;
                }

            } else if (requestCode == SELECT_FILE) {

                Uri selectedImageUri = data.getData();
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImageUri);
                    str_img[now]=imageToString(bitmap);
                    //imgView[now].setImageBitmap(bitmap);
                    //imgView[now].setVisibility(View.GONE);
                    if(str_img[now]!=null) {
                        bfoto[now].setBackgroundResource(R.drawable.roundedbtngreen);
                        bfoto[now].setText(R.string.editfoto);
                        bitmap.recycle();
                    }
                    now = 0;
                } catch (IOException e) {
                    e.printStackTrace();
                }
                globalListener();
            }
        }
    }

    public void displayAlert(String message){

        builder.setMessage(message);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();

    }

    private String imageToString(Bitmap bitmap){
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,40,byteArrayOutputStream);
        byte[] imgBytes = byteArrayOutputStream.toByteArray();
        return android.util.Base64.encodeToString(imgBytes, android.util.Base64.DEFAULT);

    }

}
