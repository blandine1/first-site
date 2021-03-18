package com.site.first.fistsite.DAO;

import com.site.first.fistsite.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
}
