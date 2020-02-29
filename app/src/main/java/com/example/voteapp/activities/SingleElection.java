package com.example.voteapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.voteapp.R;
import com.squareup.picasso.Picasso;

public class SingleElection extends AppCompatActivity {
    private TextView electionTitleView, electionDescriptionView;
    private ImageView electionPosterView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_election);


        String electionTitle = getIntent().getExtras().getString("title");
        String electionDescription = getIntent().getExtras().getString("description");
        String electionPoster = getIntent().getExtras().getString("imageUrl");

        electionTitleView = findViewById(R.id.electionTitleView);
        electionDescriptionView = findViewById(R.id.electionDescriptionView);
        electionPosterView = findViewById(R.id.electionPoster);

        electionTitleView.setText(electionTitle);
        electionDescriptionView.setText(electionDescription);

        Picasso.with(this)
                .load(electionPoster)
                .into(electionPosterView);

    }
}
