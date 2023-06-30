package com.wildflix.wildflix.config;

import com.wildflix.wildflix.enums.RoleName;
import com.wildflix.wildflix.models.Role;
import com.wildflix.wildflix.repository.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    private final RoleRepository roleRepo;

    public DataInitializer(RoleRepository roleRepository) {
        this.roleRepo = roleRepository;
    }

    @Override
    public void run(String... args) {
        for (RoleName role : RoleName.values()) {
            roleRepo.save(new Role(null, role));
        }
    }
}

