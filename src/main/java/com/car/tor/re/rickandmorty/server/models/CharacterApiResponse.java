/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.car.tor.re.rickandmorty.server.models;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author carlo
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CharacterApiResponse {
    private int id;
    private String name;
    private String status;
    private String species;
    private String type;
    private String gender;
    private Location location;
    private List<String> episode;
    private String image;
}
