package com.vzincoder.api.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;

@Entity
public class Employee extends UserAbstract {

    private String name;

    @Column(unique = true)
    private String cpf;
    
    private double salary;

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

    
}
