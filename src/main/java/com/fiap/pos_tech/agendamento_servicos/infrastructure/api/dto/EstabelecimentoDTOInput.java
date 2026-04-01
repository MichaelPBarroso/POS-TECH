package com.fiap.pos_tech.agendamento_servicos.infrastructure.api.dto;

public record EstabelecimentoDTOInput(
        String id,
        String nome,
        String horarioAbertura,
        String horarioFechamento,
        EnderecoDTO endereco,
        Integer notaMaiorQue,
        Integer notaMenorQue,
        ServicoDTO servico,
        String horarioAgendamento
) {
}
