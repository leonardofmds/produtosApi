package br.com.coti.entities;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

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
