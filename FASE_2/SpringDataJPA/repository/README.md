# CRUD

No framework do Spring Data, quais métodos são utilizados para quais operações?
Para inserir ou atualizar usamos o método `save` o método `save` serve para entidades *transient* e *detached*.

obs.: caso esteja com dúvidas, por favor consulte a [documentação](https://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/repository/CrudRepository.html).

Qual a finalidade de utilizarmos o `Optional` retornado pelo método `findById`? O comando pode retornar um elemento, se existir, por isso é usado `Optional`. O `findById` sempre devolve um `Optional`, que sabe se o elemento existe ou não. Assim, não precisamos lidar com `null` ou tratar uma exceção.

Caso tenha interesse de saber mais sobre outros repositórios que o framework disponibiliza, você pode acessar a documentação do Spring Data através [deste link](https://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/repository/Repository.html).

## Resumo

* como utilizar a ferramenta de repositório do framework;
* inserir um registro dinâmico na base de dados;
* atualizar registros salvos;
* deletar um registro por meio do seu ID;
* visualizar todos os registros salvos.

# Tipos de Repositórios

Existem diversos tipos de interfaces de repositórios disponíveis pela JpaRepository em SpringData, porém é necessário analisar qual delas satisfaz a sua necessidade.
