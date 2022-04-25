package it.algos;

import com.vaadin.flow.spring.annotation.*;
import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.boot.autoconfigure.domain.*;
import org.springframework.boot.autoconfigure.security.servlet.*;
import org.springframework.boot.web.servlet.support.*;
import org.springframework.context.*;

/**
 * Project Wiki
 * Created by Algos
 * User: gac
 * Date: lun, 25 apr 22
 * <p>
 * The entry point of the Spring Boot application. <br>
 * Spring boot web application initializer. <br>
 * <p>
 * Questa classe contiene il metodo 'main' che è il punto di ingresso di una applicazione Java <br>
 * In fase di sviluppo si possono avere diverse configurazioni, ognuna delle quali punta un ''main' diverso <br>
 * Nel JAR finale (runtime) si può avere una sola classe col metodo 'main' <br>
 * Nel WAR finale (runtime) occorre (credo) inserire dei servlet di context diversi <br>
 * Senza @ComponentScan, SpringBoot non 'vede' le classi con @SpringView che sono in una directory diversa da questo package <br>
 * <p>
 * Questa classe non fa praticamente niente se non avere le Annotation riportate qui <br>
 * Annotated with @SpringBootApplication (obbligatorio) <br>
 * Annotated with @EnableVaadin (obbligatorio) <br>
 * Annotated with @EntityScan (obbligatorio) <br>
 * Annotated with @AIScript (facoltativo Algos) per controllare la ri-creazione di questo file dal Wizard <br>
 * <p>
 * Tutte le view devono essere comprese nel path di questa classe (directory interne incluse) <br>
 * Una sola view può avere @Route("") <br>
 * <p>
 * Spring Boot introduces the @SpringBootApplication annotation. <br>
 * This single annotation is equivalent to using @Configuration, @EnableAutoConfiguration, and @ComponentScan. <br>
 * Se l'applicazione NON usa la security, aggiungere exclude = {SecurityAutoConfiguration.class} a @SpringBootApplication <br>
 */
@SpringBootApplication(scanBasePackages = {"it.algos"}, exclude = {SecurityAutoConfiguration.class})
@EnableVaadin({"it.algos"})
@EntityScan({"it.algos"})
public class WikiApplication extends SpringBootServletInitializer {

    /**
     * Constructor
     *
     * @param args eventuali parametri in ingresso
     */
    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(WikiApplication.class, args);
    }// end of SpringBoot constructor


}// end of main class
