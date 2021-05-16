# Deploy

## Gerando jar da aplicação

Através do eclipse deve se utilizar a função Run As e escolher a opção maven install, ou maven build, no segundo caso pode se informar as tarefas (Goals) a serem utilizadas no processo, normalmente sendo usado, clean para recompilar as classes do projeto e package para gerar o pacote da aplicação.

pelo prompt na raiz do projeto executar o seguinte comando

> mvn clean package

é necessário ter o maven instalado.

caso tenha testes configurados, o maven automaticamente irá executar todos antes de criar o jar da aplicação. O build do projeto será salvo em uma pasta chamada target na raiz da aplicação.

## Externalizando senhas com variáveis de ambiente

Criar um application.properties para ambiente de produção e mascarar os dados sensíveis para serem usados por variáveis de ambiente. Utilizando expression language subistitua os dados que julgar sensíveis, por nomes de varáveis de ambiente, normalmente escritas em caixa alta.

> Ex: spring.datasource.url=${DATABASE_URL}

Existem duas maneiras de prover essas variáveis de ambiente:

* Setando as variáveis de ambiente no próprio sistema onde será rodado a aplicação.
* Passando como parametro ao executar o jar da aplicação.

## Deploy usando arquivo war

As alterações que devemos fazer na aplicação para que o build dela produza um arquivo `.war` ao invés de `.jar`:

* Alterar a classe `main` da aplicação para herdar da classe `SpringBootServletInitializer`, além de nela também sobrescrever o método `configure`.
  * É necessário realizar essa alteração para que o Spring Boot seja inicializado corretamente no servidor de aplicação externo.
* Adicionar a tag `<packaging>war</packaging>` no `pom.xml` da aplicação.
  * O valor padrão da tag `<packaging>` é `jar`.
* Declarar a dependência do tomcat como **provided** no `pom.xml`.
  * Em um deploy tradicional com arquivo `.war` a biblioteca do tomcat não deve ser embutida na aplicação.

## Resumo


* O build da aplicação é realizado via maven, com o comando `mvn clean package`.
* Ao realizar o build, por padrão será criado um arquivo `.jar`.
* É possível passar parâmetros para as configurações da aplicação via variáveis de ambiente.
* É possível alterar o build para criar um arquivo `.war`, para deploy em servidores de aplicações.
