package br.com.coti.controllers;

import br.com.coti.entities.Produto;
import br.com.coti.repositories.ProdutoRepository;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

/**
 * Classe de controle para a entidade Produto
 */
@RestController
@Slf4j
@RequestMapping("/api/produtos/")
public class ProdutosController {


	@Operation(summary = "Serviço para cadastrar um novo produto")
	@PostMapping("/cadastrar")
	public ResponseEntity<?> cadastrarProduto(@RequestBody Produto produto) {
		var produtoRepository = new ProdutoRepository();

		try {
			produtoRepository.create(produto);
			return ResponseEntity.created(new URI("")).body("Produto cadastrado com sucesso.");
		} catch (Exception e) {
			log.error("Erro ao cadastrar o produto: " + e.getMessage());
			// Não se responde com a exceção, pois a mensagem pode conter informações sensíveis
			return ResponseEntity.badRequest().body("Erro ao cadastrar o produto.");
		}
	}
	
	@Operation(summary = "Serviço para atualizar um produto")
	@PutMapping("/atualizar")
	public ResponseEntity<String> atualizarProduto(@RequestBody Produto produto) {
		var produtoRepository = new ProdutoRepository();

		try {
			if(produto.getCategoria()==null){
				var produtoComCategoria = produtoRepository.findById(produto.getId());
				produto.setCategoria(produtoComCategoria.getCategoria());
			}
			produtoRepository.update(produto);
			return ResponseEntity.ok("Produto atualizado com sucesso.");
		} catch (Exception e) {
			log.error(e.getMessage());
			return ResponseEntity.badRequest().body("Erro ao atualizar o produto.: "+e.getMessage());
		}
	}
	
	@Operation(summary = "Serviço para excluir um produto")
	@DeleteMapping("/excluir")
	public ResponseEntity<String> excluirProduto(@RequestParam UUID id) {
		var produtoRepository = new ProdutoRepository();
		
		try {
			produtoRepository.delete(id);
			return ResponseEntity.ok("Produto excluído com sucesso.");
		} catch (Exception e) {
			log.error(e.getMessage());
			return ResponseEntity.badRequest().body("Erro ao excluir o produto.: "+e.getMessage());
		}
	}
	
	@Operation(summary = "Serviço para consultar todos os produtos")
	@GetMapping("/consultar")
	public ResponseEntity<List<Produto>> consultarProdutos() {
		var produtoRepository = new ProdutoRepository();

		try {
			var produtos = produtoRepository.findAll();
			if(!produtos.isEmpty()){
				return ResponseEntity.ok(produtos);
			}
			else {
				return ResponseEntity.noContent().build();
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			return ResponseEntity.badRequest().body(null);
		}
	}

	@Operation(summary = "Serviço para consultar um produto pelo id")
	@GetMapping("/consultar/{id}")
	public ResponseEntity<Produto> consultarProduto(@PathVariable UUID id) {
		var produtoRepository = new ProdutoRepository();
		Produto produto;

		try {
			produto = produtoRepository.findById(id);
			if(produto!=null) {
				return ResponseEntity.ok(produto);
			}
			else {
				return ResponseEntity.notFound().build();
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			return ResponseEntity.badRequest().body(null);
		}
	}
}
