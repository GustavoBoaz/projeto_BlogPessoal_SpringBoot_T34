package com.blogpessoal.Turma34.servicos;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blogpessoal.Turma34.modelos.Usuario;
import com.blogpessoal.Turma34.repositorios.UsuarioRepositorio;

@Service
public class UsuarioServicos {

	private @Autowired UsuarioRepositorio repositorio;

	/**
	 * Metodo utilizado para cadastrar usuario validando duplicidade de email no
	 * banco
	 * 
	 * @param usuarioParaCadastrar do tipo Usuario
	 * @return Optional com Usuario cadastrado caso email não seja existente
	 * @author Turma34
	 * @since 2.0
	 * 
	 */
	public Optional<Object> cadastrarUsuario(Usuario usuarioParaCadastrar) {
		return repositorio.findByEmail(usuarioParaCadastrar.getEmail()).map(usuarioExistente -> {
			return Optional.empty();
		}).orElseGet(() -> {
			return Optional.ofNullable(repositorio.save(usuarioParaCadastrar));
		});

	}

	/**
	 * Metodo utilizado para cadastrar usuario validando duplicidade de email no
	 * banco
	 * 
	 * @param usuarioParaCadastrar do tipo Usuario
	 * @return Optional com Usuario cadastrado caso email não seja existente
	 * @author Turma34
	 * @since 1.5
	 * 
	 */
	public Optional<Object> cadastrarUsuarioI(Usuario usuarioParaCadastrar) {
		return repositorio.findByEmail(usuarioParaCadastrar.getEmail()).map(usuarioExistente -> Optional.empty())
				.orElse(Optional.ofNullable(repositorio.save(usuarioParaCadastrar)));

	}

	/**
	 * Metodo utilizado para cadastrar usuario validando duplicidade de email no
	 * banco
	 * 
	 * @param usuarioParaCadastrar do tipo Usuario
	 * @return Optional com Usuario cadastrado caso email não seja existente
	 * @author Turma34
	 * @since 1.0
	 * 
	 */
	public Optional<Object> cadastrarUsuarioII(Usuario usuarioParaCadastrar) {
		Optional<Usuario> objetoOptional = repositorio.findByEmail(usuarioParaCadastrar.getEmail());

		if (objetoOptional.isPresent()) {
			return Optional.empty();
		} else {
			return Optional.ofNullable(repositorio.save(usuarioParaCadastrar));
		}

	}
}
