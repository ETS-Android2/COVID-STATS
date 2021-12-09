package com.example.covidstats;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    private TextView worldCases, worldRecovered, worldDeaths, worldActive;
    private static int LOCATIONS_CODE = 3;
    private Button searchButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // To Hide App Title Bar
        getSupportActionBar().hide();

        worldCases = findViewById(R.id.worldNew);
        worldRecovered = findViewById(R.id.worldRecovered);
        worldDeaths = findViewById(R.id.worldDeaths);
        worldActive = findViewById(R.id.worldActive);
        searchButton = findViewById(R.id.searchButton);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),LocationsFinder.class);
                startActivityForResult(intent,LOCATIONS_CODE);
            }
        });

        String url = "https://corona.lmao.ninja/v2/all";
        RequestQueue queue = Volley.newRequestQueue(MainActivity.this);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    String cases = response.getString("todayCases");
                    String recovered = response.getString("todayRecovered");
                    String deaths = response.getString("todayDeaths");
                    String active = response.getString("active");

                    worldCases.setText(cases);
                    worldRecovered.setText(recovered);
                    worldDeaths.setText(deaths);
                    worldActive.setText(active);


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
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == LOCATIONS_CODE){
            int cases = data.getIntExtra("todayCases",0);
            int recovered = data.getIntExtra("todayRecovered",0);
            int deaths = data.getIntExtra("todayDeaths",0);
            int active = data.getIntExtra("active",0);

            worldCases.setText(String.valueOf(cases));
            worldRecovered.setText(String.valueOf(recovered));
            worldDeaths.setText(String.valueOf(deaths));
            worldActive.setText(String.valueOf(active));
        }
    }


}