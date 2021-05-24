package br.com.zupacademy.neto.casadocodigo.clientes;

import br.com.zupacademy.neto.casadocodigo.erros.ResourceNotFoundException;
import br.com.zupacademy.neto.casadocodigo.pais.Pais;
import br.com.zupacademy.neto.casadocodigo.pais.PaisRepository;
import br.com.zupacademy.neto.casadocodigo.pais.estado.Estado;
import br.com.zupacademy.neto.casadocodigo.pais.estado.EstadoRepository;
import br.com.zupacademy.neto.casadocodigo.validacoes.Unique;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Null;
import java.util.Optional;

public class ClienteRequest {
    @Email
    @Unique(domainClass = Cliente.class, fieldName = "email", message = "se liga no email lok")
    private final String email;

    @NotBlank
    private final String nome;

    @NotBlank
    private final String sobrenome;

    @NotBlank
//    @CPForCNPJ(message = "ta errado fiao")
//    @Unique(domainClass = Cliente.class, fieldName = "email", message = "Estelionato é crime xomano!")
    private final String documento;

    @NotBlank
    private final String endereco;

    @NotBlank
    private final String complemento;

    @NotBlank
    private final String cidade;

    @NotBlank
    private final Integer pais;

    @Null
    private final Integer estado;


    @NotBlank
    private final String cep;
    @NotBlank
    private final String telefone;

    public ClienteRequest(String email, String nome, String sobrenome, String documento, String endereco, String complemento, String cidade, Integer pais, Integer estado, String telefone, String cep) {
        this.email = email;
        this.nome = nome;
        this.sobrenome = sobrenome;
        this.documento = documento;
        this.endereco = endereco;
        this.complemento = complemento;
        this.cidade = cidade;
        this.pais = pais;
        this.estado = estado;
        this.telefone = telefone;
        this.cep = cep;
    }

    public String getEmail() {
        return email;
    }

    public String getNome() {
        return nome;
    }

    public String getSobrenome() {
        return sobrenome;
    }

    public String getDocumento() {
        return documento;
    }

    public String getEndereco() {
        return endereco;
    }

    public String getComplemento() {
        return complemento;
    }

    public String getCidade() {
        return cidade;
    }

    public Integer getPais() {
        return pais;
    }

    public Integer getEstado() {
        return estado;
    }

    public String getTelefone() {
        return telefone;
    }

    public String getCep() {
        return cep;
    }

    public Cliente toValidationModel(PaisRepository paisRepository, EstadoRepository estadoRepository) {
        Optional<Pais> pais = paisRepository.findById(this.pais);
        if(!pais.isPresent()){
            throw  new ResourceNotFoundException("País informado não existe para ID:"+this.pais);
        }
        Cliente newCliente = new Cliente(this.nome, this.sobrenome, this.email, this.documento, this.telefone, this.endereco, this.complemento, this.cidade, pais.get(), this.cep);

        if (this.estado != null){
            Optional<Estado> estado = estadoRepository.findById(this.estado);
            newCliente.setEstado(estado.get());
        }

        return newCliente;
    }

}
