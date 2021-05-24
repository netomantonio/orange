package br.com.zupacademy.neto.casadocodigo.categorias;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
public class Categoria {
	
	private @Id @GeneratedValue(strategy = GenerationType.IDENTITY) Integer id;
	private @NotBlank @Column(unique = true) String nome;
	
	@Deprecated
	public Categoria() {
		
	}
	
	public Categoria(@NotBlank String categoria) {
		this.nome = categoria;
	}

	@Override
	public String toString() {
		return "Categoria [nome=" + nome + "]";
	}

	public Integer getId() {
		return id;
	}

	public String getNome() {
		return nome;
	}
}
