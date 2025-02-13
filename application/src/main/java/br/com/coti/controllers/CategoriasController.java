package br.com.coti.controllers;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.coti.dtos.CategoriaResponseDto;
import br.com.coti.repositories.CategoriaRepository;
import io.swagger.v3.oas.annotations.Operation;

/**
 * Classe de controle para a entidade Categoria
 */
@RestController
@RequestMapping("/api/categorias/")
public class CategoriasController {
	
	private ModelMapper mapper = new ModelMapper();

	/**
	 * Método para consultar todas as categorias cadastradas no sistema
	 * @return List<Categoria>
	 */
	@Operation(summary = "Serviço para consultar todas as categorias cadastradas no sistema")
	@GetMapping("/consultar")
	public List<CategoriaResponseDto> consultar() {

		try {
			CategoriaRepository repository = new CategoriaRepository();
			var categorias =  repository.findAll();			
			var response = new ArrayList<CategoriaResponseDto>();
			
			for (var categoria : categorias) { 
				response.add(mapper.map(categoria, CategoriaResponseDto.class));
			}
			
			return response;
		} catch (Exception e) {

			System.out.println(e.getMessage());
			return null;
		}
	}
}
