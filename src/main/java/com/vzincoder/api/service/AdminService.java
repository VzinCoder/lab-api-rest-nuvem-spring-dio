package com.vzincoder.api.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vzincoder.api.domain.Admin;
import com.vzincoder.api.dto.AdminDTO;
import com.vzincoder.api.exception.DataIntegrityException;
import com.vzincoder.api.exception.EntityNotFoundException;
import com.vzincoder.api.repository.AdminRepository;

@Service
public class AdminService {

    @Autowired
    private AdminRepository adminRepository;

    public AdminDTO getAdminById(int id) {
        Optional<Admin> adminFound = adminRepository.findById(id);

        if (adminFound.isEmpty()) {
            throw new EntityNotFoundException("admin not afound");
        }

        return convertToDTO(adminFound.get());
    }

    public List<AdminDTO> getAllAdmin() {
        List<Admin> adminList = adminRepository.findAll();

        List<AdminDTO> adminDTOList = adminList.stream()
                .map(this::convertToDTO)
                .toList();

        return adminDTOList;
    }

    public AdminDTO createAdmin(Admin admin) {
        try {
            Admin adminCreated = adminRepository.save(admin);
            return convertToDTO(adminCreated);
        } catch (Exception e) {
            throw new DataIntegrityException("This email is already being used!");
        }
    }

    public void deleteAdmin(int id) {
        Optional<Admin> adminFound = adminRepository.findById(id);

        if (adminFound.isEmpty()) {
            throw new EntityNotFoundException("admin not afound");
        }

        adminRepository.delete(adminFound.get());
    }

    public AdminDTO updateAdmin(Admin admin) {
        Optional<Admin> adminFound = adminRepository.findById(admin.getId());

        if (adminFound.isEmpty()) {
            throw new EntityNotFoundException("Admin not found");
        }

        Admin existingAdmin = adminFound.get();

        Optional<Admin> adminWithNewEmail = adminRepository.findByEmail(admin.getEmail());

        if (adminWithNewEmail.isPresent() && adminWithNewEmail.get().getId() != admin.getId()) {
            throw new DataIntegrityException("Email is already being used by another admin");
        }

        existingAdmin.setEmail(admin.getEmail());
        existingAdmin.setPassword(admin.getPassword());

        Admin updatedAdmin = adminRepository.save(existingAdmin);

        return convertToDTO(updatedAdmin);
    }

    private AdminDTO convertToDTO(Admin admin) {
        AdminDTO adminDTO = new AdminDTO();
        adminDTO.setId(admin.getId());
        adminDTO.setEmail(admin.getEmail());
        adminDTO.setPassword(admin.getPassword());
        return adminDTO;
    }

}
