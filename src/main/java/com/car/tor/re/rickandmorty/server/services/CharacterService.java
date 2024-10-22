/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.car.tor.re.rickandmorty.server.services;

import com.car.tor.re.rickandmorty.server.models.ApiResponse;
import com.car.tor.re.rickandmorty.server.models.CharacterResponse;
import com.car.tor.re.rickandmorty.server.models.Episode;
import com.car.tor.re.rickandmorty.server.models.Location;
import java.util.ArrayList;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 *
 * @author carlo
 */

@Service
public class CharacterService {
    private final String API_URL = "https://rickandmortyapi.com/api/character/";

    public List<CharacterResponse> getCharacters() {
        RestTemplate restTemplate = new RestTemplate();
        ApiResponse response = restTemplate.getForObject(API_URL, ApiResponse.class);
        System.out.println("Responde: " + response);
        return response.getResults().stream()
                .map(result -> {
                    CharacterResponse character = new CharacterResponse();
                    character.setId(result.getId());
                    character.setName(result.getName());
                    character.setStatus(result.getStatus());
                    character.setSpecies(result.getSpecies());
                    Location location = new Location();
                    location.setName(result.getLocation().getName());
                    location.setUrl(result.getLocation().getUrl());
                    character.setLocation(location);
                    character.setImageLink(result.getImage());
                    
                    // Obtener información del 1er episodio
                    //List<Episode> episodes = new ArrayList<>();
                    String episode01Url = result.getEpisode().get(0);
                    Episode episode01 = restTemplate.getForObject(episode01Url, Episode.class);
                    
                    if (episode01 != null) {
                            Episode episodeData = new Episode();
                            episodeData.setName(episode01.getName());
                            //episodeData.setEpisodeNum(episode.getEpisodeNum());
                            //episodes.add(episodeData);
                            character.setFirstEpisode(episodeData.getName()); 
                        
                    }
                    
                    return character;
                })
                .collect(Collectors.toList());
    }
}
