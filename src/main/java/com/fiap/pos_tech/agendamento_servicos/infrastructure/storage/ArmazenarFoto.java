package com.fiap.pos_tech.agendamento_servicos.infrastructure.storage;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class ArmazenarFoto {

    public String uploadFile(MultipartFile file) {
        //Realiza o upload da foto para um serviço de armazenamento em nuvem.
        //Foto não foi salva no banco de dados, pois isso influência grandemente no desempenho no banco de dados, além de ser uma má prática.
        //E não foi implementado o serviço em nuvem para salvar o arquivo,
        // pois isso não fazia parte do escopo do projeto nem dos temas da fase 3 da pós-tech.

        return "{URL de exemplo}?filename=" + file.getOriginalFilename();
    }
}
