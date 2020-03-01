package com.example.voteapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.voteapp.R;
import com.squareup.picasso.Picasso;

public class SingleElection extends AppCompatActivity {
    private TextView electionTitleView, electionDescriptionView, electionStatusView, electionStartDateView, electionEndDateView;
    private ImageView electionPosterView;
    private ImageButton voteCandidate1, voteCandidate2, voteCandidate3;
    private ProgressBar progressOfCandidate1, progressOfCandidate2, progressOfCandidate3;
    private TextView scoreOfCandidate1, scoreOfCandidate2, scoreOfCandidate3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_election);


        String electionTitle = getIntent().getExtras().getString("title");
        String electionDescription = getIntent().getExtras().getString("description");
        String electionPoster = getIntent().getExtras().getString("imageUrl");
        String electionId = getIntent().getExtras().getString("_id");
        String electionStartTime = getIntent().getExtras().getString("start_time");
        String electionEndTime = getIntent().getExtras().getString("end_time");
        String candidates = getIntent().getExtras().getString("candidates");


        electionTitleView = findViewById(R.id.electionTitleView);
        electionDescriptionView = findViewById(R.id.electionDescriptionView);
        electionPosterView = findViewById(R.id.electionPoster);
        electionStartDateView = findViewById(R.id.startDate);
        electionEndDateView = findViewById(R.id.endDate);

        electionTitleView.setText(electionTitle);
        electionDescriptionView.setText(electionDescription);
        Picasso.with(this)
                .load(electionPoster)
                .into(electionPosterView);
        electionStartDateView.setText(electionStartTime);
        electionEndDateView.setText(electionEndTime);

    }
}
