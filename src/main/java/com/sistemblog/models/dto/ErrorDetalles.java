package com.sistemblog.models.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data @AllArgsConstructor @NoArgsConstructor
@Builder
public class ErrorDetalles {
    private Date marcaDeTiempo;
    private String mensaje;
    private String detalles;
}
