package com.mora.SpiritX_Dev_Titans_02_Server_Side.service;

import com.mora.SpiritX_Dev_Titans_02_Server_Side.dto.PlayerDTO;
import com.mora.SpiritX_Dev_Titans_02_Server_Side.dto.PlayerResponse;

import java.util.List;

public interface PlayerService {
    PlayerDTO addPlayer(PlayerDTO playerDTO);
    List<PlayerDTO> getAllPlayers();
    PlayerDTO getPlayerById(String id);
    PlayerDTO updatePlayer(String id, PlayerDTO playerDTO);
    void deletePlayer(String id);
    List<PlayerResponse> calculatePlayerStats();
}
