package com.depotlpgbanyuwangi.consequencyandmonitoringsystem;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import com.example.asus.consquencyandmonitoringsystem.R;

import java.util.ArrayList;

public class RecyclerAdapterChecker extends RecyclerView.Adapter<RecyclerAdapterChecker.RecyclerViewHolder> {

    private ArrayList<DataProviderChecker> dataProviderCheckers = new ArrayList<DataProviderChecker>();
    Context context;

    public RecyclerAdapterChecker(ArrayList<DataProviderChecker> arrayList, Context context){
        this.dataProviderCheckers = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_notifikasi_checker,parent,false);
        RecyclerViewHolder recyclerViewHolder = new RecyclerViewHolder(view,context,dataProviderCheckers);

        return recyclerViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {

        DataProviderChecker dataProviderChecker = dataProviderCheckers.get(position);
        holder.notifikasi.setText(Html.fromHtml(dataProviderChecker.getNotification()));
        //holder.datetime.setText(dataProviderChecker.getWaktucek());

    }

    @Override
    public int getItemCount() {
        //System.out.println(dataProviderCheckers.size());
        return dataProviderCheckers.size();
    }

    static class RecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView notifikasi;
        ArrayList<DataProviderChecker> dataProviderCheckers = new ArrayList<DataProviderChecker>();
        Context context;

        public RecyclerViewHolder(View view,Context context,ArrayList<DataProviderChecker> dataProviderCheckers){
            super(view);
            view.setOnClickListener(this);
            this.dataProviderCheckers = dataProviderCheckers;
            this.context = context;
            notifikasi = (TextView) view.findViewById(R.id.notifikasi_checker);

        }

        @Override
        public void onClick(View v) {

            int position = getAdapterPosition();
            DataProviderChecker dataProviderChecker = this.dataProviderCheckers.get(position);
            Intent intent = new Intent(this.context,Download.class);
            intent.putExtra("ID_pengecekan",dataProviderChecker.getIDcek());
            intent.putExtra("filename", dataProviderChecker.getFile());
            this.context.startActivity(intent);

        }
    }

}
