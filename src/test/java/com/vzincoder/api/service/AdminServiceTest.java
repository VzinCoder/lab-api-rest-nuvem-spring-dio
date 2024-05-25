package com.vzincoder.api.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.OptimisticLockingFailureException;

import com.vzincoder.api.domain.Admin;
import com.vzincoder.api.dto.AdminCreateDTO;
import com.vzincoder.api.dto.AdminDTO;
import com.vzincoder.api.exception.DataIntegrityException;
import com.vzincoder.api.exception.EntityNotFoundException;
import com.vzincoder.api.repository.AdminRepository;

@SpringBootTest
public class AdminServiceTest {

    @Autowired
    AdminService adminService;

    @MockBean
    AdminRepository adminRepository;

    @Test
    public void createAdminTest() {
        AdminCreateDTO adminCreateDTO = new AdminCreateDTO();
        adminCreateDTO.setEmail("email@gmail.com");
        adminCreateDTO.setPassword("11111111");

        Admin admin = new Admin();
        admin.setEmail("email@gmail.com");
        admin.setPassword("11111111");
        // Assert that creating the first admin does not throw an exception
        Mockito.when(adminRepository.save(any())).thenReturn(admin);

        AdminDTO adminCreatedDTO = adminService.createAdmin(adminCreateDTO);
        assertEquals(adminCreateDTO.getEmail(), adminCreatedDTO.getEmail());
        assertEquals(adminCreateDTO.getPassword(), adminCreatedDTO.getPassword());

        // Assert that creating the second admin with the same email address throws
        // DataIntegrityException
        Mockito.when(adminRepository.save(any())).thenThrow(OptimisticLockingFailureException.class);
        assertThrows(DataIntegrityException.class, () -> adminService.createAdmin(adminCreateDTO));
    }

    @Test
    public void getAdminByIdTest() {

        // id invalid
        int idInvalid = 1234;
        Mockito.when(adminRepository.findById(idInvalid)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> adminService.getAdminById(idInvalid));

        // id valid
        int idValid = 1;
        Admin admin = new Admin();
        admin.setEmail("email@gmail.com");
        admin.setPassword("111111111");
        Mockito.when(adminRepository.findById(idValid)).thenReturn(Optional.of(admin));

        AdminDTO adminFoundDTO = adminService.getAdminById(idValid);

        assertEquals(adminFoundDTO.getEmail(), admin.getEmail());
        assertEquals(adminFoundDTO.getPassword(), admin.getPassword());

    }

    @Test
    public void getAdminByEmailTest() {

        // email invalid
        String emailInvalid = "fahbgu@gmail.com";
        Mockito.when(adminRepository.findByEmail(emailInvalid)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> adminService.getAdminByEmail(emailInvalid));

        // email valid
        String emailValid = "email1@gmail.com";
        Admin admin = new Admin();
        admin.setEmail(emailValid);
        admin.setPassword("111111111");
        Mockito.when(adminRepository.findByEmail(admin.getEmail())).thenReturn(Optional.of(admin));

        AdminDTO adminFoundDTO = adminService.getAdminByEmail(admin.getEmail());

        assertEquals(adminFoundDTO.getEmail(), admin.getEmail());
        assertEquals(adminFoundDTO.getPassword(), admin.getPassword());

    }

    @Test
    public void getAllAdminTest() {
        Admin admin1 = new Admin();
        admin1.setEmail("email1@gmail.com");
        admin1.setPassword("11111111");

        Admin admin2 = new Admin();
        admin2.setEmail("email2@gmail.com");
        admin2.setPassword("22222222");

        List<Admin> adminList = Arrays.asList(admin1, admin2);

        Mockito.when(adminRepository.findAll()).thenReturn(adminList);

        List<AdminDTO> adminListDTO = adminService.getAllAdmin();

        // checks if the DTO details are correct
        boolean isChecked = adminListDTO.stream().allMatch(adminDTO -> {
            return adminList.stream()
                    .anyMatch(admin -> {
                        boolean isEmailEqual = admin.getEmail().equals(adminDTO.getEmail());
                        boolean isPasswordEqual = admin.getPassword().equals(adminDTO.getPassword());
                        return isEmailEqual && isPasswordEqual;
                    });
        });

        assertEquals(adminListDTO.size(), adminList.size());
        assertTrue(isChecked);
    }

    @Test
    public void deleteAdminTest() {
        // id invalid
        int idInvalid = 1234;
        Mockito.when(adminRepository.findById(idInvalid)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> adminService.deleteAdmin(idInvalid));

        // id valid
        int idValid = 1;
        Mockito.when(adminRepository.findById(idValid)).thenReturn(Optional.of(new Admin()));
        assertDoesNotThrow(() -> adminService.deleteAdmin(idValid));
        Mockito.verify(adminRepository, Mockito.times(1)).delete(any());
    }

    @Test
    public void updateAdminTest() {
        // previous admin
        Admin admin = new Admin();
        admin.setId(1);
        admin.setEmail("email@gmail.com");
        admin.setPassword("11111111");

        // new admin
        Admin newAdmin = new Admin();
        newAdmin.setId(admin.getId());
        newAdmin.setEmail("emailTest@gmail.com");
        newAdmin.setPassword("22222222");

        // update admin
        Mockito.when(adminRepository.findById(admin.getId())).thenReturn(Optional.of(admin));
        Mockito.when(adminRepository.save(admin)).thenReturn(admin);
        AdminDTO adminDTO = adminService.updateAdmin(newAdmin);

        assertEquals(newAdmin.getId(), adminDTO.getId());
        assertEquals(newAdmin.getEmail(), adminDTO.getEmail());
        assertEquals(newAdmin.getPassword(), adminDTO.getPassword());

        // test update id invalid
        newAdmin.setId(1234);
        Mockito.when(adminRepository.findById(newAdmin.getId())).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> adminService.updateAdmin(newAdmin));

        // update admin email used
        newAdmin.setId(admin.getId());
        Mockito.when(adminRepository.save(admin)).thenThrow(OptimisticLockingFailureException.class);
        assertThrows(DataIntegrityException.class, () -> adminService.updateAdmin(newAdmin));
    }
}
