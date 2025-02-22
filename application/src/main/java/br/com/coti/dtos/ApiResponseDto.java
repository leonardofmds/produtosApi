package br.com.coti.dtos;

import lombok.Data;

import java.time.Instant;
import java.util.UUID;

@Data
public class ApiResponseDto {
    private Integer StatusCode;
    private String  message;
    private Instant timestamp = Instant.now();
    private UUID id;
}
