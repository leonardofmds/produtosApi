package br.com.coti.dtos;

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
