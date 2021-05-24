package br.com.zupacademy.neto.casadocodigo.categorias;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriaRepository extends JpaRepository<Categoria, Integer> {
	Optional<Categoria> findByNome(String nome);
	Optional<Categoria> findById(Integer id);

}
