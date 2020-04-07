package com.http.demo.observer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.util.Observable;
import java.util.Observer;

public class CctvDisplay implements Observer {

    private static final Logger log = LoggerFactory.getLogger(CctvDisplay.class);

    private WeathData weathData;

    // 地点
    private String address;

    // 时间
    private LocalDate date;

    // 风力
    private Integer wind;

    // 热度
    private Integer heat;

    //
    public CctvDisplay(WeathData weathData) {
        this.weathData = weathData;
        this.weathData.addObserver(this);
    }
    public static void main(String[] args) {
        WeathData weathData = new WeathData();
        CctvDisplay cctvDisplay = new CctvDisplay(weathData);

        weathData.setAddress("中国");

        weathData.setHeat(10);

        weathData.setWind(20);
    }

    @Override
    public void update(Observable o, Object arg) {
//        weathData = o;
        log.info("Observable:{},Object:{}", o, arg);
        weathData = (WeathData)o;
        address = weathData.getAddress();
        date = weathData.getDate();
        wind = weathData.getWind();
        heat = weathData.getHeat();
        display();
    }

    public void display() {
        System.out.println(toString());
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("CctvDisplay{");
        sb.append("weathData=").append(weathData);
        sb.append(", address='").append(address).append('\'');
        sb.append(", date=").append(date);
        sb.append(", wind=").append(wind);
        sb.append(", heat=").append(heat);
        sb.append('}');
        return sb.toString();
    }
}
