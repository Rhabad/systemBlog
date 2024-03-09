package com.sistemblog.services;

import com.sistemblog.models.dto.PublicationDto;
import com.sistemblog.models.dto.PublicationResponse;


public interface IPublicationService {
    PublicationDto createPublication(PublicationDto publicationDto);
    PublicationResponse getAll(int numPag, int medidaPag, String ordenarPor, String sortDir);
    PublicationDto findById(Long id);
    PublicationDto updatePublication(PublicationDto publicationDto, Long id);
    void deletePublication(Long id);
}
