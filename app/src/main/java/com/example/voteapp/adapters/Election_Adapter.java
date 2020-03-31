package com.example.voteapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.voteapp.R;
import com.example.voteapp.activities.SingleElection;
import com.example.voteapp.model.Election;
import com.squareup.picasso.Picasso;

import java.util.List;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;


public class Election_Adapter extends RecyclerView.Adapter<Election_Adapter.ViewHolder> {

    private List<Election> elections;
    private Context context;

    public Election_Adapter(List<Election> elections, Context context) {
        this.elections = elections;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Election election = elections.get(position);
        holder.textViewHead.setText(election.getElectionName());
        holder.textViewDesc.setText(election.getElectionDescription());

        Picasso.with(context)
                .load(election.getImageUrl())
                .into(holder.imageView);

        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "Vote intent for: " + election.getElectionId(), Toast.LENGTH_SHORT).show();
                Intent i = new Intent(context, SingleElection.class);
                i.putExtra("title", election.getElectionName());
                i.putExtra("description", election.getElectionDescription());
                i.putExtra("imageUrl", election.getImageUrl());
                i.putExtra("_id", election.getElectionId());
                i.putExtra("start_time", election.getElectionStartDate());
                i.putExtra("end_time", election.getElectionEndDate());
                i.putExtra("candidates", election.getElectionCandidates());
                i.putExtra("status", election.getElectionStatus());
                i.setFlags(FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return elections.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView textViewHead;
        public TextView textViewDesc;
        public ImageView imageView;
        public LinearLayout linearLayout;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewHead = itemView.findViewById(R.id.textViewHead);
            textViewDesc = itemView.findViewById(R.id.textViewDesc);
            imageView = itemView.findViewById(R.id.electionPoster);
            linearLayout = itemView.findViewById(R.id.linearLayout);
        }
    }
}
