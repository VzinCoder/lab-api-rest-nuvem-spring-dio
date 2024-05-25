package com.vzincoder.api.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;


public class AdminCreateDTO {
    @NotBlank(message = "The 'email' field is required")
    @Size(min = 11)
    @Pattern(regexp = "\\S.*\\S", message = "The 'email' must contain at least one non-whitespace character")
    private String email;

    @NotBlank(message = "The 'password' field is required")
    @Pattern(regexp = "\\S.*\\S", message = "The 'email' must contain at least one non-whitespace character")
    @Size(min = 8)
    private String password;

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

    
}
