# Atualizando o SpringBoot

Alterar a versão da release da tag parent no arquivo pom.xml assim o maven irá cuidar de fazer o download de tudo que é necessário para atualizar o spring boot para a nova versão. Sempre verificar a versão e as compatibilidades de uma versão para outra antes de atualizar, pois podem surgir incompatibilidades entre versões e dar erro de compilação.

No caso de atualizar da versão 2.1.4 para a versão 2.3.1 diversos erros ocorreram, pois na versão 2.3.0 mudou algumas questões a respeito de validações, e desde essa versão surgiu um módulo de validação esse que antes era embutido no módulo web, e para corrigir esses erros foi necessário incluir no projeto o módulo spring de validação através do pom.xml.

## Estrutura de versões

ex: 3.4.2

major 3 | minor 4 | patch 2

major -> Grandes mudanças e que normalmente ocorre quebra de compatibilidade entre versões.

minor -> Dificilmente ocorre quebra de compatibilidade, porém pode acontecer, normalmente são implementadas novas funcionalidades

patch -> Raramente acontece quebra de compatibilidade, e normalmente não tem inclusão de novas funcionalidades, mudanças patch normalmente são correções de erros, bugs, segurança, são alterações pontuais que não interferem na forma como se utiliza o framework.

Algumas versões do Spring Boot causam quebra de compatibilidade, podendo causar impactos no projeto.

## Resumo

* Para atualizar a versão do Spring Boot na aplicação, basta alterar a tag `<version>` da tag `<parent>`, no arquivo `pom.xml`.
* É importante ler as **release notes** das novas versões do Spring Boot, para identificar possíveis quebras de compatibilidades ao atualizar a aplicação.
