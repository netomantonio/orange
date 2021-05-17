# SOLID

* SRP - (Single Responsibility Principle) (Coesão)
* OCO - (Open Close Principle) (Classes abertas)
* LSP - (Liskov Substitutive Principle)
* ISP - (Interface Segregation Principle)
* DIP - (Dependecy Inversion Principle) (Inversão de Dependência)

## Coesão

Uma classe coesa é aquela que contém apenas uma única responsabilidade. Ou seja, ela toma conta de apenas um conceito dentro do sistema.

Classes coesas tendem a ser menores, e por consequência, mais fáceis de serem lidas e mantidas. Elas também tendem a ser mais reutilizáveis, afinal são mais fáceis.

Classes que não são coesas geralmente possuem muitos comportamentos (métodos). Além disso, elas também tem a tendência de crescer pra sempre, ou seja, o tempo inteiro o desenvolvedor tem que por a mão para escrever mais código.


Uma classe não coesa contém muitas diferentes responsabilidades. A ideia é então separar essas responsabilidades em classes diferentes. Como no caso do vídeo, onde separamos cada regra de cálculo em um lugar separado. Repare também que a classe `Funcionario` também é coesa, já que ela cuida apenas das responsabilidades e comportamentos de um funcionário.

Então, encontrou uma classe com muita responsabilidade, divida-a em várias classes menores.

SRP é um dos princípios SOLID, que o nome já diz, a ideia é ter uma única responsabilidade por classe. Ou seja, coesão.

### ISP


Podemos pensar sobre coesão em vários níveis diferentes. Por exemplo, o que seria uma interface coesa? Uma interface coesa é aquela que também só possui uma única responsabilidade.

E será que conseguimos ver a coesão pelo outro lado? Pense, se a classe A depende de B, idealmente B deve prover uma interface para A, somente com as coisas que A depende. Ou seja, a classe não deve depender de métodos que ela não usa. Isso é o que chamamos de **Princípio de Segregação de Interfaces**, ou ISP.

Você consegue enxergar quais são as vantagens de fazer com que classes dependam apenas de métodos que precisam?


Novamente, é propagação de mudanças. Se a interface mudar, a mudança tende a ser propagada em menos pontos. Lembre-se que a nossa ideia ao longo desse curso, é diminuir ao máximo a quantidade de pontos de mudança.

Por curiosidade, esse princípio faz mais sentido ainda em linguagens mais antigas como C++. Ao mudar uma interface, você é obrigado a recompilar e redeployar todo o código. Em sistemas grandes, isso pode ser bastante problemático. Esse princípio nasceu nessa época. Hoje, esse problema de deploy é menor, mas ainda assim o problema de propagação de mudanças é importante, e deve ser resolvido.

## Acoplamento

O problema do acoplamento é que, a partir do momento que uma classe A depende de uma classe B, qualquer mudança em B pode impactar A. Ou seja, quanto mais dependermos de outras classes, mais chances temos de uma mudança na dependência afetar a classe dependente. E, como sabemos, na prática, temos classes que mudam com muita frequência.

### Classes Estáveis


Classes estáveis são aquelas que tendem a mudar muito pouco. A vantagem de classes como essas é que são ótimas classes para se depender, já que elas não propagarão mudança para as classes dependentes.

Classes estáveis são aquelas que geralmente já são dependidas por muitas outras classes do sistema. Um bom exemplo disso são **interfaces**, pois elas geralmente possuem muitas implementações, e aí isso faz com que o desenvolvedor pense melhor antes de sair mudando a interface.

### Como resolver o problema do acoplamento

A ideia é refatorar as classes para elas, quando necessitem de uma dependência, dependam de classes estáveis. Assim, a classe estará segura em relação a propagação de mudanças. É por isso que falamos muito sobre a ideia de "programar para interfaces", afinal, interfaces, além de nos possibilitar o uso de polimorfismo, ainda são estáveis.

# OCP = Classes Abertas

O OCP diz para escrevermos classes que sejam facilmente extensíveis (ou seja, abertas pra extensão). Dessa forma, mudar o comportamento da classe atual é fácil: basta passar outras implementações concretas das abstrações que ela depende.

Classes abertas para extensão, mas fechadas para modificação, também são mais coesas.

# DIP

O DIP nos diz para sempre dependermos de módulos que sejam mais estáveis que o módulo corrente. Abstrações devem depender de abstrações, e implementação deve depender de abstração.

Com isso, diminuímos o risco do acoplamento, afinal abstrações são estáveis, e tendem a não mudar frequentemente, diminuindo a propagação de problemas.

# OCP + DIP

Ao pensar em classes abertas, o programador precisa pensar em abstrações. Afinal, é por meio delas que ele vai conseguir estender o comportamento.

Ao pensar em abstrações, idealmente o programador também pensa na estabilidade de cada uma dessas abstrações. Afinal, ele precisa gerenciar o problema do acoplamento.

# Encapsulamento

Encapsular é esconder os detalhes da implementação dentro da classe. Dessa forma, as classes que farão uso dela, não saberão como ela funciona internamente. A vantagem disso é que conseguimos depois facilmente alterar a implementação, sem que ela impacte nas classes dependentes.

Como fazer para descobrir se um código está encapsulado ou não?
Uma alternativa para isso é criar um trecho de código (seja uma nova classe, ou mesmo um teste automatizado) e fazer uso da classe. Nesse código, você deverá o tempo todo se fazer a pergunta: "Consigo saber COMO a classe está implementando essa regra de negócio?". Se a resposta for sim, então aquele comportamento não está bem encapsulado.

O que é a tal da Lei de Demeter? O que o desenvolvedor ganha quando a segue?

A Lei de Demeter, de maneira simples, diz para que você evite ao máximo fazer expressões como `a.getB().getC().getD().acao()`. O problema dessa cadeia, é que a classe que contém essa expressão, conhece muito sobre o comportamento da classe A, depois da classe B, até D. Se alguma delas mudar, a mudança será propagada para muitos lugares.

Diminuir a quantidade de invocações como essas ajuda você a encapsular melhor o comportamento e o funcionamento interno das classes.

# Herança

Por que fazer bom uso de herança é difícil?

Porque para se fazer bom uso de herança, o desenvolvedor deve pensar em cada método que a classe filha herdou e sobrescreveu, e lembrar que as pré-condições não podem ser apertadas, e as pós-condições não podem serem afrouxadas.

Isso não é tão simples, e requer muito raciocínio do desenvolvedor, sempre que for reescrever um comportamento.

Qual a alternativa para se reaproveitar comportamento, sem fazer uso de herança?

Você pode fazer uso de composição para reaproveitar comportamentos. Diferente da herança, ao compor um objeto, você não precisa se preocupar com as pré e pós condições.

No entanto, ao fazer uso de herança, você ganha o uso de polimorfismo. Quando bem usada, a herança também é uma excelente opção.

# Exercício

Atualmente existe uma classe em um sistema dentro do Orange Talents com um método chamado analisaDadosResposta que recebe como argumento objeto cujo tipo é uma classe criada no próprio sistema para se conectar com formulários do google forms.

Só que agora esse método precisa analisar dados que também podem vir do Microsoft Forms. No futuro, também pode ser necessário analisar dados que vão vir via TypeForms.

Qual seria solução/alteração para que o método analisaDadosResposta não precisasse ser modificado em função das novas fontes de dados do futuro?

Criaria uma interface form e faria o método receber essa interface, e faria todos os Forms extenderem essa interface implementandos os métodos necessários.

Existe um outro método aqui nos sistemas da Orange Talent, analisaMomentoAtual, que recebe como argumento um objeto do tipo Aluno. Dentro deste método, o único método chamado a partir de um objeto do tipo Aluno, é o getCursosFeitos(). Neste momento, qualquer alteração na classe Aluno pode influenciar no comportamento do método analisaMomentoAtual.

Como você faria para que o método analisaMomentoAtual possa continuar a receber um Aluno, mas através de uma interface mais estável?

Só para deixar claro, a ideia é que o ponto do código onde temos algo como analisaMomentoAtual(aluno) continue do mesmo jeito.

A invocação do método em si não precisa ser alterada. Isso é bem desafiador.
