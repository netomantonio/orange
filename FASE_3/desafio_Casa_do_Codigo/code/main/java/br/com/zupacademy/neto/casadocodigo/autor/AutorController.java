package br.com.zupacademy.neto.casadocodigo.autor;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/autores")
public class AutorController {
	
	@Autowired
	AutorRepository autorRepository;
	
	
	@PostMapping
	@Transactional
	public void cadastrar(@RequestBody @Valid AutorDTO autorDTO ) {
		Autor autorNovo = autorDTO.toModel();		
		autorRepository.save(autorNovo);
	}
}
