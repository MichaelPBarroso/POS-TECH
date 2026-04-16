package com.fiap.pos_tech.agendamento_servicos.infrastructure.email;

import jakarta.annotation.PostConstruct;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailAuthenticationException;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class SmtpEnviarEmailAdapter {

    private static final Logger LOG = Logger.getLogger(SmtpEnviarEmailAdapter.class);

    private final ObjectProvider<JavaMailSender> javaMailSenderProvider;
    private final String host;
    private final String porta;
    private final String usuario;
    private final String senha;
    private final String remetente;
    private volatile boolean envioHabilitado = true;

    public SmtpEnviarEmailAdapter(
            ObjectProvider<JavaMailSender> javaMailSenderProvider,
            @Value("${spring.mail.host:}") String host,
            @Value("${spring.mail.port:}") String porta,
            @Value("${spring.mail.username:}") String usuario,
            @Value("${spring.mail.password:}") String senha,
            @Value("${spring.mail.username:no-reply@teste.postech.com}") String remetente
    ) {
        this.javaMailSenderProvider = javaMailSenderProvider;
        this.host = host;
        this.porta = porta;
        this.usuario = usuario;
        this.senha = senha;
        this.remetente = remetente;
    }

    @PostConstruct
    void inicializar() {
        if (!possuiConfiguracaoValida()) {
            envioHabilitado = false;
            LOG.warn("Envio de e-mail desabilitado: parametrizacao SMTP incompleta. Configure spring.mail.host, spring.mail.port, spring.mail.username e spring.mail.password.");
            return;
        }

        JavaMailSender javaMailSender = javaMailSenderProvider.getIfAvailable();
        if (javaMailSender == null) {
            envioHabilitado = false;
            LOG.warn("Envio de e-mail desabilitado: JavaMailSender nao foi configurado. Defina as propriedades spring.mail.* para habilitar o envio.");
            return;
        }

        if (javaMailSender instanceof JavaMailSenderImpl javaMailSenderImpl) {
            try {
                javaMailSenderImpl.testConnection();
            } catch (MailAuthenticationException ex) {
                envioHabilitado = false;
                LOG.warn("Envio de e-mail desabilitado: falha na autenticacao SMTP ao iniciar a aplicacao. Verifique usuario e senha configurados.");
                LOG.debug("Detalhes tecnicos da falha de autenticacao SMTP na inicializacao.", ex);
            } catch (Exception ex) {
                envioHabilitado = false;
                LOG.warn("Envio de e-mail desabilitado: nao foi possivel validar a conexao com o servidor SMTP na inicializacao.");
                LOG.debug("Detalhes tecnicos da falha ao validar SMTP na inicializacao.", ex);
            }
        }
    }

    @Async
    public void enviar(String destinatario, String assunto, String mensagem) {
        validarEntrada(destinatario, "destinatario");
        validarEntrada(assunto, "assunto");
        validarEntrada(mensagem, "mensagem");

        if (!envioHabilitado) {
            LOG.warn("Envio de e-mail ignorado: servico SMTP desabilitado por configuracao invalida.");
            return;
        }

        JavaMailSender javaMailSender = javaMailSenderProvider.getIfAvailable();
        if (javaMailSender == null) {
            LOG.warn("Envio de e-mail ignorado: JavaMailSender nao foi configurado. Defina as propriedades spring.mail.* para habilitar o envio.");
            return;
        }

        SimpleMailMessage email = new SimpleMailMessage();
        email.setFrom(remetente);
        email.setTo(destinatario);
        email.setSubject(assunto);
        email.setText(mensagem);

        try {
            javaMailSender.send(email);
        } catch (MailAuthenticationException ex) {
            LOG.warn("Envio de e-mail nao realizado: falha na autenticacao SMTP. Verifique usuario e senha configurados.");
            LOG.debug("Detalhes tecnicos da falha de autenticacao SMTP no envio.", ex);
        } catch (MailException ex) {
            LOG.warn("Envio de e-mail nao realizado: houve uma falha na comunicacao com o servidor SMTP.");
            LOG.debug("Detalhes tecnicos da falha de comunicacao SMTP no envio.", ex);
        }
    }

    private void validarEntrada(String valor, String campo) {
        if (!StringUtils.hasText(valor)) {
            throw new IllegalArgumentException("O campo " + campo + " deve ser informado.");
        }
    }

    private boolean possuiConfiguracaoValida() {
        return StringUtils.hasText(host)
                && StringUtils.hasText(porta)
                && StringUtils.hasText(usuario)
                && StringUtils.hasText(senha);
    }
}
