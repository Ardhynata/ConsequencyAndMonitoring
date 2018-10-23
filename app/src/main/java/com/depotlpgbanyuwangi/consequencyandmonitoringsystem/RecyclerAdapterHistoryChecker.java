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

public class RecyclerAdapterHistoryChecker extends RecyclerView.Adapter<RecyclerAdapterHistoryChecker.RecyclerViewHolder> {

    private ArrayList<DataProviderHistoryChecker> dataProviderHistoryCheckers = new ArrayList<DataProviderHistoryChecker>();
    Context context;

    public RecyclerAdapterHistoryChecker(ArrayList<DataProviderHistoryChecker> arrayList, Context context){
        this.dataProviderHistoryCheckers = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_history_checker,parent,false);
        RecyclerViewHolder recyclerViewHolder = new RecyclerViewHolder(view,context,dataProviderHistoryCheckers);

        return recyclerViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {

        DataProviderHistoryChecker dataProviderHistoryChecker = dataProviderHistoryCheckers.get(position);
        holder.notifikasi.setText(Html.fromHtml(dataProviderHistoryChecker.getNotification()));
        holder.waktucek.setText(dataProviderHistoryChecker.getWaktucek());
        holder.checkercek.setText(dataProviderHistoryChecker.getCheckername());

    }

    @Override
    public int getItemCount() {
        //System.out.println(dataProviderHistoryCheckers.size());
        return dataProviderHistoryCheckers.size();
    }

    static class RecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView notifikasi, waktucek, checkercek;
        ArrayList<DataProviderHistoryChecker> dataProviderHistoryCheckers = new ArrayList<DataProviderHistoryChecker>();
        Context context;

        public RecyclerViewHolder(View view,Context context,ArrayList<DataProviderHistoryChecker> dataProviderHistoryCheckers){
            super(view);
            view.setOnClickListener(this);
            this.dataProviderHistoryCheckers = dataProviderHistoryCheckers;
            this.context = context;
            notifikasi = (TextView) view.findViewById(R.id.notifikasi);
            waktucek = (TextView) view.findViewById(R.id.waktucek);
            checkercek = (TextView) view.findViewById(R.id.checkercek);

        }

        @Override
        public void onClick(View v) {

            int position = getAdapterPosition();
            DataProviderHistoryChecker dataProviderHistoryChecker = this.dataProviderHistoryCheckers.get(position);
            Intent intent = new Intent(this.context, HistoryPageChecker.class);
            intent.putExtra("ID_pengecekan",dataProviderHistoryChecker.getIDcek());
            intent.putExtra("nopol",dataProviderHistoryChecker.getNopolcek());
            this.context.startActivity(intent);

        }
    }

}
