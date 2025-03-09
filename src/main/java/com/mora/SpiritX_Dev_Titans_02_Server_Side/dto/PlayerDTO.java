package com.mora.SpiritX_Dev_Titans_02_Server_Side.dto;

import lombok.*;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PlayerDTO {
    private String name;
    private String university;
    private String category;
    private int totalRuns;
    private int ballsFaced;
    private int inningsPlayed;
    private int wickets;
    private double oversBowled;
    private int runsConceded;
}
