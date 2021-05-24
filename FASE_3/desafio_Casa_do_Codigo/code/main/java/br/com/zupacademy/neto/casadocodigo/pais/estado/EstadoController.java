package br.com.zupacademy.neto.casadocodigo.pais.estado;

import br.com.zupacademy.neto.casadocodigo.pais.PaisRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.net.URI;

@RestController
public class EstadoController {

    private final PaisRepository paisRepository;
    private final EstadoRepository estadoRepository;

    public EstadoController(PaisRepository paisRepository, EstadoRepository estadoRepository) {
        this.paisRepository = paisRepository;
        this.estadoRepository = estadoRepository;
    }

    @RequestMapping("/api/paises/{id}/estados")
    @Transactional
    public ResponseEntity<?> cadastrar(@Valid @PathVariable("id") @NotNull Integer id,
                                       @RequestBody EstadoRequest estadoRequest,
                                       UriComponentsBuilder uriBuilder) {

        Estado novoEstado = estadoRequest.toModel(paisRepository, estadoRepository, id);
        estadoRepository.save(novoEstado);
        URI location = uriBuilder.path("/api/paises/" + id + "/estados/{id}")
                .buildAndExpand(novoEstado.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }
}
