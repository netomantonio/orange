# Pagination

Utilizar a interface PagingAndSortingRepository na interface criada que representa o repositório da classe. E atualizar os métodos que recebem List, para receber um Page, também dever ser passado um objeto Pageable para a consulta.


Nas aulas anteriores vimos como buscar funcionários pelo nome usando o método `findByNome`:

```
@Repository
public interface FuncionarioRepository extends PagingAndSortingRepository <Funcionario, Integer> {

    List<Funcionario> findByNome(String nome);

    //outros métodos omitidos
}
```

Será que a paginação também funciona com esse tipo de método? Claro que sim, basta passar o *Pageable* como parâmetro. Veja o exemplo:

```
@Repository
public interface FuncionarioRepository extends PagingAndSortingRepository <Funcionario, Integer> {

    List<Funcionario> findByNome(String nome);

    //novo método com paginação
    List<Funcionario> findByNome(String nome, Pageable pageable);

    //outros métodos omitidos
}
```

A criação do objeto `Pageable` fica como foi explicado no vídeo usando o `PageRequest`. Lembrando também que a interface `FuncionarioRepository` deve estender o `PagingAndSortingRepository`.

Quando utilizamos o repositório `PagingAndSortingRepository`, adicionamos à nossa aplicação todo o poder da paginação. Porém, para utilizarmos de fato esse poder, devemos passar um atributo no método `findAll`.

`Pageable` - enviamos esse objeto pois nele encapsulamos a página, a quantidade de itens por página e qual o tipo de ordenação. Enviamos esse objeto como parâmetro para informarmos ao nosso repository as informações que queremos receber na nossa paginação.
