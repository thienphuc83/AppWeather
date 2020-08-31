package com.example.weathertp;

public class Weather {
    private String Day;
    private String Status;
    private String Image;
    private String Max;
    private String Min;

    public Weather(String day, String status, String image, String max, String min) {
        Day = day;
        Status = status;
        Image = image;
        Max = max;
        Min = min;
    }

    public String getDay() {
        return Day;
    }

    public void setDay(String day) {
        Day = day;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getMax() {
        return Max;
    }

    public void setMax(String max) {
        Max = max;
    }

    public String getMin() {
        return Min;
    }

    public void setMin(String min) {
        Min = min;
    }
}
