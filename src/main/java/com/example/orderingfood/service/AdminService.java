package com.example.orderingfood.service;

import com.example.orderingfood.domain.Admin;
import com.example.orderingfood.repository.AdminRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final AdminRepository adminRepository;

    public Admin register(Admin admin) {
        admin.setPassword(BCrypt.hashpw(admin.getPassword(), BCrypt.gensalt()));
        return adminRepository.save(admin);
    }
}
