package it.algos.vaad23.backend.boot;

import com.vaadin.flow.spring.annotation.*;
import static it.algos.vaad23.backend.boot.VaadCost.*;
import it.algos.vaad23.backend.enumeration.*;
import it.algos.vaad23.backend.interfaces.*;
import it.algos.vaad23.backend.service.*;
import it.algos.vaad23.backend.wrapper.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.beans.factory.config.*;
import org.springframework.context.annotation.Scope;

import java.lang.reflect.*;
import java.util.*;
import java.util.function.*;

/**
 * Project vaadflow
 * Created by Algos
 * User: gac
 * Date: sab, 20-ott-2018
 * Time: 08:53
 * <p>
 * Poiché siamo in fase di boot, la sessione non esiste ancora <br>
 * Questo vuol dire che eventuali classi @VaadinSessionScope
 * NON possono essere iniettate automaticamente da Spring <br>
 * Vengono costruite con la BeanFactory <br>
 * <p>
 * Superclasse astratta per la costruzione iniziale delle Collections <br>
 * Viene invocata PRIMA della chiamata del browser, tramite il <br>
 * metodo FlowBoot.onContextRefreshEvent() <br>
 * Crea i dati di alcune collections sul DB mongo <br>
 * <p>
 * Annotated with @SpringComponent (obbligatorio) <br>
 * Annotated with @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE) <br>
 *
 * @since java 8
 */
@SpringComponent
@Qualifier(TAG_FLOW_DATA)
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class VaadData implements AIData {

    /**
     * Messaggio di errore <br>
     *
     * @since java 8
     */
    public Runnable mancaPrefLogic = () -> System.out.println("Non ho trovato la classe PreferenzaLogic");

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
    public ArrayService arrayService;

    /**
     * Istanza unica di una classe @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) di servizio <br>
     * Iniettata dal framework SpringBoot/Vaadin usando il metodo setter() <br>
     * al termine del ciclo init() del costruttore di questa classe <br>
     */
    protected FileService fileService;

    /**
     * Istanza unica di una classe @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) di servizio <br>
     * Iniettata dal framework SpringBoot/Vaadin usando il metodo setter() <br>
     * al termine del ciclo init() del costruttore di questa classe <br>
     */
    protected TextService textService;

    /**
     * Istanza unica di una classe @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) di servizio <br>
     * Iniettata dal framework SpringBoot/Vaadin usando il metodo setter() <br>
     * al termine del ciclo init() del costruttore di questa classe <br>
     */
    protected ClassService classService;

    /**
     * Istanza unica di una classe @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) di servizio <br>
     * Iniettata dal framework SpringBoot/Vaadin usando il metodo setter() <br>
     * al termine del ciclo init() del costruttore di questa classe <br>
     */
    protected LogService logger;

    /**
     * Istanza unica di una classe @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) di servizio <br>
     * Iniettata dal framework SpringBoot/Vaadin usando il metodo setter() <br>
     * al termine del ciclo init() del costruttore di questa classe <br>
     */
    protected AnnotationService annotationService;

    /**
     * Controlla che la classe sia una Entity <br>
     */
    //    protected Predicate<String> checkEntity = canonicalName -> classService.isEntity(canonicalName); ÷÷@todo rimettere

    /**
     * Controlla che la Entity estenda AREntity <br>
     */
    //    protected Predicate<Object> checkResetEntity = clazzName -> classService.isResetEntity(clazzName.toString());÷÷@todo rimettere

    /**
     * Controlla che la classe abbia usaBoot=true <br>
     */
    //    protected Predicate<Object> checkUsaBoot = clazzName -> annotationService.usaBoot(clazzName.toString());÷÷@todo rimettere

    /**
     * Controlla che il service abbia il metodo reset() oppure download() <br>
     */
    protected Predicate<Method> esisteMetodo = clazzName -> clazzName.getName().contains("reset") || clazzName.getName().contains("download");

    //    /**
    //     * Controlla che il service abbia il metodo reset() oppure download() <br>
    //     * nella sottoclasse specifica xxxService <br>
    //     * Altrimenti i dati non possono essere ri-creati <br>
    //     */
    //    protected Predicate<Object> esisteMetodoService = clazzName -> {
    //        final Method[] methods;
    //        final AIService entityService = classService.getServiceFromEntityName(clazzName.toString());
    //
    //        try {
    //            methods = entityService.getClass().getDeclaredMethods();
    //        } catch (Exception unErrore) {
    //            return false;
    //        }
    //
    //        if (Arrays.stream(methods).filter(esisteMetodo).count() == 0) {
    //            return false;
    //        }
    //        else {
    //            return true;
    //        }
    //    };

    //    /**
    //     * Controllo la singola collezione <br>
    //     * <p>
    //     * Costruisco un' istanza della classe xxxService corrispondente alla entityClazz <br>
    //     * Controllo se l' istanza xxxService è creabile <br>
    //     * Un package standard contiene sia xxxService che xxxLogicList <br>
    //     * Controllo se esiste un metodo resetEmptyOnly() nella classe xxxService specifica <br>
    //     * Invoco il metodo API resetEmptyOnly() della interfaccia AIService <br>
    //     */
    //    protected Consumer<Object> bootReset = canonicalEntity -> {
    //        AIResult result = null;
    //        final String canonicalEntityName = (String) canonicalEntity;
    //        final String canonicaName = canonicalEntityName.endsWith(SUFFIX_ENTITY) ? text.levaCoda(canonicalEntityName, SUFFIX_ENTITY) : canonicalEntityName;
    //        final String classeFinale = fileService.estraeClasseFinale(canonicaName);
    //        final String entityServicePrevista = classeFinale + SUFFIX_SERVICE;
    //        final AIService entityService = classService.getServiceFromEntityName(canonicalEntityName);
    //        final String packageName = file.estraeClasseFinale(canonicaName).toLowerCase();
    //        final String nameService;
    //        String message;
    //        Class entityClazz = null;
    //        int numRec;
    //        String type;
    //        String metodo = VUOTA;
    //        boolean isResetVuoto = false;
    //        boolean isEmptyCollection = false;
    //        try {
    //            entityClazz = Class.forName(canonicalEntityName);
    //        } catch (Exception unErrore) {
    //            logger.error(unErrore, this.getClass(), "bootReset");
    //        }
    //
    //        if (entityService == null) {
    //            message = String.format("Nel package %s manca la entityService specifica e non sono neanche riuscito a creare la EntityService generica", packageName);
    //            logger.log(AETypeLog.checkData, message);
    //            return;
    //        }
    //
    //        nameService = entityService.getClass().getSimpleName();
    //        if (nameService.equals(TAG_GENERIC_SERVICE)) {
    //            message = String.format("Nel package %s non esiste la classe %s e usa EntityService. Non esiste il metodo %s()", packageName, entityServicePrevista, TAG_METHOD_BOOT_RESET);
    //            logger.log(AETypeLog.checkData, message);
    //            return;
    //        }
    //        if (annotationService.usaReset(entityClazz)) {
    //            metodo = "reset";
    //            try {
    //                isResetVuoto = mongo.countReset(entityClazz) == 0;
    //            } catch (Exception unErrore) {
    //                logger.info(unErrore, this.getClass(), "bootReset");
    //                isResetVuoto = true;
    //            }
    //            if (isResetVuoto) {
    //                try {
    //                    entityService.getClass().getDeclaredMethod("reset");
    //                    result = entityService.reset();
    //                } catch (Exception unErrore) {
    //                    try {
    //                        entityService.getClass().getDeclaredMethod("download");
    //                        result = entityService.download() ? AResult.valido() : AResult.errato();
    //                    } catch (Exception unErrore2) {
    //                        return;
    //                    }
    //                }
    //            }
    //            else {
    //                try {
    //                    result = AResult.errato(((MongoService) mongo).countReset(entityClazz));//@todo da controllare
    //                } catch (Exception unErrore) {
    //                    logger.error(unErrore, this.getClass(), "bootReset");
    //                }
    //            }
    //        }
    //        else {
    //            metodo = "download";
    //            try {
    //                isEmptyCollection = !mongo.isValidCollection(entityClazz);
    //            } catch (AlgosException unErrore) {
    //                logger.error(unErrore, this.getClass(), "bootReset");
    //            }
    //            if (isEmptyCollection) {
    //                try {
    //                    entityService.getClass().getDeclaredMethod("reset");
    //                    result = entityService.reset();
    //                } catch (Exception unErrore) {
    //                    try {
    //                        entityService.getClass().getDeclaredMethod("download");
    //                        result = entityService.download() ? AResult.valido() : AResult.errato();
    //                    } catch (Exception unErrore2) {
    //                        return;
    //                    }
    //                }
    //            }
    //            else {
    //                try {
    //                    result = AResult.errato(((MongoService) mongo).count(entityClazz));//@todo da controllare
    //                } catch (Exception unErrore) {
    //                    logger.error(unErrore, this.getClass(), "bootReset");
    //                }
    //            }
    //        }
    //
    //        if (result != null) {
    //            numRec = result.getIntValue();
    //            type = result.getValidMessage();
    //            if (result.isValido()) {
    //                message = String.format("Nel package %s sono stati inseriti %d elementi %s col metodo %s.%s() ", packageName, numRec, type, nameService, metodo);
    //            }
    //            else {
    //                message = String.format("Nel package %s esistevano %d elementi creati col metodo %s.%s() ", packageName, numRec, nameService, metodo);
    //            }
    //            logger.log(AETypeLog.checkData, message);
    //        }
    //    };


    /**
     * Constructor with @Autowired on setter. Usato quando ci sono sottoclassi. <br>
     * Per evitare di avere nel costruttore tutte le property che devono essere iniettate e per poterle aumentare <br>
     * senza dover modificare i costruttori delle sottoclassi, l'iniezione tramite @Autowired <br>
     * viene delegata ad alcuni metodi setter() che vengono qui invocati con valore (ancora) nullo. <br>
     * Al termine del ciclo init() del costruttore il framework SpringBoot/Vaadin, inietterà la relativa istanza <br>
     */
    public VaadData() {
        this.setFile(fileService);
        this.setText(textService);
        this.setClassService(classService);
        this.setLogger(logger);
        this.setAnnotation(annotationService);
    }// end of constructor with @Autowired on setter


    /**
     * Check iniziale. Ad ogni avvio del programma spazzola tutte le collections <br>
     * Ognuna viene ricreata (mantenendo le entities che hanno reset=false) se:
     * - xxx->@AIEntity usaBoot=true,
     * - esiste xxxService.reset(),
     * - la collezione non contiene nessuna entity che abbia la property reset=true
     * Può essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     * L' ordine con cui vengono create le collections è significativo <br>
     *
     * @since java 8
     */
    public void resetData() {
        //        this.resetData("vaadflow14");
    }


    /**
     * Check iniziale. Ad ogni avvio del programma spazzola tutte le collections <br>
     * Ognuna viene ricreata (mantenendo le entities che hanno reset=false) se:
     * - xxx->@AIEntity usaBoot=true,
     * - esiste xxxService.reset(),
     * - la collezione non contiene nessuna entity che abbia la property reset=true
     * Può essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     * L' ordine con cui vengono create le collections è significativo <br>
     *
     * @param moduleName da controllare
     *
     * @since java 8
     */
    protected void resetData(final String moduleName) {
        List<String> allModulePackagesClasses = null;
        List<Object> allEntityClasses = null;
        List<Object> allUsaBootEntityClasses = null;
        List<Object> allEntityClassesRicreabiliResetDownload = null;
        String message;
        Object[] matrice = null;

        //--spazzola tutta la directory package del modulo in esame e recupera
        //--tutte le classi contenute nella directory e nelle sue sottoclassi
        try {
            allModulePackagesClasses = fileService.getModuleSubFilesEntity(moduleName);
        } catch (Exception unErrore) {
            logger.error(AETypeLog.file, unErrore);
        }

        //--seleziona le classes che estendono AEntity
        logger.info(new WrapLog().type(AETypeLog.checkData));
        try {
            //            allEntityClasses = Arrays.asList(allModulePackagesClasses.stream().filter(checkEntity).sorted().toArray());÷÷@todo rimettere
        } catch (Exception unErrore) {
            logger.error(AETypeLog.file, unErrore);
        }
        if (arrayService.isAllValid(allEntityClasses)) {
            message = String.format("In %s sono stati trovati %d packages con classi di tipo AEntity", moduleName, allEntityClasses.size());
        }
        else {
            message = String.format("In %s non è stato trovato nessun package con classi di tipo AEntity", moduleName);
        }
        logger.info(new WrapLog().type(AETypeLog.checkData).message(message));

        //        //--seleziona le Entity classes che estendono AREntity
        //        allResetEntityClasses = Arrays.asList(allEntityClasses.stream().filter(checkResetEntity).sorted().toArray());
        //        message = String.format("In %s sono stati trovati %d packages con classi di tipo AREntity da controllare", moduleName, allResetEntityClasses.size() + 1);
        //        logger.log(AETypeLog.checkData, message);

        //--seleziona le Entity classes che hanno @AIEntity usaBoot=true
        if (allEntityClasses != null) {
            //            allUsaBootEntityClasses = Arrays.asList(allEntityClasses.stream().filter(checkUsaBoot).sorted().toArray());÷÷@todo rimettere
        }
        if (arrayService.isAllValid(allUsaBootEntityClasses)) {
            message = String.format("In %s sono stati trovati %d packages con classi di tipo AEntity che hanno usaBoot=true", moduleName, allUsaBootEntityClasses.size());
        }
        else {
            message = String.format("In %s non è stato trovato nessun package con classi di tipo AEntity che hanno usaBoot=true", moduleName);
        }
        logger.info(new WrapLog().type(AETypeLog.checkData).message(message));

        //--seleziona le xxxService classes che hanno il metodo reset() oppure download()
        if (allUsaBootEntityClasses != null) {
            //            allEntityClassesRicreabiliResetDownload = Arrays.asList(allUsaBootEntityClasses.stream().filter(esisteMetodoService).sorted().toArray());÷÷@todo rimettere
        }
        if (arrayService.isAllValid(allEntityClassesRicreabiliResetDownload)) {
            message = String.format("In %s sono stati trovati %d packages con classi di tipo xxxService che hanno reset() oppure download()", moduleName, allEntityClassesRicreabiliResetDownload.size());
        }
        else {
            message = String.format("In %s non è stato trovato nessun package con classi di tipo xxxService che hanno reset() oppure download()", moduleName);
        }
        logger.info(new WrapLog().type(AETypeLog.checkData).message(message));

        //--elabora le entity classes che hanno il metodo reset() oppure download() e quindi sono ricreabili
        //--eseguendo xxxService.bootReset (forEach=elaborazione)
        if (allEntityClassesRicreabiliResetDownload != null) {
            //            allEntityClassesRicreabiliResetDownload.stream().forEach(bootReset); @todo rimettere
            message = String.format("Controllati i dati iniziali di %s", moduleName);
            logger.info(new WrapLog().type(AETypeLog.checkData).message(message));
        }

    }

    /**
     * Set con @Autowired di una property chiamata dal costruttore <br>
     * Istanza unica di una classe @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) di servizio <br>
     * Chiamata dal costruttore di questa classe con valore nullo <br>
     * Iniettata dal framework SpringBoot/Vaadin al termine del ciclo init() del costruttore di questa classe <br>
     */
    @Autowired
    void setFile(final FileService fileService) {
        this.fileService = fileService;
    }


    /**
     * Set con @Autowired di una property chiamata dal costruttore <br>
     * Istanza unica di una classe @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) di servizio <br>
     * Chiamata dal costruttore di questa classe con valore nullo <br>
     * Iniettata dal framework SpringBoot/Vaadin al termine del ciclo init() del costruttore di questa classe <br>
     */
    @Autowired
    void setText(final TextService textService) {
        this.textService = textService;
    }

    /**
     * Set con @Autowired di una property chiamata dal costruttore <br>
     * Istanza unica di una classe @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) di servizio <br>
     * Chiamata dal costruttore di questa classe con valore nullo <br>
     * Iniettata dal framework SpringBoot/Vaadin al termine del ciclo init() del costruttore di questa classe <br>
     */
    @Autowired
    void setClassService(final ClassService classService) {
        this.classService = classService;
    }

    /**
     * Set con @Autowired di una property chiamata dal costruttore <br>
     * Istanza unica di una classe @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) di servizio <br>
     * Chiamata dal costruttore di questa classe con valore nullo <br>
     * Iniettata dal framework SpringBoot/Vaadin al termine del ciclo init() del costruttore di questa classe <br>
     */
    @Autowired
    void setLogger(final LogService logger) {
        this.logger = logger;
    }

    /**
     * Set con @Autowired di una property chiamata dal costruttore <br>
     * Istanza unica di una classe @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) di servizio <br>
     * Chiamata dal costruttore di questa classe con valore nullo <br>
     * Iniettata dal framework SpringBoot/Vaadin al termine del ciclo init() del costruttore di questa classe <br>
     */
    @Autowired
    void setAnnotation(final AnnotationService annotationService) {
        this.annotationService = annotationService;
    }

}