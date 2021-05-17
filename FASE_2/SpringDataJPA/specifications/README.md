# Specifications

É uma feature do Spring Data para realização de querys dinâmicas. Abistraindo a maior parte do código que deve ser usado na construção de querys dinâmicas usando apenas JPA.

Para utilizar esses recursos é preciso incluir mais uma interface na interface de repositório da classe, essa interface é `JpaSpecificationExecutor<T>` e criar uma classe especifica para as especificações.

Quando criamos consultas dinâmicas, utilizamos a `Specification`. Qual é o seu papel na criação das consultas dinâmicas?

Ter um objeto com todos os itens necessários para realizar uma consulta dinâmica, como por exemplo `root`, `criteriaQuery` e `criteriaBuilder`. O objetivo é entregar, ao desenvolver um objeto pronto, para que ele só tenha que se preocupar com qual operação SQL ele deseja executar.

# NoSQL


É importante ressaltar que o framework do Spring Data permite a utilização de banco de dados relacionais, conforme estamos aprendendo, entretanto, ele também permite o uso de banco de dados não relacionais. Vamos ver como podemos utilizar o framework com o MongoDB, considerando que existem outras possibilidades de uso para bancos não relacionais.

Quando queremos utilizar um banco de dados não relacional, não há necessidade de adicionarmos a dependência do JPA, nem mesmo do drive do banco, pois o Spring já entrega para nós uma dependência com tudo o que for necessário para acessarmos esse terminado banco. Por exemplo, o MongoDB utiliza a seguinte dependência:

```
<dependency>
 <groupId>org.springframework.boot</groupId>
 <artifactId>spring-boot-starter-data-mongodb</artifactId>
</dependency>
```

Apesar do acesso ao banco dentro do arquivo de propriedade ser bem semelhante, as tags mudam um pouco, sai o:

```
spring.datasource.url=jdbc:mariadb://{URL}:{PORTA}/{NOME_DO_BANCO}
```

E entra a tag:

```
spring.data.mongodb.uri: mongodb://{URL}:{PORTA}/{NOME_DO_BANCO}
```

> obs.: Em alguns bancos não relacionais, é muito comum adicionar o usuário e senha na própria URI, entretanto o Spring também nos dá a opção de adicionarmos os valores de forma apartada:

```
spring.data.mongodb.username=root
spring.data.mongodb.password=root
spring.data.mongodb.database=test_db
spring.data.mongodb.port=27017
spring.data.mongodb.host=localhost
```

Com a alteração para um banco de dados não relacional, deixamos de lado nosso `CrudRepository`, pois o Spring nos entrega um repositório específico para cada tipo de banco de dados não relacional, e dentro dele já temos todos os recursos encapsulados.

No caso do Mongo, utilizamos a interface `MongoRepository`. Esse repositório segue o mesmo princípio dos demais, sendo necessário passar no diamante o objeto que desejamos manipular, e o tipo do seu ID. Pronto! Basta utilizar esses passos que sua aplicação vai trabalhar perfeitamente com banco de dados não relacionais.
