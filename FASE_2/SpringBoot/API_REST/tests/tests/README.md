# Testes automatizados com spring boot

Por padrão o spring inclui uma dependencia ao projeto do modulo de test, que é justamente utilizado para testes automatizados usando JUnit, sem precisar fazer qualquer tipo de inclusão extra, o spring também cria uma classe de teste, preparada para iniciar a qualquer momento a lógica de teste. Essa classe vem com a anotação `@SpringBootTest` essa anotação avisa para que o spring carregue todos os beans da aplicação durante os testes. A anotação `@SpringBootTest` serve para que o Spring inicialize o servidor e carregue os beans da aplicação durante a execução dos testes automatizados.

## DataJpaTest

Essa é uma anotação específica para testes de repositórios, ao inves de utilizar `@SpringBootTest` utilizar `@DataJpaTest`, pois o spring tem uma estratégia especifica preparada para testes de repositório. 

É recomendado utilizar a anotação `@DataJpaTest` em testes automatizado das interfaces **Repository** para facilitar os testes, com o controle transacional automático e para permitir a utilização do `EntityManager` de testes do próprio Spring. A anotação `@DataJpaTest` simplifica a escrita de testes automatizados de interfaces `Repository`.

## Utilizando outro BD que não H2 em memória

É possível indicamos ao Spring que os testes da interface 'Repository' devem ser realizados em outro banco de dados que **não** seja o **h2** anotando a classe de teste com`@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)`. Precisamos utilizar essa anotação para que o Spring não considere que os testes devem sempre ser executados utilizando um banco de dados em memória.

## Testando Controller

Ao criar a classe destinada aos testes de controller é necessário usar a anotação, `@SpringBootTest` e também `@AutoConfigureMockMvc` além das obrigatórias em todos os testes, como `@RunWith(SpringRunner.class)` e `@ActiveProfiles("test")` além de incluir o profile test nos profiles de classes que devem ser carregadas. E fazer a injeção de dependencia de MockMvc.

## Resumo


* É possível escrever testes automatizados de classes que são beans do Spring, como `Controllers` e `Repositories`.
* É possível utilizar injeção de dependências nas classes de testes automatizados.
* A anotação `@SpringBootTest` deve ser utilizada nas classes de testes automatizados para que o Spring inicialize o servidor e disponibilize os beans da aplicação.
* Ao testar uma interface `Repository` devemos, preferencialmente, utilizar a anotação `@DataJpaTest`.
* Por padrão, os testes automatizados dos repositories utilizam um banco de dados em memória, como o h2.
* É possível utilizar outro banco de dados para os testes automatizados, utilizando a anotação `@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)`.
* É possível forçar um profile específico para os testes automatizados com a utilização da anotação `@ActiveProfiles`.
* Para conseguir injetar o `MockMvc` devemos anotar a classe de teste com `@AutoConfigureMockMvc`.
