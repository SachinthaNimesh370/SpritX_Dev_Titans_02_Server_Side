package com.mora.SpiritX_Dev_Titans_02_Server_Side.controller;


import com.mora.SpiritX_Dev_Titans_02_Server_Side.dto.PlayerDTO;
import com.mora.SpiritX_Dev_Titans_02_Server_Side.dto.PlayerResponse;
import com.mora.SpiritX_Dev_Titans_02_Server_Side.service.PlayerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/players")
@RequiredArgsConstructor
public class PlayerController {
    private final PlayerService playerService;

    @PostMapping("/save")
    public ResponseEntity<PlayerDTO> addPlayer(@RequestBody PlayerDTO playerDTO) {
        return ResponseEntity.ok(playerService.addPlayer(playerDTO));
    }

    @GetMapping("get")
    public ResponseEntity<List<PlayerDTO>> getAllPlayers() {
        return ResponseEntity.ok(playerService.getAllPlayers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PlayerDTO> getPlayerById(@PathVariable String id) {
        return ResponseEntity.ok(playerService.getPlayerById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PlayerDTO> updatePlayer(@PathVariable String id, @RequestBody PlayerDTO playerDTO) {
        return ResponseEntity.ok(playerService.updatePlayer(id, playerDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePlayer(@PathVariable String id) {
        playerService.deletePlayer(id);
        return ResponseEntity.ok("Player deleted successfully");
    }
    @GetMapping("/stats")
    public List<PlayerResponse> getPlayerStats() {
        return playerService.calculatePlayerStats();
    }
}
