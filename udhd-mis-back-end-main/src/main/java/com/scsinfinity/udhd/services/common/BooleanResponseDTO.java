package com.scsinfinity.udhd.services.common;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BooleanResponseDTO {
    private Boolean response;
    private String message;
}
