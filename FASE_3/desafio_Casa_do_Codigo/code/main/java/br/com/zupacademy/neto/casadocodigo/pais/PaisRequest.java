package br.com.zupacademy.neto.casadocodigo.pais;

import br.com.zupacademy.neto.casadocodigo.validacoes.Unique;
import com.fasterxml.jackson.annotation.JsonCreator;

import javax.validation.constraints.NotBlank;

public class PaisRequest {

    @NotBlank
    @Unique(domainClass = Pais.class, fieldName = "nome")
    private String nome;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public PaisRequest(String nome) {
        this.nome = nome;
    }

    public Pais toModel(){
        return new Pais(this.nome);
    }
}
