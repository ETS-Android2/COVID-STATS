package com.example.covidstats;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

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


}