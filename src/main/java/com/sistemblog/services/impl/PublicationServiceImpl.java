package com.sistemblog.services.impl;

import com.sistemblog.exceptions.ResourceNotFoundException;
import com.sistemblog.models.dao.PublicationDao;
import com.sistemblog.models.dto.PublicationDto;
import com.sistemblog.models.dto.PublicationResponse;
import com.sistemblog.models.entities.Publication;
import com.sistemblog.services.IPublicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PublicationServiceImpl implements IPublicationService {

    @Autowired
    private PublicationDao publicationDao;

    @Override
    public PublicationDto createPublication(PublicationDto publicationDto) {
        //convertimoso de dto a entidad
        Publication publication = mapearEntidad(publicationDto);

        Publication nuevaPublicacion = publicationDao.save(publication);

        //convertimos de entidada a dto
        PublicationDto response = mapearDto(nuevaPublicacion);

        return response;
    }

    @Override
    public PublicationResponse getAll(int numPag, int medidaPag, String ordenarPor, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())?Sort.by(ordenarPor).ascending():Sort.by(ordenarPor).descending();

        Pageable pageable = PageRequest.of(numPag, medidaPag, sort);

        Page<Publication> publications = (Page<Publication>) publicationDao.findAll(pageable);


        List<Publication> listPublications = publications.getContent();

        List<PublicationDto> contenido = listPublications.stream().map(publication ->
               mapearDto(publication)).collect(Collectors.toList());

        PublicationResponse publicationResponse = PublicationResponse.builder()
                .contentido(contenido)
                .numPagina(publications.getNumber())
                .medidaPagina(publications.getSize())
                .totalElementos(publications.getTotalElements())
                .totalPaginas(publications.getTotalPages())
                .ultima(publications.isLast())
                .build();
        return publicationResponse;
    }

    @Override
    public PublicationDto findById(Long id) {
        Publication publication = publicationDao.findById(id)
                .orElseThrow(() ->
                new ResourceNotFoundException("Publicacion", "id", id));
        return mapearDto(publication);
    }

    @Override
    public PublicationDto updatePublication(PublicationDto publicationDto, Long id) {
        Publication publication = publicationDao.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Publicacion", "id", id));

        publication.setTitle(publicationDto.getTitle());
        publication.setDescription(publicationDto.getDescription());
        publication.setContent(publicationDto.getContent());

        Publication nuevaPublicacion = publicationDao.save(publication);

        return mapearDto(nuevaPublicacion);
    }

    @Override
    public void deletePublication(Long id) {
        Publication publication = publicationDao.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Publicacion", "id", id));
        publicationDao.delete(publication);
    }


    //convierte entidad a dto
    private PublicationDto mapearDto(Publication publication){
        PublicationDto publicationDto = PublicationDto.builder()
                .id(publication.getId())
                .title(publication.getTitle())
                .description(publication.getDescription())
                .content(publication.getContent())
                .build();

        return publicationDto;
    }

    //convierte dto a entidad
    private Publication mapearEntidad(PublicationDto publicationDto){
        Publication publication = Publication.builder()
                .title(publicationDto.getTitle())
                .description(publicationDto.getDescription())
                .content(publicationDto.getContent())
                .build();

        return publication;
    }
}
