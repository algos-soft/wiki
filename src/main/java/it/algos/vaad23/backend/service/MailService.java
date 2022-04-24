package it.algos.vaad23.backend.service;

import static it.algos.vaad23.backend.boot.VaadCost.*;
import org.springframework.beans.factory.config.*;
import org.springframework.context.annotation.Scope;
import org.springframework.mail.javamail.*;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.*;
import java.util.*;

/**
 * Project vaadin23
 * Created by Algos
 * User: gac
 * Date: mar, 08-mar-2022
 * Time: 11:25
 * <p>
 * Utility per la posta <br>
 * <p>
 * Classe di libreria; NON deve essere astratta, altrimenti SpringBoot non la costruisce <br>
 * Estende la classe astratta AbstractService che mantiene i riferimenti agli altri services <br>
 * L'istanza può essere richiamata con: <br>
 * 1) StaticContextAccessor.getBean(MailService.class); <br>
 * 3) @Autowired public MailService annotation; <br>
 * <p>
 * Annotated with @Service (obbligatorio, se si usa la catena @Autowired di SpringBoot) <br>
 * NOT annotated with @SpringComponent (inutile, esiste già @Service) <br>
 * Annotated with @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) (obbligatorio) <br>
 */
@Service
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class MailService extends AbstractService {


    private JavaMailSenderImpl mailSender;

    /**
     * Spring constructor
     */
    public MailService() {
        this.mailSender = new JavaMailSenderImpl();

        Properties properties = new Properties();
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "465");
        properties.put("mail.smtp.ssl.enable", "true");
        mailSender.setUsername("2gacgac@gmail.com");
        mailSender.setPassword("djacllkkfckgmpsx");
        mailSender.setJavaMailProperties(properties);
    }// end of constructor

    /**
     * Spedizione standard senza mittente e senza destinatario
     *
     * @param title soggetto
     * @param body  testo della mail
     */
    public boolean send(String title, String body) {
        return send("gac@algos.it", title, body);
    }

    /**
     * Spedizione standard col solo destinatario
     *
     * @param to    destinatario
     * @param title soggetto
     * @param body  testo della mail
     */
    public boolean send(String to, String title, String body) {
        return send(VUOTA, to, title, body);
    }

    /**
     * Spedizione completa con mittente e destinatario
     *
     * @param from  mittente
     * @param to    destinatario
     * @param title soggetto
     * @param body  testo della mail
     */
    public boolean send(String from, String to, String title, String body) {
        boolean status = false;
        MimeMessage message = this.mailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(message);
        try {
            if (textService.isValid(from)) {
                mimeMessageHelper.setFrom(from);
            }
            mimeMessageHelper.setSubject(title);
            mimeMessageHelper.setText(body);
            mimeMessageHelper.setTo(to);
            this.mailSender.send(message);
            status = true;
        } catch (MessagingException messageException) {
            // You could also 'throw' this exception. I am not a fan of checked exceptions.
            // If you want to go that route, then just update this method and the interface.
            throw new RuntimeException(messageException);
            //            throw AlgosException.stack(messageException, "Pippoz", getClass(), "send");
        }
        return status;
    }

}