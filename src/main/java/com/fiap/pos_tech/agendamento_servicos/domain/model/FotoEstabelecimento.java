package com.fiap.pos_tech.agendamento_servicos.domain.model;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class FotoEstabelecimento {

    private UUID id;
    private Estabelecimento estabelecimento;
    private String url;

    public static FotoEstabelecimento create(Estabelecimento estabelecimento) {
        FotoEstabelecimento fotoEstabelecimento = new FotoEstabelecimento();

        fotoEstabelecimento.setEstabelecimento(estabelecimento);

        return fotoEstabelecimento;
    }

    public static FotoEstabelecimento create(Estabelecimento estabelecimento, String url) {
        FotoEstabelecimento fotoEstabelecimento = new FotoEstabelecimento();

        fotoEstabelecimento.setEstabelecimento(estabelecimento);
        fotoEstabelecimento.setUrl(url);

        return fotoEstabelecimento;
    }

    public static FotoEstabelecimento create(UUID id, Estabelecimento estabelecimento, String url) {
        FotoEstabelecimento fotoEstabelecimento = new FotoEstabelecimento();

        fotoEstabelecimento.setId(id);
        fotoEstabelecimento.setEstabelecimento(estabelecimento);
        fotoEstabelecimento.setUrl(url);

        return fotoEstabelecimento;
    }

}
