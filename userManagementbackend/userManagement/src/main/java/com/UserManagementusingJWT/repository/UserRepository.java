package com.UserManagementusingJWT.repository;

import com.UserManagementusingJWT.model.User;
import com.UserManagementusingJWT.responsejson.AllUsersResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository  extends JpaRepository<User,Long> {

    Optional<User> findByEmail(String username);
    boolean existsByEmail(String email);
    @Query("SELECT u FROM User u WHERE u.name LIKE ?1%")
    List<User> searchUsers(String key);

    void deleteByEmail(String email);
}
