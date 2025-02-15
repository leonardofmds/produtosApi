package br.com.coti.dtos;


import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Set;
import java.util.UUID;

class ProdutoRequestDtoTest {

    private final Validator validator;

    public ProdutoRequestDtoTest() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }


    @Test
    void testNomeValid() {
        ProdutoRequestDto produtoRequestDto = new ProdutoRequestDto();
        produtoRequestDto.setNome("Produto 1");
        produtoRequestDto.setPreco(10.00);
        produtoRequestDto.setQuantidade(10);
        produtoRequestDto.setCategoriaId(new UUID(1, 1));
        Set<ConstraintViolation<ProdutoRequestDto>> violations = validator.validate(produtoRequestDto);
        Assertions.assertTrue(violations.isEmpty());
    }

    @Test
    void testShortNome() {
        ProdutoRequestDto produtoRequestDto = new ProdutoRequestDto();
        produtoRequestDto.setNome("P");
        produtoRequestDto.setPreco(10.00);
        produtoRequestDto.setQuantidade(10);
        produtoRequestDto.setCategoriaId(new UUID(1, 1));
        Set<ConstraintViolation<ProdutoRequestDto>> violations = validator.validate(produtoRequestDto);
        Assertions.assertFalse(violations.isEmpty());

        violations.forEach(violation ->
                Assertions.assertEquals("Por favor, informe um nome entre 8 e 100 caracteres", violation.getMessage()));
    }

}