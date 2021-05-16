# PAGINATION

## Pageable

A vantagem de devolver um objeto `Page`, ao invés de um `List` é adicionar dados sobre a paginação no JSON de resposta. Ao utilizar o objeto `Page`, além de devolver os registros, o Spring também devolve informações sobre a paginação no JSON de resposta , como número total de registros e páginas.


## Sorting

Para ordenar os registros, é necessário passar um parâmetro com o nome do atributo para realizar a ordenação. E se o nome do atributo informado estiver incorreto um erro será devolvido ao cliente. Ao informar um atributo que não existe, um erro 500 será devolvido.

## PageableDefault

O objetivo da anotação `@PageableDefault` é indicar o padrão de paginação/ordenação ao Spring, quando o cliente da API não enviar tais informações.

## Resumo


* Para realizar paginação com Spring Data JPA, devemos utilizar a interface `Pageable`;
* Nas classes `Repository`, os métodos que recebem um `pageable` como parâmetro retornam objetos do tipo `Page<>`, ao invés de `List<>`;
* Para o Spring incluir informações sobre a paginação no JSON de resposta enviado ao cliente da API, devemos alterar o retorno do método do *controller* de `List<>` para `Page<>`;
* Para fazer a ordenação na consulta ao banco de dados, devemos utilizar também a interface `Pageable`, passando como parâmetro a direção da ordenação, utilizando a classe `Direction`, e o nome do atributo para ordenar;
* Para receber os parâmetros de ordenação e paginação diretamente nos métodos do *controller*, devemos habilitar o módulo `SpringDataWebSupport`, adicionando a anotação `@EnableSpringDataWebSupport` na classe `ForumApplication`.

# Cache

É necessário incluir a dependencia do spring-boot-starter-cache no pom.xml e anotaçao na classe de aplicação `@EnableCaching`

#### Utilizando Cache

Anotar os métodos que irão usar cache com `@Cacheable(value = 'id')` onde id será um identificador único para identificar os diferentes metodos cacheables.

A anotação `@Cacheable` possui o parâmetro `value`, do tipo `String` para indicar ao Spring o nome do cache associado a um determinado método. A string passada como parâmetro para a anotação `@Cacheable` funciona como um identificador único do cache.

## Invalidando Cache

É importante sempre limpar o cache quando à alterações nos dados do banco de dados, seja uma atualização, um novo cadastro e/ou exclusão. Para isso, é necessário informar o spring através da anotação `@CacheEvict` passando como parametro a id do cache a ser limpo no value, e passando como true allEntries, para limpar todos os registros.

* Deve ser utilizada nos métodos que alteraram os registros armazenados em cache pela API.
  * Isso é importante para evitar que os clientes obtenham informações desatualizadas.
* Deve indicar, no parâmetro `value`, quais caches devem ser invalidados
  * Nesse parâmetro devemos indicar os identificadores dos caches que deverão ser invalidados.

## Boas Práticas no uso do Cache

Cache é um recurso utilizado para dar maior performance para aplicação. Utilizar o cache em tabelas que sofrem poucas alterações ou nenhuma, para evitar que o método de limpar o cache seja chamado a todo momento, pois isso pode causar instabilidades aumentando o custo de processamento e até piorando a performance da aplicação.

### Resumo


* Para utilizar o módulo de cache do *Spring Boot*, devemos adicioná-lo como dependência do projeto no arquivo **pom.xml**;
* Para habilitar o uso de caches na aplicação, devemos adicionar a anotação `@EnableCaching` na classe `ForumApplication`;
* Para que o Spring guarde o retorno de um método no cache, devemos anotá-lo com `@Cacheable`;
* Para o Spring invalidar algum cache após um determinado método ser chamado, devemos anotá-lo com `@CacheEvict`;
* Devemos utilizar cache apenas para as informações que nunca ou raramente são atualizadas no banco de dados.
