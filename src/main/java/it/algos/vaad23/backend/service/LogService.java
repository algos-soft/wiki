package it.algos.vaad23.backend.service;

import com.vaadin.flow.component.*;
import com.vaadin.flow.component.notification.*;
import static it.algos.vaad23.backend.boot.VaadCost.*;
import it.algos.vaad23.backend.enumeration.*;
import it.algos.vaad23.backend.exception.*;
import it.algos.vaad23.backend.packages.utility.log.*;
import it.algos.vaad23.backend.wrapper.*;
import it.algos.vaad23.ui.dialog.*;
import org.slf4j.Logger;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.beans.factory.config.*;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.*;

import javax.annotation.*;
import java.util.function.*;


/**
 * Project vaadin23
 * Created by Algos
 * User: gac
 * Date: lun, 07-mar-2022
 * Time: 10:35
 * <p>
 * <p>
 * Classe di servizio per i log. <br>
 * <p>
 * Diverse modalità di 'uscita' dei logs, regolate da flag: <br>
 * A) nella cartella di log (sempre) <br>
 * B) nella finestra del terminale - sempre in debug - mai in produzione - regolato da flag <br>
 * C) nella collection del database (facoltativo per alcuni programmi) <br>
 * D) in una mail (facoltativo e di norma solo per 'error') <br>
 * <p>
 * Diversi 'livelli' dei logs: debug, info, warn, error <br>
 * <p>
 * Diverse modalità di 'presentazione' (formattazione e incolonnamento) dei logs, regolate da flag: <br>
 * A) Nel log, incolonnare la data, alcuni campi fissi (di larghezza) e poi la descrizione libera <br>
 * ...Se è una multi-company con security, i campi fissi sono: company, utente, IP e type <br>
 * ...Se l' applicazione non è multi-company e non ha security, l' unico campo fisso è il type <br>
 * C) Nella view della collection i campi sono (per i livelli info e warn):
 * ...Multi-company -> livello, type, data, descrizione, company, user
 * ...Single-company -> livello, type, data, descrizione,
 * C) Per i livelli error e debug si aggiungono -> classe, metodo, linea
 * D) Nella mail, invece d'incolonnare i campi fissi, si va a capo <br>
 * <p>
 * I type di log vengono presi dalla enum AETypeLog <br>
 * Il progetto specifico può aggiungere dei type presi da una propria enum <br>
 * Le enum implementano un'interfaccia comune AITypeLog, per poter essere intercambiabili <br>
 */
@Service
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class LogService extends AbstractService {


    /**
     * Istanza unica di una classe @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) di servizio <br>
     * Iniettata automaticamente dal framework SpringBoot/Vaadin con l'Annotation @Autowired <br>
     * Disponibile DOPO il ciclo init() del costruttore di questa classe <br>
     */
    @Autowired
    public LoggerBackend loggerBackend;

    /**
     * Istanza unica di una classe @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) di servizio <br>
     * Iniettata automaticamente dal framework SpringBoot/Vaadin con l'Annotation @Autowired <br>
     * Disponibile DOPO il ciclo init() del costruttore di questa classe <br>
     */
    @Autowired
    public MailService mailService;

    /**
     * Riferimento al logger usato <br>
     * È nella directory 'config', il file 'logback-spring.xml' <br>
     * Deve essere creato subito dalla factory class LoggerFactory <br>
     * Va selezionato un appender da usare e che sia presente nel file di configurazione <br>
     */
    public Logger slf4jLogger;

    /**
     * Controlla che la classe abbia usaBoot=true <br>
     */
    protected Predicate<StackTraceElement> checkStartAlgos = stack -> stack.getClassName().startsWith(PATH_ALGOS);

    /**
     * Performing the initialization in a constructor is not suggested as the state of the UI is not properly set up when the constructor is invoked. <br>
     * La injection viene fatta da SpringBoot SOLO DOPO il metodo init() del costruttore <br>
     * Si usa quindi un metodo @PostConstruct per avere disponibili tutte le istanze @Autowired <br>
     * <p>
     * Ci possono essere diversi metodi con @PostConstruct e firme diverse e funzionano tutti, ma l'ordine con cui vengono chiamati (nella stessa classe) NON è garantito <br>
     * Se viene implementata una sottoclasse, passa di qui per ogni sottoclasse oltre che per questa istanza <br>
     * Se esistono delle sottoclassi, passa di qui per ognuna di esse (oltre a questa classe madre) <br>
     */
    @PostConstruct
    private void postConstruct() {
        slf4jLogger = LoggerFactory.getLogger("vaad23.admin");
    }


    /**
     * Gestisce una mail <br>
     *
     * @param type      merceologico di specificazione
     * @param wrap      di informazioni su company, userName e address
     * @param messageIn testo del log
     */
    public boolean mail(final AETypeLog type, final WrapLogCompany wrap, final String messageIn) {
        String message = fixMessageMail(type, wrap, messageIn);

        mailService.send("gac@algos.it", "Mercoledi", message);
        return false;
    }


    public String fixMessageLog(final WrapLogCompany wrap, final String messageIn) {
        String message = messageIn;

        if (wrap != null) {
            message = wrap.getLog() + DOPPIO_SPAZIO + message;
        }

        return message.trim();
    }

    public String fixMessageMail(final AETypeLog type, final WrapLogCompany wrap, final String messageIn) {
        String message = messageIn;

        if (wrap != null) {
            message = wrap.getMail(message);

        }

        return message.trim();
    }

    //    /**
    //     * Gestisce un log di info <br>
    //     *
    //     * @param message di descrizione dell'evento
    //     */
    //    public String info(final String message) {
    //        return logBase(AELevelLog.info, AETypeLog.system, false, false, message, null, null);
    //    }
    //
    //    /**
    //     * Gestisce un log di info <br>
    //     *
    //     * @param stack info su messaggio e StackTrace
    //     */
    //    public String info(final Exception stack) {
    //        return logBase(AELevelLog.info, AETypeLog.system, false, false, VUOTA, stack, null);
    //    }
    //
    //    /**
    //     * Gestisce un log di info <br>
    //     *
    //     * @param type    merceologico di specificazione
    //     * @param message di descrizione dell'evento
    //     */
    //    public String info(final AETypeLog type, final String message) {
    //        return logBase(AELevelLog.info, type, false, false, message, null, null);
    //    }
    //
    //    /**
    //     * Gestisce un log di info <br>
    //     * Facoltativo (flag) su mongoDB <br>
    //     *
    //     * @param type    merceologico di specificazione
    //     * @param message di descrizione dell'evento
    //     */
    //    public String infoDb(final AETypeLog type, final String message) {
    //        return logBase(AELevelLog.info, type, true, false, message, null, null);
    //    }
    //
    //    /**
    //     * Gestisce un log di info <br>
    //     *
    //     * @param type    merceologico di specificazione
    //     * @param message di descrizione dell'evento
    //     * @param wrap    di informazioni su company, userName e address
    //     */
    //    public String info(final AETypeLog type, final String message, final WrapLogCompany wrap) {
    //        return logBase(AELevelLog.info, type, true, false, message, null, wrap);
    //    }
    //
    //    /**
    //     * Gestisce un log di info <br>
    //     *
    //     * @param type  merceologico di specificazione
    //     * @param stack info su messaggio e StackTrace
    //     */
    //    public String info(final AETypeLog type, final Exception stack) {
    //        return logBase(AELevelLog.info, type, false, false, VUOTA, stack, null);
    //    }
    //
    //    /**
    //     * Gestisce un log di warning <br>
    //     *
    //     * @param message di descrizione dell'evento
    //     */
    //    public String warn(final String message) {
    //        return logBase(AELevelLog.warn, AETypeLog.system, false, false, message, null, null);
    //    }
    //
    //
    //    /**
    //     * Gestisce un log di warning <br>
    //     *
    //     * @param stack info su messaggio e StackTrace
    //     */
    //    public String warn(final Exception stack) {
    //        return logBase(AELevelLog.warn, AETypeLog.system, false, false, VUOTA, stack, null);
    //    }
    //
    //
    //    /**
    //     * Gestisce un log di warning <br>
    //     *
    //     * @param type    merceologico di specificazione
    //     * @param message di descrizione dell'evento
    //     */
    //    public String warn(final AETypeLog type, final String message) {
    //        return logBase(AELevelLog.warn, type, false, false, message, null, null);
    //    }
    //
    //    /**
    //     * Gestisce un log di warning <br>
    //     * Facoltativo (flag) su mongoDB <br>
    //     *
    //     * @param type    merceologico di specificazione
    //     * @param message di descrizione dell'evento
    //     */
    //    public String warnDb(final AETypeLog type, final String message) {
    //        return logBase(AELevelLog.warn, type, true, false, message, null, null);
    //    }
    //
    //
    //    /**
    //     * Gestisce un log di warning <br>
    //     *
    //     * @param type    merceologico di specificazione
    //     * @param message di descrizione dell'evento
    //     * @param wrap    di informazioni su company, userName e address
    //     */
    //    public String warn(final AETypeLog type, final String message, final WrapLogCompany wrap) {
    //        return logBase(AELevelLog.warn, type, true, false, message, null, wrap);
    //    }
    //
    //
    //    /**
    //     * Gestisce un log di warning <br>
    //     * Facoltativo (flag) su mongoDB <br>
    //     *
    //     * @param stack info su messaggio e StackTrace
    //     */
    //    public String warnDb(final Exception stack) {
    //        return logBase(AELevelLog.warn, AETypeLog.system, true, false, VUOTA, stack, null);
    //    }
    //
    //
    //    /**
    //     * Gestisce un log di warning <br>
    //     *
    //     * @param type  merceologico di specificazione
    //     * @param stack info su messaggio e StackTrace
    //     */
    //    public String warn(final AETypeLog type, final Exception stack) {
    //        return logBase(AELevelLog.warn, type, false, false, VUOTA, stack, null);
    //    }
    //
    //    /**
    //     * Gestisce un log di warning <br>
    //     * Facoltativo (flag) su mongoDB <br>
    //     *
    //     * @param type  merceologico di specificazione
    //     * @param stack info su messaggio e StackTrace
    //     */
    //    public String warnDb(final AETypeLog type, final Exception stack) {
    //        return logBase(AELevelLog.warn, type, true, false, VUOTA, stack, null);
    //    }
    //
    //    /**
    //     * Gestisce un log di errore <br>
    //     *
    //     * @param stack info su messaggio e StackTrace
    //     */
    //    public String error(final Exception stack) {
    //        return logBase(AELevelLog.error, AETypeLog.system, false, false, VUOTA, stack, null);
    //    }
    //

    /**
     * Gestisce un log di warning <br>
     *
     * @param stack info su messaggio e StackTrace
     */
    public String warn(final Exception stack) {
        return logBase(AELogLevel.warn, new WrapLog().exception(AlgosException.crea(stack)));
    }

    /**
     * Gestisce un log di warning <br>
     *
     * @param type  merceologico di specificazione
     * @param stack info su messaggio e StackTrace
     */
    public String warn(final AETypeLog type, final Exception stack) {
        return logBase(AELogLevel.warn, new WrapLog().type(type).exception(AlgosException.crea(stack)));
    }

    /**
     * Gestisce un log di errore <br>
     *
     * @param stack info su messaggio e StackTrace
     */
    public String error(final Exception stack) {
        return logBase(AELogLevel.error, new WrapLog().exception(AlgosException.crea(stack)));
    }

    /**
     * Gestisce un log di errore <br>
     *
     * @param type  merceologico di specificazione
     * @param stack info su messaggio e StackTrace
     */
    public String error(final AETypeLog type, final Exception stack) {
        return logBase(AELogLevel.error, new WrapLog().type(type).exception(AlgosException.crea(stack)));
    }

    //
    //    /**
    //     * Gestisce un log di errore <br>
    //     *
    //     * @param message di descrizione dell'evento
    //     */
    //    public String error(final String message) {
    //        return logBase(AELevelLog.error, AETypeLog.system, false, false, message, null, null);
    //    }
    //
    //    /**
    //     * Gestisce un log di errore <br>
    //     *
    //     * @param type    merceologico di specificazione
    //     * @param message di descrizione dell'evento
    //     */
    //    public String error(final AETypeLog type, final String message) {
    //        return logBase(AELevelLog.error, type, false, false, message, null, null);
    //    }
    //
    //
    //    /**
    //     * Gestisce un log di errore <br>
    //     * Facoltativo (flag) su mongoDB <br>
    //     *
    //     * @param type    merceologico di specificazione
    //     * @param message di descrizione dell'evento
    //     */
    //    public String errorDb(final AETypeLog type, final String message) {
    //        return logBase(AELevelLog.error, type, true, false, message, null, null);
    //    }
    //
    //    /**
    //     * Gestisce un log di errore <br>
    //     *
    //     * @param type  merceologico di specificazione
    //     * @param stack info su messaggio e StackTrace
    //     */
    //    public String error(final AETypeLog type, final Exception stack) {
    //        return logBase(AELevelLog.error, type, false, false, VUOTA, stack, null);
    //    }
    //
    //    /**
    //     * Gestisce un log di errore <br>
    //     * Facoltativo (flag) su mongoDB <br>
    //     *
    //     * @param type  merceologico di specificazione
    //     * @param stack info su messaggio e StackTrace
    //     */
    //    public String errorDb(final AETypeLog type, final Exception stack) {
    //        return logBase(AELevelLog.error, type, true, false, VUOTA, stack, null);
    //    }
    //
    //    /**
    //     * Gestisce un log di debug <br>
    //     *
    //     * @param message di descrizione dell'evento
    //     */
    //    public String debug(final String message) {
    //        return logBase(AELevelLog.debug, AETypeLog.system, false, false, message, null, null);
    //    }

    public String info(final WrapLog wrap) {
        return logBase(AELogLevel.info, wrap);
    }

    public String warn(final WrapLog wrap) {
        if (UI.getCurrent() != null) {
            Avviso.show("Warning").addThemeVariants(NotificationVariant.LUMO_ERROR);
        }
        return logBase(AELogLevel.warn, wrap);
    }

    public String error(final WrapLog wrap) {
        if (UI.getCurrent() != null) {
            Avviso.show("Errore").addThemeVariants(NotificationVariant.LUMO_ERROR);
        }
        return logBase(AELogLevel.error, wrap);
    }

    public String debug(final WrapLog wrap) {
        return logBase(AELogLevel.debug, wrap);
    }


    /**
     * Gestisce tutti i log <br>
     * <p>
     * Sempre su slf4jLogger <br>
     * Facoltativo (flag) su mongoDB <br>
     * Facoltativo (flag) via mail <br>
     * <p>
     * Data evento sempre (automatica)
     * Type e descrizione sempre
     * In caso di errore runTime anche classe, metodo e riga del file che ha generato l'errore
     * In caso di multiCompany anche company, user e IP
     * <p>
     * Formattazioni diverse:
     * ...slf4jLogger (parentesi quadre di larghezza fissa)
     * ...mongoDb (singole colonne per le property)
     * ...mail (a capo ogni property)
     * <p>
     */
    private String logBase(final AELogLevel level, final WrapLog wrap) {
        String typeText;
        String message;
        AETypeLog type = wrap.getType();
        boolean flagUsaDB = wrap.isUsaDB();
        boolean flagUsaMail = wrap.isUsaMail();

        //--type merceologico con quadre e pad di larghezza fissa
        type = type != null ? type : AETypeLog.system;
        typeText = type != null ? textService.fixSizeQuadre(type.getTag(), PAD_TYPE) : VUOTA;//@todo da sistemare

        //--1) Inserimento fisso iniziale del type merceologico - se manca di default usa 'system'
        message = textService.isValid(typeText) ? typeText : VUOTA;

        //--2) Messaggio fisso della descrizione
        message += textService.isValid(wrap.getMessage()) ? SEP + wrap.getMessage() : VUOTA;

        //--3) Inserimento opzionale dei dati (company, user, IP) se multiCompany
        if (wrap.isMultiCompany()) {
            message += SPAZIO + utilityService.getCompanyPack(wrap);
        }

        //--4) Inserimento opzionale dello stackTrace dell'errore (o della partenza del log)
        //--Obbligatorio per AETypeLog.error, facoltativo per gli altri type
        if (type == AETypeLog.error || wrap.isUsaStackTrace()) {
            message += SPAZIO + utilityService.getStackTrace(wrap.getException()) + SPAZIO;
            message = DUE_PUNTI_SPAZIO + message;
            //--Se forzo con WrapLog().message() un messaggio diverso da quello generato dall'errore, li mostra entrambi
            //--Quello forzato in testa e quello generato dall'errore in coda
            if (wrap.getMessage() != null && !wrap.getMessage().equals(wrap.getException().getMessage())) {
                message += SPAZIO + wrap.getException().getMessage();
            }
        }
        else {
            message = String.format("%s%s", DUE_PUNTI_SPAZIO, message);
        }

        //-- Inserimento opzionale nella collection di mongoDB
        if (flagUsaDB) {
            loggerBackend.crea(level, wrap);
        }

        //-- Invio opzionale di una mail
        if (flagUsaMail) {
        }

        // logback-spring.xml
        switch (level) {
            case info -> slf4jLogger.info(message);
            case warn -> slf4jLogger.warn(message);
            case error -> slf4jLogger.error(message);
            case debug -> slf4jLogger.debug(message);
            default -> slf4jLogger.info(message);
        }

        return message;
    }

}