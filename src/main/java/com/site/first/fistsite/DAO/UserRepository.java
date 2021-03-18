package com.site.first.fistsite.DAO;

import com.site.first.fistsite.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {

    public User findUserByUsername(String username);
}
