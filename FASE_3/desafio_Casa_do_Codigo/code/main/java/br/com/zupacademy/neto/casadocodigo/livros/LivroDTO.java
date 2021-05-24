package br.com.zupacademy.neto.casadocodigo.livros;

import br.com.zupacademy.neto.casadocodigo.validacoes.ExisteID;
import br.com.zupacademy.neto.casadocodigo.validacoes.Unique;
import br.com.zupacademy.neto.casadocodigo.autor.Autor;
import br.com.zupacademy.neto.casadocodigo.categorias.Categoria;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;

import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;

public class LivroDTO {

    @Unique(domainClass = Livro.class, fieldName = "titulo")
    private String titulo;

    @NotBlank
    @Size(max=500)
    private String resumo;


    private String sumario;

    @Positive
    @Min(20)
    private BigDecimal preco;

    @Positive
    @Min(100)
    private Integer numeroPaginas;

//    @ISBN
    @Unique(domainClass = Livro.class, fieldName = "isbn")
    private String isbn;

    @Future @JsonFormat(pattern = "dd/MM/yyyy", shape = JsonFormat.Shape.STRING)
    private LocalDate dataPublicacao;

    @NotNull @Positive @ExisteID(domainClass = Categoria.class)
    private Integer categoria;


    @Positive @NotNull @ExisteID(domainClass = Autor.class)
    private Integer autor;


    @JsonCreator
    public LivroDTO(String titulo, String resumo, String sumario, BigDecimal preco, Integer numeroPaginas, String isbn, LocalDate dataPublicacao, Integer categoria, Integer autor) {
        this.titulo = titulo;
        this.resumo = resumo;
        this.sumario = sumario;
        this.preco = preco;
        this.numeroPaginas = numeroPaginas;
        this.isbn = isbn;
        this.dataPublicacao = dataPublicacao;
        this.categoria = categoria;
        this.autor = autor;
    }

    public Integer getCategoria() {
        return categoria;
    }

    public Integer getAutor() {
        return autor;
    }

    public Livro toModel(Autor autor, Categoria categoria) {
        return new Livro(this.titulo,this.resumo,this.sumario, this.preco, this.numeroPaginas, this.isbn, this.dataPublicacao, autor, categoria);
    }
}
