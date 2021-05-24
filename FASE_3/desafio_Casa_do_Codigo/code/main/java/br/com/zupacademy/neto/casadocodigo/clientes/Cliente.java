package br.com.zupacademy.neto.casadocodigo.clientes;

import br.com.zupacademy.neto.casadocodigo.pais.Pais;
import br.com.zupacademy.neto.casadocodigo.pais.estado.Estado;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Entity
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer Id;

    @NotBlank
    private String nome;
    @NotBlank
    private String sobrenome;

    @Email
    @Column(unique = true)
    private String email;

    @Column(unique = true)
    @NotBlank
    private String documento;

    @NotBlank
    private String telefone;

    @NotBlank
    private String endereco;

    @NotBlank
    private String complemento;

    @NotBlank
    private String cidade;


    @ManyToOne
    private Pais pais;

    @ManyToOne
    private Estado estado;


    @NotBlank
    private String cep;

    @Deprecated
    public Cliente(){}

    public Cliente(String nome, String sobrenome, String email, String documento, String telefone, String endereco, String complemento, String cidade, Pais pais, String cep) {
        this.nome = nome;
        this.sobrenome = sobrenome;
        this.email = email;
        this.documento = documento;
        this.telefone = telefone;
        this.endereco = endereco;
        this.complemento = complemento;
        this.cidade = cidade;
        this.pais = pais;
        this.cep = cep;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public Integer getId() {
        return Id;
    }
}
