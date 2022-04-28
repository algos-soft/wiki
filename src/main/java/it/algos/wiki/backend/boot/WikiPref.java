package it.algos.wiki.backend.boot;

import com.vaadin.flow.spring.annotation.*;
import it.algos.vaad23.backend.boot.*;
import it.algos.vaad23.backend.enumeration.*;
import it.algos.vaad23.backend.packages.utility.preferenza.*;
import it.algos.wiki.backend.enumeration.*;
import org.springframework.beans.factory.config.*;
import org.springframework.context.annotation.Scope;
import org.springframework.context.event.*;
import org.springframework.context.event.EventListener;

import java.util.*;

/**
 * Project Wiki
 * Created by Algos
 * User: gac
 * Date: lun, 25 apr 22
 * <p>
 * Creazione da code di alcune preferenze del progetto <br>
 */
@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class WikiPref extends VaadPref {

    /**
     * The ContextRefreshedEvent happens after both Vaadin and Spring are fully initialized. At the time of this
     * event, the application is ready to service Vaadin requests <br>
     */
    @EventListener(ContextRefreshedEvent.class)
    public void onContextRefreshEvent() {
        this.inizia();
    }


    /**
     * Executed on container startup <br>
     * Setup non-UI logic here <br>
     * This method is called prior to the servlet context being initialized (when the Web application is deployed). <br>
     * You can initialize servlet context related data here. <br>
     * Metodo eseguito solo quando l'applicazione viene caricata/parte nel server (Tomcat o altri) <br>
     * Eseguito quindi a ogni avvio/riavvio del server e NON a ogni sessione <br>
     */
    public void inizia() {
        for (WPref pref : WPref.getAllEnums()) {
            crea(pref);
        }
    }

    /**
     * Inserimento di una preferenza del progetto specifico <br>
     * Controlla che la entity non esista già <br>
     */
    protected void crea(final WPref pref) {
        crea(pref.getKeyCode(), pref.getType(), pref.getDefaultValue(), pref.getDescrizione(), false);
    }

    /**
     * Inserimento di una preferenza del progetto specifico <br>
     * Controlla che la entity non esista già <br>
     */
    protected void crea(final String code, final AETypePref type, final Object value, final String descrizione, final boolean needRiavvio) {
        Preferenza preferenza = null;

        if (textService.isEmpty(code) || type == null || value == null || textService.isEmpty(descrizione)) {
            return;
        }
        if (backend.existsByCode(code)) {
            return;
        }

        preferenza = new Preferenza();
        preferenza.code = code;
        preferenza.type = type;
        preferenza.value = type.objectToBytes(value);
        preferenza.vaad23 = false;
        preferenza.usaCompany = false;
        preferenza.needRiavvio = needRiavvio;
        preferenza.visibileAdmin = true;
        preferenza.descrizione = descrizione;
        preferenza.descrizioneEstesa = descrizione;

        backend.add(preferenza);
    }

}
