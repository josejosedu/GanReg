package com.example.a6000832.ganreg.Auxiliares;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.a6000832.ganreg.Modelos.Noticia;
import com.example.a6000832.ganreg.R;

import java.util.List;

/**
 * Created by Jose Eduardo Martin.
 */

//Adaptador para el lector rss, esto es lo que completará cada campo del recyclerview con cada noticia, reutilizando los ya creados para otras tareas

public class RssFeedListAdapter extends RecyclerView.Adapter<RssFeedListAdapter.FeedModelViewHolder> implements View.OnClickListener{

    private List<Noticia> mRssFeedModels;
    private View.OnClickListener listener;

    public void setOnClickListener(View.OnClickListener listener) {
        this.listener = listener;
    }

    @Override
    public void onClick(View view) {
        if(listener != null)
            listener.onClick(view);
    }

    public static class FeedModelViewHolder extends RecyclerView.ViewHolder {
/*
*
*
*
*
*
*
*
*
*
*
*
*
 */
    }

    public RssFeedListAdapter(List<Noticia> rssFeedModels) {
        mRssFeedModels = rssFeedModels;
    }

    @Override
    public FeedModelViewHolder onCreateViewHolder(ViewGroup parent, int type) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.rss_item_fila, parent, false);
        FeedModelViewHolder holder = new FeedModelViewHolder(v);
        v.setOnClickListener(this);
        return holder;
    }

    @Override
    public void onBindViewHolder(FeedModelViewHolder holder, int position) {
/*
*
*
*
*
*
*
*
*
*
*
*
*
 */
    }

    @Override
    public int getItemCount() {
        return mRssFeedModels.size();
    }
}