package server;

import lib.exceptions.MailException;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class EmailMessageSender {
    private static EmailMessageSender sender;

    private EmailMessageSender() {
    }

    public static EmailMessageSender getInstance() {
        if (sender == null) {
            sender = new EmailMessageSender();
        }
        return sender;
    }

    public void send(String to, String info) {
        Properties properties = new Properties();
        try {
            properties.load(getClass().getClassLoader().getResourceAsStream("email.properties"));
        } catch (IOException e) {
            throw new RuntimeException("Файл конфигурации отправки сообщений отсутствует!");
        }
        String from = properties.getProperty("mail.smtp.user");
        String password = properties.getProperty("mail.smtp.password");
        Session session = Session.getDefaultInstance(properties);

        MimeMessage message = new MimeMessage(session);
        try {
            message.setFrom(new InternetAddress(from));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            message.setSubject("From app");
            message.setText(info);
            Transport.send(message, from, password);
        } catch (MessagingException e) {
            throw new MailException("Почтового адреса не существует!");
        }
    }
}
