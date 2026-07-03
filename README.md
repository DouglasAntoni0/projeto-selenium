# Projeto Selenium - Demo Web Shop

[![Selenium Tests](https://github.com/DouglasAntoni0/projeto-selenium/actions/workflows/selenium-tests.yml/badge.svg)](https://github.com/DouglasAntoni0/projeto-selenium/actions/workflows/selenium-tests.yml)
[![Java](https://img.shields.io/badge/Java-21-blue.svg)](https://www.oracle.com/java/)
[![Selenium](https://img.shields.io/badge/Selenium-4-brightgreen.svg)](https://www.selenium.dev/)
[![JUnit 5](https://img.shields.io/badge/JUnit-5.11-red.svg)](https://junit.org/junit5/)

Suite profissional de automacao Web criada do zero com **Selenium WebDriver**, **Java 21**, **JUnit 5**, **AssertJ**, **DataFaker** e **Allure Report**.

Repositorio oficial do projeto: [https://github.com/DouglasAntoni0/projeto-selenium.git](https://github.com/DouglasAntoni0/projeto-selenium.git)

## Site testado

O alvo escolhido foi o **Demo Web Shop da Tricentis**:

[https://demowebshop.tricentis.com](https://demowebshop.tricentis.com)

### Por que este site?

Escolhi o Demo Web Shop porque ele oferece um fluxo de e-commerce completo e suficientemente complexo para uma suite de QA robusta:

- cadastro e login de clientes;
- catalogo com categorias e produtos;
- produtos simples e produtos com campos obrigatorios, como gift cards;
- carrinho com quantidade, subtotal, total e remocao de itens;
- checkout completo com endereco, frete, pagamento e confirmacao;
- formulario de contato com validacoes de campos obrigatorios.

Ele e melhor para este projeto do que sites muito pequenos, porque permite exercitar uma arquitetura POM real, com estados de sessao, dados dinamicos, AJAX, regras de negocio e assercoes de valor.

## Tecnologias

| Tecnologia | Uso |
| --- | --- |
| Java 21 | Linguagem principal, tipada e madura para suites grandes |
| Selenium WebDriver 4 | Automacao Web real em navegador |
| JUnit 5 | Runner, lifecycle e tags de teste |
| AssertJ | Assercoes fluentes e legiveis |
| DataFaker | Massa de dados dinamica para clientes, enderecos e contato |
| Allure Report | Relatorio rico com evidencias |
| Maven | Build, dependencias e execucao |
| GitHub Actions | CI/CD em push na branch `main` |

## Escopo coberto

| Area | Cenarios |
| --- | --- |
| Login | sucesso, senha incorreta, formulario vazio, conta inexistente, remember me |
| Cadastro | sucesso com dados dinamicos, campos obrigatorios, email duplicado, senha divergente |
| Carrinho | adicionar item, validar nome/preco/subtotal, alterar quantidade, remover item, bloquear checkout sem termos |
| Checkout | fluxo completo autenticado, validacao de redirecionamento para usuario anonimo |
| Contato | envio com sucesso, campos obrigatorios, email invalido |
| Produto especial | gift card com campos obrigatorios e massa dinamica |

## Arquitetura

```text
src/test/java/br/com/douglasantoni0/selenium
  config/                 Configuracao de ambiente e propriedades
  driver/                 Criacao e encerramento do WebDriver
  model/                  Objetos de dados usados nos testes
  pages/
    locators/             Camada exclusiva de mapeamento de elementos
    components/           Componentes reutilizaveis, como o header
    *.java                Page Objects com acoes e leituras de pagina
  support/
    assertions/           Helpers de assercao e conversao
    data/                 Factories com DataFaker e dados do dominio
    exceptions/           Excecoes de interacao de pagina
    extensions/           Evidencias Allure em falhas
  tests/                  Camada de execucao dos testes
```

### Decisoes de design

- **Page Object Model de verdade:** locators ficam em `pages/locators`, acoes em `pages` e cenarios em `tests`.
- **Sem sleeps fixos:** a suite usa esperas explicitas para estados reais da UI, como notificacoes AJAX e troca de etapa no checkout.
- **Dados dinamicos:** emails, nomes, enderecos e mensagens sao gerados por teste para reduzir dependencia entre cenarios.
- **Assercoes de negocio:** os testes validam textos, URL, estados de checkbox, subtotal, total e mensagens de erro.
- **Evidencia automatica:** em falhas, screenshot e HTML da pagina sao anexados ao Allure.

## Pre-requisitos

- Java 21 ou superior
- Maven 3.9 ou superior
- Google Chrome instalado

O Selenium 4 usa o Selenium Manager para resolver drivers automaticamente na maioria dos ambientes.

## Como instalar

Clone o repositorio:

```bash
git clone https://github.com/DouglasAntoni0/projeto-selenium.git
cd projeto-selenium
```

Instale as dependencias pelo Maven:

```bash
mvn test -DskipTests
```

## Como rodar os testes

Rodar tudo em headless:

```bash
mvn clean test -Dheadless=true -Dbrowser=chrome
```

Rodar abrindo o navegador:

```bash
mvn clean test -Dheadless=false -Dbrowser=chrome
```

Rodar apenas smoke tests:

```bash
mvn clean test -Dgroups=smoke
```

Rodar por tag funcional:

```bash
mvn clean test -Dgroups=login
mvn clean test -Dgroups=registration
mvn clean test -Dgroups=cart
mvn clean test -Dgroups=checkout
mvn clean test -Dgroups=contact
```

## Configuracoes uteis

| Propriedade | Padrao | Exemplo |
| --- | --- | --- |
| `browser` | `chrome` | `-Dbrowser=firefox` |
| `headless` | `true` | `-Dheadless=false` |
| `baseUrl` | `https://demowebshop.tricentis.com` | `-DbaseUrl=https://ambiente.local` |
| `timeoutSeconds` | `15` | `-DtimeoutSeconds=25` |
| `viewport` | `1440x1000` | `-Dviewport=1920x1080` |

Exemplo completo:

```bash
mvn clean test \
  -Dbrowser=chrome \
  -Dheadless=true \
  -DtimeoutSeconds=20 \
  -Dviewport=1440x1000
```

## Relatorio Allure

Gerar relatorio apos a execucao:

```bash
mvn allure:report
```

Abrir relatorio localmente:

```bash
mvn allure:serve
```

Arquivos gerados:

- resultados brutos: `target/allure-results`
- relatorio HTML: `target/site/allure-maven-plugin`
- relatorios Maven Surefire: `target/surefire-reports`

## CI/CD

O workflow esta em:

```text
.github/workflows/selenium-tests.yml
```

Ele executa automaticamente a cada push na branch `main`:

1. faz checkout do repositorio;
2. configura Java 21;
3. instala Chrome;
4. roda toda a suite em modo headless;
5. gera Allure Report;
6. publica os artefatos `allure-report` e `surefire-and-allure-results`.

## Boas praticas aplicadas

- isolamento de testes com dados unicos por execucao;
- `ThreadLocal<WebDriver>` para evoluir com seguranca para paralelismo;
- mensagens de erro de interacao mais claras;
- screenshots e page source em falhas;
- seletores centralizados;
- nomes de metodos orientados a comportamento;
- comentarios apenas onde explicam decisoes tecnicas, como esperas explicitas em eventos AJAX.

## Observacao importante

O Demo Web Shop e um ambiente publico de testes. Por isso, os cenarios usam dados descartaveis e nao dependem de usuarios fixos. Se o site estiver fora do ar ou lento, ajuste `-DtimeoutSeconds=25` antes de investigar falso negativo.
