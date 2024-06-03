package com.vzincoder.api.domain;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"number","floor"}))
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @NotNull
    private int number;
    @NotNull
    private int floor;
    @NotNull
    private int qty_bed;
    @NotNull
    private int qty_bathroom;
    @NotNull
    private double priceDay;
    
    @NotNull
    private String describe;

    @OneToMany(mappedBy = "room", cascade = CascadeType.REMOVE)
    private List<Reserve> reserve;

    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }

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
