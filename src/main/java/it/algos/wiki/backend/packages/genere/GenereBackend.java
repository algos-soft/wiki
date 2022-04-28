package it.algos.wiki.backend.packages.genere;

import static it.algos.vaad23.backend.boot.VaadCost.*;
import it.algos.vaad23.backend.exception.*;
import it.algos.vaad23.backend.wrapper.*;
import static it.algos.wiki.backend.boot.WikiCost.*;
import it.algos.wiki.backend.enumeration.*;
import it.algos.wiki.backend.packages.wiki.*;
import org.springframework.data.mongodb.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.*;

import java.util.*;
import java.util.function.*;
import java.util.stream.*;

/**
 * Project wiki
 * Created by Algos
 * User: gac
 * Date: dom, 24-apr-2022
 * Time: 10:17
 * <p>
 * Service di una entityClazz specifica e di un package <br>
 * Garantisce i metodi di collegamento per accedere al database <br>
 * Non mantiene lo stato di una istanza entityBean <br>
 * Mantiene lo stato della entityClazz <br>
 * NOT annotated with @SpringComponent (inutile, esiste già @Service) <br>
 * NOT annotated with @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) (inutile, esiste già @Service) <br>
 */
@Service
public class GenereBackend extends WikiBackend {


    private GenereRepository repository;

    /**
     * Costruttore @Autowired (facoltativo) @Qualifier (obbligatorio) <br>
     * In the newest Spring release, it’s constructor does not need to be annotated with @Autowired annotation <br>
     * Si usa un @Qualifier(), per specificare la classe che incrementa l'interfaccia repository <br>
     * Si usa una costante statica, per essere sicuri di scriverla uguale a quella di xxxRepository <br>
     * Regola la classe di persistenza dei dati specifica e la passa al costruttore della superclasse <br>
     * Regola la entityClazz (final nella superclasse) associata a questo service <br>
     *
     * @param crudRepository per la persistenza dei dati
     */
    public GenereBackend(@Autowired @Qualifier(TAG_GENERE) final MongoRepository crudRepository) {
        super(crudRepository, Genere.class);
        this.repository = (GenereRepository) crudRepository;
    }

    public Genere creaIfNotExist(final String singolare, final String pluraleMaschile, final String pluraleFemminile, final boolean maschile) {
        return checkAndSave(newEntity(singolare, pluraleMaschile, pluraleFemminile, maschile));
    }

    public Genere checkAndSave(final Genere genere) {
        return isExist(genere.singolare) ? null : repository.insert(genere);
    }

    public boolean isExist(final String singolare) {
        return repository.findFirstBySingolare(singolare) != null;
    }

    /**
     * Creazione in memoria di una nuova entity che NON viene salvata <br>
     * Usa il @Builder di Lombok <br>
     * Eventuali regolazioni iniziali delle property <br>
     *
     * @return la nuova entity appena creata (non salvata)
     */
    public Genere newEntity() {
        return newEntity(VUOTA, VUOTA, VUOTA, true);
    }

    /**
     * Creazione in memoria di una nuova entity che NON viene salvata <br>
     * Usa il @Builder di Lombok <br>
     * Eventuali regolazioni iniziali delle property <br>
     * All properties <br>
     *
     * @param singolare        di riferimento
     * @param pluraleMaschile  di riferimento
     * @param pluraleFemminile di riferimento
     *
     * @return la nuova entity appena creata (non salvata e senza keyID)
     */
    public Genere newEntity(final String singolare, final String pluraleMaschile, final String pluraleFemminile, final boolean maschile) {
        Genere newEntityBean = Genere.builder()
                .singolare(textService.isValid(singolare) ? singolare : null)
                .pluraleMaschile(textService.isValid(pluraleMaschile) ? pluraleMaschile : null)
                .pluraleFemminile(textService.isValid(pluraleFemminile) ? pluraleFemminile : null)
                .maschile(maschile)
                .build();

        return newEntityBean;
    }

    public int countAll() {
        return repository.findAll().size();
    }

    public List<Genere> findBySingolare(final String value) {
        return repository.findBySingolareStartingWithIgnoreCaseOrderBySingolareAsc(value);
    }

    protected Predicate<Genere> startEx = genere -> genere.singolare.startsWith(TAG_EX) || genere.singolare.startsWith(TAG_EX2);


    public List<Genere> findStartingEx() {
        return (List<Genere>) findAll().stream().filter(startEx).collect(Collectors.toList());
    }


    /**
     * Legge la mappa di valori dal modulo di wiki <br>
     * Cancella la (eventuale) precedente lista di attività <br>
     * Elabora la mappa per creare le singole attività <br>
     * Integra le attività con quelle di genere <br>
     *
     * @param wikiTitle della pagina su wikipedia
     *
     * @return true se l'azione è stata eseguita
     */
    public void download(final String wikiTitle) {
        long inizio = System.currentTimeMillis();
        int size = 0;
        String singolare;
        String pluraliGrezzi;
        String pluraleMaschile;
        String pluraleFemminile;

        Map<String, String> mappa = wikiApiService.leggeMappaModulo(wikiTitle);

        if (mappa != null && mappa.size() > 0) {
            deleteAll();
            for (Map.Entry<String, String> entry : mappa.entrySet()) {
                singolare = entry.getKey();
                singolare = textService.primaMinuscola(singolare);
                pluraliGrezzi = entry.getValue();

                pluraleMaschile = this.estraeMaschile(pluraliGrezzi);
                pluraleFemminile = this.estraeFemminile(pluraliGrezzi);

                if (textService.isValid(pluraleMaschile)) {
                    if (creaIfNotExist(singolare, pluraleMaschile, VUOTA, true) != null) {
                        size++;
                    }
                }
                if (textService.isValid(pluraleFemminile)) {
                    if (creaIfNotExist(singolare, pluraleMaschile, VUOTA, false) != null) {
                        size++;
                    }
                }
            }
        }
        else {
            message = String.format("Non sono riuscito a leggere da wiki il modulo %s", wikiTitle);
            logger.warn(new WrapLog().exception(new AlgosException(message)).usaDb());
        }
        super.fixDownload(inizio, wikiTitle, mappa.size(), size);
    }


    /**
     * Funziona solo per il format: {"avvocati","M", "avvocate","F"} oppure: {"avvocati","M"}
     */
    public String estraeMaschile(String testoPlurale) {
        String pluraleMaschile = VUOTA;
        String tagM = "M";
        String tagApi = "\"";

        if (testoPlurale.contains(tagM)) {
            pluraleMaschile = textService.estrae(testoPlurale, tagApi, tagApi);
        }

        return pluraleMaschile;
    }


    /**
     * Funziona solo per il format: {"avvocati","M", "avvocate","F"} oppure: {"avvocate","F"}
     */
    public String estraeFemminile(String testoPlurale) {
        String pluraleFemminile = "";
        String plurale = "";
        String tagIni = "{";
        String tagEnd = "}";
        String tagVir = ",";
        String tagUgu = "=";
        String tagApi = "";
        String tagM = "M";
        String tagF = "F";
        String[] parti;
        String tag = "F";

        // Funziona solo per il format: { "avvocati","M", "avvocate","F"}
        plurale = textService.setNoGraffe(testoPlurale);
        parti = plurale.split(tagVir);
        for (int k = 0; k < parti.length; k++) {
            parti[k] = textService.setNoDoppiApici(parti[k]);
        }

        for (int k = 0; k < parti.length; k++) {
            if (parti[k].equals(tag)) {
                if (k > 0) {
                    pluraleFemminile = parti[k - 1];
                }
            }
        }

        return pluraleFemminile;
    }


}// end of crud backend class
