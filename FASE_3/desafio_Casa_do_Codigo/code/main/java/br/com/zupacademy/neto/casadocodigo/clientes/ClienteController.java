package br.com.zupacademy.neto.casadocodigo.clientes;


import br.com.zupacademy.neto.casadocodigo.pais.PaisRepository;
import br.com.zupacademy.neto.casadocodigo.pais.estado.EstadoRepository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

    private final ClienteRepository clienteRepository;
    private final PaisRepository paisRepository;
    private final EstadoRepository estadoRepository;

    public ClienteController(ClienteRepository clienteRepository, PaisRepository paisRepository, EstadoRepository estadoRepository){

        this.clienteRepository = clienteRepository;
        this.paisRepository = paisRepository;
        this.estadoRepository = estadoRepository;
    }

    @PostMapping
    @Transactional
    public String cadastrar(@RequestBody ClienteRequest clienteRequest){

        Cliente novoCliente = clienteRequest.toValidationModel(paisRepository, estadoRepository);
        clienteRepository.save(novoCliente);

        return ("{\"id\":\""+novoCliente.getId()+"\"}");
    }
}
