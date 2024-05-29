package com.vzincoder.api.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class RoomCreateDTO {

    @Positive(message = "The room number must be a positive value greater than zero")
    private int number;

    private int floor;

    @Positive(message = "The number of beds must be a positive value greater than zero")
    private int qty_bed;

    @Positive(message = "The number of bathrooms must be a positive value greater than zero")
    private int qty_bathroom;

    @Positive(message = "The price per day must be a positive value greater than zero")
    private double priceDay;
    
    @NotNull(message = "The description cannot be null")
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

}
