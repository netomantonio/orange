package br.com.zupacademy.neto.casadocodigo.pais;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/api/paises")
public class PaisController {

    private final PaisRepository repository;

    public PaisController(PaisRepository repository){
        this.repository = repository;
    }

    @PostMapping
    @Transactional
    public ResponseEntity<?> cadastrar(@RequestBody @Valid PaisRequest paisRequest, UriComponentsBuilder uriBuilder){
        Pais novoPais = paisRequest.toModel();
        repository.save(novoPais);
        URI location = uriBuilder.path("/api/paises/{id}")
                .buildAndExpand(novoPais.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }
}
