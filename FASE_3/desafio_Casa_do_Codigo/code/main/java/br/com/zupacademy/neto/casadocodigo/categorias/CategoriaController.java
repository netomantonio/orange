package br.com.zupacademy.neto.casadocodigo.categorias;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/categorias")
public class CategoriaController {
	
	@Autowired
	CategoriaRepository categoriaRepository;
	

	@PostMapping
	@Transactional
	public void cadastrar(@RequestBody @Valid CategoriaDTO novaCategoria) {
		Categoria categoria = novaCategoria.toModel();
		categoriaRepository.save(categoria);
		
	}
}
