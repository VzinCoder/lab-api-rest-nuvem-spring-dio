package com.vzincoder.api.domain;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
public class Employee extends UserAbstract {

    @NotNull
    @Size(min = 3)
    private String name;

    @NotNull
    @Column(unique = true, length = 11)
    private String cpf;

    @NotNull
    private double salary;

    @NotNull
    private LocalDate date;

    @OneToMany(fetch = FetchType.LAZY)
    private List<Reserve> reserves = new ArrayList<>();

    
    @OneToMany(mappedBy = "employee", cascade = CascadeType.REMOVE)
    private List<Reserve> reserve;

    public LocalDate getDate() {
        return date;
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

    public List<Reserve> getReserves() {
        return reserves;
    }

    public void setReserves(List<Reserve> reserves) {
        this.reserves = reserves;
    }

    @PrePersist
    protected void onCreate() {
        date = LocalDate.now();
    }

    @Override
    public String toString() {
        return "Employee [name=" + name + ", cpf=" + cpf + ", salary=" + salary + ", date=" + date + ", reserves="
                + reserves + "]";
    }

    

}
