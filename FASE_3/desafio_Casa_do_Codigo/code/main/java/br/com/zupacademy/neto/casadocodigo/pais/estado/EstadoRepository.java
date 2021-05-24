package br.com.zupacademy.neto.casadocodigo.pais.estado;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface EstadoRepository extends JpaRepository<Estado, Integer> {
    @Query("SELECT E FROM Estado E WHERE E.nome = ?1 AND E.pais.id = ?2")
    Optional<Estado> findByName_PaisId(String nome, Integer idPais);
}
