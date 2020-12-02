package com.example.sdlapp.ui.browseclub;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sdlapp.R;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.Serializable;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.RecyclerViewHolder> {

    QuerySnapshot data;

    public RecyclerAdapter(QuerySnapshot data) {
        this.data = data;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());
        View view=layoutInflater.inflate(R.layout.browseclubs,parent,false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        String titl=data.getDocuments().get(position).getString("clubName");
        String des=data.getDocuments().get(position).getString("clubDesc");
        holder.title.setText(titl);
        holder.desc.setText(des);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class RecyclerViewHolder extends RecyclerView.ViewHolder  implements Serializable {


        TextView title;
        TextView desc;
        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            title=itemView.findViewById(R.id.textTitle);
            desc=itemView.findViewById(R.id.textDesc);
            }
    }
}
