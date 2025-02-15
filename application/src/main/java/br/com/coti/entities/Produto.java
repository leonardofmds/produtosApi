package br.com.coti.entities;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Produto {	
	private UUID id;
	private String nome;
	private double preco;
	private Integer quantidade;
	private Categoria categoria;	
}
