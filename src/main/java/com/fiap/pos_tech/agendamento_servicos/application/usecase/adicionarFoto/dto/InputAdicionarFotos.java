package com.fiap.pos_tech.agendamento_servicos.application.usecase.adicionarFoto.dto;

import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

public record InputAdicionarFotos(UUID idEstabelecimento, MultipartFile foto) {
}
