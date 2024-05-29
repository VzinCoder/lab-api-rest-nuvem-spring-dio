package com.vzincoder.api.dto;

public class RoomDTO {
    
    private int id;
    private int number;
    private int floor;
    private int qty_bed;
    private int qty_bathroom;
    private double priceDay;
    private String describe;

    

    public int getNumber() {
        return number;
    }
    public void setNumber(int number) {
        this.number = number;
    }
    public int getFloor() {
        return floor;
    }
    public void setFloor(int floor) {
        this.floor = floor;
    }
    public int getQty_bed() {
        return qty_bed;
    }
    public void setQty_bed(int qty_bed) {
        this.qty_bed = qty_bed;
    }
    public int getQty_bathroom() {
        return qty_bathroom;
    }
    public void setQty_bathroom(int qty_bathroom) {
        this.qty_bathroom = qty_bathroom;
    }
    public double getPriceDay() {
        return priceDay;
    }
    public void setPriceDay(double priceDay) {
        this.priceDay = priceDay;
    }
    public String getDescribe() {
        return describe;
    }
    public void setDescribe(String describe) {
        this.describe = describe;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }


    
}
