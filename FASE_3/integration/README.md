# Primeiro Teste de Integração

Um teste de unidade isola a classe de suas dependências, e a testa independente delas. Testes de unidade fazem sentido quando nossas classes contém regras de negócio, mas dependem de infra estrutura. Nesses casos, fica fácil isolar a infra estrutura.

Já testes de integração testam a classe de maneira integrada ao serviço que usam. Um teste de DAO, por exemplo, que bate em um banco de dados de verdade, é considerado um teste de integração. Testes como esses são especialmente úteis para testar classes cuja responsabilidade é se comunicar com outros serviços.

```
@Test
    public void deveEncontrarPeloNomeEEmail() {
        Session session = new CriadorDeSessao().getSession();
        UsuarioDao usuarioDao = new UsuarioDao(session);

        Usuario novoUsuario = new Usuario
                ("João da Silva", "joao@dasilva.com.br");
        usuarioDao.salvar(novoUsuario);

        Usuario usuarioDoBanco = usuarioDao
                .porNomeEEmail("João da Silva", "joao@dasilva.com.br");

        assertEquals("João da Silva", usuarioDoBanco.getNome());
        assertEquals("joao@dasilva.com.br", usuarioDoBanco.getEmail());

        session.close();
    }
```


```
    @Test
    public void deveRetornarNuloSeNaoEncontrarUsuario() {
        Session session = new CriadorDeSessao().getSession();
        UsuarioDao usuarioDao = new UsuarioDao(session);

        Usuario usuarioDoBanco = usuarioDao
                .porNomeEEmail("João Joaquim", "joao@joaquim.com.br");

        assertNull(usuarioDoBanco);

        session.close();
    }
```

Quais são os problemas de se usar mocks (e simular a conexão com o banco de dados) para testar DAOs?


Ao usar mocks, estamos "enganando" nosso teste. Um bom teste de DAO é aquele que garante que sua consulta SQL realmente funciona quando enviada para o banco de dados; e a melhor maneira de garantir isso é enviando-a para o banco!

Repare que, na explicação, quando usamos mock objects, nosso teste passou, mesmo estando com bug! Testes como esses não servem de nada, apenas atrapalham.

Sempre que tiver classes cuja responsabilidade é falar com outro sistema, teste-a realmente integrando com esse outro sistema.


Precisamos fechar a sessão para que o banco de dados libere essa porta para um próximo teste. Não fechar a sessão pode implicar em problemas futuros, como testes que falham ou travam. Lembre-se sempre de fechar a conexão.

# Organizando o teste de integração

## Testando um SELECT COUNT

Teste o método `total()` que está no `LeilaoDao`, igual feito no vídeo. Crie um cenário com 2 leilões: um encerrado, outro não. Garanta que o método retorne 1.

Não esqueça também de abrir a transação no `@Before` e dar rollback no `@After`.

```
public class LeilaoDaoTests {
    private Session session;
    private LeilaoDao leilaoDao;
    private UsuarioDao usuarioDao;

    @Before
    public void antes() {
        session = new CriadorDeSessao().getSession();
        leilaoDao = new LeilaoDao(session);
        usuarioDao = new UsuarioDao(session);

        // inicia transacao
        session.beginTransaction();
    }

    @After
    public void depois() {
        // faz o rollback
        session.getTransaction().rollback();
        session.close();
    }

    @Test
    public void deveContarLeiloesNaoEncerrados() {
        // criamos um usuario
        Usuario mauricio = 
                new Usuario("Mauricio Aniche", "mauricio@aniche.com.br");

        // criamos os dois leiloes
        Leilao ativo = 
                new Leilao("Geladeira", 1500.0, mauricio, false);
        Leilao encerrado = 
                new Leilao("XBox", 700.0, mauricio, false);
        encerrado.encerra();

        // persistimos todos no banco
        usuarioDao.salvar(mauricio);
        leilaoDao.salvar(ativo);
        leilaoDao.salvar(encerrado);

        // pedimos o total para o DAO
        long total = leilaoDao.total();

        assertEquals(1L, total);
    }
}
```



## Mais um teste para o SELECT COUNT

Garanta que o método `total()` retorne 0, caso não haja nenhum leilão não encerrado. Para isso, crie dois leilões, dessa vez ambos encerrados.

```

    @Test
    public void deveRetornarZeroSeNaoHaLeiloesNovos() {
    Usuario mauricio = 
            new Usuario("Mauricio Aniche", "mauricio@aniche.com.br");

    Leilao encerrado = 
            new Leilao("XBox", 700.0, mauricio, false);
    Leilao tambemEncerrado = 
            new Leilao("Geladeira", 1500.0, mauricio, false);
    encerrado.encerra();
    tambemEncerrado.encerra();

    usuarioDao.salvar(mauricio);
    leilaoDao.salvar(encerrado);
    leilaoDao.salvar(tambemEncerrado);

    long total = leilaoDao.total();

    assertEquals(0L, total);
}
```


## Testando um SELECT com WHERE

Teste agora o método `novos()` do `LeilaoDao`. Ele retorna todos os leilões que não são usados.

Crie um cenário com um leilão usado e outro não, e garanta que o método devolve apenas o leilão que não é usado.

```
    @Test
    public void deveRetornarLeiloesDeProdutosNovos() {
        Usuario mauricio = new Usuario("Mauricio Aniche",
                "mauricio@aniche.com.br");

        Leilao produtoNovo = 
                new Leilao("XBox", 700.0, mauricio, false);
        Leilao produtoUsado = 
                new Leilao("Geladeira", 1500.0, mauricio,true);

        usuarioDao.salvar(mauricio);
        leilaoDao.salvar(produtoNovo);
        leilaoDao.salvar(produtoUsado);

        List<Leilao> novos = leilaoDao.novos();

        assertEquals(1, novos.size());
        assertEquals("XBox", novos.get(0).getNome());
    }
```

## Ações no fim do teste

O que geralmente fazemos no fim de cada teste de integração?

Limpamos a "sujeira" que colocamos no banco, geralmente dando rollback na transação.

Podemos fazer diversas coisas ao final de cada teste. Mas uma coisa que geralmente não é opcional é limpar o banco de dados para que o próximo teste consiga executar sem problemas.

Fazemos isso dando rollback no banco de dados, ou mesmo executando uma sequência de TRUNCATE TABLEs. Você pode escolher qual maneira agrada mais!

## Testando WHERE com data

Teste agora o método `antigos()` do `LeilaoDao`. Esse método retorna os leilões que foram criados há mais de uma semana atrás.

Crie leilões com datas recentes e datas anteriores a 7 dias, e veja se ele traz somente as anteriores!

```
    @Test
    public void deveTrazerSomenteLeiloesAntigos() {
        Usuario mauricio = new Usuario("Mauricio Aniche",
                "mauricio@aniche.com.br");

        Leilao recente = 
                new Leilao("XBox", 700.0, mauricio, false);
        Leilao antigo = 
                new Leilao("Geladeira", 1500.0, mauricio,true);

        Calendar dataRecente = Calendar.getInstance();
        Calendar dataAntiga = Calendar.getInstance();
        dataAntiga.add(Calendar.DAY_OF_MONTH, -10);

        recente.setDataAbertura(dataRecente);
        antigo.setDataAbertura(dataAntiga);

        usuarioDao.salvar(mauricio);
        leilaoDao.salvar(recente);
        leilaoDao.salvar(antigo);

        List<Leilao> antigos = leilaoDao.antigos();

        assertEquals(1, antigos.size());
        assertEquals("Geladeira", antigos.get(0).getNome());
    }

```



## Testando o limite

Sempre que temos um "<", "<=", ">", ">=" ou qualquer outro comparador, é sempre bom testar o limite. Ou seja, garanta que se o leilão foi criado há exatamente 7 dias atrás, ele apareça na lista.

```
    @Test
    public void deveTrazerSomenteLeiloesAntigosHaMaisDe7Dias() {
        Usuario mauricio = new Usuario("Mauricio Aniche",
                "mauricio@aniche.com.br");

        Leilao noLimite = 
                new Leilao("XBox", 700.0, mauricio, false);

        Calendar dataAntiga = Calendar.getInstance();
        dataAntiga.add(Calendar.DAY_OF_MONTH, -7);

        noLimite.setDataAbertura(dataAntiga);

        usuarioDao.salvar(mauricio);
        leilaoDao.salvar(noLimite);

        List<Leilao> antigos = leilaoDao.antigos();

        assertEquals(1, antigos.size());
    }
```

# Praticando com consultas mais complicadas


## Testando consulta com AND

Teste o método `porPeriodo()` da classe `LeilaoDao`. Como a query possui um AND, precisaremos testar diversos cenários.

Comece pelo cenário: "Leilões não encerrados com data dentro do intervalo devem aparecer". Crie 2 leilões, ambos não encerrados, onde um está dentro do intervalo, e outro não. Garanta que o que está dentro do intervalo apareça no resultado.

```
    @Test
    public void deveTrazerLeiloesNaoEncerradosNoPeriodo() {

        // criando as datas
        Calendar comecoDoIntervalo = Calendar.getInstance();
        comecoDoIntervalo.add(Calendar.DAY_OF_MONTH, -10);
        Calendar fimDoIntervalo = Calendar.getInstance();
        Calendar dataDoLeilao1 = Calendar.getInstance();
        dataDoLeilao1.add(Calendar.DAY_OF_MONTH, -2);
        Calendar dataDoLeilao2 = Calendar.getInstance();
        dataDoLeilao2.add(Calendar.DAY_OF_MONTH, -20);

        Usuario mauricio = new Usuario("Mauricio Aniche",
                "mauricio@aniche.com.br");

        // criando os leiloes, cada um com uma data
        Leilao leilao1 = 
                new Leilao("XBox", 700.0, mauricio, false);
        leilao1.setDataAbertura(dataDoLeilao1);
        Leilao leilao2 = 
                new Leilao("Geladeira", 1700.0, mauricio, false);
        leilao2.setDataAbertura(dataDoLeilao2);

        // persistindo os objetos no banco
        usuarioDao.salvar(mauricio);
        leilaoDao.salvar(leilao1);
        leilaoDao.salvar(leilao2);

        // invocando o metodo para testar
        List<Leilao> leiloes = 
                leilaoDao.porPeriodo(comecoDoIntervalo, fimDoIntervalo);

        // garantindo que a query funcionou
        assertEquals(1, leiloes.size());
        assertEquals("XBox", leiloes.get(0).getNome());
    }
```

## Mais um teste para o porPeriodo()

O próximo cenário a ser testado é "Leilões encerrados com data dentro do intervalo não devem aparecer". Crie leilões encerrados dentro do intervalo. Garanta que a query não os devolva.

```
    @Test
    public void naoDeveTrazerLeiloesEncerradosNoPeriodo() {

        // criando as datas
        Calendar comecoDoIntervalo = Calendar.getInstance();
        comecoDoIntervalo.add(Calendar.DAY_OF_MONTH, -10);
        Calendar fimDoIntervalo = Calendar.getInstance();
        Calendar dataDoLeilao1 = Calendar.getInstance();
        dataDoLeilao1.add(Calendar.DAY_OF_MONTH, -2);

        Usuario mauricio = new Usuario("Mauricio Aniche",
                "mauricio@aniche.com.br");

        // criando os leiloes, cada um com uma data
        Leilao leilao1 = 
                new Leilao("XBox", 700.0, mauricio, false);
        leilao1.setDataAbertura(dataDoLeilao1);
        leilao1.encerra();

        // persistindo os objetos no banco
        usuarioDao.salvar(mauricio);
        leilaoDao.salvar(leilao1);

        // invocando o metodo para testar
        List<Leilao> leiloes = 
                leilaoDao.porPeriodo(comecoDoIntervalo, fimDoIntervalo);

        // garantindo que a query funcionou
        assertEquals(0, leiloes.size());
    }
```

## Test Data Builders

No curso anterior, discutimos sobre Test Data Builders. Use o Builder abaixo para facilitar a criação dos leilões que são usados nos cenários dos testes da classe `LeilaoDaoTests`. Lembre-se que a ideia do Builder é facilitar a criação de cenários, para que o código do teste fique mais claro.

```
public class LeilaoBuilder {

    private Usuario dono;
    private double valor;
    private String nome;
    private boolean usado;
    private Calendar dataAbertura;
    private boolean encerrado;

    public LeilaoBuilder() {
        this.dono = new Usuario("Joao da Silva", "joao@silva.com.br");
        this.valor = 1500.0;
        this.nome = "XBox";
        this.usado = false;
        this.dataAbertura = Calendar.getInstance();
    }

    public LeilaoBuilder comDono(Usuario dono) {
        this.dono = dono;
        return this;
    }

    public LeilaoBuilder comValor(double valor) {
        this.valor = valor;
        return this;
    }

    public LeilaoBuilder comNome(String nome) {
        this.nome = nome;
        return this;
    }

    public LeilaoBuilder usado() {
        this.usado = true;
        return this;
    }

    public LeilaoBuilder encerrado() {
        this.encerrado = true;
        return this;
    }

    public LeilaoBuilder diasAtras(int dias) {
        Calendar data = Calendar.getInstance();
        data.add(Calendar.DAY_OF_MONTH, -dias);

        this.dataAbertura = data;

        return this;
    }

    public Leilao constroi() {
        Leilao leilao = new Leilao(nome, valor, dono, usado);
        leilao.setDataAbertura(dataAbertura);
        if(encerrado) leilao.encerra();

        return leilao;
    }

}
```

Reescreva os testes para usar o Builder.

```
public class LeilaoDaoTests {
    private Session session;
    private LeilaoDao leilaoDao;
    private UsuarioDao usuarioDao;

    @Before
    public void antes() {
        session = new CriadorDeSessao().getSession();
        leilaoDao = new LeilaoDao(session);
        usuarioDao = new UsuarioDao(session);

        // inicia transacao
        session.beginTransaction();
    }

    @After
    public void depois() {
        // faz o rollback
        session.getTransaction().rollback();
        session.close();
    }

    @Test
    public void deveContarLeiloesNaoEncerrados() {
        // criamos um usuario
        Usuario mauricio = new Usuario("Mauricio Aniche",
                "mauricio@aniche.com.br");

        // criamos os dois leiloes

        Leilao ativo = new LeilaoBuilder()
            .comDono(mauricio)
            .constroi();
        Leilao encerrado = new LeilaoBuilder()
            .comDono(mauricio)
            .encerrado()
            .constroi();

        // persistimos todos no banco
        usuarioDao.salvar(mauricio);
        leilaoDao.salvar(ativo);
        leilaoDao.salvar(encerrado);

        // pedimos o total para o DAO
        long total = leilaoDao.total();

        assertEquals(1L, total);
    }

    @Test
    public void deveRetornarZeroSeNaoHaLeiloesNovos() {
        Usuario mauricio = new Usuario("Mauricio Aniche",
                "mauricio@aniche.com.br");

        Leilao encerrado = new LeilaoBuilder()
                .comDono(mauricio)
                .encerrado()
                .constroi();
        Leilao tambemEncerrado = new LeilaoBuilder()
                .comDono(mauricio)
                .encerrado().constroi();

        usuarioDao.salvar(mauricio);
        leilaoDao.salvar(encerrado);
        leilaoDao.salvar(tambemEncerrado);

        long total = leilaoDao.total();

        assertEquals(0L, total);
    }

    @Test
    public void deveRetornarLeiloesDeProdutosNovos() {
        Usuario mauricio = new Usuario("Mauricio Aniche",
                "mauricio@aniche.com.br");

        Leilao produtoNovo = 
                new LeilaoBuilder()
                .comDono(mauricio)
                .comNome("XBox")
                .constroi(); 
        Leilao produtoUsado = 
                new LeilaoBuilder().comNome("XBox")
                .comDono(mauricio)
                .usado()
                .constroi();

        usuarioDao.salvar(mauricio);
        leilaoDao.salvar(produtoNovo);
        leilaoDao.salvar(produtoUsado);

        List<Leilao> novos = leilaoDao.novos();

        assertEquals(1, novos.size());
        assertEquals("XBox", novos.get(0).getNome());
    }

    @Test
    public void deveTrazerSomenteLeiloesAntigos() {
        Usuario mauricio = new Usuario("Mauricio Aniche",
                "mauricio@aniche.com.br");

        Leilao recente = new LeilaoBuilder()
                .comNome("XBox")
                .comDono(mauricio)
                .constroi();
        Leilao antigo = new LeilaoBuilder()
                .comDono(mauricio)
                .comNome("Geladeira")
                .diasAtras(10)
                .constroi();

        usuarioDao.salvar(mauricio);
        leilaoDao.salvar(recente);
        leilaoDao.salvar(antigo);

        List<Leilao> antigos = leilaoDao.antigos();

        assertEquals(1, antigos.size());
        assertEquals("Geladeira", antigos.get(0).getNome());
    }

    @Test
    public void deveTrazerSomenteLeiloesAntigosHaMaisDe7Dias() {
        Usuario mauricio = new Usuario("Mauricio Aniche",
                "mauricio@aniche.com.br");

        Leilao noLimite = new LeilaoBuilder()
                .diasAtras(7)
                .comDono(mauricio)
                .constroi();

        Calendar dataAntiga = Calendar.getInstance();
        dataAntiga.add(Calendar.DAY_OF_MONTH, -7);

        noLimite.setDataAbertura(dataAntiga);

        usuarioDao.salvar(mauricio);
        leilaoDao.salvar(noLimite);

        List<Leilao> antigos = leilaoDao.antigos();

        assertEquals(1, antigos.size());
    }

    @Test
    public void deveTrazerLeiloesNaoEncerradosNoPeriodo() {

        // criando as datas
        Calendar comecoDoIntervalo = Calendar.getInstance();
        comecoDoIntervalo.add(Calendar.DAY_OF_MONTH, -10);
        Calendar fimDoIntervalo = Calendar.getInstance();

        Usuario mauricio = new Usuario("Mauricio Aniche",
                "mauricio@aniche.com.br");

        // criando os leiloes, cada um com uma data
        Leilao leilao1 = new LeilaoBuilder()
                .diasAtras(2)
                .comDono(mauricio)
                .comNome("XBox")
                .constroi();

        Leilao leilao2 = new LeilaoBuilder()
                .diasAtras(20)
                .comDono(mauricio)
                .comNome("XBox")
                .constroi();

        // persistindo os objetos no banco
        usuarioDao.salvar(mauricio);
        leilaoDao.salvar(leilao1);
        leilaoDao.salvar(leilao2);

        // invocando o metodo para testar
        List<Leilao> leiloes = 
                leilaoDao.porPeriodo(comecoDoIntervalo, fimDoIntervalo);

        // garantindo que a query funcionou
        assertEquals(1, leiloes.size());
        assertEquals("XBox", leiloes.get(0).getNome());
    }

    @Test
    public void naoDeveTrazerLeiloesEncerradosNoPeriodo() {

        // criando as datas
        Calendar comecoDoIntervalo = Calendar.getInstance();
        comecoDoIntervalo.add(Calendar.DAY_OF_MONTH, -10);
        Calendar fimDoIntervalo = Calendar.getInstance();
        Calendar dataDoLeilao1 = Calendar.getInstance();
        dataDoLeilao1.add(Calendar.DAY_OF_MONTH, -2);

        Usuario mauricio = new Usuario("Mauricio Aniche",
                "mauricio@aniche.com.br");

        // criando os leiloes, cada um com uma data
        Leilao leilao1 = new LeilaoBuilder()
                .comDono(mauricio)
                .diasAtras(2)
                .comNome("XBox")
                .encerrado()
                .constroi();

        // persistindo os objetos no banco
        usuarioDao.salvar(mauricio);
        leilaoDao.salvar(leilao1);

        // invocando o metodo para testar
        List<Leilao> leiloes = 
                leilaoDao.porPeriodo(comecoDoIntervalo, fimDoIntervalo);

        // garantindo que a query funcionou
        assertEquals(0, leiloes.size());
    }
}
```

## Testando consulta com dependências

O método `disputadosEntre(Calendar inicio, Calendar fim)` devolve os leilões não encerrados com no mínimo 3 lances dados, dentro de um intervalo de valor:

```
    @SuppressWarnings("unchecked")
    public List<Leilao> disputadosEntre(Calendar inicio, Calendar fim) {
        return session.createQuery("from Leilao l where l.valorInicial " +
                "between :inicio and :fim and l.encerrado = false " +
                "and size(l.lances) >= 3")
                .setParameter("inicio", inicio)
                .setParameter("fim", fim)
                .list();
    }
```

Veja que a query é complexa!
Seus testes deverão ser parecidos com o abaixo:

```
    @Test
    public void deveRetornarLeiloesDisputados() {
        Usuario mauricio = new Usuario("Mauricio", "mauricio@aniche.com.br");
        Usuario marcelo = new Usuario("Marcelo", "marcelo@aniche.com.br");

        Leilao leilao1 = new LeilaoBuilder()
                .comDono(marcelo)
                .comValor(3000.0)
                .comLance(Calendar.getInstance(), mauricio, 3000.0)
                .comLance(Calendar.getInstance(), marcelo, 3100.0)
                .constroi();

        Leilao leilao2 = new LeilaoBuilder()
                .comDono(mauricio)
                .comValor(3200.0)
                .comLance(Calendar.getInstance(), mauricio, 3000.0)
                .comLance(Calendar.getInstance(), marcelo, 3100.0)
                .comLance(Calendar.getInstance(), mauricio, 3200.0)
                .comLance(Calendar.getInstance(), marcelo, 3300.0)
                .comLance(Calendar.getInstance(), mauricio, 3400.0)
                .comLance(Calendar.getInstance(), marcelo, 3500.0)
                .constroi();

        usuarioDao.salvar(marcelo);
        usuarioDao.salvar(mauricio);
        leilaoDao.salvar(leilao1);
        leilaoDao.salvar(leilao2);

        List<Leilao> leiloes = leilaoDao.disputadosEntre(2500, 3500);

        assertEquals(1, leiloes.size());
        assertEquals(3200.0, leiloes.get(0).getValorInicial(), 0.00001);
    }
```

## Dificuldades no teste de integração

Geralmente o grande desafio é justamente montar o cenário e validar o resultado esperado no sistema externo. No caso de banco de dados, precisamos fazer INSERTs, DELETEs, e SELECTs para validar. Além disso, ainda precisamos manter o estado do sistema consistente, ou seja, precisamos limpar o banco de dados constantemente para que um teste não atrapalhe o outro.

Veja que usamos o JUnit da mesma forma. A diferença é que justamente precisamos nos comunicar com o outro sistema.

## Lista sem repetição dos leilões em que um usuário deu um lance

No `LeilaoDao`, temos o método `listaLeiloesDoUsuario` que devolve a lista de todos os leilões em que o usuário deu pelo menos um lance:

```
    public List<Leilao> listaLeiloesDoUsuario(Usuario usuario) {
        return session.createQuery("select lance.leilao " +
                                   "from Lance lance " +
                                   "where lance.usuario = :usuario")
                .setParameter("usuario", usuario).list();
    }
```

Esse método deve devolver uma lista de leilões sem repetição. Escreva testes que garantam que o método funciona corretamente. Em particular, essa query contém um bug.

Para esse método, devemos garantir que a lista contém apenas leilões em que o usuário deu pelo menos um lance:

```
    @Test
    public void listaSomenteOsLeiloesDoUsuario() throws Exception {
        Usuario dono = new Usuario("Mauricio", "m@a.com");
        Usuario comprador = new Usuario("Victor", "v@v.com");
        Usuario comprador2 = new Usuario("Guilherme", "g@g.com");
        Leilao leilao = new LeilaoBuilder()
            .comDono(dono)
            .comValor(50.0)
            .comLance(Calendar.getInstance(), comprador, 100.0)
            .comLance(Calendar.getInstance(), comprador2, 200.0)
            .constroi();
        Leilao leilao2 = new LeilaoBuilder()
            .comDono(dono)
            .comValor(250.0)
            .comLance(Calendar.getInstance(), comprador2, 100.0)
            .constroi();
        usuarioDao.salvar(dono);
        usuarioDao.salvar(comprador);
        usuarioDao.salvar(comprador2);
        leilaoDao.salvar(leilao);
        leilaoDao.salvar(leilao2);

        List<Leilao> leiloes = leilaoDao.listaLeiloesDoUsuario(comprador);
        assertEquals(1, leiloes.size());
        assertEquals(leilao, leiloes.get(0));
    }
```

Além disso, devemos garantir que a lista devolvida pelo DAO não contém nenhum elemento repetido:

```
    @Test
    public void listaDeLeiloesDeUmUsuarioNaoTemRepeticao() throws Exception {
        Usuario dono = new Usuario("Mauricio", "m@a.com");
        Usuario comprador = new Usuario("Victor", "v@v.com");
        Leilao leilao = new LeilaoBuilder()
            .comDono(dono)
            .comLance(Calendar.getInstance(), comprador, 100.0)
            .comLance(Calendar.getInstance(), comprador, 200.0)
            .constroi();
        usuarioDao.salvar(dono);
        usuarioDao.salvar(comprador);
        leilaoDao.salvar(leilao);

        List<Leilao> leiloes = leilaoDao.listaLeiloesDoUsuario(comprador);
        assertEquals(1, leiloes.size());
        assertEquals(leilao, leiloes.get(0));
    }
```

Veja que nosso segundo teste aponta um bug no código, o método devolve uma lista de leilões contendo repetições!

Mesmo queries simples precisam de testes!

## Valor inicial médio dos leilões em que o usuário deu pelo menos um lance

A casa de leilões precisa saber o perfil de seus usuários, para isso, ela precisa saber qual é o valorInicial médio dos leilões que o usuário participou (deu pelo menos um lance). Para atender esse requisito da casa de leilões, criamos o seguinte método:

```
    public double getValorInicialMedioDoUsuario(Usuario usuario) {
        return (Double) session.createQuery("select avg(lance.leilao.valorInicial) " +
                                            "from Lance lance " +
                                            "where lance.usuario = :usuario")
                    .setParameter("usuario", usuario)
                    .uniqueResult();
    }
```

Esse método funciona da forma esperada? Escreva testes que mostrem que o método funciona ou que ele possui um bug.
O método `getValorMédioDoUsuario` possui um bug! Podemos mostrar que o método não funciona como esperado com o seguinte teste:

```
    @Test
    public void devolveAMediaDoValorInicialDosLeiloesQueOUsuarioParticipou(){
        Usuario dono = new Usuario("Mauricio", "m@a.com");
        Usuario comprador = new Usuario("Victor", "v@v.com");
        Leilao leilao = new LeilaoBuilder()
            .comDono(dono)
            .comValor(50.0)
            .comLance(Calendar.getInstance(), comprador, 100.0)
            .comLance(Calendar.getInstance(), comprador, 200.0)
            .constroi();
        Leilao leilao2 = new LeilaoBuilder()
            .comDono(dono)
            .comValor(250.0)
            .comLance(Calendar.getInstance(), comprador, 100.0)
            .constroi();
        usuarioDao.salvar(dono);
        usuarioDao.salvar(comprador);
        leilaoDao.salvar(leilao);
        leilaoDao.salvar(leilao2);

        assertEquals(150.0, leilaoDao.getValorInicialMedioDoUsuario(comprador), 0.001);
    }
```

# Testando alteração e deleção

## Testando a deleção

Escreva um teste para o método `deletar()` do `UsuarioDao`.

```
    @Test
    public void deveDeletarUmUsuario() {
        Usuario usuario = 
                new Usuario("Mauricio Aniche", "mauricio@aniche.com.br");

        usuarioDao.salvar(usuario);
        usuarioDao.deletar(usuario);

        session.flush();

        Usuario usuarioNoBanco = 
                usuarioDao.porNomeEEmail("Mauricio Aniche", "mauricio@aniche.com.br");

        assertNull(usuarioNoBanco);

    }
```

## Testando mais uma deleção

Teste agora o método `deleta()` do `LeilaoDao`.

Para garantir que o leilão foi deletado, faça a busca por id. No momento que você invocar o salvar(), o Hibernate preencherá o ID gerado pelo banco na instância de leilão automaticamente. Por isso, podemos fazer:

```
assertNull(leilaoDao.porId(leilao.getId()));
```

---

```
    @Test
    public void deveDeletarUmLeilao() {
        Usuario mauricio = new Usuario("Mauricio", "m@a.com");
        Leilao leilao = new LeilaoBuilder()
            .comUsuario(mauricio)
            .comLance(Calendar.getInstance(), mauricio, 10000.0)
            .constroi();

        usuarioDao.salvar(mauricio);
        leilaoDao.salvar(leilao);

        session.flush();

        leilaoDao.deleta(leilao);

        assertNull(leilaoDao.porId(leilao.getId()));

    }
```

## Flush

Para que serve o "flush" no meio do teste?
Para enviar os comandos SQL criados até então para o banco de dados e garantir que o banco os processou.

Precisamos forçar o Hibernate a enviar comandos para o banco de dados, e garantir que as próximas consultas levarão em consideração as anteriores. Para isso, o `flush()` torna-se obrigatório em alguns testes!

Geralmente em testes que fazemos SELECTs logo após uma deleção ou alteração em batch, o uso do flush é obrigatório.

## Testando uma alteração

Teste agora o método `alterar()` do `UsuarioDao`.

Crie um usuário e salve-o no banco. Em seguida, altere o nome e e-mail e faça uma alteração. Faça um flush para garantir que os comandos chegaram no banco. Em seguida, faça duas buscas: uma buscando o nome antigo e outra buscando o nome novo.

```
    @Test
    public void deveAlterarUmUsuario() {
        Usuario usuario = 
                new Usuario("Mauricio Aniche", "mauricio@aniche.com.br");

        usuarioDao.salvar(usuario);

        usuario.setNome("João da Silva");
        usuario.setEmail("joao@silva.com.br");

        usuarioDao.atualizar(usuario);

        session.flush();

        Usuario novoUsuario = 
                usuarioDao.porNomeEEmail("João da Silva", "joao@silva.com.br");
        assertNotNull(novoUsuario);
        System.out.println(novoUsuario);

        Usuario usuarioInexistente = 
                usuarioDao.porNomeEEmail("Mauricio Aniche", "mauricio@aniche.com.br");
        assertNull(usuarioInexistente);

    }
```


## O que você acha de testar métodos de alteração?

O que você acha de testar métodos de alteração como os que você acabou de testar?

Tudo é questão de feedback. Eles podem fazer sentido quando o processo de alteração é complicado. Por exemplo, atualizar um usuário é bem simples e feito pelo próprio Hibernate. Não há muito como dar errado.

Agora, atualizar um leilão pode ser mais trabalhoso, afinal precisamos atualizar lances juntos. Nesses casos, testar uma alteração pode ser bem importante.
