package com.fiap.pos_tech.agendamento_servicos.infrastructure.email;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.mail.MailAuthenticationException;
import org.springframework.mail.MailSendException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class SmtpEnviarEmailAdapterTest {

    @Test
    void deveIgnorarEnvioQuandoParametrizacaoSmtpEstiverIncompleta() {
        JavaMailSender javaMailSender = mock(JavaMailSender.class);
        ObjectProvider<JavaMailSender> provider = mock(ObjectProvider.class);

        when(provider.getIfAvailable()).thenReturn(javaMailSender);

        SmtpEnviarEmailAdapter adapter = new SmtpEnviarEmailAdapter(
                provider,
                "smtp.gmail.com",
                "587",
                "",
                "",
                "no-reply@email.com"
        );

        adapter.inicializar();

        assertDoesNotThrow(() -> adapter.enviar("destinatario@email.com", "Assunto", "Mensagem"));
        verify(javaMailSender, never()).send(any(SimpleMailMessage.class));
    }

    @Test
    void deveNaoPropagarExcecaoQuandoFalharAutenticacaoNoSmtp() {
        JavaMailSender javaMailSender = mock(JavaMailSender.class);
        ObjectProvider<JavaMailSender> provider = mock(ObjectProvider.class);

        when(provider.getIfAvailable()).thenReturn(javaMailSender);
        doThrow(new MailAuthenticationException("Credenciais invalidas"))
                .when(javaMailSender)
                .send(any(SimpleMailMessage.class));

        SmtpEnviarEmailAdapter adapter = new SmtpEnviarEmailAdapter(
                provider,
                "smtp.gmail.com",
                "587",
                "usuario@email.com",
                "senha",
                "usuario@email.com"
        );

        adapter.inicializar();

        assertDoesNotThrow(() -> adapter.enviar("destinatario@email.com", "Assunto", "Mensagem"));
    }

    @Test
    void deveNaoPropagarExcecaoQuandoHouverFalhaGenericaNoEnvio() {
        JavaMailSender javaMailSender = mock(JavaMailSender.class);
        ObjectProvider<JavaMailSender> provider = mock(ObjectProvider.class);

        when(provider.getIfAvailable()).thenReturn(javaMailSender);
        doThrow(new MailSendException("Servidor indisponivel"))
                .when(javaMailSender)
                .send(any(SimpleMailMessage.class));

        SmtpEnviarEmailAdapter adapter = new SmtpEnviarEmailAdapter(
                provider,
                "smtp.gmail.com",
                "587",
                "usuario@email.com",
                "senha",
                "usuario@email.com"
        );

        adapter.inicializar();

        assertDoesNotThrow(() -> adapter.enviar("destinatario@email.com", "Assunto", "Mensagem"));
    }

    @Test
    void deveEnviarEmailQuandoConfiguracaoEstiverCorreta() {
        JavaMailSender javaMailSender = mock(JavaMailSender.class);
        ObjectProvider<JavaMailSender> provider = mock(ObjectProvider.class);

        when(provider.getIfAvailable()).thenReturn(javaMailSender);

        SmtpEnviarEmailAdapter adapter = new SmtpEnviarEmailAdapter(
                provider,
                "smtp.gmail.com",
                "587",
                "usuario@email.com",
                "senha",
                "usuario@email.com"
        );

        adapter.inicializar();

        assertDoesNotThrow(() -> adapter.enviar("destinatario@email.com", "Assunto", "Mensagem"));
        verify(javaMailSender).send(any(SimpleMailMessage.class));
    }

    @Test
    void deveDesabilitarEnvioQuandoFalharAutenticacaoNaInicializacao() {
        JavaMailSenderImpl javaMailSender = mock(JavaMailSenderImpl.class);
        ObjectProvider<JavaMailSender> provider = mock(ObjectProvider.class);

        when(provider.getIfAvailable()).thenReturn(javaMailSender);
        try {
            doThrow(new MailAuthenticationException("Credenciais invalidas"))
                    .when(javaMailSender)
                    .testConnection();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }

        SmtpEnviarEmailAdapter adapter = new SmtpEnviarEmailAdapter(
                provider,
                "smtp.gmail.com",
                "587",
                "usuario@email.com",
                "senha",
                "usuario@email.com"
        );

        assertDoesNotThrow(adapter::inicializar);
        assertDoesNotThrow(() -> adapter.enviar("destinatario@email.com", "Assunto", "Mensagem"));
        verify(javaMailSender, never()).send(any(SimpleMailMessage.class));
    }
}
