package com.kd.myapplication.Models;

import java.util.ArrayList;

public class HomeModel {

    String title, desc;
    ArrayList<String> type;

    public HomeModel(String title, String desc, ArrayList<String> type) {
        this.title = title;
        this.desc = desc;
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public String getDesc() {
        return desc;
    }

    public ArrayList<String> getType() {
        return type;
    }
}
