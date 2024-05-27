package com.vzincoder.api.dto;


public class EmployeeDTO {

    private int id;
    private String email;
    private String password;
    private String name;
    private String cpf;
    private double salary;
    private double bonus;
    private double total;
    private long reserveCount;

    public EmployeeDTO(){}

    public EmployeeDTO(int id, String email, String password, String name, String cpf, double salary, double bonus, long reserveCount) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.name = name;
        this.cpf = cpf;
        this.salary = salary;
        this.bonus = bonus;
        this.reserveCount = reserveCount;
        this.total = this.salary+this.bonus;
    }

    
 

    public double getBonus() {
        return bonus;
    }

    public void setBonus(double bonus) {
        this.bonus = bonus;
    }

    public long getReserveCount() {
        return reserveCount;
    }

    public void setReserveCount(long reserveCount) {
        this.reserveCount = reserveCount;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

}
