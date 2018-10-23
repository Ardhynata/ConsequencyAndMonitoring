package com.depotlpgbanyuwangi.consequencyandmonitoringsystem;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.asus.consquencyandmonitoringsystem.R;

import java.util.ArrayList;

public class RecyclerAdapterIzinUnblock extends RecyclerView.Adapter<RecyclerAdapterIzinUnblock.RecyclerViewHolder> {

    private ArrayList<DataProviderIzinUnblock> dataProviderIzinUnblocks = new ArrayList<DataProviderIzinUnblock>();
    Context context;

    public RecyclerAdapterIzinUnblock(ArrayList<DataProviderIzinUnblock> arrayList, Context context){
        this.dataProviderIzinUnblocks = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_notif_izin_unblock,parent,false);
        RecyclerViewHolder recyclerViewHolder = new RecyclerViewHolder(view,context,dataProviderIzinUnblocks);

        return recyclerViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {

        DataProviderIzinUnblock dataProviderIzinUnblock = dataProviderIzinUnblocks.get(position);
        holder.notifikasi.setText(Html.fromHtml(dataProviderIzinUnblock.getNotification()));

    }

    @Override
    public int getItemCount() {
        //System.out.println(dataProviderIzinUnblocks.size());
        return dataProviderIzinUnblocks.size();
    }

    static class RecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView notifikasi;
        ArrayList<DataProviderIzinUnblock> dataProviderIzinUnblocks = new ArrayList<DataProviderIzinUnblock>();
        Context context;

        public RecyclerViewHolder(View view,Context context,ArrayList<DataProviderIzinUnblock> dataProviderIzinUnblocks){
            super(view);
            view.setOnClickListener(this);
            this.dataProviderIzinUnblocks = dataProviderIzinUnblocks;
            this.context = context;
            notifikasi = (TextView) view.findViewById(R.id.notifikasi);
        }

        @Override
        public void onClick(View v) {

            int position = getAdapterPosition();
            DataProviderIzinUnblock dataProviderIzinUnblock = this.dataProviderIzinUnblocks.get(position);
            Intent intent = new Intent(this.context, Unblock.class);
            intent.putExtra("nomor_polisi", dataProviderIzinUnblock.getNomor_polisi());
            intent.putExtra("nama_sppbe", dataProviderIzinUnblock.getNama_sppbe());
            intent.putExtra("status_izin", dataProviderIzinUnblock.getStatus_izin());
            intent.putExtra("deadline", dataProviderIzinUnblock.getDeadline());
            this.context.startActivity(intent);

        }
    }

}
