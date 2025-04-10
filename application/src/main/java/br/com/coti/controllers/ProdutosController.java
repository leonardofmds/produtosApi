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

import java.util.ArrayList;
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

		var response = new ApiResponseDto();

		try {
			if(Objects.isNull(categoriaRepository.findById(request.getCategoriaId()))) {
				//return ResponseEntity.status(404).body("Categoria não encontrada, verifique o ID informado");
				response.setMessage("Categoria náo encontrada, verifique o ID informado");
				response.setStatusCode(400);
			}
			else{
				var produto = mapper.map(request, Produto.class);
				produto.setId(UUID.randomUUID());

				produtoRepository.create(produto, request.getCategoriaId());

				response.setMessage("Produto cadastrado com sucesso");
				response.setId(produto.getId());
				response.setStatusCode(201);
				//return ResponseEntity.created(new URI("")).body("Produto cadastrado com sucesso.");
			}


		} catch (Exception e) {
			log.error("Erro ao cadastrar o produto: {}", e.getMessage());
			// Não se responde com a exceção, pois a mensagem pode conter informações sensíveis
			//return ResponseEntity.internalServerError().body("Erro ao cadastrar o produto: " + e.getMessage());
			response.setMessage("Erro ao cadastrar o produto: ");
			response.setStatusCode(500);
		}
		return ResponseEntity.status(response.getStatusCode()).body(response);
	}

	@Operation(summary = "Serviço para atualizar um produto")
	@PutMapping("/atualizar/{id}")
	public ResponseEntity<?> atualizarProduto(@RequestBody @Valid ProdutoRequestDto request, @PathVariable UUID id) {

		var response = new ApiResponseDto();

		try {
			if(Objects.isNull(categoriaRepository.findById(request.getCategoriaId()))){
				response.setMessage("Categoria não encontrada, verifique o ID informado");
				response.setStatusCode(400);
			}
			else if(Objects.isNull(produtoRepository.findById(id))) {
				response.setMessage("Produto não encontrado, verifique o ID informado");
				response.setStatusCode(400);
			}
			else {
				var produto = mapper.map(request, Produto.class);
				produto.setId(id);

				produtoRepository.update(produto);

				response.setMessage("Produto atualizado com sucesso.");
				response.setStatusCode(200);
				response.setId(produto.getId());
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			response.setMessage("Erro ao atualizar produto: " + e.getMessage());
			response.setStatusCode(500);
		}
		return ResponseEntity.status(response.getStatusCode()).body(response);
	}

	@Operation(summary = "Serviço para excluir um produto")
	@DeleteMapping("/excluir/{id}")
	public ResponseEntity<?> excluirProduto(@PathVariable UUID id) {
		var response = new ApiResponseDto();

		try {
			if(Objects.isNull(produtoRepository.findById(id))){
				response.setMessage("Produto não encontrado, favor verificar o ID do produto");
				response.setStatusCode(400);
			}
			else {
				produtoRepository.delete(id);
				response.setMessage("Produto excluído com sucesso.");
				response.setStatusCode(200);
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			response.setMessage("Erro ao excluir produto: " + e.getMessage());
			response.setStatusCode(500);
		}
		return ResponseEntity.status(response.getStatusCode()).body(response.getMessage());
	}

	@Operation(summary = "Serviço para consultar todos os produtos")
	@GetMapping("/consultar")
	@ApiResponse(responseCode = "200", description = "Sucesso",
			content = @Content(mediaType = "application/json",schema = @Schema(implementation = ProdutoResponseDto.class)))
	public ResponseEntity<?> consultarProdutos() {

		var response = new ArrayList<ProdutoResponseDto>();

		try {
			var produtos = produtoRepository.findAll();
			if(!produtos.isEmpty()){
				response = mapper.map(produtos, new TypeToken<List<ProdutoResponseDto>>() {}.getType());
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			return ResponseEntity.internalServerError().body("Erro ao consultar os produtos: " + e.getMessage());
		}
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

	@Operation(summary = "Serviço para consultar produtos pelo nome")
	@GetMapping("/consultarPorNome/{nome}")
	@ApiResponse(responseCode = "200", description = "Sucesso",
			content = @Content(mediaType = "application/json",schema = @Schema(implementation = ProdutoResponseDto.class)))
	public ResponseEntity<?> consultarProdutos(@PathVariable String nome) {

		var response = new ArrayList<ProdutoResponseDto>();

		try {
			var produtos = produtoRepository.findAll(nome);
			if(!produtos.isEmpty()){
				 response = mapper.map(produtos, new TypeToken<List<ProdutoResponseDto>>() {}.getType());
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			return ResponseEntity.internalServerError().body("Erro ao consultar os produtos: " + e.getMessage());
		}
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

	@Operation(summary = "Serviço para consultar um produto pelo id")
	@GetMapping("/consultarPorId/{id}")
	public ResponseEntity<?> consultarProduto(@PathVariable UUID id) {
		ProdutoResponseDto response;


		try {
			var produto = produtoRepository.findById(id);
			if(produto!=null) {
				response = mapper.map(produto, ProdutoResponseDto.class);
			}
			else {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Produto não encontrado, verifique o ID informado");
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			return ResponseEntity.internalServerError().body("Erro ao consultar produto: " + e.getMessage());
		}
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
}

