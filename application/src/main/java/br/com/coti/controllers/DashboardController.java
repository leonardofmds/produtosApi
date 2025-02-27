package br.com.coti.controllers;

import br.com.coti.dtos.CategoriaQtdProdutoResponseDto;
import br.com.coti.repositories.CategoriaRepository;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/dashboard/")
public class DashboardController {

    final CategoriaRepository categoriaRepository;

    public DashboardController(CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    @Operation(summary = "Serviço para consultar o total de produtos por categoria")
    @GetMapping("totalProdutosPorCategoria")
    public ResponseEntity<?> totalProdutosPorCategoria() {

        List<CategoriaQtdProdutoResponseDto> categoriaQtdProdutoResponseDto;

        try{
            categoriaQtdProdutoResponseDto = categoriaRepository.groupByQtdProdutos();
        }
        catch(Exception e) {
            return ResponseEntity.internalServerError().body("Erro interno no servidor.");
        }
        return ResponseEntity.ok(categoriaQtdProdutoResponseDto);
    }
}
