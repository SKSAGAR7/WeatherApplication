package com.example.weatherapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;

public class MainActivity extends AppCompatActivity {

    Button button;
    EditText city;
    TextView result;
    TextView visibility;
    TextView timeZone;

    // https://api.openweathermap.org/data/2.5/weather?q=London,uk&appid=9fa6c6157115567f0a9ff18b171066f5
    String baseUrl = "https://api.openweathermap.org/data/2.5/weather?q=";
    String API = "&appid=9fa6c6157115567f0a9ff18b171066f5";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        button = findViewById(R.id.btn);
        city = findViewById(R.id.city);
        result = findViewById(R.id.result);
        visibility = findViewById(R.id.visibility);
        timeZone =findViewById(R.id.timeZone);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String myUrl = baseUrl + city.getText().toString() + API;
               Log.i("URL", "MYURL:" +myUrl);


                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, myUrl, null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject jsonObject) {
                                Log.i("JSON", "JSON: " + jsonObject);

                                try {
                                    String info = jsonObject.getString("weather");

                                    String visible = jsonObject.getString("visibility");
                                    Log.i("INFO", "Visibility: " + visible);
                                    visibility.setText(visible);

                                    String time = jsonObject.getString("timezone");
                                    Log.i("INFO", "TimeZone: " + time);
                                    timeZone.setText(time);

                                    Log.i("INFO", "INFO: " + info);
                                    JSONArray ar = new JSONArray(info);

                                    for (int i = 0; i < ar.length(); i++) {
                                        JSONObject parObj = ar.getJSONObject(i);

                                        String myWeather = parObj.getString("main");
                                        result.setText(myWeather);


//                                        for (int i = 2; i < ar.length(); i++) {
//                                        JSONObject parObj2 = ar.getJSONObject(i);
//                                        JSONObject parObj2 = ar.getJSONObject(3);
//                                        String myWeather2 = parObj2.getString("temp");
//                                        mint.setText(myWeather2);

                                        Log.i("ID", "ID: " + parObj.getString("id"));
                                        Log.i("MAIN", "MAIN: " + parObj.getString("main"));
                                    }

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }

                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError volleyError) {
                                Log.i("Error", "Some thing went Worng " + volleyError);
                            }
                        }
                );
                MySingleton.getInstance(MainActivity.this).addToRequestQue(jsonObjectRequest);
            }


        });
    }
}