package com.test.backend.Repository;

import com.test.backend.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserR extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
