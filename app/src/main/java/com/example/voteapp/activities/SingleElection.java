package com.example.voteapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.voteapp.R;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class SingleElection extends AppCompatActivity implements View.OnClickListener {
    private TextView electionTitleView, electionDescriptionView, electionStatusView, electionStartDateView, electionEndDateView;
    private ImageView electionPosterView;
    private TextView candidate1, candidate2, candidate3;
    private ImageButton voteCandidateBtn1, voteCandidateBtn2, voteCandidateBtn3;
    private ProgressBar progressBar1, progressBar2, progressBar3;
    private TextView scoreOfCandidate_1, scoreOfCandidate_2, scoreOfCandidate_3;

    private String CANDIDATE1_ID, CANDIDATE2_ID, CANDIDATE3_ID;
    private final String BASE_URL = "https://blooming-sea-25214.herokuapp.com/";
    private String REQUEST_URL = BASE_URL + "update_election?_id=";

    //    LinearLayout
    private LinearLayout layout1, layout2, layout3;

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


        REQUEST_URL += electionId + "&candidateId=";


        electionTitleView = findViewById(R.id.electionTitleView);
        electionDescriptionView = findViewById(R.id.electionDescriptionView);
        electionPosterView = findViewById(R.id.electionPoster);
        electionStartDateView = findViewById(R.id.startDate);
        electionEndDateView = findViewById(R.id.endDate);

//        Assigning values to the text view.
        candidate1 = findViewById(R.id.candidateName1);
        candidate2 = findViewById(R.id.candidateName2);
        candidate3 = findViewById(R.id.candidateName3);

//        Assigning values to the text view.
        scoreOfCandidate_1 = findViewById(R.id.scoreOfCandidate1);
        scoreOfCandidate_2 = findViewById(R.id.scoreOfCandidate2);
        scoreOfCandidate_3 = findViewById(R.id.scoreOfCandidate3);

//        Assigning values to the LinearLayout.
        layout1 = findViewById(R.id.candidate1Layout);
        layout2 = findViewById(R.id.candidate2Layout);
        layout3 = findViewById(R.id.candidate3Layout);

//        Assigning
        progressBar1 = findViewById(R.id.progressOfCandidate1);
        progressBar2 = findViewById(R.id.progressOfCandidate2);
        progressBar3 = findViewById(R.id.progressOfCandidate3);


//        Assigning values to the ImageButton
        voteCandidateBtn1 = findViewById(R.id.voteCandidate1);
        voteCandidateBtn2 = findViewById(R.id.voteCandidate2);
        voteCandidateBtn3 = findViewById(R.id.voteCandidate3);

//        Add OnClickListner to ImageButton
        voteCandidateBtn1.setOnClickListener(this);
        voteCandidateBtn2.setOnClickListener(this);
        voteCandidateBtn3.setOnClickListener(this);

        electionTitleView.setText(electionTitle);
        electionDescriptionView.setText(electionDescription);
        Picasso.with(this)
                .load(electionPoster)
                .into(electionPosterView);
        electionStartDateView.setText(electionStartTime);
        electionEndDateView.setText(electionEndTime);

//        converting candidates string data to JSON Object
        try {
            JSONArray candidatesArr = new JSONArray(candidates);
            for (int i = 0; i < candidatesArr.length(); i++) {
                JSONObject obj = candidatesArr.getJSONObject(i);

                int total = 0;
                if (candidatesArr.length() < 2) {
                    total = candidatesArr.getJSONObject(0).getInt("voteCount") + candidatesArr.getJSONObject(1).getInt("voteCount");
                } else if (candidatesArr.length() == 2) {
                    total = candidatesArr.getJSONObject(0).getInt("voteCount") + candidatesArr.getJSONObject(1).getInt("voteCount") + candidatesArr.getJSONObject(2).getInt("voteCount");
                }

                if (i == 0) {
                    candidate1.setText(obj.getString("name"));
                    scoreOfCandidate_1.setText(obj.getString("voteCount"));
                    int progress = obj.getInt("voteCount");
                    progressBar1.setProgress(progress);
                    progressBar1.setScaleY(4f);
                    layout1.setVisibility(View.VISIBLE);
                    CANDIDATE1_ID = obj.getString("_id");
                } else if (i == 1) {
                    candidate2.setText(obj.getString("name"));
                    scoreOfCandidate_2.setText(obj.getString("voteCount"));
                    int progress = obj.getInt("voteCount");
                    progressBar2.setProgress(progress);
                    progressBar2.setScaleY(4f);
                    layout2.setVisibility(View.VISIBLE);
                    CANDIDATE2_ID = obj.getString("_id");
                } else if (i == 2) {
                    candidate3.setText(obj.getString("name"));
                    scoreOfCandidate_3.setText(obj.getString("voteCount"));
                    int progress = obj.getInt("voteCount");
                    progressBar3.setProgress(progress);
                    progressBar3.setScaleY(4f);
                    layout3.setVisibility(View.VISIBLE);
                    CANDIDATE3_ID = obj.getString("_id");
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onClick(View view) {
        int currentValue = 0;
        switch (view.getId()) {
            case R.id.voteCandidate1:
                // Do something
                currentValue = Integer.parseInt(scoreOfCandidate_1.getText().toString());
                scoreOfCandidate_1.setText(Integer.toString(currentValue + 1));
                progressBar1.incrementProgressBy(1);
                disableAllButttons(voteCandidateBtn1, voteCandidateBtn2, voteCandidateBtn3);
                REQUEST_URL += CANDIDATE1_ID;
                break;
            case R.id.voteCandidate2:
                // Do something
                currentValue = Integer.parseInt(scoreOfCandidate_2.getText().toString());
                scoreOfCandidate_2.setText(Integer.toString(currentValue + 1));
                progressBar2.incrementProgressBy(1);
                disableAllButttons(voteCandidateBtn1, voteCandidateBtn2, voteCandidateBtn3);
                REQUEST_URL += CANDIDATE2_ID;
                break;
            case R.id.voteCandidate3:
                // Do something
                currentValue = Integer.parseInt(scoreOfCandidate_3.getText().toString());
                scoreOfCandidate_3.setText(Integer.toString(currentValue + 1));
                progressBar3.incrementProgressBy(1);
                disableAllButttons(voteCandidateBtn1, voteCandidateBtn2, voteCandidateBtn3);
                REQUEST_URL += CANDIDATE3_ID;
                break;
        }

//        Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
// Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, REQUEST_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
//                        textView.setText("Response is: "+ response.substring(0,500));
                        Toast.makeText(SingleElection.this, "Voting Successful", Toast.LENGTH_SHORT).show();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(SingleElection.this, "Error recording vote. Try Again", Toast.LENGTH_SHORT).show();
            }
        });

// Add the request to the RequestQueue.
        queue.add(stringRequest);
    }

    public void disableAllButttons(ImageButton button1, ImageButton button2, ImageButton button3){
        button1.setClickable(false);
        button2.setClickable(false);
        button3.setClickable(false);
    }

}
