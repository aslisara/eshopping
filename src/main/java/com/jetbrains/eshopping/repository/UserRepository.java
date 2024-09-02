
package com.jetbrains.eshopping.repository;

import com.jetbrains.eshopping.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // Özel sorgular ekleyebilirsiniz
    Optional<User> findByUsername(String username);
}
