package com.depotlpgbanyuwangi.consequencyandmonitoringsystem;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.asus.consquencyandmonitoringsystem.R;

public class Download extends AppCompatActivity {

    String filename;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download);

         filename = getIntent().getStringExtra("filename");

        DownloadManager.Request request = new DownloadManager.Request(Uri.parse("http://depotlpgbanyuwangi.com/laporan/"+ filename));
        request.setTitle("File Download.");
        request.setDescription("Downloading...");
        //request.setMimeType("application/pdf");
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        String nameOfFile = "Laporan.pdf";
        //String nameOfFile = URLUtil.guessFileName(myHTTPUrl,null, MimeTypeMap.getFileExtensionFromUrl(myHTTPUrl));
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS,nameOfFile);
        DownloadManager manager = (DownloadManager)getSystemService(Context.DOWNLOAD_SERVICE);
        manager.enqueue(request);

        Intent intent = new Intent(Download.this,NotifikasiChecker.class);
        startActivity(intent);
    }
}
