package com.example.loginpageofrastreo;

//package com.example.myapplication786;

import java.io.Serializable;

public class Details implements Serializable {
    public String name;
    public String val;

    public Details(String name,String val) {
        this.name = name;
        this.val = val;
    }

    public String getName() {
        return name;
    }

    public String getVal() {
        return val;
    }
}
