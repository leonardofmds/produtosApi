package br.com.coti.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.coti.entities.Categoria;
import br.com.coti.repositories.CategoriaRepository;
import io.swagger.v3.oas.annotations.Operation;

/**
 * Classe de controle para a entidade Categoria
 */
@RestController
@RequestMapping("/api/categorias/")
public class CategoriasController {

	/**
	 * Método para consultar todas as categorias cadastradas no sistema
	 * @return List<Categoria>
	 */
	@Operation(summary = "Serviço para consultar todas as categorias cadastradas no sistema")
	@GetMapping("/consultar")
	public List<Categoria> consultar() {

		try {
			CategoriaRepository repository = new CategoriaRepository();
			return repository.findAll();
		} catch (Exception e) {

			System.out.println(e.getMessage());
			return null;
		}
	}
}
