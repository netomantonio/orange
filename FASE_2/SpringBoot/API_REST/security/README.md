# Spring Security

Módulo dedicato a seguraça que é responsável pelas autênticações e autorizações.

## Habilitação do módulo.

É necessário incluir a dependencia do modulo no pom.xml. Criar uma classe java que será utilizada para fazer todas as configurações de segurança do projeto, utilizando anotações na classe para informar o spring onde buscar tais configurações, usando `@EnableWebSecurity` e `@Configuration` respectivamente, essa classe também deve herdar WebSecurityConfigurerAdapter.

* Ao habilitar o Spring Security, os *endpoints* da API serão bloqueados, por padrão.

## Liberar acesso aos endpoints

Na classe de configuração deve-se sobrescrever os 3 métodos configure herdados. Cada um desses 3 métodos servem para questões diferentes, e podem ser diferenciados pela sua assinatura.

> * AuthenticationManagerBuilder auth
>   * Configurações de autênticação
> * HttpSecurity http
>   * Configurações de autorização
> * WebSecurity web
>   * Configurações de recursos estaticos

Para liberar os endpoints é necessário implementar as regras dentro do método configure correspondente ao HttpSecurity, responsável pelas URLs.

### HttpSecurity

`http.authorizeRequests().antMachers(HttpMethod.método"``").permitAll().antMachers(HttpMethod.método"``/*").permitAll();`

Se não indicarmos o método HTTP, todos seriam permitidos.

### Restringir acesso aos endpoints privados

`http.authorizeRequests().antMachers(HttpMethod.método"``").permitAll().antMachers(HttpMethod.método"``/*").permitAll().anyRequest().authenticated().and().formLogin();`

Implementar a interface `UserDetails` na classe que representa o usuário do sistema  e sobrescrever todos os métodos herdados e implementar um atributo de perfil de usuário, onde a mesma será uma entidade e deve ser persistida no banco de dados, com id e nome do perfil e essa classe deve implementar uma interface chamada GrantedAuthority e sobrescrever o método getAythority.

Nas configurações de autorização, o objetivo de chamar o método `anyRequest().authenticated()` é para indicar que outras URLs que não foram configuradas devem ter acesso restrito. Essa configuração evita que uma URL que não foi configurada seja pública.

## Autenticando Usuário

Para configurar a lógica de autenticação, é necessário implementar a lógica no método auth dentro da classe de configurações de segurança.

> auth.userDetailsService(classeDeAutenticacao)

criar uma classe de autenticação anotada com `@Service` implementando a interface UserDetailsService e implementar os métodos loadUserByUsername.

É necessário criar uma classe, implementando a interface `UserDetailsService`, para indicar ao Spring Security que essa é a classe `service` que executa a lógica de autenticação. A classe que implementa essa interface geralmente contém uma lógica para validar as credenciais de um cliente que está se autenticando

## Resumo


* Para utilizar o módulo do Spring Security, devemos adicioná-lo como dependência do projeto no arquivo **pom.xml**;
* Para habilitar e configurar o controle de autenticação e autorização do projeto, devemos criar uma classe e anotá-la com `@Configuration` e `@EnableWebSecurity`;
* Para liberar acesso a algum *endpoint* da nossa API, devemos chamar o método `http.authorizeRequests().antMatchers().permitAll()` dentro do método `configure(HttpSecurity http)`, que está na classe `SecurityConfigurations`;
* O método `anyRequest().authenticated()` indica ao Spring Security para bloquear todos os *endpoints* que não foram liberados anteriormente com o método `permitAll()`;
* Para implementar o controle de autenticação na API, devemos implementar a interface `UserDetails` na classe `Usuario` e também implementar a interface `GrantedAuthority` na classe `Perfil`;
* Para o Spring Security gerar automaticamente um formulário de login, devemos chamar o método `and().formLogin()`, dentro do método `configure(HttpSecurity http)`, que está na classe `SecurityConfigurations`;
* A lógica de autenticação, que consulta o usuário no banco de dados, deve implementar a interface `UserDetailsService`;
* Devemos indicar ao Spring Security qual o algoritmo de `hashing` de senha que utilizaremos na API, chamando o método `passwordEncoder()`, dentro do método `configure(AuthenticationManagerBuilder auth)`, que está na classe `SecurityConfigurations`.

# Json Web Token - JWT

Implementar as dependencias no pom.xml e substituir na classe de configuração onde tem `and().formLogin()` por `and().csrf().disable().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);` e criar uma classe controller para autenticação.

é possível fazer injeção de dependências de propriedades do arquivo **application.properties**.

```
@Value(“${forum.jwt.secret}”)
private String secret;
```

A anotação `@Value` deve declarar o nome da propriedade como `String`, utilizando a *expression language* `${}`.

application.properties

```
# jwt
forum.jwt.secret=rm'!@N=Ke!~p8VTA2ZRK~nMDQX5Uvm!m'D&]{@Vr?G;2?XhbC:Qa#9#eMLN\}x3?JR3.2zr~v)gYF^8\:8>:XfB:Ww75N/emt9Yj[bQMNCWwW\J?N,nvH.<2\.r~w]*e~vgak)X"v8H`MH/7"2E`,^k@n<vE-wD3g9JWPy;CrY*.Kd2_D])=><D?YhBaSua5hW%{2]_FVXzb9`8FH^b[X3jzVER&:jw2<=c38=>L/zBq`}C6tT*cCSVC^c]-L}&/
forum.jwt.expiration=86400000
```

Ao devolver o *token* para o cliente, foi enviado juntamente outra informação, chamada **`Bearer`**. A que se refere essa informação? É o tipo de autenticação a ser feita pelo cliente com o *token* que lhe foi devolvido.  **`Bearer`** é um dos mecanismos de autenticação utilizados no protocolo HTTP, tal como o `Basic` e o `Digest`.

## Resumo


* Em uma API Rest, não é uma boa prática utilizar autenticação com o uso de *session*;
* Uma das maneiras de fazer autenticação *stateless* é utilizando *tokens* **JWT** (*Json Web Token*);
* Para utilizar JWT na API, devemos adicionar a dependência da biblioteca **jjwt** no arquivo **pom.xml** do projeto;
* Para configurar a autenticação *stateless* no Spring Security, devemos utilizar o método `sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)`;
* Para disparar manualmente o processo de autenticação no Spring Security, devemos utilizar a classe `AuthenticationManager`;
* Para poder injetar o `AuthenticationManager` no *controller*, devemos criar um método anotado com `@Bean`, na classe `SecurityConfigurations`, que retorna uma chamada ao método `super.authenticationManager()`;
* Para criar o *token* JWT, devemos utilizar a classe `Jwts`;
* O *token* tem um período de expiração, que pode ser definida no arquivo **application.properties**;
* Para injetar uma propriedade do arquivo **application.properties**, devemos utilizar a anotação `@Value`.


# Autenticação via JWT

No último vídeo, vimos que precisamos criar um filtro, que vai conter a lógica de recuperar o *token* do cabeçalho `Authorization`, validá-lo e autenticar o cliente. Vimos também que esse filtro precisa ser registrado no Spring.

Qual anotação foi utilizada para registrar o filtro?

Não existe anotação para registrar o filtro. O filtro deve ser registrado via método `addFilterBefore`, na classe `SecurityConfigurations`.

Por que não é possível fazer injeção de dependências com a anotação `@Autowired` na classe `AutenticacaoViaTokenFilter`?

Porque ela não é um *bean* gerenciado pelo Spring. O filtro foi instanciado manualmente por nós, na classe `SecurityConfigurations` e portanto o Spring não consegue realizar injeção de dependências via `@Autowired`.


necessário indicar ao Spring que o cliente está autenticado. Por que essa autenticação foi feita com a classe `SecurityContextHolder` e não com a `AuthenticationManager`?

Porque a classe `AuthenticationManager` dispara o processo de autenticação via *username*/*password*. A classe `AuthenticationManager` deve ser utilizada apenas na lógica de autenticação via *username/password*, para a geração do *token*.

## Resumo


* Para enviar o *token* JWT na requisição, é necessário adicionar o cabeçalho `Authorization`, passando como valor `Bearer token`;
* Para criar um filtro no Spring, devemos criar uma classe que herda da classe `OncePerRequestFilter`;
* Para recuperar o *token* JWT da requisição no *filter*, devemos chamar o método `request.getHeader("Authorization")`;
* Para habilitar o filtro no Spring Security, devemos chamar o método `and().addFilterBefore(new AutenticacaoViaTokenFilter(), UsernamePasswordAuthenticationFilter.class)`;
* Para indicar ao Spring Security que o cliente está autenticado, devemos utilizar a classe `SecurityContextHolder`, chamando o método `SecurityContextHolder.getContext().setAuthentication(authentication)`.
