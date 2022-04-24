package it.algos.vaad23.backend.enumeration;

import static it.algos.vaad23.backend.boot.VaadCost.*;
import it.algos.vaad23.backend.exception.*;
import it.algos.vaad23.backend.packages.utility.preferenza.*;
import it.algos.vaad23.backend.service.*;
import it.algos.vaad23.backend.wrapper.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

import javax.annotation.*;
import java.util.*;

/**
 * Project vaadin23
 * Created by Algos
 * User: gac
 * Date: mer, 30-mar-2022
 * Time: 21:29
 */
public enum Pref {
    debug("debug", AETypePref.bool, false, "Flag generale di debug") {},
    doubleClick("doubleClick", AETypePref.bool, true, "Doppio click abilitato nelle righe della Grid") {},
    durataAvviso("durataAvviso", AETypePref.integer, 2000, "Durata in millisecondi dell'avviso a video") {},
    ;


    //--codice di riferimento. Se è usaCompany=true, DEVE contenere anche il code della company come prefisso.
    private String keyCode;

    //--tipologia di dato da memorizzare.
    //--Serve per convertire (nei due sensi) il valore nel formato byte[] usato dal mongoDb
    private AETypePref type;

    //--Valore java iniziale da convertire in byte[] a seconda del type
    private Object defaultValue;

    //--preferenze singole per ogni company; usa un prefisso col codice della company
    private boolean usaCompany;

    //--preferenze generale del framework e NON specifica di un'applicazione
    private boolean vaadFlow;

    //--preferenze che necessita di un riavvio del programma per avere effetto
    private boolean needRiavvio;

    //--preferenze visibile agli admin se l'applicazione è usaSecurity=true
    private boolean visibileAdmin;

    //--descrizione breve ma comprensibile. Ulteriori (eventuali) informazioni nel campo 'note'
    private String descrizione;

    //--descrizione aggiuntiva eventuale
    private String note;

    //--Link injettato da un metodo static
    private PreferenzaBackend preferenzaBackend;

    //--Link injettato da un metodo static
    private LogService logger;

    //--Link injettato da un metodo static
    private DateService date;


    Pref(final String keyCode, final AETypePref type, final Object defaultValue, final String descrizione) {
        this.keyCode = keyCode;
        this.type = type;
        this.defaultValue = defaultValue;
        this.descrizione = descrizione;
    }// fine del costruttore

    public static List<Pref> getAllEnums() {
        return Arrays.stream(values()).toList();
    }

    public void setPreferenzaBackend(PreferenzaBackend preferenzaBackend) {
        this.preferenzaBackend = preferenzaBackend;
    }

    public void setLogger(LogService logger) {
        this.logger = logger;
    }

    public void setDate(DateService date) {
        this.date = date;
    }

    public Object get() {
        return null;
    }

    private Object getValue() {
        Object javaValue;
        Preferenza preferenza = null;

        if (preferenzaBackend == null) {
            return null;
        }

        preferenza = preferenzaBackend.findByKey(keyCode);
        javaValue = preferenza != null ? type.bytesToObject(preferenza.getValue()) : null;

        return javaValue;
    }

    public String getStr() {
        String message;

        if (type == AETypePref.string) {
            return getValue() != null ? (String) getValue() : VUOTA;
        }
        else {
            message = String.format("La preferenza %s è di type %s. Non puoi usare getStr()", keyCode, type);
            logger.error(new WrapLog().exception(new AlgosException(message)).usaDb());
            return VUOTA;
        }
    }

    public boolean is() {
        String message;

        if (type == AETypePref.bool) {
            return getValue() != null && (boolean) getValue();
        }
        else {
            message = String.format("La preferenza %s è di type %s. Non puoi usare is()", keyCode, type);
            logger.error(new WrapLog().exception(new AlgosException(message)).usaDb());
            return false;
        }
    }

    public int getInt() {
        String message;

        if (type == AETypePref.integer) {
            return getValue() != null ? (int) getValue() : 0;
        }
        else {
            message = String.format("La preferenza %s è di type %s. Non puoi usare getInt()", keyCode, type);
            logger.error(new WrapLog().exception(new AlgosException(message)).usaDb());
            return 0;
        }
    }

    public AETypePref getType() {
        return type;
    }

    public String getKeyCode() {
        return keyCode;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public Object getDefaultValue() {
        return defaultValue;
    }

    @Component
    public static class PreferenzaServiceInjector {

        @Autowired
        private PreferenzaBackend preferenzaBackend;

        @Autowired
        private LogService logger;

        @Autowired
        private DateService date;

        @PostConstruct
        public void postConstruct() {
            for (Pref pref : Pref.values()) {
                pref.setPreferenzaBackend(preferenzaBackend);
                pref.setLogger(logger);
                pref.setDate(date);
            }
        }

    }

}
