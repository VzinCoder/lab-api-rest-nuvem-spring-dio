package com.vzincoder.api.dto;

import java.time.LocalDate;

import com.vzincoder.api.domain.ReserveStatus;

public class ReserveDTO {

    private int id;
    private ClientDTO client;
    private EmployeeReserveDTO employee;
    private RoomDTO room;
    private double price;
    private LocalDate dateCheckIn;
    private LocalDate dateCheckOut;
    private ReserveStatus status;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ClientDTO getClient() {
        return client;
    }

    public void setClient(ClientDTO client) {
        this.client = client;
    }

    public RoomDTO getRoom() {
        return room;
    }

    public void setRoom(RoomDTO room) {
        this.room = room;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public LocalDate getDateCheckIn() {
        return dateCheckIn;
    }

    public void setDateCheckIn(LocalDate dateCheckIn) {
        this.dateCheckIn = dateCheckIn;
    }

    public LocalDate getDateCheckOut() {
        return dateCheckOut;
    }

    public void setDateCheckOut(LocalDate dateCheckOut) {
        this.dateCheckOut = dateCheckOut;
    }

    public ReserveStatus getStatus() {
        return status;
    }

    public void setStatus(ReserveStatus status) {
        this.status = status;
    }

    public EmployeeReserveDTO getEmployee() {
        return employee;
    }

    public void setEmployee(EmployeeReserveDTO employee) {
        this.employee = employee;
    }

}
