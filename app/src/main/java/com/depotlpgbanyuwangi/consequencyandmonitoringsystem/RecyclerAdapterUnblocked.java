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

public class RecyclerAdapterUnblocked extends RecyclerView.Adapter<RecyclerAdapterUnblocked.RecyclerViewHolder> {

    private ArrayList<DataProviderUnblocked> dataProviderUnblockeds = new ArrayList<DataProviderUnblocked>();
    Context context;

    public RecyclerAdapterUnblocked(ArrayList<DataProviderUnblocked> arrayList, Context context){
        this.dataProviderUnblockeds = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_notif_blocked,parent,false);
        RecyclerViewHolder recyclerViewHolder = new RecyclerViewHolder(view,context,dataProviderUnblockeds);

        return recyclerViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {

        DataProviderUnblocked dataProviderUnblocked = dataProviderUnblockeds.get(position);
        holder.notifikasi.setText(Html.fromHtml(dataProviderUnblocked.getNotification()));

    }

    @Override
    public int getItemCount() {
        //System.out.println(dataProviderUnblockeds.size());
        return dataProviderUnblockeds.size();
    }

    static class RecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView notifikasi;
        ArrayList<DataProviderUnblocked> dataProviderUnblockeds = new ArrayList<DataProviderUnblocked>();
        Context context;

        public RecyclerViewHolder(View view,Context context,ArrayList<DataProviderUnblocked> dataProviderUnblockeds){
            super(view);
            view.setOnClickListener(this);
            this.dataProviderUnblockeds = dataProviderUnblockeds;
            this.context = context;
            notifikasi = (TextView) view.findViewById(R.id.notifikasi);
        }

        @Override
        public void onClick(View v) {

            int position = getAdapterPosition();
            DataProviderUnblocked dataProviderUnblocked = this.dataProviderUnblockeds.get(position);
            Intent intent = new Intent(this.context, TankSearch.class);
            //intent.putExtra("nomor_polisi", dataProviderUnblocked.getNomor_polisi());
            //intent.putExtra("nama_sppbe", dataProviderUnblocked.getNama_sppbe());
            //intent.putExtra("status_izin", dataProviderUnblocked.getStatus_izin());
            //intent.putExtra("deadline", dataProviderUnblocked.getDeadline());

            SharedPrefs.setDefaults("block_redirect","true",this.context);
            SharedPrefs.setDefaults("nomor_polisi",dataProviderUnblocked.getNomor_polisi(),this.context);


            this.context.startActivity(intent);

        }
    }

}
