package com.blogpessoal.Turma34.controladores;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blogpessoal.Turma34.modelos.Usuario;
import com.blogpessoal.Turma34.repositorios.UsuarioRepositorio;

@RestController
@RequestMapping("/api/v1/usuario")
public class UsuarioControlador {

	private @Autowired UsuarioRepositorio repositorio;
	
	@GetMapping("/todes")
	public ResponseEntity<List<Usuario>> pegarTodes(){
		List<Usuario> objetoLista = repositorio.findAll();
		
		if (objetoLista.isEmpty()) {
			return ResponseEntity.status(204).build();
		} else {
			return ResponseEntity.status(200).body(objetoLista);
		}
	}
	
	@GetMapping("/{id_usuario}")
	public ResponseEntity<Usuario> pegarPorId(@PathVariable(value = "id_usuario") Long idUsuario ){
		Optional<Usuario> objetoOptional = repositorio.findById(idUsuario);
		
		if (objetoOptional.isPresent()) {
			return ResponseEntity.status(200).body(objetoOptional.get());
		} else {
			return ResponseEntity.status(204).build();
		}
	}
}