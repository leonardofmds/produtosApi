package br.com.coti.entities;

import lombok.*;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Categoria {
	private UUID id;
	private String nome;
	private List<Produto> produtos;	
}
