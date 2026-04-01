# Teste de carga de agendamento com Gatling

O projeto agora possui uma simulacao Gatling para carga no endpoint `POST /agendamento`.

## Arquivo principal

- `src/test/java/com/fiap/pos_tech/agendamento_servicos/performance/AgendamentoCargaSimulation.java`

## Como executar

Suba a aplicacao antes do teste. Exemplo no Windows:

```powershell
mvnw.cmd spring-boot:run
```

Em outro terminal, execute:

```powershell
mvnw.cmd gatling:test -Dgatling.simulationClass=com.fiap.pos_tech.agendamento_servicos.performance.AgendamentoCargaSimulation
```

## Parametros uteis

Todos os parametros abaixo sao opcionais:

```powershell
-DbaseUrl=http://localhost:8080
-Dgatling.agendamento.usersPerSec=5
-Dgatling.agendamento.rampSeconds=10
-Dgatling.agendamento.durationSeconds=30
-Dgatling.agendamento.requestsPerUser=3
```

Exemplo:

```powershell
mvnw.cmd gatling:test `
  -Dgatling.simulationClass=com.fiap.pos_tech.agendamento_servicos.performance.AgendamentoCargaSimulation `
  -DbaseUrl=http://localhost:8080 `
  -Dgatling.agendamento.usersPerSec=10 `
  -Dgatling.agendamento.durationSeconds=60 `
  -Dgatling.agendamento.requestsPerUser=4
```

## Estrategia do teste

- O `before()` cria um estabelecimento e um profissional validos para a simulacao.
- Cada usuario virtual envia multiplos agendamentos.
- As datas sao geradas de forma unica por usuario para evitar colisao na validacao de agendamento existente.
- O teste valida `201 Created` em cada requisicao e falha se houver requests com erro.
