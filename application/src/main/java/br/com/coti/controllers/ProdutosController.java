package br.com.coti.controllers;

import br.com.coti.dtos.ProdutoRequestDto;
import br.com.coti.dtos.ProdutoResponseDto;
import br.com.coti.entities.Produto;
import br.com.coti.repositories.CategoriaRepository;
import br.com.coti.repositories.ProdutoRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * Classe de controle para a entidade Produto
 */
@RestController
@Slf4j
@RequestMapping("/api/produtos/")
public class ProdutosController {

	ModelMapper mapper = new ModelMapper();
	private final CategoriaRepository categoriaRepository;

	public ProdutosController(CategoriaRepository categoriaRepository) {
		this.categoriaRepository = categoriaRepository;
	}

	@Operation(summary = "Serviço para cadastrar um novo produto")
	@PostMapping("/cadastrar")
	public ResponseEntity<?> cadastrarProduto(@RequestBody @Valid ProdutoRequestDto request) {

		try {
			if(Objects.isNull(categoriaRepository.findById(request.getCategoriaId()))){
				return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Categoria não encontrada, verifique o ID informado");
			}

			var produto = mapper.map(request, Produto.class);

			var produtoRepository = new ProdutoRepository();
			produtoRepository.create(produto, request.getCategoriaId());

			return ResponseEntity.created(new URI("")).body("Produto cadastrado com sucesso.");
		} catch (Exception e) {
            log.error("Erro ao cadastrar o produto: {}", e.getMessage());
			return ResponseEntity.internalServerError().body("Erro ao cadastrar o produto: " + e.getMessage());
		}
	}

	@Operation(summary = "Serviço para atualizar um produto")
	@PutMapping("/atualizar/{id}")
	public ResponseEntity<String> atualizarProduto(@RequestBody @Valid ProdutoRequestDto request, @PathVariable UUID id) {

		try {
			if(Objects.isNull(categoriaRepository.findById(request.getCategoriaId()))){
				return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Categoria não encontrada, verifique o ID informado");
			}
			var produto = mapper.map(request, Produto.class);
			produto.setId(id);

			var produtoRepository = new ProdutoRepository();
			produtoRepository.update(produto);

			return ResponseEntity.ok("Produto atualizado com sucesso.");
		} catch (Exception e) {
			log.error(e.getMessage());
			return ResponseEntity.internalServerError().body("Erro ao atualizar produto: " + e.getMessage());
		}
	}

	@Operation(summary = "Serviço para excluir um produto")
	@DeleteMapping("/excluir")
	public ResponseEntity<String> excluirProduto(@RequestParam UUID id) {
		var produtoRepository = new ProdutoRepository();

		try {
			if(Objects.isNull(produtoRepository.findById(id))){
				return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Produto não encontrado, favor verificar o ID do produto");
			}
			produtoRepository.delete(id);
			return ResponseEntity.ok("Produto excluído com sucesso.");
		} catch (Exception e) {
			log.error(e.getMessage());
			return ResponseEntity.internalServerError().body("Erro ao excluir produto: " + e.getMessage());
		}
	}

	@Operation(summary = "Serviço para consultar todos os produtos")
	@GetMapping("/consultar")
	@ApiResponse(responseCode = "200", description = "Sucesso",
			content = @Content(mediaType = "application/json",schema = @Schema(implementation = ProdutoResponseDto.class)))
	public ResponseEntity<?> consultarProdutos() {
		var produtoRepository = new ProdutoRepository();

		var produtos = produtoRepository.findAll();
		List<ProdutoResponseDto> response = mapper.map(produtos, new TypeToken<List<ProdutoResponseDto>>() {}.getType());
		return ResponseEntity.ok(response);


	}

	@Operation(summary = "Serviço para consultar um produto pelo id")
	@GetMapping("/consultar/{id}")
	public ResponseEntity<?> consultarProduto(@PathVariable UUID id) {

		ProdutoRepository produtoRepository = new ProdutoRepository();

		Produto produto = produtoRepository.findById(id);

		if(produto == null){
			return ResponseEntity.noContent().build();
		}
		ProdutoResponseDto response = mapper.map(produto, ProdutoResponseDto.class);
		return ResponseEntity.ok(response);


	}
}
