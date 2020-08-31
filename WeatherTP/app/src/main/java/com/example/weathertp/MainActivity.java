package com.example.weathertp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    EditText edtSearch;
    TextView tvCityName, tvNation, tvTemperature, tvStatus, tvHumidity, tvCloud, tvWind, tvTime;
    Button btnNextDay;
    ImageView imgSearch, imgIcon;
    String City = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
////      hide status bar
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        AnhXa();
        GetCurrentWeatherData("Saigon");
        imgSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String city= edtSearch.getText().toString().trim();
                if (city.equals("")){
                    City = "Saigon";
                    GetCurrentWeatherData(City);
                }else {
                    City = city;
                    GetCurrentWeatherData(City);
                }

            }
        });

        btnNextDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String city= edtSearch.getText().toString().trim();
                Intent intent= new Intent(MainActivity.this, NextDayActivity.class);
                intent.putExtra("name", city);
                startActivity(intent);

            }
        });
    }

    private void GetCurrentWeatherData(String data){
        RequestQueue requestQueue= Volley.newRequestQueue(MainActivity.this);
        String url = "https://api.openweathermap.org/data/2.5/weather?q="+data+"&units=metric&appid=23af0066e05794930bcdb010517de4f5";

        StringRequest stringRequest= new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
//                Toast.makeText(MainActivity.this, response.toString(), Toast.LENGTH_SHORT).show();
                try {
                    JSONObject jsonObject=new JSONObject(response);

                    String day = jsonObject.getString("dt");
                    String namecity= jsonObject.getString("name");
                    tvCityName.setText(namecity);

                    long l = Long.valueOf(day);
                    Date date = new Date(l*1000L);
                    SimpleDateFormat simpleDateFormat= new SimpleDateFormat("EEEE yyyy-MM-dd HH-mm-ss");
                    String Day = simpleDateFormat.format(date);
                    tvTime.setText(Day);

                    JSONArray jsonArrayWeather=jsonObject.getJSONArray("weather");
                    JSONObject jsonObjectWeather = jsonArrayWeather.getJSONObject(0);
                    String status = jsonObjectWeather.getString("main");
                    String icon = jsonObjectWeather.getString("icon");

                    Picasso.get().load("http://openweathermap.org/img/wn/"+icon+"@2x.png").into(imgIcon);
                    tvStatus.setText(status);

                    JSONObject jsonObjectMain = jsonObject.getJSONObject("main");
                    String nhietdo = jsonObjectMain.getString("temp");
                    String doam= jsonObjectMain.getString("humidity");

                    Double a = Double.valueOf(nhietdo);
                    String Nhietdo= String.valueOf(a.intValue());
                    tvTemperature.setText(Nhietdo);
                    tvHumidity.setText(doam + " %");

                    JSONObject jsonObjectWind = jsonObject.getJSONObject("wind");
                    String gio = jsonObjectWind.getString("speed");
                    tvWind.setText(gio+ " m/s");

                    JSONObject jsonObjectCloud = jsonObject.getJSONObject("clouds");
                    String may= jsonObjectCloud.getString("all");
                    tvCloud.setText(may + " %");

                    JSONObject jsonObjectSys = jsonObject.getJSONObject("sys");
                    String country = jsonObjectSys.getString("country");
                    tvNation.setText(country);



                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(stringRequest);
    }

    private void AnhXa() {
        edtSearch = findViewById(R.id.edtSearch);
        tvCityName = findViewById(R.id.tvCityName);
        tvNation = findViewById(R.id.tvNation);
        tvTemperature = findViewById(R.id.tvTemperature);
        tvStatus = findViewById(R.id.tvStatus);
        tvHumidity = findViewById(R.id.tvHumidity);
        tvCloud = findViewById(R.id.tvCloud);
        tvWind = findViewById(R.id.tvWind);
        tvTime = findViewById(R.id.tvTime);
        btnNextDay = findViewById(R.id.btnDay);
        imgIcon = findViewById(R.id.imgIcon);
        imgSearch = findViewById(R.id.imgSearch);
    }
}