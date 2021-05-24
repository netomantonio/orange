package br.com.zupacademy.neto.casadocodigo.autor;

public class DetalhesAutorResponse {
    private final String descricao;
    private final String nome;

    public DetalhesAutorResponse(Autor autor) {
        nome = autor.getNome();
        descricao = autor.getDescricao();
    }

    public String getDescricao() {
        return descricao;
    }

    public String getNome() {
        return nome;
    }
}
