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
	 * Metodo utilizado para atualizar usuario no banco
	 * 
	 * @param usuarioParaAtualizar do tipo Usuario
	 * @return Optional com Usuario atualizado
	 * @author Turma34
	 * @since 1.0
	 * 
	 */
	public Optional<Usuario> atualizarUsuario(Usuario usuarioParaAtualizar){
		return repositorio.findById(usuarioParaAtualizar.getIdUsuario()).map(resp -> {
			resp.setNome(usuarioParaAtualizar.getNome());
			resp.setSenha(usuarioParaAtualizar.getSenha());
			return Optional.ofNullable(repositorio.save(resp));
		}).orElseGet(() -> {
			return Optional.empty();
		});
	}
}
