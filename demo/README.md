# Aplicação de Criptografia com Spring Boot, PostgreSQL e Docker Compose

## Descrição do Projeto

Esta aplicação demonstra a implementação de criptografia de dados sensíveis em uma API CRUD utilizando **Java 21**, **Spring Boot**, **PostgreSQL**, **Docker Compose** e boas práticas de **TDD** e **Design Patterns**.

Campos sensíveis como userDocument e creditCardToken são criptografados antes de serem persistidos no banco de dados e descriptografados ao serem recuperados, garantindo a proteção desses dados.


### Stack Utilizada
:::info
<p>
Java 21

Spring Boot

PostgreSQL via Docker Compose

Maven para gerenciamento de dependências

TDD (Test-Driven Development)

Design Patterns (padrão "Converter" para criptografia transparente)

Bouncy Castle para a implementação de criptografia
</p>
:::

### Configuração e Execução Local

:::danger
<details>
    <summary>Pré-requisitos</summary>

    Certifique-se de ter as seguintes ferramentas instaladas:
    
    JDK 21 [(Download JDK)](https://www.oracle.com/java/technologies/javase/jdk21-archive-downloads.html)
    
    Maven 3.6+ [(Download Maven)](https://maven.apache.org/download.cgi)
    
    Docker e Docker Compose [(Instalar Docker)](https://docs.docker.com/desktop/install/windows-install/)

    Insomnia [Instalar Insomnia](https://docs.insomnia.rest/insomnia/install)
</details>
:::

:::info
<details>
    <summary>Executar as aplicações </summary>

    As instruções abaixo demonstram como baixar o código para a sua máquina e como executar 
    as aplicações via container utilizando o docker.
    
    **Passo 1:** Clonar o Repositório git
    No prompt de comando execute: git clone https://github.com/Robinhor10/java-criptografia.git 
    a versão estável vai estar na branch **main**

    **Passo 2:** Baixar as dependências do maven e gerar o .jar
    No prompt de comando execute: mvn clean install

    **Passo 3:** Executar o docker
    No prompt de comando execute: docker-compose up -d

    **Passo 4:** Executar quando quiser parar o container
    No prompt de comando execute: docker-compose down

</details>
:::

### Executar Testes Via Insomnia

:::info
<details>
    <summary>Executar Testes </summary>

    As instruções abaixo demonstram como realizar chamadas na API e realizando
    CRUD no banco dados utilizando a ferramenta insomnia

    **Passo 1:** Baixar a collection e importar no insomnia
    Acesse a pasta /demo/src/test/collection baixe para a sua máquina o arquivo
    Insomnia_java_criptografia  e import na ferramenta insomnia


</details>
:::

### Verificando a tabela e Registros

:::info
<details>
    <summary>Acessando o banco postgres </summary>

    As instruções abaixo demonstram como podemos acessar o banco de dados no container
    e realizar consultas para verificar os registros.

    **Passo 1:** Acessando o banco de dados dentro do container
    - Primeiro, obtenha o nome ou o ID do container de banco de dados executando **docker ps**.
    - Execute o comando docker exec -it <nome-do-container-db> psql -U demo_user -d demo
    - Isso abrirá um shell do PostgreSQL. 
      Você pode listar as tabelas executando o comando SQL: **\dt**

    **Passo 2:** Executar Consultas SQL
    - Para consultar todos os registros execute 
      **select * from tbl_sensitive;**
:::info
<details>
    <summary>Exemplo</summary>
     id | credit_card_token | user_document
    ----+-------------------+---------------
     1  | xxx-encrypted-abc  | 12345678900
     2  | yyy-encrypted-def  | 09876543211

</details>
:::info

    **Passo 3:** Sair do PostgreSQL
    - Para sair do PostgresSQL
      execute **\q**

</details>
:::

### Visão os Testes Unitários

Para o Service:
**findAll():**
 - Retorna todas as entidades com sucesso.
- Retorna uma lista vazia quando não há entidades.

**findById():**
- Retorna uma entidade quando ela existe no banco de dados.
- Lança exceção quando a entidade não existe.

**save():**
- Salva uma entidade com sucesso.

**Para o Controller:**
  - GET /api/sensitive-entities:
    - Retorna a lista de entidades em formato JSON com status 200 OK.
    - Retorna status 204 No Content quando não há entidades.
  - POST /api/sensitive-entities:
    - Cria uma nova entidade com sucesso e retorna os dados persistidos.