package it.algos.vaad23.backend.boot;

import com.vaadin.flow.spring.annotation.*;
import it.algos.vaad23.backend.enumeration.*;
import it.algos.vaad23.backend.packages.utility.preferenza.*;
import it.algos.vaad23.backend.service.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.beans.factory.config.*;
import org.springframework.context.annotation.Scope;
import org.springframework.context.event.*;

import javax.servlet.*;

/**
 * Project vaadin23
 * Created by Algos
 * User: gac
 * Date: mer, 30-mar-2022
 * Time: 20:55
 * Creazione da code di alcune preferenze di base <br>
 */
@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class VaadPref implements ServletContextListener {

    /**
     * Istanza unica di una classe @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) di servizio <br>
     * Iniettata automaticamente dal framework SpringBoot/Vaadin con l'Annotation @Autowired <br>
     * Disponibile DOPO il ciclo init() del costruttore di questa classe <br>
     */
    @Autowired
    public PreferenzaBackend backend;

    /**
     * Istanza unica di una classe @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) di servizio <br>
     * Iniettata automaticamente dal framework SpringBoot/Vaadin con l'Annotation @Autowired <br>
     * Disponibile DOPO il ciclo init() del costruttore di questa classe <br>
     */
    @Autowired
    public TextService textService;

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
        for (Pref pref : Pref.getAllEnums()) {
            crea(pref);
        }
    }

    /**
     * Inserimento di una preferenza del progetto base Vaadin23 <br>
     * Controlla che la entity non esista già <br>
     */
    protected void crea(final Pref pref) {
        crea(pref.getKeyCode(), pref.getType(), pref.getDefaultValue(), pref.getDescrizione(), false);
    }

    /**
     * Inserimento di una preferenza del progetto base Vaadin23 <br>
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
        preferenza.vaad23 = true;
        preferenza.usaCompany = false;
        preferenza.needRiavvio = needRiavvio;
        preferenza.visibileAdmin = false;
        preferenza.descrizione = descrizione;
        preferenza.descrizioneEstesa = descrizione;

        backend.add(preferenza);
    }

}
