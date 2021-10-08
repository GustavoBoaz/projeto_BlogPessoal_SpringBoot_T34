package com.blogpessoal.Turma34.controladores;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.blogpessoal.Turma34.modelos.Usuario;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestInstance(Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UsuarioControladorTest {

	private @Autowired TestRestTemplate testRestTemplate;

	private Usuario usuarioParaCriar;
	private Usuario usuarioParaAlterar;

	@BeforeEach
	void start() {

		usuarioParaCriar = new Usuario("Ruan Boaz", "ruan@email.com", "134652");
		usuarioParaAlterar = new Usuario(1L, "Ruan Boaz ALTERADO", "ruan@email.com", "134652");

	}

	@Test
	@Order(1)
	void salvarUsuarioRetornaStatus201() {

		HttpEntity<Usuario> requisicao = new HttpEntity<Usuario>(usuarioParaCriar);

		ResponseEntity<Usuario> response = testRestTemplate.exchange("/api/v1/usuario/salvar", HttpMethod.POST,
				requisicao, Usuario.class);

		assertEquals(HttpStatus.CREATED, response.getStatusCode());

	}

	@Test
	@Order(2)
	@DisplayName("👍 Listar todos os Usuários!")
	void buscarTodesRetornaStatus200() {

		ResponseEntity<String> response = testRestTemplate.withBasicAuth("ruan@email.com", "134652")
				.exchange("/api/v1/usuario/todes", HttpMethod.GET, null, String.class);

		assertEquals(HttpStatus.OK, response.getStatusCode());

	}

	@Test
	@Order(3)
	void atualizarUsuarioNoBancoRetornaStatus201() {

		HttpEntity<Usuario> requisicao = new HttpEntity<Usuario>(usuarioParaAlterar);

		ResponseEntity<Usuario> response = testRestTemplate.withBasicAuth("ruan@email.com", "134652")
				.exchange("/api/v1/usuario/atualizar", HttpMethod.PUT, requisicao, Usuario.class);

		assertEquals(HttpStatus.OK, response.getStatusCode());

	}

}
