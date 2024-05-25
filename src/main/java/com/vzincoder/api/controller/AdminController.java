package com.vzincoder.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vzincoder.api.domain.Admin;
import com.vzincoder.api.dto.AdminCreateDTO;
import com.vzincoder.api.dto.AdminDTO;
import com.vzincoder.api.dto.MessageResponseDTO;
import com.vzincoder.api.service.AdminService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @GetMapping("/{id}")
    public ResponseEntity<AdminDTO> getAdminById(@PathVariable int id) {
        return ResponseEntity.ok(adminService.getAdminById(id));
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<AdminDTO> getAdminByEmail(@PathVariable String email) {
        return ResponseEntity.ok(adminService.getAdminByEmail(email));
    }

    @GetMapping
    public ResponseEntity<List<AdminDTO>> getAllAdmins() {
        List<AdminDTO> admins = adminService.getAllAdmin();
        return ResponseEntity.ok(admins);
    }

    @PostMapping
    public ResponseEntity<AdminDTO> createAdmin(@Valid @RequestBody AdminCreateDTO adminCreateDTO) {
        AdminDTO createdAdmin = adminService.createAdmin(adminCreateDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdAdmin);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AdminDTO> updateAdmin(@PathVariable int id,@Valid @RequestBody AdminCreateDTO adminCreateDTO) {
        Admin updateAdmin = new Admin();
        updateAdmin.setId(id);
        updateAdmin.setEmail(adminCreateDTO.getEmail());
        updateAdmin.setPassword(adminCreateDTO.getPassword());
        AdminDTO updatedAdmin = adminService.updateAdmin(updateAdmin);
        return ResponseEntity.ok(updatedAdmin);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponseDTO> deleteAdmin(@PathVariable int id) {
        adminService.deleteAdmin(id);
        return ResponseEntity.ok(new MessageResponseDTO("User with ID " + id + " deleted successfully"));
    }
}
