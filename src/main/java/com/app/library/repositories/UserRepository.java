package com.app.library.repositories;

import com.app.library.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, Long> {
    @Query(value ="SELECT ID FROM LIBRARY_USERS u WHERE u.NAME = ?1 and u.FIRST_NAME = ?2",
            nativeQuery = true)
    long findIdByNameAndFirstName(String name, String firstName);
}
