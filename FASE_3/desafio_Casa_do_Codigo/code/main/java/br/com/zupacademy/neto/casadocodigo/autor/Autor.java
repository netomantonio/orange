package br.com.zupacademy.neto.casadocodigo.autor;

import java.time.LocalDateTime;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.hibernate.annotations.CreationTimestamp;

@Entity
public class Autor {

	private @Id @GeneratedValue(strategy = GenerationType.IDENTITY) Integer id;
	private @NotBlank String nome;
	private @NotBlank @Email @Column(unique = true) String email;
	private @NotBlank @Size(max = 400) String descricao;
	private @CreationTimestamp LocalDateTime criadoEm;

	@Deprecated
	public Autor() {

	}

	public Autor(@NotBlank String nome, @NotBlank @Email String email, @NotBlank @Size(max = 400) String descricao) {
		this.nome = nome;
		this.email = email;
		this.descricao = descricao;
		this.criadoEm = LocalDateTime.now();
	}

	@Override
	public String toString() {
		return "Autor [id=" + id + ", nome=" + nome + ", email=" + email + ", descricao=" + descricao + ", criadoEm="
				+ criadoEm + "]";
	}

	public String getEmail() {
		return this.email;
	}

	public Integer getId() {
		return id;
	}

	public String getNome() {
		return nome;
	}

	public String getDescricao() {
		return descricao;
	}
}
