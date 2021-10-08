package com.blogpessoal.Turma34.repositorios;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

import com.blogpessoal.Turma34.modelos.Usuario;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestInstance(Lifecycle.PER_CLASS)
class UsuarioRepositorioTest {
	
	private @Autowired UsuarioRepositorio repositorio;

	@BeforeEach
	void start() {
		
		Usuario objetoUsuario1 = new Usuario("Amanda Boaz", "amanda@email.com", "134652");
		if(!repositorio.findByEmail(objetoUsuario1.getEmail()).isPresent())
			repositorio.save(objetoUsuario1);
		
		Usuario objetoUsuario2 = new Usuario("João Boaz", "joao@email.com", "134652");
		if(!repositorio.findByEmail(objetoUsuario2.getEmail()).isPresent())
			repositorio.save(objetoUsuario2);
		
	}
	
	@Test
	void findByEmailExistReturnTrue() {
		Optional<Usuario> optionalAmanda = repositorio.findByEmail("amanda@email.com");
		
		assertTrue(optionalAmanda.get().getEmail().equals("amanda@email.com"));
		
	}
	
	@Test
	void findAllByNomeContainingIgnoreCaseReturnTwoUsuario() {
		List<Usuario> objetoLista = repositorio.findAllByNomeContainingIgnoreCase("Boaz");
		
		assertEquals(2, objetoLista.size());
		
	}
	
	@AfterAll
	void end() {
		System.out.println("Teste FINALIZADO!");
	}

}
