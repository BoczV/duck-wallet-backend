package com.duckwallet.userservice.repository;

import com.duckwallet.userservice.model.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<AppUser, Long> {

    Optional<AppUser> findByNickName(String nickName);
}
