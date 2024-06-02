package com.vzincoder.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public class ReserveCreateDTO {

    @NotBlank
    @Size(min = 11, max = 11, message = "cpf client must have 11 digits")
    private String clientCpf;

    @NotBlank
    @Size(min = 11, max = 11, message = "cpf employee must have 11 digits")
    private String employeeCpf;

    @NotNull
    @Positive
    private int roomId;

    @NotBlank(message = "Check-in date cannot be blank")
    @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}", message = "Invalid check-in date format. Please use the format YYYY-MM-DD.")
    private String dateCheckIn;

    @NotBlank(message = "Check-in date cannot be blank")
    @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}", message = "Invalid check-out date format. Please use the format YYYY-MM-DD.")
    private String dateCheckOut;

    public String getClientCpf() {
        return clientCpf;
    }

    public void setClientCpf(String clientCpf) {
        this.clientCpf = clientCpf;
    }

    public String getEmployeeCpf() {
        return employeeCpf;
    }

    public void setEmployeeCpf(String employeeCpf) {
        this.employeeCpf = employeeCpf;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }


    public String getDateCheckIn() {
        return dateCheckIn;
    }

    public void setDateCheckIn(String dateCheckIn) {
        this.dateCheckIn = dateCheckIn;
    }

    public String getDateCheckOut() {
        return dateCheckOut;
    }

    public void setDateCheckOut(String dateCheckOut) {
        this.dateCheckOut = dateCheckOut;
    }

}
