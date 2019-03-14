package com.example.jws.bzapp;

import android.location.Address;

public class BookInfo {
    String Lat, Lng, area, addr, time;

    BookInfo(String Lat, String Lng, String area, String addr, String time) {
        this.Lat = Lat;
        this.Lng = Lng;
        this.area = area;
        this.addr = addr;
        this.time = time;
    }

    public String getLat() {
        return Lat;
    }

    public String getLng() {
        return Lng;
    }

    public String getArea() {
        return area;
    }

    public String getAddr() {
        return addr;
    }

    public String getTime() {
        return time;
    }

}