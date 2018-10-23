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

public class RecyclerAdapterHistory extends RecyclerView.Adapter<RecyclerAdapterHistory.RecyclerViewHolder> {

    private ArrayList<DataProviderHistory> dataProviderHistorys = new ArrayList<DataProviderHistory>();
    Context context;

    public RecyclerAdapterHistory(ArrayList<DataProviderHistory> arrayList, Context context){
        this.dataProviderHistorys = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_history,parent,false);
        RecyclerViewHolder recyclerViewHolder = new RecyclerViewHolder(view,context,dataProviderHistorys);

        return recyclerViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {

        DataProviderHistory dataProviderHistory = dataProviderHistorys.get(position);
        holder.notifikasi.setText(Html.fromHtml(dataProviderHistory.getNotification()));
        holder.waktucek.setText(dataProviderHistory.getWaktucek());
        holder.checkercek.setText(dataProviderHistory.getCheckername());

    }

    @Override
    public int getItemCount() {
        //System.out.println(dataProviderHistorys.size());
        return dataProviderHistorys.size();
    }

    static class RecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView notifikasi, waktucek, checkercek;
        ArrayList<DataProviderHistory> dataProviderHistorys = new ArrayList<DataProviderHistory>();
        Context context;

        public RecyclerViewHolder(View view,Context context,ArrayList<DataProviderHistory> dataProviderHistorys){
            super(view);
            view.setOnClickListener(this);
            this.dataProviderHistorys = dataProviderHistorys;
            this.context = context;
            notifikasi = (TextView) view.findViewById(R.id.notifikasi);
            waktucek = (TextView) view.findViewById(R.id.waktucek);
            checkercek = (TextView) view.findViewById(R.id.checkercek);

        }

        @Override
        public void onClick(View v) {

            int position = getAdapterPosition();
            DataProviderHistory dataProviderHistory = this.dataProviderHistorys.get(position);
            Intent intent = new Intent(this.context, HistoryPage.class);
            intent.putExtra("ID_pengecekan",dataProviderHistory.getIDcek());
            intent.putExtra("nopol",dataProviderHistory.getNopolcek());
            this.context.startActivity(intent);

        }
    }

}
