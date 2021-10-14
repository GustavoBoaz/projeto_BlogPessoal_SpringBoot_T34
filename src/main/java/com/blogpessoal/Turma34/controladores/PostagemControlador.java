package com.blogpessoal.Turma34.controladores;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blogpessoal.Turma34.modelos.Postagem;
import com.blogpessoal.Turma34.repositorios.PostagemRepositorio;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/api/v1/postagem")
@Api(tags = "Controlador de Postagem", description = "Utilitario de Postagens")
@CrossOrigin(allowedHeaders = "*", origins = "*")
public class PostagemControlador {

	private @Autowired PostagemRepositorio repositorio;

	@ApiOperation(value = "Busca lista de postagens no sistema")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Retorna lista de postagens")
	})
	@GetMapping("/todas")
	public ResponseEntity<List<Postagem>> pegarTodas() {
		return ResponseEntity.ok(repositorio.findAll());

	}

	@ApiOperation(value = "Busca postagem por Id")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Retorna postagem existente"),
			@ApiResponse(code = 204, message = "Retorno inexistente")
	})
	@GetMapping("/{id_postagem}")
	public ResponseEntity<Postagem> pegarPorId(@PathVariable(value = "id_postagem") Long idPostagem) {
		return repositorio.findById(idPostagem).map(resp -> ResponseEntity.ok(resp))
				.orElse(ResponseEntity.noContent().build());

	}

	@ApiOperation(value = "Salva nova postagem no sistema")
	@ApiResponses(value = {
			@ApiResponse(code = 201, message = "Retorna postagem cadastrada")
	})
	@PostMapping("/salvar")
	public ResponseEntity<Postagem> salvar(@Valid @RequestBody Postagem novaPostagem) {
		return ResponseEntity.status(201).body(repositorio.save(novaPostagem));

	}

	@ApiOperation(value = "Atualizar postagem existente")
	@ApiResponses(value = {
			@ApiResponse(code = 201, message = "Retorna postagem atualizada")
	})
	@PutMapping("/atualizar")
	public ResponseEntity<Postagem> atualizar(@Valid @RequestBody Postagem novaPostagem) {
		return ResponseEntity.status(201).body(repositorio.save(novaPostagem));

	}

	@ApiOperation(value = "Deletar postagem existente")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Caso deletada!"),
			@ApiResponse(code = 400, message = "Id de postagem invalida")
	})
	@DeleteMapping("/deletar/{id_postagem}")
	public ResponseEntity<Postagem> deletar(@PathVariable(value = "id_postagem") Long idPostagem) {
		Optional<Postagem> objetoOptional = repositorio.findById(idPostagem);

		if (objetoOptional.isPresent()) {
			repositorio.deleteById(idPostagem);
			return ResponseEntity.status(200).build();
		} else {
			return ResponseEntity.status(400).build();
		}
	}

}
