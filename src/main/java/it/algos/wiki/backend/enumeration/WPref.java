package it.algos.wiki.backend.enumeration;

import static it.algos.vaad23.backend.boot.VaadCost.*;
import it.algos.vaad23.backend.enumeration.*;
import it.algos.vaad23.backend.exception.*;
import it.algos.vaad23.backend.packages.utility.preferenza.*;
import it.algos.vaad23.backend.service.*;
import it.algos.vaad23.backend.wrapper.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

import javax.annotation.*;
import java.util.*;

/**
 * Project Wiki
 * Created by Algos
 * User: gac
 * Date: mar, 15-mar-2022
 * Time: 09:17
 * <p>
 * Creazione da code di alcune preferenze del progetto <br>
 */
public enum WPref  {

    lastDownloadGenere("lastGenere", AETypePref.localdatetime, "Data dell'ultimo download di genere dal Modulo:Bio/Plurale attività genere.",
            ROOT_DATA_TIME) {},
    durataDownloadGenere("durataGenere", AETypePref.integer, "Durata in secondi dell'ultimo download del modulo genere.",0) {},
    lastDownloadAttivita("lastAttivita", AETypePref.localdatetime, "Data dell'ultimo download di attività dal Modulo:Bio/Plurale attività.", ROOT_DATA_TIME) {},
    durataDownloadAttivita("durataAttivita", AETypePref.integer, "Durata in secondi dell'ultimo download del modulo attività.",0) {},
    ;
    
    //--codice di riferimento.
    private String keyCode;

    //--tipologia di dato da memorizzare.
    //--Serve per convertire (nei due sensi) il valore nel formato byte[] usato dal mongoDb
    private AETypePref type;

    //--descrizione breve ma comprensibile. Ulteriori (eventuali) informazioni nel campo 'note'
    private String descrizione;

    //--Valore java iniziale da convertire in byte[] a seconda del type
    private Object defaultValue;

    //--preferenze che necessita di un riavvio del programma per avere effetto
    private boolean needRiavvio;

    //--Link injected da un metodo static
    private PreferenzaBackend preferenzaBackend;

    //--Link injected da un metodo static
    private LogService logger;

    //--Link injected da un metodo static
    private DateService date;



    WPref(final String keyCode, final AETypePref type, final String descrizione, final Object defaultValue) {
        this.keyCode = keyCode;
        this.type = type;
        this.descrizione = descrizione;
        this.defaultValue = defaultValue;
    }// fine del costruttore


    public static List<WPref> getAllEnums() {
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

    public void setValue(Object javaValue) {
        Preferenza preferenza;

        if (preferenzaBackend == null) {
            return;
        }

        preferenza = preferenzaBackend.findByKey(keyCode);
        if (preferenza == null) {
            return;
        }

        preferenza.setValue(type.objectToBytes(javaValue));
        preferenzaBackend.update(preferenza);
    }

    public Object get() {
        return getValue();
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
            for (WPref pref : WPref.values()) {
                pref.setPreferenzaBackend(preferenzaBackend);
                pref.setLogger(logger);
                pref.setDate(date);
            }
        }

    }

}
