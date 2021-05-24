# Deploy com Docker e na nuvem

## Gerar imagem Docker da aplicação

Criar um dockerfile na pasta raiz do projeto com as seguintes informações:

> FROM openjdk:8-jdk-alpine
> RUN addgroup -S spring && adduser -S spring -G spring
> USER spring:spring
> ARG JAR_FILE=target/*.jar
> COPY ${JAR_FILE} app.jar
> ENTRYPOINT ["java", "-jar", "/app.jar"]

Depois executar no prompt o comando na raiz do projeto:

> docker build -t `<nome da aplicacao>` .

## Rodando aplicação via Docker

É necessário passar as variávis de ambiente para configuração de como a aplicação será executada, para isso no comando docker deve utilizar a flag -e seguida de uma variável de ambiente. Atenção, para cada variável de ambiente deve ser antecedida pela flag -e.

## Deploy no Heroku

* Heroku login
* heroku container:login
* heroku create [nome da aplicacao]
* heroku git:remote -a [nome da aplicacao]
* Alterar a linha correspondente abaixo no dockerfile, para limitar o uso de memória.

  > * ENTRYPOINT ["java", "-Xmx512m", "-Dserver.port=${PORT}", "-jar", "/app.jar"]
  >
* Incluir a linha abaixo no arquivo application-prod.properties

  > * server.port={PORT}
  >
* Configurar as variáveis de ambiente diretamente pelo site do heroku
* heroku container:push web
* heroku container:release web
* heroku open

## Resumo


* É possível utilizar o Docker para criação de imagens e de containers para aplicações que utilizam Java com Spring Boot.
* Devemos criar um arquivo `Dockerfile` no diretório raiz da aplicação, para ensinar ao Docker como deve ser gerada a imagem dela.
* É possível passar as variáveis de ambiente utilizadas pela aplicação para o container Docker.
* É possível realizar o deploy de aplicações Java com Spring Boot em ambientes Cloud, como o Heroku.
* Cada provedor Cloud pode exigir diferentes configurações adicionais a serem realizadas na aplicação, para que ela seja executada sem nenhum tipo de problema.
