package br.com.zupacademy.neto.casadocodigo.livros;

import br.com.zupacademy.neto.casadocodigo.autor.DetalhesAutorResponse;
import br.com.zupacademy.neto.casadocodigo.categorias.Categoria;
import com.fasterxml.jackson.annotation.JsonCreator;

import java.math.BigDecimal;
import java.time.LocalDate;

public class DetalhesLivroDTO {
    
    private final String titulo;
    
    private final String resumo;

    private final String sumario;

    private final BigDecimal preco;

    private final Integer numeroPaginas;
    
    private final LocalDate dataPublicacao;

    private final Categoria categoria;

    private final DetalhesAutorResponse autor;

    private final String isbn;

    @JsonCreator
    public DetalhesLivroDTO(Livro livro) {
        this.autor = new DetalhesAutorResponse(livro.getAutor());
        this.categoria = livro.getCategoria();
        this.titulo = livro.getTitulo();
        this.resumo = livro.getResumo();
        this.preco = livro.getPreco();
        this.sumario = livro.getSumario();
        this.numeroPaginas = livro.getNumeroPaginas();
        this.dataPublicacao = livro.getDataPublicacao();
        this.isbn = livro.getIsbn();
    }

    public String getTitulo() {
        return titulo;
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

    public DetalhesAutorResponse getAutor() {
        return autor;
    }
}
