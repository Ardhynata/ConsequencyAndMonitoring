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

public class CekDriver extends Activity {

    AlertDialog.Builder builder;

    RadioGroup rGSH, rGUni, rGSS, rGBT;
    int selectedSH, selectedUni, selectedSS, selectedBT;


    Uri uri;
    Button BtSaveDriver;
    private Button[] bfoto = new Button[4];
    Integer REQUEST_CAMERA=1, SELECT_FILE=0;
    private Bitmap bitmap;
    private ImageView[] imgView = new ImageView[4];
    int idSH = 0, idUni = 0, idSS = 0, idBT = 0;
    int now;

    String mCurrentPhotoPath;
    String img_name,checker,ID_pengecekan;
    String drivercek_url = "http://depotlpgbanyuwangi.com/drivercek.php";
    String[] str_img = new String[4];

    public void checkButton(View view) {

        switch(rGSH.getCheckedRadioButtonId()){
            case 0:
                break;
            case R.id.rdSH1:
                selectedSH = 1;
                idSH = 1;
                break;
            case R.id.rdSH2:
                selectedSH = 0;
                idSH = 1;
                break;
        }

        switch(rGUni.getCheckedRadioButtonId()){
            case 0:
                break;
            case R.id.rdUni1:
                selectedUni = 1;
                idUni = 1;
                break;
            case R.id.rdUni2:
                selectedUni = 0;
                idUni = 1;
                break;
        }

        switch(rGSS.getCheckedRadioButtonId()){
            case 0:
                break;
            case R.id.rdSS1:
                selectedSS = 1;
                idSS = 1;
                break;
            case R.id.rdSS2:
                selectedSS = 0;
                idSS = 1;
                break;
        }

        switch(rGBT.getCheckedRadioButtonId()){
            case 0:
                break;
            case R.id.rdBT1:
                selectedBT = 1;
                idBT = 1;
                bfoto[3].setEnabled(false);
                bfoto[3].setBackgroundResource(R.drawable.roundedbtngrey);
                bfoto[3].setText(R.string.foto);
                str_img[3]=null;
                break;
            case R.id.rdBT2:
                selectedBT = 0;
                idBT = 1;
                bfoto[3].setEnabled(true);
                bfoto[3].setBackgroundResource(R.drawable.roundbtnyellow);
                break;
        }
        globalListener();
    }

    private ImageView help;
    private ImageView help2;
    private ImageView help3;
    private ImageView help4;
    private TextView panduan;
    private TextView panduan2;
    private TextView panduan3;
    private TextView panduan4;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); //enable full screen
        setContentView(R.layout.cek_driver);

        help = findViewById(R.id.helpSH);
        help2 = findViewById(R.id.helpUNI);
        help3 = findViewById(R.id.helpSS);
        help4 = findViewById(R.id.helpBT);

        panduan = findViewById(R.id.panduan);
        panduan.setVisibility(View.GONE);
        panduan2 = findViewById(R.id.panduan2);
        panduan2.setVisibility(View.GONE);
        panduan3 = findViewById(R.id.panduan3);
        panduan3.setVisibility(View.GONE);
        panduan4 = findViewById(R.id.panduan4);
        panduan4.setVisibility(View.GONE);

        BtSaveDriver = (Button)findViewById(R.id.btSaveDriver);
        BtSaveDriver.setEnabled(false);
        BtSaveDriver.setBackgroundResource(R.drawable.roundedbtngrey);

        img_name = SharedPrefs.getDefaults("img_name",CekDriver.this);
        checker = SharedPrefs.getDefaults("id_login",CekDriver.this);
        ID_pengecekan = SharedPrefs.getDefaults("ID_pengecekan",CekDriver.this);

        rGSH    = (RadioGroup)findViewById(R.id.rgSH);
        rGUni    = (RadioGroup)findViewById(R.id.rgUni);
        rGSS    = (RadioGroup)findViewById(R.id.rgSS);
        rGBT    = (RadioGroup)findViewById(R.id.rgBT);

        imgView[0]= (ImageView)findViewById(R.id.imgSH);
        imgView[1]= (ImageView)findViewById(R.id.imgUni);
        imgView[2]= (ImageView)findViewById(R.id.imgSS);
        imgView[3]= (ImageView)findViewById(R.id.imgBT);

        bfoto[0] = (Button) findViewById(R.id.btFotoSH);
        bfoto[0].setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick (View view){
                now = 0;
                selectImage();
            }
        });

        bfoto[1] = (Button) findViewById(R.id.btFotoUni);
        bfoto[1].setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick (View view){
                now = 1;
                selectImage();
            }
        });

        bfoto[2] = (Button) findViewById(R.id.btFotoSS);
        bfoto[2].setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick (View view){
                now = 2;
                selectImage();
            }
        });

        bfoto[3] = (Button) findViewById(R.id.btFotoBT);
        bfoto[3].setEnabled(false);
        bfoto[3].setBackgroundResource(R.drawable.roundedbtngrey);
        bfoto[3].setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick (View view){
                now=3;
                selectImage();
            }
        });

        builder = new AlertDialog.Builder(CekDriver.this);
        BtSaveDriver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(CekDriver.this,"Saving Data...",Toast.LENGTH_LONG).show();

                    StringRequest stringRequest = new StringRequest(Request.Method.POST, drivercek_url, new Response.Listener<String>() {
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
                                    Toast.makeText(CekDriver.this,jsonObject.getString("message"),Toast.LENGTH_SHORT).show();
                                    SharedPrefs.setDefaults("driver_cek","done",CekDriver.this);
                                    Intent intent = new Intent(CekDriver.this,CekArea.class);
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
                            Toast.makeText(CekDriver.this,"Connection to Server Lost",Toast.LENGTH_SHORT).show();
                            error.printStackTrace();

                        }
                    }){
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String,String> params = new HashMap<String, String>();
                            params.put("safety_helmet_status",Integer.toString(selectedSH));
                            params.put("uniform_status",Integer.toString(selectedUni));
                            params.put("safety_shoes_status",Integer.toString(selectedSS));
                            params.put("barang_terlarang_status",Integer.toString(selectedBT));
                            params.put("ID_pengecekan", ID_pengecekan);

                            params.put("foldername",img_name+"("+checker+")");
                            params.put("imgSH",str_img[0]);
                            params.put("imgUni",str_img[1]);
                            params.put("imgSS",str_img[2]);
                            if(str_img[3]!=null) {
                                params.put("BTstatus", "1");
                                params.put("imgBT", str_img[3]);
                            }else{
                                params.put("BTstatus", "0");
                            }
                            return params;
                        }
                    };
                    stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                            30000,
                            DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                    MySingleton.getmInstance(CekDriver.this).addToRequestQueue(stringRequest);
            }
        });
    }

    public void klik1 (View v)
    {
        if (panduan.getVisibility() == View.GONE){
            panduan.setVisibility(View.VISIBLE);
        }else{panduan.setVisibility(View.GONE);}
    }

    public void klik2 (View v)
    {
        if (panduan2.getVisibility() == View.GONE){
            panduan2.setVisibility(View.VISIBLE);
        }else{panduan2.setVisibility(View.GONE);}
    }

    public void klik3 (View v)
    {
        if (panduan3.getVisibility() == View.GONE){
            panduan3.setVisibility(View.VISIBLE);
        }else{panduan3.setVisibility(View.GONE);}
    }

    public void klik4 (View v)
    {
        if (panduan4.getVisibility() == View.GONE){
            panduan4.setVisibility(View.VISIBLE);
        }else{panduan4.setVisibility(View.GONE);}
    }

    private void globalListener(){
        if(idSH==1 && idUni==1 && idSS==1 && idBT==1 && str_img[0]!=null && str_img[1]!=null && str_img[2]!=null ){
            if (selectedBT==0){
                if(str_img[3]==null){
                    BtSaveDriver.setEnabled(false);
                    BtSaveDriver.setBackgroundResource(R.drawable.roundedbtngrey);
                }else{
                    BtSaveDriver.setEnabled(true);
                    BtSaveDriver.setBackgroundResource(R.drawable.roundedbtngreen);
                }
            }else{
                if(selectedBT==1){
                    if(str_img[3]==null){
                        BtSaveDriver.setEnabled(true);
                        BtSaveDriver.setBackgroundResource(R.drawable.roundedbtngreen);
                    }
                }
            }

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

        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(CekDriver.this);
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

                            Uri photoURI = FileProvider.getUriForFile(CekDriver.this,"com.depotlpgbanyuwangi.consquencyandmonitoringsystem",photoFile);
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
                    bitmap = MediaStore.Images.Media.getBitmap(CekDriver.this.getContentResolver(), Uri.fromFile(file));
                    str_img[now]=imageToString(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                if (str_img[now]!=null){
                    //Do your stuff here.
                    //imgView[now].setImageBitmap(bitmap[now]);
                    //imgView[now].setVisibility(View.GONE);
                    bitmap.recycle();
                    System.out.println("GAK ERROR");
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

