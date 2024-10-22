/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.car.tor.re.rickandmorty.server.controllers;

import com.car.tor.re.rickandmorty.server.models.CharacterApiResponse;
import com.car.tor.re.rickandmorty.server.models.CharacterResponse;
import com.car.tor.re.rickandmorty.server.services.CharacterService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
/**
 *
 * @author carlo
 */
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/characters")
public class CharacterController {
    @Autowired
    private CharacterService characterService;

    @GetMapping()
    public List<CharacterResponse> getCharacters() {
        return characterService.getCharacters();
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<CharacterApiResponse> getCharacterById(@PathVariable Integer id) {
        CharacterApiResponse character = characterService.getCharacterById(id);
        return ResponseEntity.ok(character);
    }
    
    @GetMapping("/{id}/pdf")
    public ResponseEntity<ByteArrayResource> downloadCharacterPdf(@PathVariable int id) {
        return characterService.generateCharacterPdf(id);
    }
}
