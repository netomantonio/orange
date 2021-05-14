# About JPA

* Escrever SQL no código Java é trabalhoso e precisa de manutenção contínua
* A JPA é um ORM (*Object Relacional Mapper*) Java
  * Um ORM mapeia as classes para tabelas e gera o SQL de forma automática
* Para inicializar a JPA, é preciso definir um arquivo de configuração, chamado **persistence.xml**
  * Nele, colocamos as configurações mais importantes, como o *driver* e os dados da conexão
* A classe `Persistence` lê a configuração e cria uma `EntityManagerFactory`
* Podemos usar a JPA para gerar e atualizar as tabelas no banco de dados
* Uma entidade deve usar as anotações `@Entity` e `@Id`
  * `@GeneratedValue` não é obrigatório, mas pode ser útil para definir uma chave *auto-increment*

## Transations

Para que uma persistencia seja efetivada, é necessário iniciar uma transação antes de persistir, e após persistir, é necessário realizar um commit da transação. Isso garante que caso aja falha no processo, um rollback seja efetivado, fazendo com que não tenha alterações no banco.

### O que é uma transaction?

A transação é um mecanismo para manter a consistência das alterações de estado no banco, visto que todas as operações precisam ser executadas com sucesso, para que a transação seja confirmada.

## Find (EntityManager Method)

Ao utilizar o método find, para buscar dados de um modelo direto do BD, o entity manager, cria uma referência para o objeto, com status de Managed. Desse modo, o JPA reconhece quando os dados originais são alterados, fazendo com que ao iniciar uma transação e fazer qualquer alteração e após isso gerar um commit, automaticamente o JPA sincroniza esses dados com os dados do banco de dados, persistindo os dados atualizados no banco de dados.

# Entity Manager

É o serviço central para todas as ações de persistência. Entidades são objetos Java claros alocados como qualquer outro objeto Java. A persistência só fica explicita após o código interagir com o EntityManager. EM faz o mapeamento entre a classe e a fonte de dados.  Provê APIs para criar consultas, busca objetos, sincroniza e insere.

## Managed

É um estado da entidade, e quando nesse estado está automaticamente sincronizada com o banco de dados.

A característica do estado *Managed* é a sincronização automática.

## Detached

Toda entidade mapeada, após fechar o entity manager, vai para o estado Detached, ou seja? não está mais sincronizada com o banco, isso quer dizer que se fizer alterações nesse objeto, não irá refletir nos dados do banco de dados. Sempre que usamos JPA, queremos usufruir do estado managed e evitar o estado Detached. Para isso, podemos utilizar um esquema para alterar o estado do objeto em estado Detached, utilizando método merge de entity manager, ao utilizar esse método passando como parâmetro o objeto, o jpa irá atualizar as informações para refletir as alterações dentro do banco de dados.

## Transient

Sua característica é um objeto que existe na memória, possui informações e não tem `Id` nenhum, mas pode se tornar Managed futuramente. Para fazer esta transformação, bastará termos um `EntityManager` para inserirmos `persist()` passando o `objeto`.


## Removed

Caso queiramos **remover** o objeto Managed, poderemos usar o método **`remove()`** passando um `objeto` que será deletado de seu contexto JPA, o que gerará a sincronização e aplicará um `delete` no banco de dados, transformando-o em um estado **Removed**.

Para executarmos o código, executaremos uma transação com `get.Transaction()` e `begin()` conforme é necessário para a mudança de estado. Por fim, usaremos `commit()`.

### Exemplo

```
import br.com.alura.jpa.modelo.Conta;

public class TestandoEstados {
    public static void main(String[] args) {

        //Transient
        Conta conta = new Conta();
        conta.setTitular("Almiro");
        conta.setNumero(321321);
        conta.setAgencia(123123);

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("alura");
        EntityManager em = emf.createEntityManager();

        em.getTransaction().begin();

        //Transient -> Managed
        em.persist(conta);

        //Managed -> Removed
        em.remove(conta);

        em.getTransaction().commit();
    }
}
```


Como resultado, veremos que a `Conta` é criada e logo em seguida é deletada. Digitando `select * from conta;`, visualizaremos a tabela sem a última conta do Almiro, pois fora removida.

Portanto, tudo é consequência da transição de estados.

Como já aprendemos todos os estados possíveis de entidades trabalhando com JPA, saberemos que, como desenvolvedores, nosso objetivo é **sempre transformá-las em Managed**; afinal, poderemos usufruir da sincronização automática com as `querys` necessárias.

Também sabemos que analogias geralmente feitas em estudos **nem sempre** são verdadeiras, e deveremos ter em mente que as `querys` são resultantes da transição de estados.


## Resumo


* A JPA sincroniza o estado de uma entidade gerenciada com o banco de dados
  * Isto é, o SQL será gerado baseado na diferença entre a entidade em memória e o banco de dados
  * Para essa sincronização acontecer, a entidade precisa estar gerenciada (*Managed*)
* Os estados de uma entidade são: *Managed*, *Detached*, *Transient* e *Removed*
* Os métodos do `EntityManager`, como `persist`, `merge` ou `remove` alteram o estado da entidade

# Relational Mapping


* Relacionamentos entre entidades precisam ser configurados pelas anotações no atributo que define o relacionamento na classe
* Um relacionamento do tipo *Muitos-para-Um* deve usar anotação a `@ManyToOne`
  * A anotação `@ManyToOne` causa a criação de uma chave estrangeira
* A JPA carrega automaticamente o relacionamento ao carregar a entidade


* Como relacionar uma entidade com uma coleção de uma outra entidade
  * Para tal, temos as anotações `@OneToMany` e `@ManyToMany`, dependendo da cardinalidade
  * Um relacionamento `@ToMany` precisa de uma tabela extra para a representação no banco de dados
* Aprendemos também como relacionar uma entidade com uma outra entidade
  * Para tal, temos as anotações `@OneToOne` e `@ManyToOne`, dependendo da cardinalidade
* Ao persistir uma entidade, devemos persistir as entidades transientes do relacionamento

# JPQL

Java Persistence Query Language, é uma linguagem de consulta em alto nível, abstraindo e padronizando consultas ao banco de dados, independentemente do SGBD sendo utilizado, facilita tbm a construção de query que pode ser reaproveitadas em qualquer SGBD, inclusive caso necessite migração de base de dados.

Resumindo, JPQL é orientado a objetos diferentemente de SQL.

## Resumo


* Como executar *queries* com JPA, usando **JPQL**
  * A linguagem JPQL é bem parecida com SQL, no entanto orientada a objetos
  * Com JPQL, usamos as classes e atributos (no lugar das tabelas e colunas) para definir a pesquisa
  * O objeto do tipo `javax.persistence.Query` encapsula a *query*
  * `javax.persistence.TypedQuery` é a versão *tipada* do `javax.persistence.Query`

# Exercício

Dado que todo(a) aluno(a) tem um email (máximo de 30 caracteres),nome (máximo de 30 caracteres) e idade (entre 1 e 100). Como você vai modelar essa classe e configurá-la para que possa ser utilizada pelo Hibernate?

Respostas: Criaria uma classe aluno, com anotação entity, criaria os campos requisitados de forma privada, utilizando string para o que é texto e int para idade, incluiria um campo id do tipo long de forma privada com anotação id e generated value utilizando a estratégia de identity, juntamente com os getters e setters de forma publica e incluiria o caminho da classe no persistence.xml.

Motivo: Usar as anotações entity e id para informar ao hibernate como ele deve agir e o que deve criar a estrutura da tabela, colocar o caminho no persistece para que o hibernate possa mapear a classe, criar os métodos privados, para que não se tenha acesso direto aos atributos fora da classe garantindo um nivel maior de segurança no código.

Dentro do bootcamp temos também um conjunto de avaliações que são respondidas pelas pessoas. Toda avaliação tem um título e uma descrição do que precisa ser feito. Como você vai modelar essa classe e configurá-la para que possa ser utilizada pelo Hibernate?

Resposta: Criaria uma classe chamada avaliacao anotada com entity, criando os atributos de forma privada, sendo eles, o campo id, do tipo long e as anotações id e generated value com estratégia de identity, criaria os campos titulo e descricao do tipo string, e na descricao colocaria a anotação column passando um parametro columnDefinition="MEDIUMTEXT" e a anotação Lob e incluiria o caminho da classe no persistence.xml.

Motivo: ao utilizar columnDefinition="MEDIUMTEXT" e Lob, estou informando ao hibernate que o campo descricao é de um tipo texto maior que varchar, reservando um espaço maior para armazenar a informação.

Cada aluno(a) responde uma ou mais avaliações que chamamos de cognitive walkthrough, ela tem que descrever os passos da solução dela para determinada situação problema. Toda resposta tem um campo aberto para que a pessoa consiga descrever a solução dela. É necessário que toda resposta seja linkada com a pessoa que a respondeu e também com a avaliação relativa àquela resposta. Como você vai modelar essa classe e configurá-la para que possa ser utilizada pelo Hibernate?

Resposta: criar uma classe RespostaCognitiva com as devidas anotações para e campos necessários respeitando o padrão Bean e para criar os relacionamentos, incluiria dois campos, um do tipo aluno e outro do tipo avaliação e anotaria-os com manyToOne e incluiria o caminho da classe no persistence.xml.

Motivo: ao escolher manyToOne estou garantindo que uma resposta esteja linkada a uma unica avaliacao e a um unico cliente e que uma avaliao e um aluno tenha uma ou mais respostas.

Além de responder sua avaliação, a pessoa também responde um outro formulário onde ela corrige sua resposta em função da resposta de um mentor(a). Essa correção sempre tem uma nota de 1 a 10 e está linkada com a avaliação respondida pela própria pessoa. Como você vai modelar essa classe e configurá-la para que possa ser utilizada pelo Hibernate?

Resposta: criaria uma classe chamada AutoAvaliacao com o campo id fazendo as devidas anotações para chave primaria e geração de valor auto incrementado, criaria um campo do tipo RespostaCognitiva com anotação OneToOne, garantindo que uma respostaCognitiva esteja atrelada unica e exclusivamente em uma AutoAvaliação e vice-versa, criaria uma classe especial enum chamada Nota com as notas de zero a dez de forma escrita e criaria um campo do tipo Nota, com anotação enumerated com o parâmetro enumtype ordinal e incluiria o caminho da classe AutoAvaliacao no persistence.xml.

Motivo: A criação da classe especial enum, é para garantir que nenhum valor que esteja fora do range de 0 á 10 seja alocado de forma indevida ao banco de dados.

Agora que você modelou e mapeou as classes para que possam ser utilizadas pelo hibernate, temos desafios extras.

Quais são os passos para salvar para salvar um(a) aluno(a)?

criaria um novo objeto da classe Aluno, setaria os atributos com os valores necessários, instanciaria um EntityManagerFactory depois criaria um EntityManager, através dele inciaria uma transação, persistiria os dados, daria um commit na transação e fecharia a transação.

Quais são os passos para salvar para salvar uma avaliação?

criaria um novo objeto da classe Avaliacao, setaria os atributos com os valores necessários, instanciaria um EntityManagerFactory depois criaria um EntityManager, através dele inciaria uma transação, persistiria os dados, daria um commit na transação e fecharia a transação.

Quais são os passos para salvar uma resposta de um(a) aluno(a)?

criaria um novo objeto da classe RespostaCognitiva, criaria um objeto da classe Aluno, preenchendo os dados com os valores correspondentes ao aluno que está respondendo , criaria um objeto da classe Avaliacao com os dados referentes a Avaliacao a ser respondida, setaria o aluno e a avaliacao no objeto da classe resposta, setaria a resposta do aluno no atributo descricao e instanciaria um EntityManagerFactory depois criaria um EntityManager, através dele inciaria uma transação, persistiria os dados, daria um commit na transação e fecharia a transação.

Quais são os passos para salvar a auto correção de um(a) aluno(a)?

Criaria um objeto da classe AutoAvaliacao, setando a resposta a qual está fazendo a auto avaliação, criaria um algorítimo para validar a nota escolhida e utilizar a classe especial correspondente na hora de setar a nota no atributo do objeto de auto avaliação e instanciaria um EntityManagerFactory depois criaria um EntityManager, através dele inciaria uma transação, persistiria os dados, daria um commit na transação e fecharia a transação.

Caso você precise carregar uma auto correção e tenha que descobrir o nome do(a) aluno(a) que fez, como você faria? Algum ponto de atenção que deveria ter?

Resposta: através do link entre os atributos, levando em consideração que o objeto chama autoAvaliacao, usaria o esquema de link dos atributos, acessando autoAvaliacao.resposta.aluno.nome armazenando o dados em um objeto do tipo String. Ter atenção na estrutura de links para que acesse o caminho correto.

Caso você precise carregar as respostas de um(a) aluno(a) a partir do objeto da classe Aluno, como você faria? Algum ponto de atenção?

Resposta: Através do objeto da classe aluno, pegaria o id, e faria uma busca no banco de dados através de um EntityManager criando uma query JPQL buscando todas as respostas correspondentes ao aluno e através do método createQuery, passando como parametro a a query JPQL e a classe respostas, armazenando em um objeto do tipo TypeQuery, setaria o parametro e através do método getResult, armazenaria o resultado em uma List do tipo RespostaCognitiva.
