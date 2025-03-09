package com.mora.SpiritX_Dev_Titans_02_Server_Side.repository;

import com.mora.SpiritX_Dev_Titans_02_Server_Side.entity.Player;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayerRepository extends MongoRepository<Player, String> {
}
