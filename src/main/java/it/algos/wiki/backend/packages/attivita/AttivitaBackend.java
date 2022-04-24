package it.algos.wiki.backend.packages.attivita;

import static it.algos.vaad23.backend.boot.VaadCost.*;
import it.algos.vaad23.backend.exception.*;
import it.algos.vaad23.backend.logic.*;
import it.algos.vaad23.backend.wrapper.*;
import static it.algos.wiki.backend.boot.WikiCost.*;
import it.algos.wiki.backend.enumeration.*;
import it.algos.wiki.backend.service.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.data.mongodb.repository.*;
import org.springframework.stereotype.*;

import java.time.*;
import java.util.*;

/**
 * Project vaadwiki
 * Created by Algos
 * User: gac
 * Date: lun, 18-apr-2022
 * Time: 21:25
 * <p>
 * Service di una entityClazz specifica e di un package <br>
 * Garantisce i metodi di collegamento per accedere al database <br>
 * Non mantiene lo stato di una istanza entityBean <br>
 * Mantiene lo stato della entityClazz <br>
 * NOT annotated with @SpringComponent (inutile, esiste già @Service) <br>
 * NOT annotated with @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) (inutile, esiste già @Service) <br>
 */
@Service
public class AttivitaBackend extends CrudBackend {

    /**
     * Istanza unica di una classe @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) di servizio <br>
     * Iniettata automaticamente dal framework SpringBoot/Vaadin con l'Annotation @Autowired <br>
     * Disponibile DOPO il ciclo init() del costruttore di questa classe <br>
     */
    @Autowired
    public WikiApiService wikiApiService;

    private AttivitaRepository repository;

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
    //@todo registrare eventualmente come costante in VaadCost il valore del Qualifier
    public AttivitaBackend(@Autowired @Qualifier("Attivita") final MongoRepository crudRepository) {
        super(crudRepository, Attivita.class);
        this.repository = (AttivitaRepository) crudRepository;
    }

    /**
     * Crea e registra una entityBean <br>
     *
     * @param singolare di riferimento (obbligatorio, unico)
     * @param plurale   (facoltativo, non unico)
     *
     * @return la nuova entityBean appena creata e salvata
     */
    public Attivita creaOriginale(final String singolare, final String plurale) {
        return repository.insert(newEntity(singolare, plurale, false));
    }

    /**
     * Creazione in memoria di una nuova entity che NON viene salvata <br>
     * Usa il @Builder di Lombok <br>
     * Eventuali regolazioni iniziali delle property <br>
     *
     * @return la nuova entity appena creata (non salvata)
     */
    public Attivita newEntity() {
        return newEntity(VUOTA, VUOTA, true);
    }


    /**
     * Creazione in memoria di una nuova entityBean che NON viene salvata <br>
     * Usa il @Builder di Lombok <br>
     * Eventuali regolazioni iniziali delle property <br>
     *
     * @param singolare di riferimento (obbligatorio, unico)
     * @param plurale   (facoltativo, non unico)
     * @param aggiunta  flag (facoltativo, di default false)
     *
     * @return la nuova entityBean appena creata (non salvata)
     */
    public Attivita newEntity(final String singolare, final String plurale, final boolean aggiunta) {
        Attivita newEntityBean = Attivita.builder()
                .singolare(textService.isValid(singolare) ? singolare : null)
                .plurale(textService.isValid(plurale) ? plurale : null)
                .aggiunta(aggiunta)
                .build();

        //        return (Attivita) fixKey(newEntityBean);
        return newEntityBean;
    }

    /**
     * Esegue un azione di download, specifica del programma/package in corso <br>
     * Deve essere sovrascritto, senza invocare il metodo della superclasse <br>
     */
    @Override
    public void download() {
        downloadModulo(PATH_MODULO_ATTIVITA);
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
    public boolean downloadModulo(String wikiTitle) {
        boolean status = false;
        String message;
        long inizio = System.currentTimeMillis();
        long fine;
        Long delta;
        int durata;

        Map<String, String> mappa = wikiApiService.leggeMappaModulo(wikiTitle);

        if (mappa != null && mappa.size() > 0) {
            deleteAll();
            for (Map.Entry<String, String> entry : mappa.entrySet()) {
                this.creaOriginale(entry.getKey(), entry.getValue());
            }
            status = true;
            fine = System.currentTimeMillis();
            delta = fine - inizio;
            delta = delta / 1000;
            durata = delta.intValue();
            WPref.durataDownloadAttivita.setValue(durata);
            WPref.lastDownloadAttivita.setValue(LocalDateTime.now());
        }
        else {
            message = String.format("Non sono riuscito a leggere da wiki il modulo %s", wikiTitle);
            logger.warn(new WrapLog().exception(new AlgosException(message)).usaDb());
        }

        //        status = aggiunge();

        //        super.fixDataDownload();
        return status;
    }

    public int countAll() {
        return repository.findAll().size();
    }

}// end of crud backend class
