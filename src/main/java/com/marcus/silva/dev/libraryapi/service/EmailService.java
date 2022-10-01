package com.marcus.silva.dev.libraryapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmailService {

    @Autowired private JavaMailSender javaMailSender;
    @Value("application.mail.default-sender")
    private String sender;

    public void sendEmail(String message, List<String> emailList){
        String[] emails = emailList.toArray(new String[emailList.size()]);
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom(sender);
        simpleMailMessage.setSubject("Livro com emprestimo atrasado ou próximo a vencer");
        simpleMailMessage.setText(message);
        simpleMailMessage.setTo(emails);

        javaMailSender.send(simpleMailMessage);
    }
}
