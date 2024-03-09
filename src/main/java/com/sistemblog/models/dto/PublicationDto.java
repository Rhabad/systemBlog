package com.sistemblog.models.dto;

import com.sistemblog.models.entities.Comment;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
public class PublicationDto {
    private Long id;
    @NotEmpty
    @Size(min = 2, message = "El titulo de la publicacion debe tener minimo 2 caracteres.")
    private String title;
    @NotEmpty
    @Size(min = 10, message = "La descripcion de la publicacion debe tener minimo 10 caracteres.")
    private String description;
    @NotEmpty
    private String content;
}
