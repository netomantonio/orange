package br.com.zupacademy.neto.casadocodigo.livros;

import br.com.zupacademy.neto.casadocodigo.autor.Autor;
import br.com.zupacademy.neto.casadocodigo.categorias.Categoria;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/livros")
public class LivrosController {

    @PersistenceContext
    EntityManager em;

    @PostMapping
    @Transactional
    public void cadastrar(@RequestBody @Valid LivroDTO livroDto) {

        Autor autor = em.find(Autor.class, livroDto.getAutor());
        Categoria categoria = em.find(Categoria.class, livroDto.getCategoria());

        Livro novoLivro = livroDto.toModel(autor, categoria);

        em.persist(novoLivro);
    }

    @GetMapping
    @Transactional
    public List<LivrosDetalhesDTO> listar() {
        List<Livro> livros = em.createQuery("select l from Livro l").getResultList();
        List<LivrosDetalhesDTO> lista = new ArrayList<>();
        for (Livro livro : livros) {
            lista.add(new LivrosDetalhesDTO(livro));
        }
        return lista;
    }

    @GetMapping(value = "/{id}")
    @Transactional
    public ResponseEntity<?> detalhar(@PathVariable("id") Integer id) {

        Livro novoLivro = em.find(Livro.class, id);

        if (novoLivro == null){
            return new ResponseEntity<>("Não existe livro com o id="+id+" em nosso sistema",HttpStatus.NOT_FOUND);
        }
        DetalhesLivroDTO livroDetalhado = new DetalhesLivroDTO(novoLivro);
        return new ResponseEntity<DetalhesLivroDTO>(livroDetalhado, HttpStatus.OK);

    }

}