package com.http.demo.observer;

import javax.swing.*;
import java.time.LocalDate;
import java.util.Observable;

public class WeathData extends Observable {

    // 地点
    private String address;

    // 时间
    private LocalDate date;

    // 风力
    private Integer wind;

    // 热度
    private Integer heat;

    public void setAddress(String address) {
        this.address = address;
        this.setChanged();
        super.notifyObservers();
    }

    public void setDate(LocalDate date) {
        this.date = date;
        this.setChanged();
        super.notifyObservers();
    }

    public void setWind(Integer wind) {
        this.wind = wind;
        this.setChanged();
        super.notifyObservers();
    }

    public void setHeat(Integer heat) {
        this.heat = heat;
        this.setChanged();
        super.notifyObservers();
    }

    public String getAddress() {
        return address;
    }

    public LocalDate getDate() {
        return date;
    }

    public Integer getWind() {
        return wind;
    }

    public Integer getHeat() {
        return heat;
    }
}
