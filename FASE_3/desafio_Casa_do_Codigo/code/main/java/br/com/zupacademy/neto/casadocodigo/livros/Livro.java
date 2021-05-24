package br.com.zupacademy.neto.casadocodigo.livros;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.hibernate.validator.constraints.ISBN;

import br.com.zupacademy.neto.casadocodigo.autor.Autor;
import br.com.zupacademy.neto.casadocodigo.categorias.Categoria;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
public class Livro {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@NotBlank
	@Column(unique = true)
	private String titulo;
	
	@NotBlank
	@Size(max = 500)
	private String resumo;
	
	@NotBlank
	@Lob
	private String sumario;
	
	@Positive
	private BigDecimal preco;
	
	@Positive
	private Integer numeroPaginas;
	
//	@ISBN
	@Column(unique = true)
	private String isbn;


	private LocalDate dataPublicacao;
	
	@ManyToOne
	@NotNull
	private Categoria categoria;
	
	@ManyToOne
	@NotNull
	private Autor autor;

	@Deprecated
	public Livro (){

	}

	public Livro(String titulo, String resumo, String sumario, BigDecimal preco, Integer numeroPaginas, @ISBN String isbn, LocalDate dataPublicacao, Autor autor, Categoria categoria) {
				this.titulo = titulo;
				this.resumo = resumo;
				this.sumario = sumario;
				this.preco = preco;
				this.numeroPaginas = numeroPaginas;
				this.isbn = isbn;
				this.dataPublicacao = dataPublicacao;
				this.autor = autor;
				this.categoria = categoria;
	}

	@Override
	public String toString() {
		return "Livro [id=" + id + ", titulo=" + titulo + ", resumo=" + resumo + ", sumario=" + sumario + ", preco="
				+ preco + ", numeroPaginas=" + numeroPaginas + ", isbn=" + isbn + ", dataPublicacao=" + dataPublicacao
				+ ", categoria=" + categoria + ", autor=" + autor + "]";
	}
	public Integer getId(){
		return this.id;
	}

	public String getTitulo(){
		return this.titulo;
	}

	public String getResumo() {
		return resumo;
	}

	public String getSumario() {
		return sumario;
	}

	public BigDecimal getPreco() {
		return preco;
	}

	public Integer getNumeroPaginas() {
		return numeroPaginas;
	}

	public String getIsbn() {
		return isbn;
	}

	public LocalDate getDataPublicacao() {
		return dataPublicacao;
	}

	public Categoria getCategoria() {
		return categoria;
	}

	public Autor getAutor() {
		return autor;
	}
}
