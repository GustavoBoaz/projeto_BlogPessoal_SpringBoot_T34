package com.blogpessoal.Turma34.servicos;

import java.nio.charset.Charset;
import java.util.Optional;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.blogpessoal.Turma34.modelos.Usuario;
import com.blogpessoal.Turma34.modelos.dtos.CredenciaisDTO;
import com.blogpessoal.Turma34.modelos.dtos.UsuarioLoginDTO;
import com.blogpessoal.Turma34.repositorios.UsuarioRepositorio;

@Service
public class UsuarioServicos {

	private @Autowired UsuarioRepositorio repositorio;

	/**
	 * Metodo utilizado para cadastrar usuário validando duplicidade de email no
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
			BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
			String senhaCriptografada = encoder.encode(usuarioParaCadastrar.getSenha());
			usuarioParaCadastrar.setSenha(senhaCriptografada);
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
	public Optional<Usuario> atualizarUsuario(Usuario usuarioParaAtualizar) {
		return repositorio.findById(usuarioParaAtualizar.getIdUsuario()).map(resp -> {
			resp.setNome(usuarioParaAtualizar.getNome());
			resp.setSenha(usuarioParaAtualizar.getSenha());
			return Optional.ofNullable(repositorio.save(resp));
		}).orElseGet(() -> {
			return Optional.empty();
		});
	}

	/**
	 * Metodo utilizado para pegar credenciais do usuario com Tokem (Formato Basic),
	 * este método sera utilizado para retornar ao front o token utilizado para ter
	 * acesso aos dados do usuario e mantelo logado no sistema
	 * 
	 * @param usuarioParaAutenticar do tipo UsuarioLoginDTO necessario email e senha
	 *                              para validar
	 * @return ResponseEntity com CredenciaisDTO preenchido com informações mais o Token
	 * @since 1.0
	 * @author Turma34
	 */
	public ResponseEntity<CredenciaisDTO> pegarCredenciais(UsuarioLoginDTO usuarioParaAutenticar) {
		return repositorio.findByEmail(usuarioParaAutenticar.getEmail()).map(resp -> {
			BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

			if (encoder.matches(usuarioParaAutenticar.getSenha(), resp.getSenha())) {

				String estruturaBasic = usuarioParaAutenticar.getEmail() + ":" + usuarioParaAutenticar.getSenha(); // gustavoboaz@gmail.com:134652
				byte[] estruturaBase64 = Base64.encodeBase64(estruturaBasic.getBytes(Charset.forName("US-ASCII"))); // hHJyigo-o+i7%0ÍUG465sas=-
				String tokenBasic = "Basic " + new String(estruturaBase64); // Basic hHJyigo-o+i7%0ÍUG465sas=-

				CredenciaisDTO objetoCredenciaisDTO = new CredenciaisDTO();
				
				objetoCredenciaisDTO.setIdUsuario(resp.getIdUsuario());
				objetoCredenciaisDTO.setNome(resp.getNome());
				objetoCredenciaisDTO.setEmail(resp.getEmail());
				objetoCredenciaisDTO.setSenha(resp.getSenha());
				objetoCredenciaisDTO.setToken(tokenBasic);

				return ResponseEntity.status(201).body(objetoCredenciaisDTO); // Usuario Credenciado
			} else {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Senha Incorreta!"); // Senha incorreta
			}
		}).orElseGet(() -> {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email não existe!"); // Email não existe
		});

	}
}
