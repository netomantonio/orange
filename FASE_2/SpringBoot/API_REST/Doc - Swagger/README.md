# Documentação de API

É muito importante documentar uma api, pois facilita o entendimento daqueles que irão utiliza-la. Sem documentação, os consumidores da api podem enfrentar diversos problemas por não compreenderem como é o funcionamento, talvez não conhecam todos os endpoints disponíveis e assim por diante.

Escrever documentação é sempre um trabalho negligenciado, e para facilitar existem diversas ferramentas para documentar de forma automatizada, uma delas é Swagger e veremos adiante como utilizar para documentar uma api spring.

## Swagger

Para utilizar o Swagger na documentação de API desenvolvidas com spring, existe uma biblioteca chamada SpringFox, desenvolvida com base no swagger para projetos que utilizam SpringBoot. Para utilizar é necessário incluir suas dependencias do projeto, através do pom.xml e habilitar através da anotação `@EnableSwagger2` na classe principal da aplicação e criar uma classe de configuração anotada com `@Configuration`.


Para configurar o **SpringFox Swagger**, para gerar a documentação, de maneira automatizada, da nossa API. Nas configurações, foi necessário adicionar o seguinte trecho de código:

```
ignoredParameterTypes(Usuario.class)
```

Para ignorar nos *endpoints* os parâmetros relacionados à classe `Usuario`. Como nossa classe `Usuario` possui atributos relacionados ao login, senha e perfis de acesso, não é recomendado que essas informações sejam expostas na documentação do Swagger.

Como fazer para testar os *endpoints* que exigem autenticação na interface do Swagger?

É necessário configurar um *header* para enviar o *token* de autenticação na interface do Swagger. Sem esse *header* não será possível testar os *endpoints* que exigem autenticação pela interface do Swagger.

O código da classe `SwaggerConfiguration`:

```
@Configuration
public class SwaggerConfigurations {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("br.com.alura.forum"))
                .paths(PathSelectors.ant("/**"))
                .build()
                .ignoredParameterTypes(Usuario.class)
                .globalOperationParameters(
                        Arrays.asList(
                                new ParameterBuilder()
                                    .name("Authorization")
                                    .description("Header para Token JWT")
                                    .modelRef(new ModelRef("string"))
                                    .parameterType("header")
                                    .required(false)
                                    .build()));
    }

}
```

O código da classe `SecurityConfigurations`:

```
@Override
public void configure(WebSecurity web) throws Exception {
    web.ignoring()
        .antMatchers("/**.html", "/v2/api-docs", "/webjars/**", "/configuration/**", "/swagger-resources/**");
}
```

## Resumo


* Para documentar a nossa API Rest, podemos utilizar o Swagger, com o módulo **SpringFox Swagger**;
* Para utilizar o **SpringFox Swagger** na API, devemos adicionar suas dependências no arquivo **pom.xml**;
* Para habilitar o Swagger na API, devemos adicionar a anotação `@EnableSwagger2` na classe `ForumApplication`;
* As configurações do Swagger devem ser feitas criando-se uma classe chamada `SwaggerConfigurations` e adicionando nela a anotação `@Configuration`;
* Para configurar quais *endpoints* e pacotes da API o Swagger deve gerar a documentação, devemos criar um método anotado com `@Bean`, que devolve um objeto do tipo `Docket`;
* Para acessar a documentação da API, devemos entrar no endereço [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html);
* Para liberar acesso ao Swagger no Spring Security, devemos chamar o seguinte método `web.ignoring().antMatchers("/**.html", "/v2/api-docs", "/webjars/**", "/configuration/**", "/swagger-resources/**")`, dentro do método `void configure(WebSecurity web)`, que está na classe `SecurityConfigurations`.
