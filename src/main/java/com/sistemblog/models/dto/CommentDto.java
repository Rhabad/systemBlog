package com.sistemblog.models.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CommentDto {
    private Long id;
    @NotEmpty(message = "Campo nombre no debe estar vacio")
    private String nombre;
    @NotEmpty(message = "Campo email no debe estar vacio")
    @Email
    private String email;
    @NotEmpty
    @Size(min = 10, message = "Campo cuerpo debe tener al menos 10 caracteres")
    private String cuerpo;
}
