package com.example.covidstats;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class LocationsFinder extends AppCompatActivity {
    private EditText countrySearchET;

    private Button submitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_locations_finder);
        // To Hide App Title Bar
        getSupportActionBar().hide();
        countrySearchET = findViewById(R.id.countrySearchET);
        submitButton = findViewById(R.id.submitButton);

        submitButton.setOnClickListener(v -> {
            String url = "https://api.covid19api.com/summary";
            RequestQueue queue = Volley.newRequestQueue(LocationsFinder.this);

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                @Override
                public void onResponse(JSONObject res) {
                    try {
                        JSONArray stats = res.getJSONArray("Countries");

                        for(int i = 0 ; i < stats.length(); i++){
                            JSONObject jsonObject = stats.getJSONObject(i);
                            if(jsonObject.getString("Country").toLowerCase().equals(countrySearchET.getText().toString().trim().toLowerCase())){
                                int cases = jsonObject.getInt("NewConfirmed");
                                int recovered = jsonObject.getInt("NewRecovered");
                                int deaths = jsonObject.getInt("NewDeaths");
                                int active = jsonObject.getInt("TotalConfirmed");
                                System.out.println(deaths + cases + recovered);
                                Intent intent = new Intent();
                                intent.putExtra("active",active);
                                intent.putExtra("todayRecovered",recovered);
                                intent.putExtra("todayDeaths",deaths);
                                intent.putExtra("todayCases",cases);
                                setResult(Activity.RESULT_OK, intent);
                                finish();
                                return;
                            }
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("API-Error", "Failed to get data.");
                }
            });
            queue.add(jsonObjectRequest);
        });

    }
}