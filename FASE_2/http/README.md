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

Imagine que um servidor seja um condominio, e cada porta representa um apartamento, por padrão o protocolo http usa a porta 80, não sendo necessária espefica-la
