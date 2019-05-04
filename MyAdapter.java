package com.example.newsasynctask;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;


public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ProgramHolder>  {
    List<Bean> newsData;
    Context context;
    public MyAdapter(List<Bean> newsData, Context context){
        this.newsData=newsData;
        this.context=context;

    }

    @NonNull
    @Override
    public ProgramHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.news_layout,viewGroup,false);
        return new ProgramHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProgramHolder programHolder, int i) {
        Bean bean=newsData.get(i);
        programHolder.newsTitle.setText(newsData.get(i).getNewsTitle());
        programHolder.newsName.setText(newsData.get(i).getNewsName());
        programHolder.newsAuthor.setText(newsData.get(i).getNewsAuthor());
        Picasso.get().load(newsData.get(i).getNewsImage()).into(programHolder.newsImage);


    }

    @Override
    public int getItemCount() {
        return newsData.size();
    }

    public class ProgramHolder extends RecyclerView.ViewHolder {
        TextView newsTitle, newsName, newsAuthor;
        ImageView newsImage;
        public ProgramHolder(@NonNull View itemView) {
            super(itemView);
            newsImage=itemView.findViewById(R.id.newsImage);
            newsTitle=itemView.findViewById(R.id.newsTitle);
            newsName=itemView.findViewById(R.id.newsName);
            newsAuthor=itemView.findViewById(R.id.newsAuthor);


        }
    }
}
