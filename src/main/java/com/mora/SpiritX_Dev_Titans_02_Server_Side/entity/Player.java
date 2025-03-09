package com.mora.SpiritX_Dev_Titans_02_Server_Side.entity;


import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "players")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Player {
    @Id
    private String id;
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
