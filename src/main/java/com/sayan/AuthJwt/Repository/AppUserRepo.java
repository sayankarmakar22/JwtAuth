package com.sayan.AuthJwt.Repository;

import com.sayan.AuthJwt.Model.AppUsers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface AppUserRepo extends JpaRepository<AppUsers,Integer> {
    Optional<AppUsers> findByUsername(String username);

    boolean existsByUsername(String username);
}
