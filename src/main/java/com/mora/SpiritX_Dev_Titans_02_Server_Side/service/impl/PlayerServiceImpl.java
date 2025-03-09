package com.mora.SpiritX_Dev_Titans_02_Server_Side.service.impl;

import com.mora.SpiritX_Dev_Titans_02_Server_Side.dto.PlayerDTO;
import com.mora.SpiritX_Dev_Titans_02_Server_Side.dto.PlayerResponse;
import com.mora.SpiritX_Dev_Titans_02_Server_Side.entity.Player;
import com.mora.SpiritX_Dev_Titans_02_Server_Side.entity.User;
import com.mora.SpiritX_Dev_Titans_02_Server_Side.repository.PlayerRepository;
import com.mora.SpiritX_Dev_Titans_02_Server_Side.repository.UserRepository;
import com.mora.SpiritX_Dev_Titans_02_Server_Side.service.PlayerService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PlayerServiceImpl implements PlayerService {

    private final PlayerRepository playerRepository;
    private final ModelMapper modelMapper;  // Inject ModelMapper

    @Override
    public PlayerDTO addPlayer(PlayerDTO playerDTO) {
        Player player = modelMapper.map(playerDTO, Player.class);
        player = playerRepository.save(player);
        return modelMapper.map(player, PlayerDTO.class);
    }
    @Override
    public List<PlayerResponse> calculatePlayerStats() {
        List<Player> players = playerRepository.findAll();

        return players.stream().map(player -> {
            // Perform calculations
            double battingStrikeRate = (player.getBallsFaced() != 0) ? (player.getTotalRuns() * 100.0) / player.getBallsFaced() : 0.0;
            double battingAverage = (player.getInningsPlayed() != 0) ? (double) player.getTotalRuns() / player.getInningsPlayed() : 0.0;
            double totalBallsBowled = player.getOversBowled() * 6; // Convert overs to balls
            double bowlingStrikeRate = (player.getWickets() != 0) ? totalBallsBowled / player.getWickets() : 0.0;
            double economyRate = (totalBallsBowled != 0) ? (player.getRunsConceded() * 6.0) / totalBallsBowled : 0.0;

            double playerPoints = (battingStrikeRate / 5 + battingAverage * 0.8) +
                    ((bowlingStrikeRate != 0) ? (500 / bowlingStrikeRate) : 0) +
                    ((economyRate != 0) ? (140 / economyRate) : 0);

            double valueInRupees = Math.round(((9 * playerPoints + 100) * 1000) / 50000) * 50000;

            // Handle infinity or NaN cases
            if (Double.isInfinite(battingStrikeRate) || Double.isNaN(battingStrikeRate)) battingStrikeRate = 0.0;
            if (Double.isInfinite(battingAverage) || Double.isNaN(battingAverage)) battingAverage = 0.0;
            if (Double.isInfinite(bowlingStrikeRate) || Double.isNaN(bowlingStrikeRate)) bowlingStrikeRate = 0.0;
            if (Double.isInfinite(economyRate) || Double.isNaN(economyRate)) economyRate = 0.0;
            if (Double.isInfinite(playerPoints) || Double.isNaN(playerPoints)) playerPoints = 0.0;
            if (Double.isInfinite(valueInRupees) || Double.isNaN(valueInRupees)) valueInRupees = 0.0;

            // Round values to 2 decimal places
            battingStrikeRate = Math.round(battingStrikeRate * 100.0) / 100.0;
            battingAverage = Math.round(battingAverage * 100.0) / 100.0;
            bowlingStrikeRate = Math.round(bowlingStrikeRate * 100.0) / 100.0;
            economyRate = Math.round(economyRate * 100.0) / 100.0;
            playerPoints = Math.round(playerPoints * 100.0) / 100.0;

            // Convert Player to PlayerResponse using ModelMapper
            PlayerResponse response = modelMapper.map(player, PlayerResponse.class);
            response.setName(player.getName());
            response.setBattingStrikeRate(battingStrikeRate);
            response.setBattingAverage(battingAverage);
            response.setBowlingStrikeRate(bowlingStrikeRate);
            response.setEconomyRate(economyRate);
            response.setPlayerPoints(playerPoints);
            response.setValueInRupees(valueInRupees);

            return response;
        }).collect(Collectors.toList());
    }

    @Override
    public List<PlayerDTO> getAllPlayers() {
        return playerRepository.findAll()
                .stream()
                .map(player -> modelMapper.map(player, PlayerDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public PlayerDTO getPlayerById(String id) {
        return playerRepository.findById(id)
                .map(player -> modelMapper.map(player, PlayerDTO.class))
                .orElse(null);
    }

    @Override
    public PlayerDTO updatePlayer(String id, PlayerDTO playerDTO) {
        Optional<Player> existingPlayer = playerRepository.findById(id);
        if (existingPlayer.isPresent()) {
            Player player = existingPlayer.get();
            modelMapper.map(playerDTO, player);  // Map new values to existing entity
            return modelMapper.map(playerRepository.save(player), PlayerDTO.class);
        }
        return null;
    }

    @Override
    public void deletePlayer(String id) {
        playerRepository.deleteById(id);
    }


    @Service
    @RequiredArgsConstructor
    public static class CustomUserDetailsService implements UserDetailsService {
        private final UserRepository userRepository;

        @Override
        public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
            User user = userRepository.findByUsername(username)
                    .orElseThrow(() -> new UsernameNotFoundException("User not found"));

            return org.springframework.security.core.userdetails.User.builder()
                    .username(user.getUsername())
                    .password(user.getPassword())
                    .roles(user.getRole())  // Ensure role is correctly assigned
                    .build();
        }
    }
}
