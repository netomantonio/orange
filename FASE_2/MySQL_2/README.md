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
