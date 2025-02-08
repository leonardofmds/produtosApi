package br.com.coti.controllers;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;

/**
 * Classe de controle para a entidade Produto
 */
@RestController
@RequestMapping("/api/produtos/")
public class ProdutosController {


	@Operation(summary = "Serviço para cadastrar um novo produto")
	@PostMapping("/cadastrar")
	public void cadastrarProduto() {
		//TODO implementar o serviço de cadastro de produtos

	}
	
	@Operation(summary = "Serviço para atualizar um produto")
	@PutMapping("/atualizar")
	public void atualizarProduto() {
		//TODO implementar o serviço de atualização de produtos

	}
	
	@Operation(summary = "Serviço para excluir um produto")
	@DeleteMapping("/excluir")
	public void excluirProduto() {
		// TODO implementar o serviço de exclusão de produtos
	}
	
	@Operation(summary = "Serviço para consultar todos os produtos")
	@GetMapping("/consultar")
	public void consultarProdutos() {
		//TODO implementar o serviço de consulta de produtos
	}
}
