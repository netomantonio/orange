package br.com.zupacademy.neto.casadocodigo.pais;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
public class Pais {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotBlank
    @Column(unique = true)
    private String nome;

    @Deprecated
    private Pais(){}

    public Pais(@NotBlank String nome) {
        this.nome = nome;
    }

    public Integer getId() {
        return this.id;
    }

    public String getNome() {
        return nome;
    }
}
