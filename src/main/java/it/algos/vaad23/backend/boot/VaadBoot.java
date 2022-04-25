package it.algos.vaad23.backend.boot;

import static it.algos.vaad23.backend.boot.VaadCost.*;
import it.algos.vaad23.backend.interfaces.*;
import it.algos.vaad23.backend.packages.geografia.continente.*;
import it.algos.vaad23.backend.packages.utility.log.*;
import it.algos.vaad23.backend.packages.utility.nota.*;
import it.algos.vaad23.backend.packages.utility.preferenza.*;
import it.algos.vaad23.backend.packages.utility.versione.*;
import it.algos.vaad23.wizard.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.context.event.EventListener;
import org.springframework.context.event.*;
import org.springframework.core.env.*;

import javax.servlet.*;
import java.util.*;

/**
 * Project vaadin23
 * Created by Algos
 * User: gac
 * Date: dom, 06-mar-2022
 * Time: 08:03
 * <p>
 * Running logic after the Spring context has been initialized <br>
 * Executed on container startup, before any browse command <br>
 * Any class that use the @EventListener annotation, will be executed before the application is up and its
 * onContextRefreshEvent method will be called
 * <p>
 * Questa classe astratta riceve un @EventListener dalla sottoclasse concreta alla partenza del programma <br>
 * Deve essere creata una sottoclasse (obbligatoria) per l' applicazione specifica che: <br>
 * 1) regola alcuni parametri standard del database MongoDB <br>
 * 2) regola le variabili generali dell'applicazione <br>
 * 3) crea i dati di alcune collections sul DB mongo <br>
 * 4) crea le preferenze standard e specifiche dell'applicazione <br>
 * 5) aggiunge le @Route (view) standard e specifiche <br>
 * 6) lancia gli schedulers in background <br>
 * 7) costruisce una versione demo <br>
 * 8) controlla l' esistenza di utenti abilitati all' accesso <br>
 */
public abstract class VaadBoot implements ServletContextListener {


    /**
     * Istanza di una classe @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE) <br>
     * Iniettata automaticamente dal framework SpringBoot/Vaadin con l'Annotation @Autowired <br>
     * Disponibile DOPO il ciclo init() del costruttore di questa classe <br>
     */
    public AIData dataInstance;

    /**
     * Istanza di una classe @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE) <br>
     * Iniettata automaticamente dal framework SpringBoot/Vaadin con l'Annotation @Autowired <br>
     * Disponibile DOPO il ciclo init() del costruttore di questa classe <br>
     */
    public AIVers versInstance;

    /**
     * Istanza unica di una classe @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) di servizio <br>
     * Iniettata dal framework SpringBoot/Vaadin usando il metodo setter() <br>
     * al termine del ciclo init() del costruttore di questa classe <br>
     */
    public Environment environment;

    /**
     * Constructor with @Autowired on setter. Usato quando ci sono sottoclassi. <br>
     * Per evitare di avere nel costruttore tutte le property che devono essere iniettate e per poterle aumentare <br>
     * senza dover modificare i costruttori delle sottoclassi, l'iniezione tramite @Autowired <br>
     * viene delegata ad alcuni metodi setter() che vengono qui invocati con valore (ancora) nullo. <br>
     * Al termine del ciclo init() del costruttore il framework SpringBoot/Vaadin, inietterà la relativa istanza <br>
     */
    public VaadBoot() {
        //        this.setEnvironment(environment);
        //        this.setMongo(mongo);
        //        this.setLogger(logger);
        this.setDataInstance(dataInstance);
        this.setVersInstance(versInstance);
        this.setEnvironment(environment);
    }// end of constructor with @Autowired on setter

    /**
     * The ContextRefreshedEvent happens after both Vaadin and Spring are fully initialized. At the time of this
     * event, the application is ready to service Vaadin requests <br>
     */
    @EventListener(ContextRefreshedEvent.class)
    public void onContextRefreshEvent() {
        this.inizia();
    }

    /**
     * Primo ingresso nel programma <br>
     * <p>
     * 1) regola alcuni parametri standard del database MongoDB <br>
     * 2) regola le variabili generali dell'applicazione <br>
     * 3) crea le preferenze standard e specifiche dell'applicazione <br>
     * 4) crea i dati di alcune collections sul DB mongo <br>
     * 5) aggiunge al menu le @Route (view) standard e specifiche <br>
     * 6) lancia gli schedulers in background <br>
     * 7) costruisce una versione demo <br>
     * 8) controllare l' esistenza di utenti abilitati all' accesso <br>
     * <p>
     * Può essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    protected void inizia() {
        //        this.fixDBMongo();
        this.fixVariabili();
        //        this.fixPreferenze();
        //        this.fixData();
        this.fixMenuRoutes();
        this.fixVersioni();
    }

    /**
     * Regola le variabili generali dell' applicazione con il loro valore iniziale di default <br>
     * Le variabili (static) sono uniche per tutta l' applicazione <br>
     * Il loro valore può essere modificato SOLO in questa classe o in una sua sottoclasse <br>
     * Può essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    protected void fixVariabili() {

        /**
         * Nome identificativo minuscolo del progetto base vaadflow <br>
         * Deve essere regolato in backend.boot.VaadBoot.fixVariabili() del progetto base <br>
         */
        VaadVar.projectVaadFlow = PROJECT_VAADFLOW;

        /**
         * Lista dei moduli di menu da inserire nel Drawer del MainLayout per le gestione delle @Routes. <br>
         * Regolata dall'applicazione durante l'esecuzione del 'container startup' (non-UI logic) <br>
         * Usata da ALayoutService per conto di MainLayout allo start della UI-logic <br>
         */
        VaadVar.menuRouteList = new ArrayList<>();

        /**
         * Classe da usare per lo startup del programma <br>
         * Di default FlowData oppure possibile sottoclasse del progetto <br>
         * Deve essere regolato in backend.boot.xxxBoot.fixVariabili() <br>
         */
        VaadVar.dataClazz = VaadData.class;

        /**
         * Classe da usare per gestire le versioni <br>
         * Di default FlowVers oppure possibile sottoclasse del progetto <br>
         * Deve essere regolato in backend.boot.xxxBoot.fixVariabili() del progetto corrente <br>
         */
        VaadVar.versionClazz = VaadVers.class;

        /**
         * Versione dell' applicazione base vaadflow14 <br>
         * Usato solo internamente <br>
         * Deve essere regolato in backend.boot.VaadBoot.fixVariabili() del progetto corrente <br>
         */
        VaadVar.vaadin23Version = Double.parseDouble(Objects.requireNonNull(environment.getProperty("algos.vaadin23.version")));

        /**
         * Controlla se l' applicazione è multi-company oppure no <br>
         * Di default uguale a false <br>
         * Deve essere regolato in backend.boot.xxxBoot.fixVariabili() del progetto corrente <br>
         * Se usaCompany=true anche usaSecurity deve essere true <br>
         */
        VaadVar.usaCompany = false;

        /**
         * Controlla se l' applicazione usa il login oppure no <br>
         * Se si usa il login, occorre la classe SecurityConfiguration <br>
         * Se non si usa il login, occorre disabilitare l'Annotation @EnableWebSecurity di SecurityConfiguration <br>
         * Di default uguale a false <br>
         * Deve essere regolato in backend.boot.xxxBoot.fixVariabili() del progetto corrente <br>
         * Se usaCompany=true anche usaSecurity deve essere true <br>
         * Può essere true anche se usaCompany=false <br>
         */
        VaadVar.usaSecurity = false;
    }

    /**
     * Aggiunge al menu le @Route (view) standard e specifiche <br>
     * Questa classe viene invocata PRIMA della chiamata del browser <br>
     * <p>
     * Nella sottoclasse che invoca questo metodo, aggiunge le @Route (view) specifiche dell' applicazione <br>
     * Le @Route vengono aggiunte ad una Lista statica mantenuta in VaadVar <br>
     * Verranno lette da MainLayout la prima volta che il browser 'chiama' una view <br>
     * Può essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    protected void fixMenuRoutes() {
        //        VaadVar.menuRouteList.add(HelloWorldView.class);
        //        VaadVar.menuRouteList.add(AboutView.class);
        //        VaadVar.menuRouteList.add(AddressFormView.class);
        //        VaadVar.menuRouteList.add(CarrelloFormView.class);
        //        VaadVar.menuRouteList.add(ContinenteView.class);
        VaadVar.menuRouteList.add(WizardView.class);
        VaadVar.menuRouteList.add(NotaView.class);
        VaadVar.menuRouteList.add(VersioneView.class);
        VaadVar.menuRouteList.add(LoggerView.class);
        VaadVar.menuRouteList.add(PreferenzaView.class);
        VaadVar.menuRouteList.add(ContinenteView.class);
    }

    /**
     * Inizializzazione delle versioni standard di vaadinFlow <br>
     * Inizializzazione delle versioni del programma specifico <br>
     */
    protected void fixVersioni() {
        this.versInstance.inizia();
    }


    /**
     * Set con @Autowired di una property chiamata dal costruttore <br>
     * Istanza di una classe @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE) <br>
     * Chiamata dal costruttore di questa classe con valore nullo <br>
     * Iniettata dal framework SpringBoot/Vaadin al termine del ciclo init() del costruttore di questa classe <br>
     */
    @Autowired
    @Qualifier(TAG_FLOW_DATA)
    public void setDataInstance(final AIData dataInstance) {
        this.dataInstance = dataInstance;
    }

    /**
     * Set con @Autowired di una property chiamata dal costruttore <br>
     * Istanza di una classe @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE) <br>
     * Chiamata dal costruttore di questa classe con valore nullo <br>
     * Iniettata dal framework SpringBoot/Vaadin al termine del ciclo init() del costruttore di questa classe <br>
     */
    @Autowired
    @Qualifier(TAG_FLOW_VERSION)
    public void setVersInstance(final AIVers versInstance) {
        this.versInstance = versInstance;
    }

    /**
     * Set con @Autowired di una property chiamata dal costruttore <br>
     * Istanza di una classe di SpringBoot <br>
     * Chiamata dal costruttore di questa classe con valore nullo <br>
     * Iniettata dal framework SpringBoot/Vaadin al termine del ciclo init() del costruttore di questa classe <br>
     */
    @Autowired
    public void setEnvironment(final Environment environment) {
        this.environment = environment;
    }

}
