package br.com.zupacademy.neto.casadocodigo.autor;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AutorRepository extends JpaRepository<Autor, Integer> {
	Optional<Autor> findByEmail(String email);
	Optional<Autor> findById(Integer id);
}
