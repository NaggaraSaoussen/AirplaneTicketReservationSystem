package com.gestion.notificationservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

/**
 * 📧 EmailService
 *
 * Ce service est responsable de l’envoi des emails dans le système.
 * Il est utilisé par le notification-service pour notifier les utilisateurs
 * par email suite à certaines actions (réservation, annulation, confirmation, etc.).
 */
@Service
@RequiredArgsConstructor
public class EmailService {

    // Composant Spring fourni pour l'envoi des emails
    private final JavaMailSender mailSender;

    // Adresse email de l'expéditeur (récupérée depuis application.properties)
    @Value("${spring.mail.username}")
    private String from;

    /**
     * ✉️ Envoi d'un email simple
     *
     * @param to      adresse email du destinataire
     * @param subject objet de l'email
     * @param text    contenu du message
     */
    public void send(String to, String subject, String text) {

        // Création d'un message email simple
        SimpleMailMessage msg = new SimpleMailMessage();

        // Définition de l'expéditeur
        msg.setFrom(from);

        // Définition du destinataire
        msg.setTo(to);

        // Définition de l'objet de l'email
        msg.setSubject(subject);

        // Contenu textuel de l'email
        msg.setText(text);

        // Envoi effectif de l'email via le serveur SMTP configuré
        mailSender.send(msg);
    }
}
