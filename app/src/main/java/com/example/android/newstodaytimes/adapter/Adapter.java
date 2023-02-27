package com.example.android.newstodaytimes.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.android.newstodaytimes.modalclass.ModelClass;
import com.example.android.newstodaytimes.R;
import com.example.android.newstodaytimes.webView;

import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    Context context ;
    ArrayList<ModelClass> modelClassArrayList ;

    public Adapter(Context context, ArrayList<ModelClass> modelClassArrayList) {
        this.context = context;
        this.modelClassArrayList = modelClassArrayList;
    }

    // method for filtering our recyclerview items.
    public void filterList(ArrayList<ModelClass> filterlist) {
        // below line is to add our filtered
        // list in our course array list.
        modelClassArrayList = filterlist;
        // below line is to notify our adapter
        // as change in recycler view data.
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_item , null , false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter.ViewHolder holder ,int position) {

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context , webView.class);
                intent.putExtra("url" , modelClassArrayList.get(position).getUrl());
                context.startActivity(intent);
            }
        });

        holder.mAuthor.setText(modelClassArrayList.get(position).getAuthor());
        holder.mContent.setText(modelClassArrayList.get(position).getDescription());
        holder.mHeading.setText(modelClassArrayList.get(position).getTitle());
        holder.mTime.setText("Published At:- "  + modelClassArrayList.get(position).getPublishedAt());
        Glide.with(context).load(modelClassArrayList.get(position).getUrlToImage()).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return modelClassArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView mHeading , mContent , mAuthor  , mTime ;
        CardView cardView ;
        ImageView imageView ;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mHeading = itemView.findViewById(R.id.mainHeading);
            mContent = itemView.findViewById(R.id.content);
            mAuthor = itemView.findViewById(R.id.author);
            mTime = itemView.findViewById(R.id.time);
            cardView = itemView.findViewById(R.id.cardView);
            imageView = itemView.findViewById(R.id.imageView);
        }
    }
}
