/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.car.tor.re.rickandmorty.server.services;

import com.car.tor.re.rickandmorty.server.models.ApiResponse;
import com.car.tor.re.rickandmorty.server.models.CharacterApiResponse;
import com.car.tor.re.rickandmorty.server.models.CharacterResponse;
import com.car.tor.re.rickandmorty.server.models.Episode;
import com.car.tor.re.rickandmorty.server.models.Location;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import java.util.ArrayList;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.io.ByteArrayOutputStream;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import java.io.IOException;
import java.net.URL;
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
                        
                    String episode01Url = result.getEpisode().get(0);
                    Episode episode01 = restTemplate.getForObject(episode01Url, Episode.class);
                    
                    if (episode01 != null) {
                            Episode episodeData = new Episode();
                            episodeData.setName(episode01.getName());
                            character.setFirstEpisode(episodeData.getName()); 
                        
                    }
                    
                    return character;
                })
                .collect(Collectors.toList());
    }
    
    public CharacterApiResponse getCharacterById(int id) {
        RestTemplate restTemplate = new RestTemplate();
        String url = API_URL + id;
        CharacterApiResponse response = restTemplate.getForObject(url, CharacterApiResponse.class);
        System.out.println("Respuesta del persoanje: " + response);
        
        return response;
    }
    
    
    public ResponseEntity<ByteArrayResource> generateCharacterPdf(int id) {
        CharacterApiResponse character = getCharacterById(id);
        if (character == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {
            PdfWriter writer = new PdfWriter(byteArrayOutputStream);
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);

            document.add(new Paragraph("Character ID: " + character.getId()));
            document.add(new Paragraph("Name: " + character.getName()));
            document.add(new Paragraph("Status: " + character.getStatus()));
            document.add(new Paragraph("Species: " + character.getSpecies()));
            document.add(new Paragraph("Type: " + character.getType()));
            document.add(new Paragraph("Gender: " + character.getGender()));
            document.add(new Paragraph("Location: " + character.getLocation().getName()));
            
            String imageUrl = character.getImage();
            try {
                ImageData imageData = ImageDataFactory.create(new URL(imageUrl));
                Image image = new Image(imageData);
                document.add(image);
            } catch (IOException e) {
                e.printStackTrace();
                document.add(new Paragraph("Unable to load image."));
            }
            
            document.add(new Paragraph("Image Link: " + character.getImage()));
            document.add(new Paragraph("List of Episodes: " + character.getEpisode()));

            document.close();

            ByteArrayResource resource = new ByteArrayResource(byteArrayOutputStream.toByteArray());
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=character_" + id + ".pdf");

            return ResponseEntity.ok()
                    .headers(headers)
                    .contentLength(resource.contentLength())
                    .contentType(org.springframework.http.MediaType.APPLICATION_PDF)
                    .body(resource);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
