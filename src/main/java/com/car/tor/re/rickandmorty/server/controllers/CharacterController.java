/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.car.tor.re.rickandmorty.server.controllers;

import com.car.tor.re.rickandmorty.server.models.CharacterResponse;
import com.car.tor.re.rickandmorty.server.services.CharacterService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;
/**
 *
 * @author carlo
 */
@CrossOrigin(origins = "*")
@RestController
public class CharacterController {
    @Autowired
    private CharacterService characterService;

    @GetMapping("/api/characters")
    public List<CharacterResponse> getCharacters() {
        return characterService.getCharacters();
    }
}
