package br.com.zupacademy.neto.casadocodigo.autor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import br.com.zupacademy.neto.casadocodigo.validacoes.Unique;


public class AutorDTO {
	
	@NotBlank(message = "Nome obrigatoio")
	private String nome;
	@NotBlank(message = "Ja foi avisado que tem que preencher tudo zé oreia")
	@Email(message = "Bota o email cabeça de bagre")
	@Unique(domainClass = Autor.class, fieldName = "email")
	private String email;
	@NotBlank
	@Size(max = 400, min = 20)
	private String descricao;
	
	public AutorDTO(@NotBlank(message = "Nome obrigatório") String nome, @NotBlank @Email String email, @NotBlank @Size(max = 400) String descricao) {
		super();
		this.nome = nome;
		this.email = email;
		this.descricao = descricao;
	}

	public Autor toModel() {
		return new Autor(this.nome, this.email, this.descricao);
	}
	

}
