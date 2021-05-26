package com.example.app2;

import com.google.firebase.Timestamp;

import java.util.Date;

public class Shopping {

    private String shopNeed;

    private String firstName;

    private Timestamp timeAdded;

    private int count;

    public Shopping(String shopNeed, String firstName, Timestamp timeAdded, int count) {
        this.shopNeed = shopNeed;
        this.firstName = firstName;
        this.timeAdded = timeAdded;
        this.count = count;
    }

    @Override
    public String toString() {
        return "Shopping{" +
                "shopNeed='" + shopNeed + '\'' +
                ", firstName='" + firstName + '\'' +
                ", timeAdded=" + timeAdded +
                ", count=" + count +
                '}';
    }

    public Shopping() {
    }

    public String getShopNeed() {
        return shopNeed;
    }

    public void setShopNeed(String shopNeed) {
        this.shopNeed = shopNeed;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }


    public Timestamp getTimeAdded() {
        return timeAdded;
    }

    public void setTimeAdded(Timestamp timeAdded) {
        this.timeAdded = timeAdded;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
