# História do SQL

## Vantagens

* Aprendizado
* Portabilidade
* Longevidade
* Comunicação
* Liberdade de Escolha

## Desvantagens

* Falta de criatividade
* Falta de estruturação

## Comandos

* DDL - Manipulação das estruturas do banco de dados (create, update, delete de tabelas, colunas e relacionamentos)
* DML - Manipulação dos dados ( incluir, excluir e editar dados das tabelas e consulta aos dados)
* DCL - Linguagem para controle de regras da base de dados

# História do MySQL

### Características

* Servidor
* Portabilidade
* Multithreads
* Formas de Armazenamento
* Velocidade
* Desempenho
* Segurança
* Capacidade
* Aplicabilidade
* Logs


# Introdução ao MySQL

## Estrutura de um BD (Banco de Dados)

### Tabelas, Colunas, Registros e Índice

A maior entidade é o banco de dados, e esse é composto por diversas entidades, que estruturam os dados dentro do banco. A principal entidade é a famosa Tabela, composta por linhas e colunas, onde as colunas na maioria das vezes representa o tipo de dado, e as linhas, as ocorrências ( registros ) em muitos casos é necessário garantir a integridade dos registros, para isso utiliza-se alguns recurso e o principal deles é a utilização de Chave Primária, em ingles as Primary Key (PK). Não bastasse isso, dentro de um banco de dados existem diversas tabelas e muitas vezes é necessário que exista um relacionamento entre elas, para que que isso seja possível, existe as chaves estrangeiras em ingles as Foreign Keys, que são PKs de outras tabelas que servem para referencia-las onde necessário, criando assim um relacionamento, uma ligação direta, entre duas ou mais tabelas. Também existe o Índice, esse são necessários para facilitar as buscas de registros de uma tabela, ao indexar os registros, existe uma lógica que deve ser implementada de acordo com cada caso, mas o objetivo será sempre o mesmo, ter acesso de forma mais rápida aos registros, sem que seja necessário percorrer todas as linhas de uma tabela para encontrar um ou mais registros.

### Schemes

Esquemas são maneiras de subdividir o banco de dados por área de interesse, assunto, tópico. Seu objetivo é organizar melhor as tabelas e seus relacionamentos.

### View

As views são agrupamentos de tabelas, de forma simplista, as views são tabelas virtuais geradas apartir de um conjunto de dados oriundos de mais de uma tabela, cujo objetivo é expressar um conjunto de dados relacionados de forma mais direta, facilitando as consultas.

### Joins

Os joins são utilizados para unir dados de tabelas, proporcionando um resultado com dados mais completos, e normalmente são utilizados na construção das views.

### Procedures

São um conjunto de comandos que permitem que um SGBD possa executar comandos de programação estruturada, esses comandos são condicionais, laços de repetição utilizando a linguagem nativa do banco de dados. As procedures são préviamente compiladas pelo SQL e são executadas apartir do código précompilado sempre que são chamadas, podem ter parâmetros de entra e saída e nem sempre é necessário ter um valor de retorno, além de poderem invocar funcions dentro da procedure.

## Functions

As funções são muito semelhantes as procedures, porém existem diferenças importantes, por exemplo, diferentemente das procedures, as funções são compiladas e executadas no momento que são chamadas, possuem apenas parâmetros de entrada, devem sempre retornar um valor e não podem invocar procedures.


## Functions vs Procedures


##### A diferença básica


* Function deve retornar um valor, mas em stored o retorno é opcional.
* As functions podem ter apenas parâmetros de entrada. As storeds podem ter parâmetros de entrada / saída.
* Functions podem ser chamadas de dentro de storeds, já as storeds não podem ser chamados a partir de functions.

##### Diferenças avançadas

* Procedures não podem ser utilizadas em uma instrução SELECT enquanto que a function pode ser incorporado em uma instrução SELECT.
* Procedures não podem ser chamadas e/ou utilizadas nas instruções de SQL em qualquer lugar do WHERE/HAVING/SELECT, enquanto que a function pode ser.
* A característica mais importante de storeds procedures em relação a functions é a de retenção e reutilização do plano de execução, enquanto no caso da função que irá ser compilada cada vez que for executada.
* Functions que retornam tabelas podem ser tratadas como um outro conjunto dados. Isto quer dizer que podemos utilizá-las em associações (JOINS) com outras tabelas.
* Exceções podem ser tratadas por bloco try-catch em um storeds enquanto bloco try-catch não pode ser usado em uma function.
* Podemos utilizar Transaction Management em um stored, e não em uma function.

## Trigger

Triggers são gatilhos, que implementam uma lógica de disparo, e sempre que algo préviamente estabelecido aconteça, é disparado o trigger e alguma ação é executada.


# Exercício


Dado que todo(a) aluno(a) tem um email(máximo de 30 caracteres),nome(máximo de 30 caracteres) e idade(entre 1 e 100). O que você faria para representar essa estrutura no banco?

**Resposta:** Usaria o comando abaixo:

`CREATE TABLE aluno ( email VARCHAR(30) PRIMARY KEY, nome VARCHAR(30), idade SMALLINT UNSIGNED)`

**Motivo:** Para ser armazenar alguma informação no banco de dados é necessário mapear os dados, criando uma tabela e coluna relacionada aos dados a serem armazenados, o email foi escolhido como primary key, pois emails são unicos o que os elegem a ser chave primária. o tipo escolhido foi varchar (30) pois varchar tem uma dinâmica melhor, pois não adiciona espaços em branco caso o tamanho máximo não seja atingido, e para idade o smallint, pois armazena uma quantidade de numeros suficientes para idades entre 1 e 100 e unsigned para que não seja salvo números negativos.

* O que você precisa fazer agora para inserir novos(as) alunos(as) nessa tabela?

**Resposta:** usaria o comando `INSERT INTO aluno (email, nome idade) VALUES ('antonio.neto@zup.com.br', 'Antonio Miranda Neto', 33)`

**Motivo:** Poderia escolher usar a ferramenta de interface gráfica para fazer o procedimento, mas escolhi usar uma query por ser mais prática e rápida de executar.

* E para listar tudo que está registrado?

**Resposta:** usaria o comando `select * from aluno`

**Motivo:** Ao invés de usar apenas `*` poderia especificar o nome de cada coluna separados por virgula que surtiria o mesmo efeito, mas pela praticidade escolhi usar de forma simplificada.

* E para apagar um registro pelo email?

**Resposta:** usaria o comando `DELETE FROM aluno WHERE email = 'email@zup.com.br'`

**Motivo:** Outra maneira de apagar dados de uma tabela é unsando o `TRUNCATE` porém esse apagaria todos os dados, como existe uma condição que é por email, escolhi usar o comando `DELETE` com `WHERE` colocando a condição do email.

* Agora você precisa buscar todos os(as) alunos(as) que tem Zup no email. Como você faria?

**Resposta:** usaria o comando `SELECT * FROM aluno WHERE email LIKE '%zup.com.br'`

**Motivo:** para dar match e mostrar apenas os alunos que tem zup no email foi usado a condição `WHERE <coluna> LIKE <pattern>` junto com o comando de `SELECT` que tem como objetivo mostrar apenas os alunos cujo email termine com zup.com.br.

* E para fechar é necessário que alunos e alunas sejam listados pela sua idade em ordem crescente.

**Resposta:** usaria o comando `SELECT * FROM aluno ORDER BY idade ASC`

**Motivo:** para que seja mostrado os alunos ordenados por idade de forma crescente utilizei o comando `ORDER BY idade ASC` pois é a forma mais simples de fazer.
