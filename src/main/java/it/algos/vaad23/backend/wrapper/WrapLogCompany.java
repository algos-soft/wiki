package it.algos.vaad23.backend.wrapper;

import com.vaadin.flow.spring.annotation.*;
import static it.algos.vaad23.backend.boot.VaadCost.*;
import it.algos.vaad23.backend.service.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.beans.factory.config.*;
import org.springframework.context.annotation.Scope;

/**
 * Project vaadin23
 * Created by Algos
 * User: gac
 * Date: mar, 08-mar-2022
 * Time: 07:32
 */
@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class WrapLogCompany {

    public static final String IP_DEFAULT = "127.0.0.1";

    public static final String LOCAL_HOST = "localhost";

    public static final int PAD_COMPANY = 5;

    public static final int PAD_UTENTE = 15;

    public static final int PAD_ADDRESS_IP = 37;

    /**
     * Istanza unica di una classe @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) di servizio <br>
     * Iniettata automaticamente dal framework SpringBoot/Vaadin con l'Annotation @Autowired <br>
     * Disponibile DOPO il ciclo init() del costruttore di questa classe <br>
     */
    @Autowired
    public TextService textService;

    private String companySigla;

    private String userName;

    private String addressIP;

    /**
     * Constructor not @Autowired. <br>
     * Non utilizzato e non necessario <br>
     * In the newest Spring release, it’s constructor does not need to be annotated with @Autowired annotation <br>
     * Se c'è un SOLO costruttore questo diventa automaticamente @Autowired e IntelliJ Idea 'segna' in rosso i
     * parametri <br>
     * Per evitare il bug (solo visivo), aggiungo un costruttore senza parametri <br>
     */
    public WrapLogCompany() {
    }// end of second constructor not @Autowired

    /**
     * Constructor not @Autowired. <br>
     */
    public WrapLogCompany(final String companySigla, final String userName, final String addressIP) {
        this.companySigla = companySigla;
        this.userName = userName;
        this.addressIP = addressIP;
    }// end of constructor not @Autowired


    public static WrapLogCompany crea(final String companySigla, final String userName, final String addressIP) {
        return new WrapLogCompany(companySigla, userName, addressIP);
    }

    public String getLog() {
        String companyText = this.companySigla;
        String userText = this.userName;
        String addressText = this.addressIP;
        addressText = textService.isValid(addressText) ? addressText : IP_DEFAULT;

        companyText = textService.fixSizeQuadre(companyText, PAD_COMPANY);
        userText = textService.fixSizeQuadre(userText, PAD_UTENTE);
        addressText = textService.fixSizeQuadre(addressText, PAD_ADDRESS_IP);

        return companyText + SPAZIO + userText + SPAZIO + addressText;
    }

    public String getMail(final String messageIn) {
        String messageOut = VUOTA;

        messageOut += "Messaggio spedito per prova";
        messageOut += RETURN;
        messageOut += RETURN;
        messageOut += String.format("Company: %s", companySigla);
        messageOut += RETURN;
        messageOut += String.format("User: %s", userName);
        messageOut += RETURN;
        messageOut += String.format("IP: %s", addressIP);

        messageOut += RETURN;
        messageOut += RETURN;
        messageOut += messageIn;

        //        messageOut= companyText + DOPPIO_SPAZIO + userText + DOPPIO_SPAZIO + addressText;

        return messageOut;
    }

}
