package com.example.voteapp.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.voteapp.R;
import com.example.voteapp.adapters.Election_Adapter;
import com.example.voteapp.model.Election;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ElectionList extends AppCompatActivity{

    private static final String TAG = "ElectionList";

    private static final String URL = "https://blooming-sea-25214.herokuapp.com/get_election";
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private List<Election> elections;
    private ImageButton refreshButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadActivityData();
    }

    private void loadRecyclerViewData() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading data ...");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray array = jsonObject.getJSONArray("results");

                            for (int i = 0; i < array.length(); i++) {
                                JSONObject o = array.getJSONObject(i);

                                Log.d(TAG, "Object: " + o.getString("_id"));

                                Election item = new Election(
                                        o.getString("name").trim(),
                                        o.getString("description").trim(),
                                        o.getString("image_url").trim(),
                                        o.getString("_id").trim(),
                                        o.getString("start_time").trim(),
                                        o.getString("end_time").trim(),
                                        o.getString("candidates").trim()

                                );

                                Log.d(TAG, "Candidates: " + o.getString("candidates").trim()
                                );
                                elections.add(item);


                                adapter = new Election_Adapter(elections, getApplicationContext());
                                recyclerView.setAdapter(adapter);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
        );

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void loadActivityData(){
        setContentView(R.layout.recycler_list);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        elections = new ArrayList<>();
        refreshButton = findViewById(R.id.refreshBtn);

        refreshButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(ElectionList.this, "Button Clicked", Toast.LENGTH_SHORT).show();
                if (view.getId() == R.id.refreshBtn) {
                    //        Getting actual data from server.
                    loadActivityData();
                    Log.d(TAG, "onClick: Tring to get data from ");
                }
            }
        });
//        Getting actual data from server.
        loadRecyclerViewData();
        adapter = new Election_Adapter(elections, this);
        recyclerView.setAdapter(adapter);


    }
}
