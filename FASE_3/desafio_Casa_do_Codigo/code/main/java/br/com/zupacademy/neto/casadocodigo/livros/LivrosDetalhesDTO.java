package br.com.zupacademy.neto.casadocodigo.livros;

import com.fasterxml.jackson.annotation.JsonCreator;
public class LivrosDetalhesDTO {
    private final Integer id;
    private final String nome;

    @JsonCreator
    public LivrosDetalhesDTO(Livro livro){
        this.id = livro.getId();
        this.nome = livro.getTitulo();
    }

    public Integer getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

}
