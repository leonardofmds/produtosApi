package br.com.coti.dtos;

import lombok.Data;

import java.util.UUID;

@Data
public class CategoriaResponseDto {
	private UUID id;
	private String nome;

}
