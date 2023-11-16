package com.example.pratiqueandroid_examenfinal;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

;

public class Film_RecyclerViewAdapter extends RecyclerView.Adapter<Film_RecyclerViewAdapter.MyViewHolder> {
    Context context;
    ArrayList<Film> listefilms;
    Resources resources;
    public Film_RecyclerViewAdapter(Context context, ArrayList<Film> listefilms){
        this.context=context;
        this.listefilms = listefilms;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from((context));
        View view = inflater.inflate(R.layout.recycler_view_row,parent,false);
        return new MyViewHolder(view);
    }


    @SuppressLint("ResourceType")
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.vw_num.setText(listefilms.get(position).getNum() + "");
        holder.vw_titre.setText(listefilms.get(position).getTitre());
        holder.vw_categorie.setText(listefilms.get(position).getCodeCateg() + "");
        holder.vw_langue.setText(listefilms.get(position).getLangue());
        holder.vw_cote.setText(listefilms.get(position).getCote() + "");
        String pochette = listefilms.get(position).getPochette();

        resources = context.getResources();
        if (pochette.startsWith("content")) {
            try {
                holder.vw_image.setImageURI(Uri.parse(pochette));
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (!pochette.equals("Pochette")) {
            holder.vw_image.setImageResource(resources.getIdentifier(pochette, "drawable",
                    context.getPackageName()));
        } else {
            holder.vw_image.setImageResource(resources.getIdentifier("film", "drawable",
                    context.getPackageName()));
        }
    }

    @Override
    public int getItemCount() {
        return listefilms.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView vw_num,vw_titre,vw_categorie,vw_langue,vw_cote;
        ImageView vw_image;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            vw_num = itemView.findViewById(R.id.num);
            vw_titre = itemView.findViewById(R.id.titre);
            vw_categorie = itemView.findViewById(R.id.categorie);
            vw_langue = itemView.findViewById(R.id.langue);
            vw_cote = itemView.findViewById(R.id.cote);
            vw_image = itemView.findViewById(R.id.image);
        }
    }
}
