package br.com.coti.controllers;

import br.com.coti.dtos.ApiResponseDto;
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
	private final ProdutoRepository produtoRepository;

	public ProdutosController(CategoriaRepository categoriaRepository, ProdutoRepository produtoRepository) {
		this.categoriaRepository = categoriaRepository;
		this.produtoRepository = produtoRepository;
	}

	@Operation(summary = "Serviço para cadastrar um novo produto")
	@PostMapping("/cadastrar")
	public ResponseEntity<?> cadastrarProduto(@RequestBody @Valid ProdutoRequestDto request) {

		if(Objects.isNull(categoriaRepository.findById(request.getCategoriaId()))){
			return ResponseEntity.status(HttpStatus.NO_CONTENT)
					.body("Categoria não encontrada, verifique o ID informado");
		}

		var produto = mapper.map(request, Produto.class);

		produtoRepository.create(produto);

		ApiResponseDto response = new ApiResponseDto();
		response.setMessage("Produto cadastrado com sucesso");
		response.setId(produto.getId());
		response.setStatusCode(201);

		return ResponseEntity.created(URI.create("")).body(response);
	}

	@Operation(summary = "Serviço para atualizar um produto")
	@PutMapping("/atualizar/{id}")
	public ResponseEntity<?> atualizarProduto(@RequestBody @Valid ProdutoRequestDto request, @PathVariable UUID id) {


		if(Objects.isNull(categoriaRepository.findById(request.getCategoriaId()))){
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Categoria não encontrada, verifique o ID informado");
		}
		var produto = mapper.map(request, Produto.class);
		produto.setId(id);

		produtoRepository.update(produto);
		var response = new ApiResponseDto();
		response.setMessage("Produto atualizado com sucesso.");
		response.setId(produto.getId());

		return ResponseEntity.ok(response);
	}

	@Operation(summary = "Serviço para excluir um produto")
	@DeleteMapping("/excluir/{id}")
	public ResponseEntity<?> excluirProduto(@PathVariable UUID id) {

		if(Objects.isNull(produtoRepository.findById(id))){
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Produto não encontrado, favor verificar o ID do produto");
		}

		ApiResponseDto response = new ApiResponseDto();
		produtoRepository.delete(id);
		response.setMessage("Produto excluído com sucesso.");


		return ResponseEntity.ok().body(response.getMessage());
	}

	@Operation(summary = "Serviço para consultar todos os produtos")
	@GetMapping("/consultar")
	@ApiResponse(responseCode = "200", description = "Sucesso",
			content = @Content(mediaType = "application/json",schema = @Schema(implementation = ProdutoResponseDto.class)))
	public ResponseEntity<?> consultarProdutos() {

		var produtos = produtoRepository.findAll();

		List<ProdutoResponseDto> response = mapper.map(produtos, new TypeToken<List<ProdutoResponseDto>>() {}.getType());
		return ResponseEntity.ok(response);

	}

	@Operation(summary = "Serviço para consultar todos os produtos")
	@GetMapping("/consultarPorNome/{nome}")
	@ApiResponse(responseCode = "200", description = "Sucesso",
			content = @Content(mediaType = "application/json",schema = @Schema(implementation = ProdutoResponseDto.class)))
	public ResponseEntity<?> consultarProdutos(@PathVariable String nome) {


		var produtos = produtoRepository.findAll(nome);
		if(!produtos.isEmpty()){

			List<ProdutoResponseDto> response = mapper.map(produtos, new TypeToken<List<ProdutoResponseDto>>() {}.getType());
			return ResponseEntity.ok(response);
		}
		else {
			return ResponseEntity.status(404).body("Nenhum produto encontrado");
		}

	}

	@Operation(summary = "Serviço para consultar um produto pelo id")
	@GetMapping("/consultar/{id}")
	public ResponseEntity<?> consultarProduto(@PathVariable UUID id) {

		Produto produto = produtoRepository.findById(id);

		if(produto == null){
			return ResponseEntity.noContent().build();
		}
		ProdutoResponseDto response = mapper.map(produto, ProdutoResponseDto.class);
		return ResponseEntity.ok(response);


	}
}
