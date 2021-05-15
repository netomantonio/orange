# Anotações Gerais

Jackson -> cuida da parte de pegar os dados e enviar em formato serializado para o cliente.

## ResponseBody

Por padrão, o Spring considera que o retorno do método é o nome da página que ele deve carregar, mas ao utilizar a anotação `@ResponseBody`, indicamos que o retorno do método deve ser *serializado* e devolvido no corpo da resposta, essa anotação se torna desnecessária quando utilizado `@RestController` ao invés de `@Controller` como anotação da classe.

## RestController

Anotação para indicar ao spring que a classe é do tipo rest e deve retornar o conteúdo no corpo da resposta. Ao contrário de `@Controller` que o spring interpreta como esperado uma view, ou seja, uma pagina HTML.

## DevTools

O módulo **DevTools** inclui ferramentas utilitárias no projeto, dentre elas a **Automatic Restart**, que reinicia o servidor automaticamente ao detectar alterações no código fonte da aplicação.

## O que é Rest

***Representational State Transfer*** é um modelo de arquitetura para sistemas distribuídos. Usado para integração de sistemas via web e é baseado em alguns conceitos.

* Recursos
* Identificador de recursos (URI)
* Manipulação de recursos (Verbos HTTP)
* Representação de recursos (Json, XML, HTML, PDF, XHTML . . .)
* Comunicação Stateless


## Resumo


* Sobre a API que desenvolveremos ao longo do curso e sobre as classes de domínio dela;
* Que, para um método no *controller* não encaminhar a requisição a uma página `JSP`, ou `Thymeleaf`, devemos utilizar a anotação `@ResponseBody`;
* Que o Spring, por padrão, converte os dados no formato JSON, utilizando a biblioteca `Jackson`;
* Que, para não repetir a anotação `@ResponseBody` em todos os métodos do *controller*, devemos utilizar a anotação `@RestController`;
* Que, para não precisar reiniciar manualmente o servidor a cada alteração feita no código, basta utilizar o módulo **Spring Boot DevTools**;
* Que não é uma boa prática retornar entidades JPA nos métodos dos *controllers*, sendo mais indicado retornar classes que seguem o padrão **DTO** (*Data Transfer Object*);
* Os principais conceitos sobre o modelo arquitetural **REST**, como **recursos**, **URIs**, **verbos HTTP**, **Representações** e comunicação ***stateless***.


# Spring Data JPA

Módulo spring para trabalhar com a interação com banco de dados.

## Application Properties

No arquivo **application.properties**, devem ser declaradas as configurações da aplicação, inclusive as relacionadas ao banco de dados dela.

#### Exemplo de arquivo application.properties

```
# data source
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.url=jdbc:h2:mem:alura-forum
spring.datasource.username=sa
spring.datasource.password=

# jpa
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
spring.jpa.hibernate.ddl-auto=update

# h2
spring.h2.console.enabled=true
spring.h2.console.path=/h2-console
```

#### Autowired

Anotação necessário para fazer injeção de dependencias.

## Repository

uma aplicação que utiliza o Spring Data JPA, o acesso ao banco de dados é feito com a criação de uma interface, seguindo o padrão ***Repository***.

```
public interface TopicoRepository 
    extends JpaRepository<Topico, Long>{}
```

A interface `TopicoRepository` está herdando da interface correta do `Spring Data JPA`.

## Consultas com Filtro

O Spring Data JPA consegue gerar a *query* de consulta ao banco de dados baseado no nome do método na classe *repository*. As alternativas a seguir apresentam o correto padrão de nomenclatura ao filtrar por um atributo de um relacionamento em uma entidade.

É possível declarar o nome do método concatenando nele o nome do atributo que representa o relacionamento, seguido do nome do atributo a ser filtrado.

```
findByCursoNome(String nomeCurso);
```

Também é válido separar o nome do atributo, que representa o relacionamento, do nome do atributo a ser filtrado, com o uso do operador `_`.

findByCurso_Nome(String nomeCurso);
## Resumo


* Para utilizar o JPA no projeto, devemos incluir o módulo `Spring Boot Data JPA`, que utiliza o **Hibernate**, por padrão, como sua implementação;
* Para configurar o banco de dados da aplicação, devemos adicionar as propriedades do *datasource* e do JPA no arquivo **`src/main/resources/application.properties`**;
* Para acessar a página de gerenciamento do banco de dados H2, devemos configurar o *console* do H2 com propriedades no arquivo **`src/main/resources/application.properties`**;
* Para mapear as classes de domínio da aplicação como entidade JPA, devemos utilizar as anotações `@Entity`, `@Id`, `@GeneratedValue`, `@ManyToOne`, `@OneToMany` e `@Enumerated`;
* Para que o Spring Boot popule automaticamente o banco de dados da aplicação, devemos criar o arquivo **`src/main/resources/data.sql`**;
* Para criar um *Repository*, devemos criar uma interface, que herda da interface `JPARepository` do Spring Data JPA;
* Para criar consultas que filtram por atributos da entidade, devemos seguir o padrão de nomenclatura de métodos do Spring, como por exemplo `findByCursoNome`;
* Para criar manualmente a consulta com JPQL, devemos utilizar a anotação `@Query`;

# POST

É necessário anotar o parâmetro do método `cadastrar` com a anotação `@RequestBody`. Para indicar ao Spring que os parâmetros enviados no corpo da requisição devem ser atribuídos ao parâmetro do método.

## Boas práticas

* ResponseEntity\<DTO\>
* UriCOmponentBuilder

## Retorno VOID

Se algum método em uma classe `Controller` não tiver retorno, ou seja, se ele tiver retorno `void`, o Spring devolverá o código HTTP 200 (*OK*), caso a requisição seja processada com sucesso. Para métodos `void`, será devolvida uma resposta sem conteúdo, juntamente com o código HTTP 200 (*OK*), caso a requisição seja processada com sucesso.

## Request

É necessário adicionar um cabeçalho na requisição, chamado `Content-Type`  que serve exatamente para indicar o tipo de conteúdo sendo enviado ao servidor.

## Resumo


* Que para evitar repetir a URL em todos os métodos, devemos utilizar a anotação `@RequestMapping` em cima da classe *controller*;
* Que para mapear requisições do tipo POST, devemos utilizar a anotação `@PostMapping`;
* Que para receber dados enviados no corpo da requisição, a boa prática é criar uma classe que também siga o padrão **DTO** (*Data Transfer Object*);
* Que a boa prática para métodos que cadastram informações é devolver o código HTTP 201, ao invés do código 200;
* Que para montar uma resposta a ser devolvida ao cliente da API, devemos utilizar a classe `ResponseEntity` do Spring;
* Que para testar requisições do tipo POST, precisamos utilizar alguma ferramenta de testes de API Rest;
* Como utilizar o Postman para testar uma API Rest;

# Bean Validation

Para fazer esse tipo de validação de formulários, de campo obrigatório, tamanho mínimo, tamanho máximo, letra, número e etc., existe uma especificação do Java, que é o tal do Bean Validation. E o Spring se integra com essa especificação. Podemos utilizá-lo e a validação será toda feita por anotações, fica muito mais fácil. Em cima dos atributos, posso colocar as anotações do Bean Validation: `@NotNull`, para dizer ao Spring que o campo não pode ser nulo e `@NotEmpty` para dizer que esse campo não pode ser vazio. Posso também indicar um número mínimo de caracteres com `@Length(min = 5)`. Essas anotações fazem parte do Bean Validation que posso utilizar para fazer a validação.

## Validações com Bean Validation

O Bean Validation também é flexível. Você pode criar uma nova anotação, caso não tenha. Por exemplo, você quer validar um campo CPF. Você pode criar uma anotação `@CPF` e ensinar como é essa validação. É uma especificação bem bacana e simples de trabalhar.

Para realizar validações de formulário, utilizando a especificação ***Bean Validation***. É necessário anotar o parâmetro, no método enviado, com a anotação `@Valid`. Para Indicar ao Spring para executar as validações do *Bean Validation* no parâmetro do método.

## Simplificando JSON

O Spring tem uma solução para esse tipo de cenário. A solução é criar uma espécie de interceptador.

O objetivo de se utilizar a anotação `@ResponseStatus` na classe que representa o `RestControllerAdvice` é alterar o *status code* devolvido como resposta. O *status code* padrão a ser devolvido será o 200, mas é possível modificá-lo com a anotação `@ResponseStatus`.

## Resumo


* Para fazer validações das informações enviadas pelos clientes da API, podemos utilizar a especificação ***Bean Validation***, com as anotações `@NotNull`, `@NotEmpty`, `@Size`, dentre outras;
* Para o Spring disparar as validações do *Bean Validation* e devolver um erro 400, caso alguma informação enviada pelo cliente esteja inválida, devemos utilizar a anotação `@Valid`;
* Para interceptar as *exceptions* que forem lançadas nos métodos das classes *controller*, devemos criar uma classe anotada com `@RestControllerAdvice`;
* Para tratar os erros de validação do *Bean Validation* e personalizar o JSON, que será devolvido ao cliente da API, com as mensagens de erro, devemos criar um método na classe `@RestControllerAdvice` e anotá-lo com `@ExceptionHandler` e `@ResponseStatus`.

# PUT, DELETE e tratamento de erros

## Paths dinÂmicos

É possível definir um ***path*** com partes dinâmicas. A maneira correta para se definir um *path* dinâmico no Spring é `GetMapping("/{id}")` . A parte dinâmica do *path* deve ser declarada entre chaves.

## Controle Transacional

Ao utilizar a anotação `@Transactional` está informando ao spring que deve ser comitada a transação se for bem sucedida.

## PUT

Para implementar a funcionalidade para atualizar um recurso, no método do *controller* é necessário adicionar a anotação `@Transactional`. Os objetivos dessa anotação é:

* Efetuar o *commit* automático da transação, caso não ocorra uma *exception*
  * Ao finalizar o método, o Spring efetuará o *commit* automático da transação, caso nenhuma *exception* tenha sido lançada.
* Executar o método dentro de um contexto transacional
  * Métodos anotados com `@Transactional` serão executados dentro de um contexto transacional.

## DELETE

A anotação `@DeleteMapping` é mais apropriada para se utilizar nos métodos de exclusão de recursos.

## Erro 404

### Tratamento

É importante fazer um tratamento para quando um recurso não for encontrado, devolvendo nesses casos o código HTTP 404. Para evitar que a *exception* seja devolvida para o cliente no corpo da resposta. Não é interessante devolver *exceptions* e *stack traces* para os clientes, em casos de erros na API Rest.

## Resumo


* Para receber parâmetros dinâmicos no *path* da URL, devemos utilizar a anotação `@PathVariable`;
* Para mapear requisições do tipo `PUT`, devemos utilizar a anotação `@PutMapping`;
* Para fazer o controle transacional automático, devemos utilizar a anotação `@Transactional` nos métodos do *controller*;
* Para mapear requisições do tipo `DELETE`, devemos utilizar a anotação `@DeleteMapping`;
* Para tratar o erro 404 na classe *controller*, devemos utilizar o método `findById`, ao invés do método `getOne`, e utilizar a classe `ResponseEntity` para montar a resposta de *not found*;
* O método `getOne` lança uma *exception* quando o `id` passado como parâmetro não existir no banco de dados;
* O método `findById` retorna um objeto `Optional<>`, que pode ou não conter um objeto.


# Exercício


Antes de pularmos para a parte da atividade onde você vai precisar descrever como faria determinada implementação, vamos começar pelo básico. Qual a utilidade do Spring Boot?

Resposta: Framework para facilitar a criação de aplicações santd-alone, fornecendo uma série de recursos para minimizar o processo de configuração de várias camadas de uma aplicação além de prover um servidor embutido.

Agora, precisamos começar a construir um sistema para suportar nosso modelo de avaliações e respostas do Orange Talents. Para esse primeiro momento precisamos cadastrar novos(as) alunos(as) e também conseguir ver os detalhes de cada pessoa cadastrada.

Para a parte do cadastro, todo(a) aluno(a) precisa de um nome, email e idade. O nome não pode ter mais de 30 caracteres, o email também só pode ter no máximo 30 caracteres e a idade precisa ser maior ou igual a 18 anos. É importante que esses dados sejam cadastrados no banco de dados.

Usando o que foi visto durante o curso,  descreva todos os passos que você faria para conseguir receber os dados, validar, fazer com que as informações sejam persistidas no banco de dados e retornar um status 200 para a aplicação cliente em caso de sucesso ou 400 em caso de falha de validação.

Agora que o cadastro foi feito, é necessário que os detalhes de cada aluno(a) possam ser acessados. Uma restrição importante aqui, a identificação do(a) aluno(a) será feita pelo id do banco de dados e deve fazer parte do endereço de acesso. Para o detalhe, só precisamos exibir o nome e o email.

Usando o que foi visto durante o curso,  descreva todos os passos que você faria desde conseguir tratar a requisição feita para determinado endereço até retornar as informações do(a) aluno(a) em formato JSON.
