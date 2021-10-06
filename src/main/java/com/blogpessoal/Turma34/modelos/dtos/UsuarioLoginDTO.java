package com.blogpessoal.Turma34.modelos.dtos;

import javax.validation.constraints.NotBlank;

/**
 * Classe Espelho para logar no sistema
 * 
 * @author Turma34
 *
 */
public class UsuarioLoginDTO {

	private @NotBlank String email; // Necessario Para Login
	private @NotBlank String senha; // Necessario Para Login

	private Long idUsuario;
	private String nome;
	private String token;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public Long getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Long idUsuario) {
		this.idUsuario = idUsuario;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

}
