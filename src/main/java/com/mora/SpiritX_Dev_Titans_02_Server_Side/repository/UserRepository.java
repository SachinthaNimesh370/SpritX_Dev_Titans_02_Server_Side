package com.mora.SpiritX_Dev_Titans_02_Server_Side.repository;

import com.mora.SpiritX_Dev_Titans_02_Server_Side.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String> {
    Optional<User> findByUsername(String username);
}
