# Spring Data

Estrutura de funcionamento

* Spring Data
  * JPA
  * Hibernate
  * JDBC
  * Driver

O Spring Data junta todas as implementações em um unico módulo, facilitando a manipulação de dados do banco.

Dentro da nossa classe `SpringDataAplication` temos nosso método `main`, que no Java é utilizado para iniciar uma aplicação. Dentro desse método adicionamos uma linha relacionada ao Spring:

```
SpringApplication.run(SpringDataApplication.class, args)
```

Ela faz com que o framework do Spring seja inicializado junto a nossa aplicação. Precisamos inicializar o framework para conseguirmos utilizar suas ferramentas dentro da nossa aplicação. Além disso, existem algumas anotações como a `@SpringBootApplication` que devemos usar.

## Usando CrudRepository

Qual a principal vantagem que temos em utilizar a interface `CrudRepository` dentro do nosso projeto?

Realizar métodos CRUD sem a necessidade de criar os objetos do JPA. Sem a necessidade de criar nenhum objeto do JPA, conseguimos realizar métodos do CRUD.

### Injeção de dependências

O Spring ficou famoso por causa do **Injeção de dependências** (ou *Dependency Injection (DI)*)mas não foi o primeiro framework nem é o único que oferece esse recurso.

Hoje em dia a **DI** é amplamente usada e outros frameworks bem comuns, são [Guice](https://github.com/google/guice) da Google e o [CDI](https://jakarta.ee/specifications/cdi/2.0/cdi-spec-2.0.html) do Jakarta EE.

Se estiver com dúvidas ainda, vale assistir o [video da Alura+ sobre](https://cursos.alura.com.br/injecao-de-dependencias-o-que-e--c224).

## Resumo

* qual a stack necessária para nosso projeto;
* configurar nosso projeto Spring para conectar ao banco de dados;
* criar tabelas de forma automática e alterar o nome delas conforme a necessidade da aplicação;
* utilizar os repositórios do framework para trazer agilidade nas nossas operações de CRUD.


# Exercício


Precisamos possibilitar a inserção, atualização, exibição e remoção de uma avaliação criada por mentores(as). Além disso, vamos querer buscar as avaliações pesquisando pelos títulos delas.

Descreva exatamente quais passos você faria para possibilitar que tais operações fossem feitas usando o Spring Data JPA aproveitando o máximo de coisas prontas da tecnologia.

Começaria preparando a aplicação para utilizar o módulo do Spring Data adicionando as dependencias no pom.xml. Criaria uma interface AvaliacaoRepository extendendo extendendo a interface CrudRepository de spring data, como parametro a classe correspondente a avaliação e o tipo do atributo id da classe modelo de avaliação e criaria um método que retorna uma lista de avaliação usando a nomenclatura findByTitulo passando uma string com o nome do título.

Pergunta bônus: Se você só cria interface no Spring Data JPA, onde está a real implementação? Está dentro do módulo Spring Data que prove todos os recursos já implementados, desde que use os padrões exigidos.
