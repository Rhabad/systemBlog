package com.sistemblog.exceptions;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends  RuntimeException{

    private  static final long serialVersionUID = 1l;

    @Getter @Setter
    private String nameResource;
    @Getter @Setter
    private String nameField;
    @Getter @Setter
    private long valueField;

    public ResourceNotFoundException(String nameResource, String nameField, long valueField) {
        super(String.format("%s no encontrada con : %s : '%s'", nameResource, nameField, valueField));
        this.nameResource = nameResource;
        this.nameField = nameField;
        this.valueField = valueField;
    }
}
