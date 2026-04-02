# POS-TECH - Atividade substitutiva

API desenvolvida para o Tech Challenge de Arquitetura Java da FIAP, com foco no gerenciamento de agendamentos para serviços de beleza e bem-estar. O projeto centraliza o cadastro de estabelecimentos e profissionais, o agendamento de atendimentos, o registro de avaliações e a consulta de disponibilidade e busca avançada via GraphQL.

## Objetivo do projeto

Esta aplicação atende ao cenário de uma plataforma de agendamento em que o cliente pode:

- consultar estabelecimentos e profissionais;
- verificar horários disponíveis;
- criar, reagendar, cancelar e registrar ausência em agendamentos;
- avaliar atendimentos realizados;
- visualizar dados do domínio por REST e GraphQL.

## Tecnologias utilizadas

- Java 21
- Spring Boot 4
- Spring Web MVC
- Spring Data JPA
- Spring for GraphQL
- PostgreSQL
- Maven
- Lombok
- MapStruct
- Springdoc OpenAPI / Swagger UI
- Docker e Docker Compose
- JaCoCo
- Postman

## Organização da solução

O projeto está estruturado em camadas, separando responsabilidades entre:

- `domain`: entidades e regras centrais do negócio;
- `application`: casos de uso, DTOs de entrada/saída e contratos de gateway;
- `infrastructure`: APIs REST/GraphQL, persistência JPA, presenters, configurações e armazenamento de arquivos.

Essa organização facilita a evolução, testes e substituição de detalhes de infraestrutura sem acoplar as regras de negócio aos frameworks.

## Funcionalidades implementadas

### REST

- cadastro de estabelecimento;
- upload de foto para estabelecimento;
- cadastro de profissional com especialidades, serviços e horários;
- criação de agendamento;
- reagendamento de agendamento;
- cancelamento de agendamento;
- registro de ausência do cliente;
- registro de avaliação;
- atualização da média de notas do estabelecimento após novas avaliações;
- validação de existência de estabelecimento, profissional e agendamento antes de executar os fluxos.

As especificações OpenAPI estão em `src/main/resources/api` e são usadas para gerar as interfaces REST da aplicação.

### GraphQL

- busca de estabelecimentos com filtros combinados;
- consulta de disponibilidade de profissional por data;
- consulta de profissionais, serviços oferecidos, fotos e endereço do estabelecimento em uma única resposta.

O schema principal está em `src/main/resources/graphql/schema.graphqls`, e a coleção Postman com exemplos de chamadas está em `docs/postman`.

## Como o projeto contempla os requisitos da Fase 3

Considerando o escopo implementado nesta base e o material de apoio presente em `docs/Tech Challenge - JAVA - Fase 3..pdf`, o projeto cobre os principais requisitos funcionais da fase da seguinte forma:

| Requisito esperado | Como está contemplado no projeto |
| --- | --- |
| Cadastro de estabelecimentos | Endpoint REST para criação de estabelecimentos com endereço e horários de funcionamento |
| Cadastro de profissionais | Endpoint REST para associar profissionais a estabelecimentos, com especialidades, serviços e agenda de horários |
| Gestão de agendamentos | Endpoints REST para criar, reagendar, cancelar e registrar ausencia |
| Avaliação do atendimento | Endpoint REST para registrar nota e comentário vinculados ao agendamento |
| Consulta de disponibilidade | Query GraphQL `disponibilidadeProfissional` retorna apenas os horários livres para a data informada |
| Busca avançada de estabelecimentos | Query GraphQL `buscarEstabelecimentos` permite filtrar por dados do estabelecimento, endereço, faixa de nota, servico, faixa de preço e horário |
| Documentacao e exploracao da API | OpenAPI/Swagger para REST e schema GraphQL/Postman para consultas e testes |
| Persistência relacional | Integração com PostgreSQL usando Spring Data JPA |
| Execução conteinerizada | `Dockerfile` e `docker-compose.yml` para subir aplicação e banco |

## Como executar

### Requisitos

- Java 21
- Maven 3.9+
- Docker e Docker Compose

### Subindo com Docker

```bash
docker compose up --build
```

A aplicação sobe em `http://localhost:8080` e o PostgreSQL em `localhost:5432`.

### Executando localmente com Maven

```bash
./mvnw spring-boot:run
```

No Windows:

```bash
mvnw.cmd spring-boot:run
```

## Dados e documentação

- `src/main/resources/application.properties`: configuração local do datasource;
- `src/main/resources/data.sql`: carga inicial de clientes;
- `docs/postman`: coleção Postman com exemplos REST e GraphQL;
- `docs/export-postman`: exportacao pronta para uso no Postman com a collection `Agendamento-Rest.postman_collection.json` e o environment `Parametros-Agendamento.postman_environment.json`;
- `src/main/resources/api/*.yaml`: contratos OpenAPI;
- `src/main/resources/graphql/schema.graphqls`: schema GraphQL.

## Endpoints úteis

- Swagger UI: `http://localhost:8080/swagger-ui.html`
- GraphQL: `http://localhost:8080/graphql`

## Testes

O projeto possui diferentes níveis de testes automatizados em `src/test/java`, com cobertura via JaCoCo:

- testes unitários dos casos de uso, cobrindo validações e regras de negócio;
- testes de integração REST e GraphQL com `MockMvc`, validando o fluxo completo de cadastro, agendamento, reagendamento, cancelamento, ausência e avaliacao;
- teste de carga com Gatling para o endpoint `POST /agendamento`.

### Como executar os testes

Para executar a suite automatizada:

```bash
./mvnw test
```

No Windows:

```bash
mvnw.cmd test
```

Para gerar a cobertura com JaCoCo:

```bash
./mvnw verify
```

No Windows:

```bash
mvnw.cmd verify
```

Para executar apenas o teste de carga com Gatling, com a aplicação já iniciada:

```bash
mvnw.cmd gatling:test -Dgatling.simulationClass=com.fiap.pos_tech.agendamento_servicos.performance.AgendamentoCargaSimulation
```

Mais detalhes sobre o cenário de carga estão em `docs/gatling-agendamento.md`.

## Testes manuais com Postman

Na pasta `docs/export-postman` estão os arquivos prontos para importação no Postman:

- `Agendamento-Rest.postman_collection.json`;
- `Parametros-Agendamento.postman_environment.json`.

Importe os dois arquivos para executar os testes manuais. A ordem de execução da collection deve ser mantida, porque os scripts de teste das primeiras requisições salvam automaticamente no environment os identificadores retornados pela API, como `estabelecimento_id`, `profissional_id`, `servico_id` e `agendamento_id`, que são reutilizados nas requisições seguintes.