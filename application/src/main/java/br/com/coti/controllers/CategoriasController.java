package br.com.coti.controllers;

import br.com.coti.dtos.CategoriaResponseDto;
import br.com.coti.repositories.CategoriaRepository;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

/**
 * Classe de controle para a entidade Categoria
 */
@Slf4j
@RestController
@RequestMapping("/api/categorias/")
public class CategoriasController {
	
	private final ModelMapper mapper = new ModelMapper();

	/**
	 * Método para consultar todas as categorias cadastradas no sistema
	 * @return List<Categoria>
	 */
	@Operation(summary = "Serviço para consultar todas as categorias cadastradas no sistema")
	@GetMapping("/consultar")
	public ResponseEntity<?> consultar() {

		try {
			CategoriaRepository repository = new CategoriaRepository();
			var categorias =  repository.findAll();			
			var response = new ArrayList<CategoriaResponseDto>();

			if(categorias.isEmpty()) {
				return ResponseEntity.noContent().build();
			}

			for (var categoria : categorias) { 
				response.add(mapper.map(categoria, CategoriaResponseDto.class));
			}

			return ResponseEntity.ok(response);
		} catch (Exception e) {
			log.error("Erro ao consultar as categorias: " + e.getMessage());
			return ResponseEntity.badRequest().body("Erro ao consultar as categorias.");
		}
	}
}
