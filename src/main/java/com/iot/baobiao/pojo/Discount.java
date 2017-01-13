package com.iot.baobiao.pojo;

import java.time.LocalDateTime;

/**
 * Created by jia on 17-1-11.
 */
public class Discount {
    private String name;
    private LocalDateTime beginTime;
    private LocalDateTime endTime;
    private String description;
    private int price;                  //活动价格
    private int num;                    //活动能参与人数

    public Discount() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(LocalDateTime beginTime) {
        this.beginTime = beginTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    @Override
    public String toString() {
        return "Discount{" +
                "name='" + name + '\'' +
                ", beginTime=" + beginTime +
                ", endTime=" + endTime +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", num=" + num +
                '}';
    }
}
