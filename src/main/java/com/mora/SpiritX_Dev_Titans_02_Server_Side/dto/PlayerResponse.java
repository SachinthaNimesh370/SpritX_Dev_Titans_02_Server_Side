package com.mora.SpiritX_Dev_Titans_02_Server_Side.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlayerResponse {
    private String id;
    private String name;
    private String university;
    private String category;
    private double battingStrikeRate;
    private double battingAverage;
    private double bowlingStrikeRate;
    private double economyRate;
    private double playerPoints;
    private double valueInRupees;
}
