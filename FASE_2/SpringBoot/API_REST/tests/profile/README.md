# Spring Profiles

Fornece uma maneira de segregar partes da configuração da aplicação e disponibilizar conforme o ambiente. Através dos perfis criados usando o profiles, é possível configurar como será e o que irá conter em cada um deles, tendo configurações especificas para cada caso.

O principal motivo de se utilizar profiles é permitir ter múltiplas configurações distintas na aplicação. Com profiles podemos ter configurações distintas para cada tipo de ambiente, como *desenvolvimento*, *testes* e *produção*.

## Beans baseados em profiles

Utilizar anotações do tipo profiles e qual profile representa, para que o spring possa orquestrar essas informações, as classes anotadas são carregadas apenas quando o profile ativo corresponde ao mesmo informado na anotação. Quando nenhum profile está ativo, nenhuma classe anotada com profile será carregada.

O comportamento do Spring ao encontrar uma classe anotada com `@Profile(“prod”)` é carregar a classe apenas se o profile ativo for *prod*. A anotação `@Profile(“prod”)` indica ao Spring que determinada classe deve apenas ser carregada se o profile ativo for *prod*.


### Resumo

* Profiles devem ser utilizados para separar as configurações de cada tipo de ambiente, tais como desenvolvimento, testes e produção.
* A anotação `@Profile` serve para indicar ao Spring que determinada classe deve ser carregada apenas quando determinados profiles estiverem ativos.
* É possível alterar o profile ativo da aplicação por meio do parâmetro `-Dspring.profiles.active`.
* Ao não definir um profile para a aplicação, o Spring considera que o profile ativo dela e o profile de nome **default**.
