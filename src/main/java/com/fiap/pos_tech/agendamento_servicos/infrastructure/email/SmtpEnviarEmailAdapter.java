package com.fiap.pos_tech.agendamento_servicos.infrastructure.email;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;


/***
 * Implementação não realizada, por não estar dentro dos requisitos da fase 3.
 * De acordo o que foi informado pelo coordenador, não necessitar sem implementado,
 * porem foi adicionada a estrutura generica para envio do email.
 */
@Component
public class SmtpEnviarEmailAdapter {

    private final ObjectProvider<JavaMailSender> javaMailSenderProvider;
    private final String remetente;

    public SmtpEnviarEmailAdapter(
            ObjectProvider<JavaMailSender> javaMailSenderProvider,
            @Value("${spring.mail.username:no-reply@localhost}") String remetente
    ) {
        this.javaMailSenderProvider = javaMailSenderProvider;
        this.remetente = remetente;
    }

    public void enviar(String destinatario, String assunto, String mensagem) {
        validarEntrada(destinatario, "destinatario");
        validarEntrada(assunto, "assunto");
        validarEntrada(mensagem, "mensagem");

        JavaMailSender javaMailSender = javaMailSenderProvider.getIfAvailable();
        if (javaMailSender == null) {
            throw new IllegalStateException(
                    "JavaMailSender nao foi configurado. Defina as propriedades spring.mail.* para habilitar o envio."
            );
        }

        SimpleMailMessage email = new SimpleMailMessage();
        email.setFrom(remetente);
        email.setTo(destinatario);
        email.setSubject(assunto);
        email.setText(mensagem);

        javaMailSender.send(email);
    }

    private void validarEntrada(String valor, String campo) {
        if (!StringUtils.hasText(valor)) {
            throw new IllegalArgumentException("O campo " + campo + " deve ser informado.");
        }
    }
}
