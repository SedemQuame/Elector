package com.example.voteapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
    private static final String TAG = "SingleElection";
    private ImageButton voteCandidateBtn1, voteCandidateBtn2, voteCandidateBtn3;
    private String CANDIDATE1_ID, CANDIDATE2_ID, CANDIDATE3_ID;
    private final String BASE_URL = "https://blooming-sea-25214.herokuapp.com/";
    private String REQUEST_URL = BASE_URL + "update_election?_id=";

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
        String electionStatus = getIntent().getExtras().getString("status");


        REQUEST_URL += electionId + "&candidateId=";


        TextView electionTitleView = findViewById(R.id.electionTitleView);
        TextView electionDescriptionView = findViewById(R.id.electionDescriptionView);
        ImageView electionPosterView = findViewById(R.id.electionPoster);
        TextView electionStartDateView = findViewById(R.id.startDate);
        TextView electionEndDateView = findViewById(R.id.endDate);
        TextView electionStatusView = findViewById(R.id.status);

        electionStatusView.setText(electionStatus);


//        Referencing candidates image views.
        ImageView candidateImg1 = findViewById(R.id.candidateImg1);
        ImageView candidateImg2 = findViewById(R.id.candidateImg2);
        ImageView candidateImg3 = findViewById(R.id.candidateImg3);

//        Assigning values to the text view.
        TextView candidate1 = findViewById(R.id.candidateName1);
        TextView candidate2 = findViewById(R.id.candidateName2);
        TextView candidate3 = findViewById(R.id.candidateName3);

//        Assigning values to the text view.
        //    private ProgressBar progressBar1, progressBar2, progressBar3;
        TextView scoreOfCandidate_1 = findViewById(R.id.scoreOfCandidate1);
        TextView scoreOfCandidate_2 = findViewById(R.id.scoreOfCandidate2);
        TextView scoreOfCandidate_3 = findViewById(R.id.scoreOfCandidate3);

//        Assigning values to the LinearLayout.
        //    LinearLayout
        LinearLayout layout1 = findViewById(R.id.candidate1Layout);
        LinearLayout layout2 = findViewById(R.id.candidate2Layout);
        LinearLayout layout3 = findViewById(R.id.candidate3Layout);


//        Assigning values to the ImageButton
        voteCandidateBtn1 = findViewById(R.id.voteCandidate1);
        voteCandidateBtn2 = findViewById(R.id.voteCandidate2);
        voteCandidateBtn3 = findViewById(R.id.voteCandidate3);

//        Add OnClickListener to ImageButton
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

                if (i == 0) {
                    candidate1.setText(obj.getString("name"));
                    scoreOfCandidate_1.setText(String.format("Current vote count: %s", obj.getString("voteCount")));
                    Toast.makeText(this, (obj.getString("candidateImg")), Toast.LENGTH_SHORT).show();
                    Picasso.with(this)
                            .load(obj.getString("candidateImg"))
                            .into(candidateImg1);
//                    layout1.setVisibility(View.VISIBLE);
                    CANDIDATE1_ID = obj.getString("_id");
                } else if (i == 1) {
                    candidate2.setText(obj.getString("name"));
                    scoreOfCandidate_2.setText(String.format("Current vote count: %s", obj.getString("voteCount")));
                    Picasso.with(this)
                            .load(obj.getString("candidateImg"))
                            .into(candidateImg2);
//                    layout2.setVisibility(View.VISIBLE);
                    CANDIDATE2_ID = obj.getString("_id");
                } else if (i == 2) {
                    candidate3.setText(obj.getString("name"));
                    scoreOfCandidate_3.setText(String.format("Current vote count: %s", obj.getString("voteCount")));
                    Picasso.with(this)
                            .load(obj.getString("candidateImg"))
                            .into(candidateImg3);
                    layout3.setVisibility(View.VISIBLE);
                    CANDIDATE3_ID = obj.getString("_id");
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


        Log.d(TAG, "Election Status: " + electionStatus);
        if(electionStatus.equals("Ended")){
            disableAllButtons(voteCandidateBtn1, voteCandidateBtn2, voteCandidateBtn3);
//            findViewById(R.id.ballot).setVisibility(View.GONE);
        }

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.voteCandidate1:
                disableAllButtons(voteCandidateBtn1, voteCandidateBtn2, voteCandidateBtn3);
                REQUEST_URL += CANDIDATE1_ID;
                break;
            case R.id.voteCandidate2:
                disableAllButtons(voteCandidateBtn1, voteCandidateBtn2, voteCandidateBtn3);
                REQUEST_URL += CANDIDATE2_ID;
                break;
            case R.id.voteCandidate3:
                disableAllButtons(voteCandidateBtn1, voteCandidateBtn2, voteCandidateBtn3);
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

    public void disableAllButtons(ImageButton button1, ImageButton button2, ImageButton button3){
        button1.setClickable(false);
        button2.setClickable(false);
        button3.setClickable(false);
    }

}
