package com.vzincoder.api.dto;


public class EmployeeDTO {

    private int id;
    private String email;
    private String name;
    private String cpf;
    private double salary;
    private double bonus;
    private double total;
    private int qtyReserve;


    public double getBonus() {
        return bonus;
    }

    public void setBonus(double bonus) {
        this.bonus = bonus;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public int getQtyReserve() {
        return qtyReserve;
    }

    public void setQtyReserve(int qtyReserve) {
        this.qtyReserve = qtyReserve;
    }

}
