# Exercício


Todas as respostas dadas pelos alunos e alunas do programa Orange Talent podem ser acessadas por mentores e programa managers que acompanham as turmas. Mas, como esse endereço reside no mesmo sistema onde os próprios alunos e alunas respondem às avaliações, é necessário controlarmos o acesso. Tanto aluno, como mentor, como program manager fazem login no mesmo sistema mas, por conta do nível de acesso, tem acessos a endereços diferentes.

Descreva quais seriam os passos para você, usando o Spring Security, proteger os acessos em função do nível de acesso(roles) de cada tipo de usuário logado no sistema.

Resposta: Adicionando a chamada ao método `hasRole(“NOME_DO_ROLE”)` no código de configuração do endpoint na classe `SecurityConfigurations` antes da criação das políticas de sessão, atribuindo a uri e o metodo e o perfil que tem acesso com hasRole ao invés de permitAll.

Além disso, agora, para cada resposta dada por um aluno ou aluna, o sistema deve enviar um email para os(as) program managers avisando do evento. Só que tem um detalhe, em ambiente de desenvolvimento esse envio deve ser apenas de brincadeira, um mero print exibindo o email que deveria ser enviado. Só que em produção, o email deveria ser enviado usando o provedor de envio de emails que a Zup utiliza.

Descreva quais seriam os passos para você implementar o suporte a envios de emails de maneiras diferentes em função do ambiente de execução.
Resposta: Criaria arquivos de application.properties diferentes para cada perfil, assim, posso configurar regras diferentes de como a aplicação vai funcionar em determinado ambiente, e assim funciona para o envio de emails, poderia criar um application-prod.properties por exemplo e configurar os provedores de emails que a zup utiliza através de variáveis de ambiente.
