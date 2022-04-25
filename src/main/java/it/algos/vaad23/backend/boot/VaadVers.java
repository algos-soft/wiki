package it.algos.vaad23.backend.boot;

import com.vaadin.flow.spring.annotation.*;
import static it.algos.vaad23.backend.boot.VaadCost.*;
import it.algos.vaad23.backend.enumeration.*;
import it.algos.vaad23.backend.interfaces.*;
import it.algos.vaad23.backend.packages.utility.versione.*;
import it.algos.vaad23.backend.service.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.beans.factory.config.*;
import org.springframework.context.annotation.Scope;

/**
 * Project vaadflow14
 * Created by Algos
 * User: gac
 * Date: mar, 08-feb-2022
 * Time: 16:45
 * <p>
 * Log delle versioni, modifiche e patch installate <br>
 * Executed on container startup <br>
 * Setup non-UI logic here <br>
 * Classe eseguita solo quando l'applicazione viene caricata/parte nel server (Tomcat o altri) <br>
 * Eseguita quindi a ogni avvio/riavvio del server e NON a ogni sessione <br>
 */
@SpringComponent
@Qualifier(TAG_FLOW_VERSION)
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class VaadVers implements AIVers {

    /**
     * Property statica per le versioni inserite da vaadin23 direttamente <br>
     */
    private static final String CODE_PROJECT_VAADIN = "V";

    /**
     * Istanza unica di una classe @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) di servizio <br>
     * Iniettata automaticamente dal framework SpringBoot/Vaadin con l'Annotation @Autowired <br>
     * Disponibile DOPO il ciclo init() del costruttore di questa classe <br>
     */
    @Autowired
    public DateService dateService;

    /**
     * Property corrente per la sigla iniziale della codifica alfanumerica delle versioni <br>
     */
    protected String codeProject;

    /**
     * Istanza unica di una classe @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) di servizio <br>
     * Iniettata automaticamente dal framework SpringBoot/Vaadin con l'Annotation @Autowired <br>
     * Disponibile DOPO il ciclo init() del costruttore di questa classe <br>
     */
    @Autowired
    protected LogService logger;

    /**
     * Istanza unica di una classe @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) di servizio <br>
     * Iniettata automaticamente dal framework SpringBoot/Vaadin con l'Annotation @Autowired <br>
     * Disponibile DOPO il ciclo init() del costruttore di questa classe <br>
     */
    @Autowired
    protected VersioneBackend backend;

    /**
     * Executed on container startup <br>
     * Setup non-UI logic here <br>
     * This method is called prior to the servlet context being initialized (when the Web application is deployed). <br>
     * You can initialize servlet context related data here. <br>
     * Metodo eseguito solo quando l'applicazione viene caricata/parte nel server (Tomcat o altri) <br>
     * Eseguito quindi a ogni avvio/riavvio del server e NON a ogni sessione <br>
     * <p>
     * Deve essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     * Tutte le aggiunte, modifiche e patch vengono inserite con una versione <br>
     * L'ordine di inserimento è FONDAMENTALE <br>
     */
    public void inizia() {
        int k = 0;
        codeProject = CODE_PROJECT_VAADIN;

        //--prima installazione del progetto base vaadin23
        //--non fa nulla, solo informativo
        if (installa(++k)) {
            this.crea(k, AETypeVers.setup, "Installazione iniziale di vaadin23");
        }

        //--new package
        if (installa(++k)) {
            this.crea(k, AETypeVers.addition, "Package 'versione' funzionante");
        }

        //--new package
        if (installa(++k)) {
            this.crea(k, AETypeVers.addition, "Package 'nota' funzionante");
        }

        //--new package
        if (installa(++k)) {
            this.crea(k, AETypeVers.addition, "Package 'logger' funzionante");
        }

        if (installa(++k)) {
            this.crea(k, AETypeVers.addition, "Alert colorati in testa alle view");
        }

        //--new package
        if (installa(++k)) {
            this.crea(k, AETypeVers.addition, "Preferenze 'base' (string, int, boolean) funzionanti");
        }

        if (installa(++k)) {
            this.crea(k, AETypeVers.addition, "Risorse CSV recuperabili dal server algos");
        }

        if (installa(++k)) {
            this.crea(k, AETypeVers.addition, "Aggiunto checkBox a 3 vie");
        }
    }

    /**
     * Controlla che la versione non esista già <br>
     * Controlla in base alla sigla iniziale del progetto e al numero progressivo <br>
     */
    public boolean installa(final int k) {
        return backend.isMancaByCode(codeProject, k);
    }


    /**
     * Inserimento di una versione del progetto base Vaadin23 <br>
     * Controlla che la entity non esista già <br>
     * Ordine messo in automatico (progressivo) <br>
     */
    protected void crea(final int k, final AETypeVers type, final String descrizione) {
        String key = codeProject + k;
        boolean riferitoVaadin23 = codeProject.equals(CODE_PROJECT_VAADIN);
        backend.crea(key, type, descrizione, ALGOS, riferitoVaadin23);
    }

}
