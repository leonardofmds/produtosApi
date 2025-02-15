package br.com.coti.dtos;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.util.UUID;

@Data
public class ProdutoResponseDto {
    private UUID id;
    private String nome;
    private Double preco;
    private Integer quantidade;
    private CategoriaResponseDto categoria;

}
