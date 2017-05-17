package com.tyler.object;

import java.io.Serializable;

/**
 * Created by tyler on 2017/5/17.
 */
public class Car implements Serializable{
    private String name;
    private String price;

    public Car(){}

    public Car(String name, String price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
