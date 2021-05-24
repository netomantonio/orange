package br.com.zupacademy.neto.casadocodigo.pais.estado;

import br.com.zupacademy.neto.casadocodigo.erros.ResourceNotFoundException;
import br.com.zupacademy.neto.casadocodigo.pais.Pais;
import br.com.zupacademy.neto.casadocodigo.pais.PaisRepository;
import com.fasterxml.jackson.annotation.JsonCreator;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Optional;

public class EstadoRequest {

    @NotBlank
    private final String nome;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public EstadoRequest(String nome) {
        this.nome = nome;
    }

    public Estado toModel(@NotNull PaisRepository paisRepository, @NotNull EstadoRepository estadoRepository, @NotBlank Integer idPais){

        Optional<Pais> possivelPais = paisRepository.findById(idPais);
        if (!possivelPais.isPresent()) {
            throw new ResourceNotFoundException("País não enntrado para o ID: "+idPais);
        }
        Optional<Estado> estadoPais = estadoRepository.findByName_PaisId(this.nome, idPais);

        if (estadoPais.isPresent()) {
            throw new ResourceNotFoundException("O estado de "+this.nome+ " já existe para o "+possivelPais.get().getNome());
        }


        return new Estado(this.nome, possivelPais.get());
    }

    public String getNome() {
        return nome;
    }
}
