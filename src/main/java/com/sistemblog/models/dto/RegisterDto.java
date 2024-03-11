package com.sistemblog.models.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RegisterDto {
    private String nombre;
    private String username;
    private String email;
    private String password;
}
