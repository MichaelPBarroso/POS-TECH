package com.fiap.pos_tech.agendamento_servicos.infrastructure.storage;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class ArmazenarFoto {

    public String uploadFile(MultipartFile file) {
        //Realiza o upload da foto para um serviço de armazenamento em nuvem.

        return "{URL de exemplo}?filename=" + file.getOriginalFilename();
    }
}
