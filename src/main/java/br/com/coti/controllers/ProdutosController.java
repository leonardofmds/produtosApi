package br.com.coti.controllers;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/api/produtos/")
public class ProdutosController {

	@Operation(summary = "Serviço para cadastrar um novo produto")
	@PostMapping("/cadastrar")
	public void cadastrarProduto() {
		

	}
	
	@Operation(summary = "Serviço para atualizar um produto")
	@PutMapping("/atualizar")
	public void atualizarProduto() {


	}
	
	@Operation(summary = "Serviço para excluir um produto")
	@DeleteMapping("/excluir")
	public void excluirProduto() {
		
	}
	
	@Operation(summary = "Serviço para consultar todos os produtos")
	@GetMapping("/consultar")
	public void consultarProdutos() {

	}
}
