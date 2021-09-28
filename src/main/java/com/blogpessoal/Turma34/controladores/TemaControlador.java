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

import com.blogpessoal.Turma34.modelos.Tema;
import com.blogpessoal.Turma34.repositorios.TemaRepositorio;

@RestController
@RequestMapping("/api/v1/tema")
@CrossOrigin(allowedHeaders = "*", origins = "*")
public class TemaControlador {

	private @Autowired TemaRepositorio repositorio;

	@GetMapping("/todos")
	public ResponseEntity<List<Tema>> pegarTodos() {
		List<Tema> objetoLista = repositorio.findAll();

		if (objetoLista.isEmpty()) {
			return ResponseEntity.status(204).build();
		} else {
			return ResponseEntity.status(200).body(objetoLista);
		}
	}

	@GetMapping("/{id_tema}")
	public ResponseEntity<Tema> pegarPorId(@PathVariable(value = "id_tema") Long idTema) {
		Optional<Tema> objetoOptional = repositorio.findById(idTema);

		if (objetoOptional.isPresent()) {
			return ResponseEntity.status(200).body(objetoOptional.get());
		} else {
			return ResponseEntity.status(204).build();
		}
	}

	@PostMapping("/salvar")
	public ResponseEntity<Tema> salvar(@Valid @RequestBody Tema novoTema) {
		return ResponseEntity.status(201).body(repositorio.save(novoTema));

	}

	@PutMapping("/atualizar")
	public ResponseEntity<Tema> atualizar(@Valid @RequestBody Tema novoTema) {
		return ResponseEntity.status(201).body(repositorio.save(novoTema));

	}

	@DeleteMapping("/deletar/{id_tema}")
	public ResponseEntity<Tema> deletar(@PathVariable(value = "id_tema") Long idTema) {
		Optional<Tema> objetoOptional = repositorio.findById(idTema);

		if (objetoOptional.isPresent()) {
			repositorio.deleteById(idTema);
			return ResponseEntity.status(204).build();
		} else {
			return ResponseEntity.status(400).build();
		}
	}

}
