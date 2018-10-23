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

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.RecyclerViewHolder> {

    private ArrayList<DataProvider> dataProviders = new ArrayList<DataProvider>();
    Context context;

    public RecyclerAdapter(ArrayList<DataProvider> arrayList, Context context){
        this.dataProviders = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_notifikasi,parent,false);
        RecyclerViewHolder recyclerViewHolder = new RecyclerViewHolder(view, context, dataProviders);

        return recyclerViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {

        DataProvider dataProvider = dataProviders.get(position);
        holder.notifikasi.setText(Html.fromHtml(dataProvider.getNotification()));
        holder.datetime.setText(dataProvider.getWaktucek());

    }

    @Override
    public int getItemCount() {
        return dataProviders.size();
    }

    static class RecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView notifikasi, datetime;
        ArrayList<DataProvider> dataProviders = new ArrayList<DataProvider>();
        Context context;

        public RecyclerViewHolder(View view,Context context,ArrayList<DataProvider> dataProviders){
            super(view);
            view.setOnClickListener(this);
            this.dataProviders = dataProviders;
            this.context = context;
            notifikasi = (TextView) view.findViewById(R.id.notifikasi);
            datetime = (TextView) view.findViewById(R.id.tanggal);

        }

        @Override
        public void onClick(View v) {

            int position = getAdapterPosition();
            DataProvider dataProvider = this.dataProviders.get(position);
            Intent intent = new Intent(this.context,Laporan.class);
            intent.putExtra("ID_pengecekan",dataProvider.getIDcek());
            intent.putExtra("nopol",dataProvider.getNopolcek());
            this.context.startActivity(intent);

        }
    }

}
