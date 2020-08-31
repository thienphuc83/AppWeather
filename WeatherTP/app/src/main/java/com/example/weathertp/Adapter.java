package com.example.weathertp;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class Adapter extends BaseAdapter {

    Context context;
    ArrayList<Weather> weatherArrayList;

    public Adapter(Context context, ArrayList<Weather> weatherArrayList) {
        this.context = context;
        this.weatherArrayList = weatherArrayList;
    }

    @Override
    public int getCount() {
        return weatherArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return weatherArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {

        LayoutInflater layoutInflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = layoutInflater.inflate(R.layout.dong_item,null);

        Weather weather = weatherArrayList.get(position);
        TextView tvNgay = view.findViewById(R.id.tvNgay);
        TextView tvTrangThai = view.findViewById(R.id.tvTrangThai);
        TextView tvMax = view.findViewById(R.id.tvMax);
        TextView tvMin = view.findViewById(R.id.tvMin);
        ImageView img = view.findViewById(R.id.img);

        tvNgay.setText(weather.getDay());
        tvTrangThai.setText(weather.getStatus());
        tvMax.setText(weather.getMax()+"°C");
        tvMin.setText(weather.getMin()+"°C");

        Picasso.get().load("http://openweathermap.org/img/wn/"+weather.getImage()+"@2x.png").into(img);

        return view;
    }
}
