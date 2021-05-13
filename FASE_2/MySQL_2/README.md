# FILTROS

Para realizar buscas num banco de dados, é necessário conhecer a estrutura do banco de dados, ou seja, suas tabelas e relacionamentos, bem como seus campos e tipos. Conhecendo toda essa estrutura é possível aplicar filtros para refinar a busca por dados. Muitas IDEs próprias para banco de dados, fornecem uma função para gerar um diagrama, nesse diagrama é possível visualizar toda estrutura mencionada assim de forma visual, para facilitar o entendimento.

Para incluir um filtro de pesquisa, é necessário usar a palavra reservada `WHERE` seguida de uma condição lógica referente aos termos de busca, quando essa condição resultar em verdadeiro, os dados serão mostrados, caso seja falso, nada será mostrado. 

A expressão abaixo é verdadeira ou falsa?

```
(NOT ((3 > 2) OR (4 >= 5)) AND (5 > 4) ) OR (9 > 0)
```

verdadeira

## DISTINCT

Ao usar o parametro distinct ao realizar uma consulta, os resultados serão aprensentados somente com os valores diferentes, evitando duplicidade de conteúdo. Para usar esse parâmetro deve se colocar na posição correta, que é logo após o select e antes dos campos. Ex: `SELECT DISTINCT * FROM TABELA WHERE CONDITION`


## Limit

O limit é um limitador de quantidade, seu uso no mysql é restrito para a ultima posição da query de consulta, deve sempre ser o ultimo parâmetro. Ex: selecionar os 3 primeiros bairros da cidade do rio de janeiro que existem clientes em ordem crescente. Query: `select distinct bairro from tabela_de_clientes where cidade = 'rio de janeiro' order by bairro asc limit 3`

Queremos obter as 10 primeiras vendas do dia 01/01/2017. Qual seria o comando SQL para obter este resultado?

`select * from notas_fiscais where data_venda = '2017-01-01' order by data_venda limit 10`


# GROUP BY

Apresenta o resultado agrupando valores numéricos por uma chave de critério. Ex: Queremos agrupar pelo campo X e somar os valores em Y. `SELECT X, SUM(Y) FROM TABLE GROUP BY X`

Para realizar um group by, não necessáriamente precisa usar `SUM()`, o que podemos usar?

* SUM: soma
* MAX: máximo
* MIN: minimo
* AVG: média
* COUNT: quantidade de ocorrências

Caso a query de consulta tenha apenas um campo no select e esse corresponde a uma das funções anteriores, não é necessário usar Group By, porém caso exista mais que um campo é necessário utilizar.


# HAVING

O having é uma condição (filtro) que se aplica ao resultado de uma agregação. Ex: `SELECT X, SUM(Y) FROM TAB GROUP BY X` sabendo que essa query vai resultar em uma tabela de valores Y somados para cada X, podemos aplicar o HAVING para filtrar esse valores, por exemplo para selecionar somente aquilo que for maior ou igual 3, para isso ficaria assim: `SELECT X, SUM(Y) FROM TAB GROUP BY X HAVING SUM(Y) >= 3`


# CASE

Serve para fazer testes em um ou mais campos e, dependendo do resultado, teremos um valor relacionado.

```
CASE
    WHEN <CONDITION-1> THEN <VALUE-1>
    WHEN <CONDITION-2> THEN <VALUE-2>
    (...)
    WHEN <CONDITION-N> THEN <VALUE-N>
    ELSE VALUE
END
```

# JOINS

Possibilidade de unir uma ou mais tabelas através de campos em comum.

para isso existem vários tipos disponíveis, os quais são:

* inner join ( apenas os resultados que existem relacionamentos entre as tabelas )
  * Ex: ```SELECT YEAR(DATA_VENDA), SUM(QUANTIDADE * PRECO) AS FATURAMENTO FROM notas_fiscais NF INNER JOIN itens_notas_fiscais INF ON NF.NUMERO = INF.NUMERO GROUP BY YEAR(DATA_VENDA)```
* left join ( todos os resultados da tabela da esquerda + os resultados da direita que existem relacionamento com a tabela da esquerda )
  * Ex: ```SELECT DISTINCT A.CPF, A.NOME, B.CPF FROM tabela_de_clientes A LEFT JOIN notas_fiscais B ON A.CPF = B.CPF WHERE B.CPF IS NULL AND YEAR(B.DATA_VENDA) = 2015```
* right join ( todos os resultados da tabela da direita + os resultados da esquerda que existem relacionamento com a tabela da direita)
  * Ex: `SELECT DISTINCT A.CPF, A.NOME, B.CPF FROM notas_fiscais B RIGHT JOIN tabela_de_clientes A ON A.CPF = B.CPF WHERE B.CPF IS NULL`
* full join ( todos os resultados da direita e esquerda )
  * O MySQL não implementa o full join, porém é possível obter o mesmo resultado utilizando UNION
* cross join ( retorna o produto cartesiano entre as tabelas )
  * Ex: `SELECT vendedores.BAIRRO, vendedores.NOME, vendedores.DE_FERIAS, clientes.BAIRRO, clientes.NOME FROM tabela_de_vendedores AS vendedores, tabela_de_clientes AS clientes`

Ex: Obter o faturamento anual da empresa

```
SELECT YEAR(DATA_VENDA), SUM(QUANTIDADE * PRECO) AS FATURAMENTO
FROM notas_fiscais NF INNER JOIN itens_notas_fiscais INF 
ON NF.NUMERO = INF.NUMERO
GROUP BY YEAR(DATA_VENDA)
```

# UNION

Faz a união de duas ou mais tabelas, é importante que as tabelas que serão unidas tenham o mesmo número e tipo de campo e seu comportamento inclui o DISTINCT, caso essa condição não seja satisfatória deve-se usar o UNION ALL.


# Sub consultas

São consultas realizadas dentro de uma consulta maior, e serão executadas primeiro, pois a consulta depende do resultado da subconsulta, em linhas gerais, uma subconsulta pode ser interpretada como uma tabela pela consulta hierarquicamente acima da subconsulta.


# Functions (MySQL)


## Funções Escalares

São funções que manipulam textos.

* CONCAT() - utilizado para concatenar strings
* LTRIM() - remove espaços a esquerda de uma string
* RTRIM() - remove espaços a direita de uma string
* TRIM() - remove espaços da esquerda e direita
* LCASE()  ou LOWER() - deixa string minuscula
* UCASE() ou UPPER() - deixa string maiuscula
* SUBSTRING() - remove um pedaço de dentro de uma string
* LENGTH() - retorna o tamanho de uma string em bytes


## Funções de Data

São funções de manipulação de datas.

* CURDATE() - retorna a data do dia atual do servidor
* CURRENT_TIME() - retorna a hora atual do servidor
* CURRENT_TIMESTAMP() - retorna data e hora atual do servidor
* YEAR(), DAY(), MONTH(), DAYNAME() - extrai de uma data o ano, dia, mês e dia da semana respectivamente
* DATEDIFF() - retorna a diferença entre duas datas em dias

## Funções Matemáticas

Funções para manipulação de dados numérios.

* AVG() - retorna a média aritmética de um conjunto de valores numéricos
* SUM() - retorna a soma
* MIN() - retorna o menor valor
* MAX() - retorna o maior valor
* CEILING() - para arredondar um valor para o primeiro valor inteiro acima
* ROUND() - para arredondar um valor para o primeiro valor inteiro mais próximo
* FLOOR() - para arredondar um valor para o primeiro valor inteiro abaixo
* RAND() - gera um valor aleatório
* SQRT() - retorna a raiz quadrada

# Exercício

Dado que todo(a) aluno(a) tem um email (máximo de 30 caracteres),nome (máximo de 30 caracteres) e idade (entre 1 e 100). O que você faria para representar essa estrutura no banco?

Resposta: Criaria uma tabela chamada aluno com os campos requeridos, incluiria um campo ID do tipo numérico auto_increment para ser a chave primária.

Motivo: Ao colocar ID numérico é devido a maior facilidade do SGBD lidar com numeros, auto_increment para que a cada novo registro um numero unico sequencial seja gerado.

Dentro do bootcamp temos também um conjunto de avaliações que são respondidas pelas pessoas. Toda avaliação tem um título e uma descrição do que precisa ser feito. O que você faria para representar essa estrutura no banco?

Resposta: Criaria uma tabela chamada avaliacao com os campos requeridos e incluiria o campo ID do tipo numerico auto_increment e chave primária.

Cada aluno(a) responde uma ou mais avaliações que chamamos de cognitive walkthrough, ela tem que descrever os passos da solução dela para determinada situação problema. Toda resposta tem um campo aberto para que a pessoa consiga descrever a solução dela. É necessário que toda resposta seja linkada com a pessoa que a respondeu e também com a avaliação relativa àquela resposta. O que você faria para representar essa estrutura no banco de dados?

Resposta: Criaria uma tabela chamada resposta com um campo ID do tipo numérico, auto_increment e chave primária, um campo chamado id_aluno, do tipo numérico e seria uma chave estrangeira do campo id da tabela aluno, também colocaria um campo id_avaliacao, do tipo numérico e chave estrangeira do campo id da tabela avaliacao, um campo chamado descricao do tipo text não nulo.

Motivo: usaria as duas chaves estrageiras descritas para relacionar a resposta com o aluno e a avaliacao, evitando que uma resposta fique sem as informações de quem respondeu e a qual avaliação a resposta pertence, o campo descricao como text foi escolhido por sua capacidade ser maior que o tipo varchar, e não nulo pois é obrigatório ter uma resposta.

Além de responder sua avaliação, a pessoa também responde um outro formulário onde ela corrige sua resposta em função da resposta de um mentor(a). Essa correção sempre tem uma nota de 1 a 10 e está linkada com a avaliação respondida pela própria pessoa. O que você faria para representar essa estrutura no banco de dados?

Resposta: criaria uma tabela chamada auto_avaliacao, contendo o campo ID numérico, auto_incremente e chave primária, juntamente com um campo chamado nota de tipo numérico com o parâmetro enum, passando os valores de 1 a 10 e um campo id_resposta, do tipo numérico que é uma chave estrangeira para o campo id da tabela resposta.

Motivo: foi escolhido a parâmetro enum para que não seja colocado valores que não correspondem ao solicitado.

Agora que você montou a estrutura, temos algumas consultas que precisamos fazer.

Precisamos saber todo mundo que respondeu uma avaliação com um título específico.

select aluno.nome, aluno.email from aluno inner join resposta on aluno.id = resposta.id_aluno inner join avaliacao on avaliacao.titulo = "titulo específico"

motivo: usar inner join para juntar os dados de outras tabelas, para poder gerar o resultado necessário

Precisamos saber quantas respostas foram dadas por avaliação

select avaliacao.titulo, count(resposta.id_avaliacao) from resposta inner join avaliacao on avaliacao.id = resposta.id_avaliacao group by avaliacao.titulo

motivo: usar count() e group by para que a contagem seja representada de acordo com ada avaliacao.

Precisamos da nota média da autocorreção por avaliação

select avaliacao.titulo, avg(auto_avaliacao.nota) from auto_avaliacao inner join resposta on auto_avaliacao.id_resposta = resposta.id inner join avaliacao on resposta.id_avaliacao = avaliacao.id group by avaliacao.titulo

motivo: usar inner join duas vezes para juntar dados de outras tabelas que se referem a mesma consulta, usando a função avg que calcula a média.
