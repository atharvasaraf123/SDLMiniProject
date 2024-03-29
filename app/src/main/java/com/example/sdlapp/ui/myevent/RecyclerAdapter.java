package com.example.sdlapp.ui.myevent;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sdlapp.Event;
import com.example.sdlapp.EventDetail;
import com.example.sdlapp.R;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.gson.JsonObject;

import org.w3c.dom.Text;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

import static androidx.core.content.ContextCompat.startActivity;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.RecyclerViewHolder>  implements Serializable{

    QuerySnapshot data;


    public RecyclerAdapter(QuerySnapshot data) {
        this.data = data;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());
        View view=layoutInflater.inflate(R.layout.list_layout_item,parent,false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        String titl=data.getDocuments().get(position).getString("title");
            String des=data.getDocuments().get(position).getString("desc");
            String dat=data.getDocuments().get(position).getString("date");
        holder.title.setText(titl);
        holder.desc.setText(des);
        holder.date.setText(dat);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("test",titl);
                Intent intent=new Intent(view.getContext(), EventDetail.class);
                Event event=new Event(titl,des,dat,data.getDocuments().get(position).getString("time"),data.getDocuments().get(position).getString("venue"), (HashMap<String, Boolean>) data.getDocuments().get(position).getData().get("dept"),data.getDocuments().get(position).getString("uid"),data.getDocuments().get(position).getString("eventID"), (Long) data.getDocuments().get(position).get("visitorCount"));
                intent.putExtra("mapp", (Serializable) event);
                startActivity(view.getContext(),intent, Bundle.EMPTY);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder  implements Serializable{


        TextView title;
        TextView desc;
        TextView date;
        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            title=itemView.findViewById(R.id.textTitle);
            desc=itemView.findViewById(R.id.textDesc);
            date=itemView.findViewById(R.id.date_event);
        }
    }
}
