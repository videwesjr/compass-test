package com.compass.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public
class Match {
    private int sourceId;
    private int matchId;
    private String accuracy;
}