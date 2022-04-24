package it.algos.vaad23.backend.logic;

import com.vaadin.flow.component.orderedlayout.*;
import com.vaadin.flow.router.*;
import static it.algos.vaad23.backend.boot.VaadCost.*;
import it.algos.vaad23.backend.enumeration.*;
import it.algos.vaad23.backend.service.*;
import it.algos.vaad23.backend.wrapper.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.context.*;

/**
 * Project vaadin23
 * Created by Algos
 * User: gac
 * Date: ven, 11-mar-2022
 * Time: 18:33
 * Superclasse astratta delle view xxxList. Contiene la logica comune. <br>
 * Serve per 'dichiarare' i riferimenti ad altre classi e usarli nelle sottoclassi concrete <br>
 * I riferimenti sono 'public' per poterli usare con TestUnit <br>
 * Serve per 'dichiarare' le property e usarle nelle sottoclassi concrete <br>
 * Le properties sono 'protected' per poterle usare nelle sottoclassi <br>
 * <p>
 * Gestione della 'view' di @Route e della 'business logic' <br>
 * Mantiene lo 'stato' <br>
 * L' istanza (PROTOTYPE) della sottoclasse concretaq viene creata a ogni chiamata del browser <br>
 * Eventuali parametri (opzionali) devono essere passati nell'URL <br>
 */
public abstract class Logic extends VerticalLayout implements HasUrlParameter<String>, BeforeEnterObserver, AfterNavigationObserver {

    /**
     * Istanza di una interfaccia SpringBoot <br>
     * Iniettata automaticamente dal framework SpringBoot con l'Annotation @Autowired <br>
     * Disponibile DOPO il ciclo init() del costruttore di questa classe <br>
     */
    @Autowired
    public ApplicationContext appContext;

    /**
     * Istanza unica di una classe @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) di servizio <br>
     * Iniettata automaticamente dal framework SpringBoot/Vaadin con l'Annotation @Autowired <br>
     * Disponibile DOPO il ciclo init() del costruttore di questa classe <br>
     */
    //    @Autowired
    //    public HtmlService html;

    /**
     * Istanza unica di una classe @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) di servizio <br>
     * Iniettata automaticamente dal framework SpringBoot/Vaadin con l'Annotation @Autowired <br>
     * Disponibile DOPO il ciclo init() del costruttore di questa classe <br>
     */
    //    @Autowired
    //    public ArrayService array;

    /**
     * Istanza unica di una classe @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) di servizio <br>
     * Iniettata automaticamente dal framework SpringBoot/Vaadin con l'Annotation @Autowired <br>
     * Disponibile DOPO il ciclo init() del costruttore di questa classe <br>
     */
    @Autowired
    public AnnotationService annotationService;

    /**
     * Istanza unica di una classe @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) di servizio <br>
     * Iniettata automaticamente dal framework SpringBoot/Vaadin con l'Annotation @Autowired <br>
     * Disponibile DOPO il ciclo init() del costruttore di questa classe <br>
     */
    @Autowired
    public ReflectionService reflectionService;

    /**
     * Istanza unica di una classe @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) di servizio <br>
     * Iniettata automaticamente dal framework SpringBoot/Vaadin con l'Annotation @Autowired <br>
     * Disponibile DOPO il ciclo init() del costruttore di questa classe <br>
     */
    @Autowired
    public LogService logger;

    /**
     * Istanza unica di una classe @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) di servizio <br>
     * Iniettata automaticamente dal framework SpringBoot/Vaadin con l'Annotation @Autowired <br>
     * Disponibile DOPO il ciclo init() del costruttore di questa classe <br>
     */
    //    @Autowired
    //    public AIMongoService mongo;

    /**
     * Istanza unica di una classe @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) di servizio <br>
     * Iniettata automaticamente dal framework SpringBoot/Vaadin con l'Annotation @Autowired <br>
     * Disponibile DOPO il ciclo init() del costruttore di questa classe <br>
     */
    @Autowired
    public RouteService routeService;

    /**
     * Istanza unica di una classe @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) di servizio <br>
     * Iniettata automaticamente dal framework SpringBoot/Vaadin con l'Annotation @Autowired <br>
     * Disponibile DOPO il ciclo init() del costruttore di questa classe <br>
     */
    //    @Autowired
    //    public ClassService classService;

    /**
     * Istanza unica di una classe @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) di servizio <br>
     * Iniettata automaticamente dal framework SpringBoot/Vaadin con l'Annotation @Autowired <br>
     * Disponibile DOPO il ciclo init() del costruttore di questa classe <br>
     */
    @Autowired
    public TextService textService;

    /**
     * Istanza unica di una classe @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) di servizio <br>
     * Iniettata automaticamente dal framework SpringBoot/Vaadin con l'Annotation @Autowired <br>
     * Disponibile DOPO il ciclo init() del costruttore di questa classe <br>
     */
    @Autowired
    public DateService dateService;

    /**
     * Istanza unica di una classe @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) di servizio <br>
     * Iniettata automaticamente dal framework SpringBoot/Vaadin con l'Annotation @Autowired <br>
     * Disponibile DOPO il ciclo init() del costruttore di questa classe <br>
     */
    //    @Autowired
    //    public AVaadinService vaadinService;

    /**
     * Istanza unica di una classe @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) di servizio <br>
     * Iniettata automaticamente dal framework SpringBoot/Vaadin con l'Annotation @Autowired <br>
     * Disponibile DOPO il ciclo init() del costruttore di questa classe <br>
     */
    //    @Autowired
    //    public UtilityService utility;

    /**
     * Istanza unica di una classe @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) di servizio <br>
     * Iniettata automaticamente dal framework SpringBoot/Vaadin con l'Annotation @Autowired <br>
     * Disponibile DOPO il ciclo init() del costruttore di questa classe <br>
     */
    //    @Autowired
    //    protected DataProviderService dataService;

    /**
     * Istanza unica di una classe @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) di servizio <br>
     * Iniettata automaticamente dal framework SpringBoot/Vaadin con l'Annotation @Autowired <br>
     * Disponibile DOPO il ciclo init() del costruttore di questa classe <br>
     */
    //    @Autowired
    //    protected AWikiApiService wikiApi;


    /**
     * Wrapper di dati recuperati dall'url del browser, obbligatorio per il form <br>
     */
    protected WrapParametro routeParameter;

    /**
     * Regola i parametri del browser per una view costruita da @Route <br>
     * Usato per costruire GenericLogicList e GenericLogicForm <br>
     * Se c'è solo il primo segmento, routeParameter NON è valido (non serve) <br>
     * <p>
     * Chiamato da com.vaadin.flow.router.Router tramite l' interfaccia HasUrlParameter <br>
     * Chiamato DOPO @PostConstruct ma PRIMA di beforeEnter() <br>
     *
     * @param beforeEvent        con stringa del browser (bodyTextUTF8) da decodificare: primoSegmento e queryParameters
     * @param parametroOpzionale (poco usato) eventualmente presente DOPO il primoSegmento dell'URL e DOPO lo slash
     */
    @Override
    public void setParameter(final BeforeEvent beforeEvent, @OptionalParameter String parametroOpzionale) {
        //--routeParameter contiene sia il primoSegmento sia multiParametersMap sia singleParameter
        routeParameter = routeService.estraeParametri(beforeEvent, parametroOpzionale);

        //--Regola le property indispensabili per gestire questa view
        //--Se c'è solo il primo segmento, non serve regolare le property (probabilmente perché siamo in una sottoclasse specifica)
        if (routeParameter != null && routeParameter.getTypeParam() != AETypeParam.segmentOnly) {
            fixProperty();
        }
    }


    protected void fixProperty() {
        if (routeParameter == null && annotationService.getRouteMenuName(this.getClass()).equals(ROUTE_NAME_GENERIC_VIEW)) {
            //            logger.error("Qualcosa non quadra", Logic.class, "fixProperty"); @todo rimettere
        }

        //        this.entityClazz = null;
        //        this.entityBean = null;
        //        this.entityBeanPrevID = VUOTA;
        //        this.entityBeanNextID = VUOTA;
        //        //        this.entityService = null;
        //
        //        this.wikiPageTitle = VUOTA;
        //        //        this.wikiModuloTitle = VUOTA;
        //        //        this.wikiStatisticheTitle = VUOTA;
        //        this.topLayout = null;
        //        this.bottomLayout = null;
    }

}
