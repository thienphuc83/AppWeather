package com.example.weathertp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class NextDayActivity extends AppCompatActivity {

    TextView tvTenTP;
    Toolbar toolbar;
    ListView lv;
    Adapter adapter;
    ArrayList<Weather> weatherArrayList;

    String tenthanhpho = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_next_day);

        AnhXa();
        ActionToolBar();

        Intent intent = getIntent();
        String city = intent.getStringExtra("name");
//        Log.d("ketqua", "dữ liệu truyền qua: " + city);
        if (city.equals("")){
            tenthanhpho = "Saigon";
            Get7DaysData(tenthanhpho);
        }else {
            tenthanhpho = city;
            Get7DaysData(tenthanhpho);
        }
    }

    private void ActionToolBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void AnhXa() {
        toolbar = findViewById(R.id.toolbar);
        tvTenTP = findViewById(R.id.tvName);
        lv = findViewById(R.id.lv);
        weatherArrayList = new ArrayList<>();
        adapter = new Adapter(getApplicationContext(), weatherArrayList);
        lv.setAdapter(adapter);
    }

    private void Get7DaysData(String data) {
        String url = "http://api.openweathermap.org/data/2.5/forecast?q="+data+"&units=metric&cnt=7&appid=23af0066e05794930bcdb010517de4f5";
        RequestQueue requestQueue = Volley.newRequestQueue(NextDayActivity.this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
//                Log.d("ketqua", "Json: " + response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONObject jsonObjectCity = jsonObject.getJSONObject("city");
                    String namect = jsonObjectCity.getString("name");
                    tvTenTP.setText(namect);

                    JSONArray jsonArray = jsonObject.getJSONArray("list");
                    for (int i=0; i<jsonArray.length();i++){
                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                        String ngay = jsonObject1.getString("dt");

                        long l = Long.valueOf(ngay);
                        Date date = new Date(l*1000L);
                        SimpleDateFormat simpleDateFormat= new SimpleDateFormat("EEEE yyyy-MM-dd");
                        String Day = simpleDateFormat.format(date);

                        JSONObject jsonObjectTemp = jsonObject1.getJSONObject("main");
                        String max = jsonObjectTemp.getString("temp_max");
                        String min = jsonObjectTemp.getString("temp_min");

                        Double a = Double.valueOf(max);
                        Double b = Double.valueOf(min);
                        String Nhietdomax= String.valueOf(a.intValue());
                        String Nhietdomin= String.valueOf(b.intValue());

                        JSONArray jsonArrayweather = jsonObject1.getJSONArray("weather");
                        JSONObject jsonObjectweather= jsonArrayweather.getJSONObject(0);
                        String trangthai= jsonObjectweather.getString("description");
                        String icon= jsonObjectweather.getString("icon");
                        weatherArrayList.add(new Weather(Day,trangthai,icon,Nhietdomax,Nhietdomin));

                    }

                    adapter.notifyDataSetChanged();

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
}