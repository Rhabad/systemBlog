package com.sistemblog.controllers;

import com.sistemblog.models.dto.PublicationDto;
import com.sistemblog.models.dto.PublicationResponse;
import com.sistemblog.services.IPublicationService;
import com.sistemblog.utils.AppConstantes;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class PublicationController {

    @Autowired
    private IPublicationService publicationService;

    @GetMapping("/publications")
    public PublicationResponse listAllPublications(
            @RequestParam(value = "pageNo", defaultValue = AppConstantes.num_pagina_por_defecto, required = false) int numPagina,
            @RequestParam(value = "pageSize", defaultValue = AppConstantes.medida_pagina_por_defecto, required = false) int medidaPag,
            @RequestParam(value = "sortBy", defaultValue = AppConstantes.ordenar_por_defecto, required = false) String ordenarPor,
            @RequestParam(value = "sortDir", defaultValue = AppConstantes.ordenar_por_direccion_defecto, required = false) String sortDir
    ){
        return publicationService.getAll(numPagina, medidaPag, ordenarPor, sortDir);
    }

    @GetMapping("/publication/{id}")
    public ResponseEntity<PublicationDto> getPublicationForId(@PathVariable Long id){
        return ResponseEntity.ok(publicationService.findById(id));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/publication")
    public ResponseEntity<PublicationDto> savePublication(@Valid @RequestBody PublicationDto publicationDto){
        return new ResponseEntity<>(publicationService.createPublication(publicationDto), HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/publication-update/{id}")
    public ResponseEntity<PublicationDto> update(@Valid @RequestBody PublicationDto publicationDto, @PathVariable Long id){
        PublicationDto response = publicationService.updatePublication(publicationDto, id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @DeleteMapping("/publication-delete/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id){
        publicationService.deletePublication(id);
        return new ResponseEntity<>("Publicacion eliminada exitosamente", HttpStatus.OK);
    }
}
