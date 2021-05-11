# HyperText Transfer Protocol

## HTTP

é o mais importante protocolo usado na internet para comunicação entre cliente (navegador) e servidor (aplicação web) estabelencendo regras de comunicação, que define a forma como dados serão trafegados na rede.

Diferentemente do HTTP, o P2P é um outro protocolo de comunicação, onde não existe um papel especifico na rede pra quem é cliente, e quem é servidor, no caso do P2P, todos que estão na rede, são cliente e servidor ao mesmo tempo. Este protocolo é muito utilizado quando a carga de trabalho deve ser distribuida. O HTTP tem a característica de ser centralizador, existe 1 servidor e diversos clientes podem se conectar a ele, centralizando a carga de trabalho no servidor.

Além do HTTP e do P2P existem muitos outros protocolos de comunicação, alguns deles são, FTP, SMTP, SSH, websockets e muitos outros.

### Resumo

* Arquiterura Cliente-Servidor.
* Protocolo é um conjunto de regras.
* HTTP é um protocolo que define regras de comunicação entre cliente e servidor na internet.
* HTTP é o protocolo mais importante da internet.

## HTTPS ( HTTP + SSL/TLS )

### HTTPS

O protocolo http trafega dados de forma não segura, ou seja, quando dados são trafegados usando http os dados podem ser lidos por qualquer um que tiver acesso a requisião, para isso foi implementado mais uma camada de segurança para mascarar esses dados, e para isso se deu o nome de HTTPS, para garantir que somente o servidor possa ter acesso aos dados trafegados.

### Funcionamento

para que o protocolo https funcione, é preciso ter um certificado digital que vai criptografar os dados, para isso certificado tem uma chave pública para criptografar e no servidor uma chave privada para descriptografar os dados.

Na aba de segurança nas ferramentas de desenvolvedor dos navegadores é possível verificar o certificado

### Para Saber Mais: As chaves do HTTPS

Aprendemos no vídeo que o HTTPS usa uma **chave pública** e uma **chave privada**. As chaves estão *ligadas* matematicamente, o que foi cifrado pela chave pública só pode ser decifrado pela chave privada. Isso garante que os dados cifrados pelo navegador (chave pública) só podem ser lidos pelo servidor (chave privada). Como temos duas chaves diferentes envolvidas, esse método de criptografia é chamado de **criptografia assimétrica**. No entanto, a criptografia assimétrica tem um problema, ela é **lenta**.

![](https://s3.amazonaws.com/caelum-online-public/http/cripto-assimetrica.png)

Por outro lado, temos a **criptografia simétrica**, que usa a mesma chave para cifrar e decifrar os dados, como na vida real, onde usamos a mesma chave para abrir e fechar a porta. A criptografia simétrica é muito **mais rápida**, mas infelizmente não **tão segura**. Como existe apenas uma chave, ela ficará espalhada pelos clientes (navegadores) e qualquer um, que tem a posse dessa chave, pode decifrar a comunicação.

![](https://s3.amazonaws.com/caelum-online-public/http/cripto-simetrica.png)

Agora, o interessante é que o **HTTPS usa ambos os métodos de criptografia, assimétrica e simétrica**. Como assim? Muita calma, tudo o que aprendemos é verdade! Só faltou o grande final :)

No certificado, vem a chave pública para o cliente utilizar, certo? E o servidor continua na posse da chave privada, ok? Isso é seguro, mas lento e por isso o cliente gera uma chave simétrica ao vivo. Uma chave só para ele e o servidor com o qual está se comunicando naquele momento! Essa chave exclusiva (e simétrica) é então enviada para o servidor utilizando a criptografia assimétrica (chave privada e pública) e então é utilizada para o restante da comunicação.

Então, HTTPS **começa** com criptografia **assimétrica** para **depois** mudar para criptografia **simétrica**. Essa chave simétrica será gerada no início da comunicação e será reaproveitada nas requisições seguintes. Bem-vindo ao mundo fantástico do HTTPS :)

### Resumo

* Através do https podemos ter uma web mais segura
* Utiliza certificado digital para garantir a identidade de um site
* Existem chave pública para criptografar e chave privada para descriptografar.
* O certificado é emitido por uma Autoridade certificadora (CA - Certificate Authority)

## Resumo Geral

* Por padrão, os dados são trafegados como texto puro na web.
* Apenas com HTTPS a Web é segura
* O protocolo HTTPS nada mais é do que o protocolo HTTP mais uma camada adicional de segurança, a TLS/SSL
* O tipo de criptografia de chave pública/chave privada
* O que são os certificados digitais
* Certificados possuem identidade e validade
* As chaves públicas estão no certificado, a chave privada fica apenas no servidor
* O que é uma autoridade certificadora
* O navegador utiliza a chave pública para criptografar os dados

# Endereços

"[protocolo]: //[dominio]:[porta]"

###### exemplo: https://www.zup.com.br

## Domínio

Um domínio é um endereço textual para um IP fixo na rede. por exemplo www.zup.com.br == 198.71.233.96

o domínio é o nome do site na web e serve para facilitar a navegação do usuário, que acaba não precisando lembrar o IP de cada site.

para que isso funcione são necessários os servidores de DNS (Domain Name System) que fazem a indexação desses IP's com os nomes textuais.

### DNS

O **DNS** realiza a tradução do nome de um domínio para o endereço de IP. Existem vários servidores DNS no mundo e é fundamental para a nossa web o funcionamento deles

## PORTAS

Imagine que um servidor seja um condominio, e cada porta representa um apartamento, por padrão o protocolo http usa a porta 80, não sendo necessário espefica-la. Isso também é válido para o https, porém a porta padrão utilizada é a 443


### Resumo


Navegando dentro da Alura, mais informações aparecem depois do nome e do domínio. Por exemplo, para acessar a página principal dos cursos, usamos **`https://cursos.alura.com.br/dashboard`**. O */dashboard* é um recurso (***resource***) do site que gostaríamos de acessar. Existem vários outros recursos na Alura como as carreiras (`/careers`), ou o fórum de discussões (`/forum`). O importante é que cada *recurso possua o seu nome único*.

Navegando um pouco mais na Alura, podemos perceber que entre o domínio e o recurso podem vir outras informações . Por exemplo, para acessar o curso **HTML5 e CSS3 I: Suas primeiras páginas da Web**, usamos [https://cursos.alura.com.br/course/introducao-html-css](https://cursos.alura.com.br/course/introducao-html-css). Ou seja, para acessarmos o recurso `/introducao-html-css`, usamos um caminho intermediário, o `/course`. Há vários outros exemplos na Alura que usam caminhos para chegar ao recurso concreto, como por exemplo `/courses/mine`, e navegando na Alura você encontrará mais.

### Finalmente, a URL

Repare que estamos usando umas regras bem definidas para descrever a localização de um recurso na web. Todos os endereços na web sempre seguem esse mesmo padrão: **`protocolo://dominio:porta/caminho/recurso`**. Esse padrão, na verdade, segue uma especificação que foi batizada de ***Uniform Resource Locator***, abreviada como ***URL***. Então, as URLs são os endereços na web!

![](https://s3.amazonaws.com/caelum-online-public/http/http-url.png)

## Resumo Geral


### O que aprendemos nesse capítulo?

* **URL** são os endereços da Web
* Uma URL começa com o protocolo (por exemplo `https://`) seguido pelo **domínio** (`www.alura.com.br`)
* Depois do domínio pode vir a porta, se não for definida é utilizada a porta padrão desse protocolo
* Após o *domínio:porta*, é especificado o **caminho** para um **recurso** (`/course/introducao-html-css`)
* Um **recurso** é algo concreto na aplicação que queremos acessar

![](https://s3.amazonaws.com/caelum-online-public/http/http-url.png)

## Para Saber mais: URL ou URI?


Muitas vezes, desenvolvedores usam a sigla **URI** (***U**niform* ***R**esource* ***I**dentifier*) quando falam de endereços na web. Alguns preferem **URL** (***U**niform* ***R**esource* ***L**ocator*), e alguns misturam as duas siglas à vontade. Há uma certa confusão no mercado a respeito e mesmo desenvolvedores experientes não sabem explicar a diferença. Então, qual é a diferença?

**Resposta 1 (fácil):** Uma **URL** é uma **URI**. No contexto do desenvolvimento web, ambas as siglas são válidas para falar de endereços na web. As siglas são praticamente sinônimos e são utilizadas dessa forma.

**Resposta 2 (mais elaborada):** Uma **URL** é uma **URI**, mas nem todas as **URI's** são **URL's**! Existem **URI's** que identificam um recurso sem definir o endereço, nem o protocolo. Em outras palavras, uma **URL** representa uma *identificação* de um recurso (**URI**) através do endereço, mas nem todas as identificações são **URL's**.

Humm ... ficou claro? Não? Vamos dar um exemplo! Existe um outro padrão que se chama **URN** (***U**niform* ***R**esource* ***N**ame*). Agora adivinha, os **URN's** também são **URI's**! Um **URN** segue também uma sintaxe bem definida, algo assim **`urn:cursos:alura:course:introducao-html-css`**. Repare que criamos uma outra identificação do curso **Introdução ao HTML e CSS** da Alura, mas essa identificação não é um endereço.

![](https://s3.amazonaws.com/caelum-online-public/http/http-uri-urn-url.png)

Novamente, a resposta 2 vai muito além do que você realmente precisa no dia a dia. *Normalmente **URL** e **URI** são usados como sinônimos*.


# Request e Response Model

Por padrão o protocolo http é stateless, ou seja, o servidor não tem conhecimento do que foi requisitado anteriormente. Para facilitar a navegação e não ser necessário ficar fazendo login a cada nova requisição, existem os cookies e as sessions, que tem como objetivo identificar o usuário ativo, além de armazenar informações uteis para futuros acessos desse usuário. As sessões guarda informações entre as requisições.

## Cookies


Quando falamos de **Cookies** na verdade queremos dizer **Cookies HTTP** ou **Cookie web**. Um cookie é um pequeno arquivo de texto, normalmente criado pela aplicação web, para guardar algumas informações sobre usuário no navegador. Quais são essas informações depende um pouco da aplicação. Pode ser que fique gravado alguma preferência do usuário. Ou algumas informações sobre as compras na loja virtual ou, como vimos no vídeo, a identificação do usuário. Isso depende da utilidade para a aplicação web.

Um cookie pode ser manipulado e até apagado pelo navegador e, quando for salvo no navegador, fica associado com um domínio. Ou seja, podemos ter um cookie para `www.alura.com.br`, e outro para `www.caelum.com.br`. Aliás, um site ou web app pode ter vários cookies!

Podemos visualizar os cookies salvos utilizando o navegador. Como visualizar, depende um pouco do navegador em questão:

No Chrome: ***Configurações -> Privacidade -> Configurações de conteúdo... -> Todos os cookies e dados de site... -> Pesquisar alura***

No Firefox: ***Preferências -> Privacidade -> remover cookies individualmente -> Pesquisar alura***

## Resumo


* O protocolo HTTP segue o modelo **Requisição-Resposta**
* Sempre o **cliente** inicia a comunicação
* Uma **requisição** precisa ter **todas as informações** para o servidor gerar a resposta
* HTTP é **stateless**, não mantém informações entre requisições
* As plataformas de desenvolvimento usam **sessões** para guardar informações entre requisições


# Depurando HTTP

Utilizar as ferramentas de desenvolvedor do navegador escolhido e abrir a aba de network, para analisar as requisições.


Abaixo há um exemplo de uma requisição e resposta, usando a ferramenta ***telnet***. Através dele, acessamos `www.caelum.com.br` na porta padrão `80`.

![](https://s3.amazonaws.com/caelum-online-public/http/telnet-http.png)

O *telnet* estabelece apenas uma conexão TCP (protocolo de rede que roda abaixo do HTTP) e permite que enviemos dados em cima dessa conexão, através do terminal. Uma vez a conexão estabelecida, basta escrever no terminal e os dados serão enviados automaticamente para o servidor. Para o servidor realmente entender os dados, devemos respeitar a sintaxe do protocolo HTTP

``Nesse exemplo digitamos no terminal:

```
GET / HTTP/1.1
HOST: www.caelum.com.br
```

E a resposta do servidor segue logo abaixo:

```
HTTP/1.1 200 OK
Content-Type: text/html; charset=utf-8
Vary: Accept-Encoding,User-Agent
Content-Language: pt-br
Date: Mon, 01 Jun 2015 21:00:20 GMT
Server: Google Frontend
Cache-Control: private
```

A tabela completa de mensagens HTTP pode ser vista em: [https://www.w3schools.com/tags/ref_httpmessages.asp](https://www.w3schools.com/tags/ref_httpmessages.asp).


Segue um link que explica os códigos HTTP de forma divertida: [httpstatusdogs](https://httpstatusdogs.com/)

https://httpstatuses.com/

## Parâmetros da requisição

Por padrão ao enviar parâmetros na url estará usando o método GET, sendo assim, os dados desses parâmetros estarão descobertos, podendo ser interceptados e visualizados por qualquer um.


Quando enviamos parâmetros na URL, devemos iniciar pelo **`?`**, o nome do parâmetro e um **`=`**, para separar o nome do parâmetro do seu valor:

```
?nome_do_parametro=seu_valor
```

Quando usamos mais do que, um parâmetro devemos usar **`&`** para separá-los:

```
?nome_do_parametro=seu_valor&nome_do_outro_param=valor
```



Como, por exemplo, podemos enviar uma requisição usando o método `GET` para carregarmos a página que exibe informações sobre o *usuário de número 2*? Devemos passar o parâmetro `id` com o valor `2`. Como por exemplo:

```
http://meusite.com.br/usuario?id=2
```

Uma outra forma de fazer seria passar os valores na própria URL! Veja o exemplo:

```
http://meusite.com.br/usuario/2
```

Mas tem um probleminha, não estamos dizendo explicitamente que o valor 2 realmente representa o `id`. Quando um parâmetro irá receber um certo valor, devemos combinar com o servidor (com o desenvolvedor da aplicação). Neste caso, foi combinado que o parâmetro recebido seria equivalente ao `id` passado antes.

Vamos ver um exemplo prático, em um serviço que retorna informações sobre um endereço de um determinado CEP? Acesse a URL: [http://viacep.com.br/ws/20040030/json](http://viacep.com.br/ws/20040030/json)

A resposta será todas as informações do CEP da Caelum Rio, como complemento, número e bairro, formatadas em JSON. Isso significa que foi combinado com o servidor, que o primeiro valor passado depois de **`ws`** deve ser o CEP e logo após, o formato em que os dados deverão chegar. No nosso caso, JSON. Tudo bem? :)

Experimente agora trocar para o CEP de sua casa e para outro formato de dados, como por exemplo, XML.



Já falamos bastante sobre os métodos (ou verbos) HTTP, `GET` e `POST`. Esses dois são utilizados na grande maioria das aplicações web, e fazem parte do dia a dia do desenvolvedor, no entanto existem diversos outros métodos.

Se o `GET` foi criado para receber dados, e o `POST` para adicionar algo no servidor, será que não existe algo para apagar e atualizar?

A resposta é sim, e os métodos se chamam **`DELETE`** e **`PUT`**.

Novamente esses métodos normalmente não são utilizados quando se trata de uma aplicação web, e são mais importantes quando o assunto é Web Services.

Agora vem a pergunta, você já ouviu falar de Web Services?


### Web Services


Quando falamos de um Web Service, sempre usamos o protocolo da web, ou seja o HTTP.

Um Web Service disponibiliza uma funcionalidade na web, através do protocolo HTTP. As funcionalidades variam muito e dependem muito da empresa e do negócio dela, mas por exemplo, na Alura temos um Web Service que traz todas as informações de um curso (nome, capítulos, exercícios, etc). O Google ou Facebook possuem muitos Web Services para acessar um usuário, ver os posts dele, interesses, etc. Muitas vezes esses serviços são pagos.

O importante é que sempre usamos o protocolo HTTP. A grande diferença de um Web Service é que os dados **não** vem no formato HTML, e sim em algum formato independente da visualização, como XML ou JSON.

Temos um pequeno exemplo de um Web Services que usamos em um dos treinamentos presenciais. Tente acessar: [http://argentumws.caelum.com.br/negociacoes](https://argentumws-spring.herokuapp.com/negociacoes)

Repare que recebemos dados sobre negociações, mas o formato é XML. Isso é um Web Service! É a tarefa do cliente ler os dados e apresentar para o usuário final. O cliente não precisa ser o navegador (e normalmente não é), pode ser um celular ou uma aplicação Desktop.

## Methods

### GET

Receber dados (params na URL)


### POST

Submeter dados (params no corpo da requisição)


### DELETE

Remover um recurso


### PUT

Atualizar um recurso

# Serviços na web com REST

Nem sempre é o melhor transportar dados em formato html através do HTTP, por exemplo quando precisa-se alimentar de dados um aplicativo mobile, quando esse faz uma requisição, não faz sentido retornar um código html, para isso existem outros tipos melhores, como XML e JSON, sendo hoje o mais utilizado o json.



O protocolo **HTTP** envia dados em texto puro e já estudamos sobre as implicações disso em questões de segurança. Vimos inclusive como o **HTTPS** lida com isso.

Em alguns momentos se faz necessário avisar na própria requisição um formato específico esperado para a resposta.

Usando o cabeçalho **Accept**.

Através dele podemos usar algum formato específico como:

```
Accept: text/html, Accept: text/css, Accept: application/xml, Accept: application/json, Accept:image/jpeg e Accept: image/*
```

Ou até mesmo não definir nenhum formato específico colocando:

```
Accept: */*
```

## REST

O padrão REST visa unir e de certa forma verbalizar os métodos do http com a uri do recurso, para explicitar uma ação. Ex:

```
GET -> Listagem
POST -> Criação
```

```
http://alurafood.com/api/restaurante --> GET
//PEDE TODOS OS RESTAURANTES

http://alurafood.com/api/restaurante --> POST
//ADICIONA UM RESTAURANTE
```


### O padrão REST

Logo podemos perceber que o padrão usado pela equipe do webservice define que uma requisição web tem três tipos de componentes importantes: recursos (URI), operações (`GET, POST, PUT, DELETE/...`) e representação de dados(`XML, JSON, ...`).

Esses três componentes em conjuntos seguindo algumas práticas são a base para o modelo arquitetural **REST(Representational State Transfer)** ou em português **Transferência de Estado Representacional**.

![](https://s3.amazonaws.com/caelum-online-public/http/images/08/imagem2-cap8-rest-http.png)

### Recurso

Ao criar as URIs do nosso sistema devemos levar em conta que elas representam recursos, não ações.

Em sistemas REST, nossas URIs devem conter apenas substantivos, que são nossos recursos: /restaurante/adiciona não é uma boa URI, pois contém um verbo e não está identificando um recurso, mas sim uma operação.

Para representar a adição de um restaurante podemos usar a URI /restaurante com o método HTTP POST, que representa que estamos adicionando alguma informação no sistema.

### Operações

O protocolo HTTP possui operações através de métodos como: GET, POST, PUT e DELETE.

Cada método tem uma semântica diferente e juntando o método à URI deveríamos conseguir representar todas as ações do nosso sistema.

As semânticas principais são:

* GET - recupera informações sobre o recurso identificado pela URI. Ex: listar restaurante, visualizar o restaurante 1. Uma requisição GET não deve modificar nenhum recurso do seu sistema, ou seja, não deve ter nenhum efeito colateral, você apenas recupera informações do sistema.
* POST - adiciona informações usando o recurso da URI passada. Ex: adicionar um restaurante. Pode adicionar informações a um recurso ou criar um novo recurso.
* PUT - adiciona (ou modifica) um recurso na URI passada. Ex: atualizar um restaurante.
* DELETE - remove o recurso representado pela URI passada. Ex: remover um restaurante.

### Representação

Quando fazemos uma aplicação não trafegamos um recurso pela rede, apenas uma representação dele. E essa representação pode ser feita de diferentes formas como JSON, XML ou HTML.

### Conclusão

Nossas URIs devem representar recursos, as operações no recurso devem ser indicadas pelos métodos HTTP e podemos falar qual é o formato em que conversamos com o servidor com o `Content-Type` e `Accept` que são cabeçalhos do HTTP.

### Resumo

* REST é um padrão arquitetural para comunicações entre aplicações
* Aproveita da estrutura que o HTTP proporciona
* Recursos são definidos via URI
* Operações com os métodos do HTTP(GET/POST/PUT/DELETE)
* Cabeçalhos(Accept/Content-Type) para especificar a representação(JSON, XML, ...)


# Tipos de Dados


Em alguns cabeçalhos do **HTTP** devemos especificar algum formato. Os formatos são chamados na documentação de **MIME types**. E na definição do cabeçalho usamos a seguinte estrutura: `tipo/subtipo`. São **tipos** conhecidos:

```
text, image, application, audio e video
```

E alguns **subtipos**:

```
text -> text/plain, text/html, text/css, text/javascript
```

```
image -> image/gif, image/png, image/jpeg
```

```
audio -> audio/midi, audio/mpeg, audio/webm, audio/ogg, audio/wav
```

```
video -> video/mp4
```

```
application -> application/xml,  application/pdf
```

Para conhecer outros formatos aceitos você pode acessar aqui: [https://developer.mozilla.org/en-US/docs/Web/HTTP/Basics_of_HTTP/MIME_types](https://developer.mozilla.org/en-US/docs/Web/HTTP/Basics_of_HTTP/MIME_types)

# HTTP/2.0


O protocolo que estamos trabalhando até agora foi especificado na década de 90 e de lá até hoje muitas alterações foram feitas até na forma como usamos a internet.

Com a chegada do mundo mobile novas preocupações apareceram e otimizações são cada vez mais necessárias para uma boa performance. Por isso uma mudança foi necessária e em 2015 depois de alguns anos de especificações e reuniões surgiu a versão 2 desse protocolo.

A nova versão é batizada de **HTTP/2** e tem como página principal de documentação e referência essa: `https://http2.github.io/`.

A nova versão do protocolo HTTP traz mudanças fundamentais para a Web. Recursos fantásticos que vão melhorar muito a performance da Web além de simplificar a vida dos desenvolvedores.

No HTTP 1.1, para melhorar a performance, habilitamos o **GZIP** no servidor para comprimir os dados das respostas. É uma excelente prática, mas que precisa ser habilitada explicitamente. No HTTP/2, o **GZIP é padrão e obrigatório**.

É como se a gente passasse a ter a resposta assim:

![IMAGEM GZIP](https://s3.amazonaws.com/caelum-online-public/http/images/08/gzip.png)

Mas, se você já olhou como funciona uma requisição HTTP, vai notar que só GZIPar as respostas resolve só metade do problema. Tanto o `request` quanto o `response` levam vários cabeçalhos (headers) que não são comprimidos no HTTP 1.1 e ainda viajam em texto puro.

Já na nova versão, os headers passam a ser binários:

![IMAGEM BINÁRIO](https://s3.amazonaws.com/caelum-online-public/http/images/08/binario.png)

Além de binários eles são comprimidos usando um algoritmo chamado **HPACK**. Isso diminui bastante o volume de dados trafegados nos headers.

![IMAGEM HPACK](https://s3.amazonaws.com/caelum-online-public/http/images/08/hpack.png)

Além de todas essas otimizações para melhorar a performance ainda houve uma preocupação com segurança exigindo TLS por padrão também.

![IMAGEM TLS](https://s3.amazonaws.com/caelum-online-public/http/images/08/tls.png)


Apesar do protocolo HTTP/1.1 ter sido de extrema importância para a Web ao longo de vários anos, como toda boa tecnologia, é necessário um *update*. A nova versão do HTTP veio para adequar este protocolo tão famoso a um mundo onde temos muito mais dados sendo trafegados na rede, e a velocidade de acesso e segurança do usuário se tornam bastante importantes.


## HPACK

Para que serve a tecnologia HPACK implementada no protocolo HTTP/2 ?

O HPACK é uma tecnologia especializada em comprimir os Headers das comunicações HTTP/2. Como toda requisição HTTP acompanha algum header por padrão, uma tecnologia de compressão embutida no protocolo é demasiadamente útil para economizar dados trafegados.

HPACK é especialista em comprimir os Headers da requisições/respostas HTTP, deixando as mais leves.

## Cabeçalhos Stateful

Diferente do HTTP/1.1 no HTTP/2 os cabeçalhos são stateful, siginifica que informações do cabeçalho são mantidas entre requisições, para todos os outros casos HTTP/2 ainda é stateless.

```Como trafegamos apenas os headers que mudam de uma requisição para outra, acabamos por economizar uma boa quantidade de dados, pois não precisamos enviar headers que mudam poucas vezes a todo momento, como o Accept```

Headers Stateful permitem que apenas os cabeçalhos que mudem sejam enviados a cada requisição, economizando muita banda que seriam cabeçalhos repetidos.

Quando estamos utilizando *Headers Stateful*, simplesmente colocamos nas requisições os cabeçalhos que se alteraram entre uma e outra, trazendo uma enorme economia de dados, visto que toda requisição HTTP possui um cabeçalho e que, muitas vezes, no HTTP/1.1, cabeçalhos repetidos eram trafegados em todas as requisições.


## Server Push


A partir do HTTP2 temos uma conversa mais paralela. Anteriormente estávamos trabalhando com conceitos de requisições seriais, fazíamos uma requisição e esperávamos receber, fazíamos outra requisição e esperávamos receber e por aí vai. No HTTP2, quando o cliente realiza uma requisição para **index.html*, o servidor devolve a página, mas ele já pode passar para o browser as informações necessárias para que essa página possa ser, de fato, exibida. Ou seja, ele consegue dar um passo além:

![server push](https://s3.amazonaws.com/caelum-online-public/http/http2-push.png)

Isso é uma outra abordagem que surgiu no HTTP2, muito mais interessante. Mas quando o browser for interpretar essa página HTML, vai ter que passar pelo conteúdo que especifica o arquivo CSS? Sim, mas quando ele passar pelo **estilo.css**, vai verificar que já recebeu. Ou seja, ele percebe que já recebeu essas informações.

Este é o conceito de ***Server Push***, ou seja, o ***server*** envia dados para o cliente sem que o cliente tenha solicitado, tornando o tráfego de dados muito mais otimizado.

## Multiplexação

Para falar de multiplexação é necessário compreender como funciona a estrutura de camada de redes, quando falamos de http, estamos trabalhando na camada de aplicação, e abaixo dela existem outras, por exemplo, para que o http funcione, ele depende de um protocolo da camada de transportes chamado `TCP`, e desde o`HTTP/1.1` existe um mecanismo chamado `Keep-Alive`, esse mecanismo determina o tempo de conexão do protocolo `TCP`, e nesse tempo ocorre várias requisições e respostas `HTTP`de forma serializada. No`HTTP/2`esse mecanismo tomou um rumo diferente, aproveitando que existe uma comunicação aberta ele proporciona que requisições podem ser enviadas em paralelo, agilizando o processo, o que era sincrono no`HTTP/1.1`agora é assincrono no`HTTP/2`.


Na imagem abaixo, fizemos a requisição 1 e requisição 2, quando íamos fazer requisição 3, já recebemos uma resposta:

![Keep-Alive HTTP2](https://s3.amazonaws.com/caelum-online-public/http/images/08/keep-alive-http2.png)

Esse conceito que surgiu no HTTP2 é chamado de ***Multiplexing*** e traz uma performance bastante relevante para o nosso HTTP.


## Resumo

* Atua sobre o que já se conhece de HTTP
* Headers binários e comprimidos (HPACK)
* GZIP padrão na resposta
* Multiplexing(Requisição e respostas são paralelas)
* Headers Stateful (Mandamos apenas os cabeçalhos que mudam)
* Server-push (servidor envia dados sem que o navegador tenha pedido)

O HTTP2 atua sobre o que já conhecemos do HTTP. Ou seja, ele não muda nada em relação ao que já conhecemos de HTTP. E que todo o seu conteúdo é usado no HTTP2 de forma bastante simples.

Hoje, o que o HTTP2 especifica é mais a nível de servidor. E acaba que nós desenvolvedores não atuamos tanto nesse nível. Fica mais na outra ponta, que é quem vai produzir servidores e tudo mais, seguir esse novo protocolo.

Vimos que HTTP2 é nada mais que o HTTP com algumas melhorias, até porque o HTTP1 estava bastante desatualizado em relação ao que o mercado já vinha sofrendo.

Também vimos que os **`headers`** são binários e eles são comprimidos com algoritmos chamados de **`HPACK`**.

Vimos ainda, que o HTTP2 habilita o **`GZIP`** como padrão na resposta, logo, esses dados vêm zipado. Coisa que tínhamos que configurar manualmente na versão anterior, ou seja, HTTP1.1.

Além disso, no HTTP2, as requisições e respostas podem ser paralelas. Não precisamos ficar esperando que uma requisição termine pra fazer a próxima. Temos uma otimização maior.

Outro assunto foi que os cabeçalhos guardam status. Quando enviamos uma requisição, a próxima, para o mesmo domínio, não precisa enviar os mesmo dados que já foram trafegados na última. Conclui-se que no HTTP2 isso é evitado, ou seja, menos informação enviada, menos dados que enviamos, menos banda que usamos do usuário, mais feliz ele fica.

Além de ***Headers Stateful***, vimos também que o HTTP2 especifica o famoso ***Server-push***, que é o ato do servidor enviar dados sem que o browser tenha pedido, que foi o que aconteceu lá no **index.html**. O HTTP2 pode enviar dados diretamente para o browser sem ficar esperando uma requisição. Assim, ele dá um passo além.
