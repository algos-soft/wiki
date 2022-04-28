package it.algos.wiki.backend.packages.doppionome;

import static it.algos.vaad23.backend.boot.VaadCost.*;
import static it.algos.wiki.backend.boot.WikiCost.*;
import it.algos.wiki.backend.enumeration.*;
import it.algos.wiki.backend.packages.wiki.*;
import org.springframework.data.mongodb.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.*;

/**
 * Project wiki
 * Created by Algos
 * User: gac
 * Date: mar, 26-apr-2022
 * Time: 19:34
 * <p>
 * Service di una entityClazz specifica e di un package <br>
 * Garantisce i metodi di collegamento per accedere al database <br>
 * Non mantiene lo stato di una istanza entityBean <br>
 * Mantiene lo stato della entityClazz <br>
 * NOT annotated with @SpringComponent (inutile, esiste già @Service) <br>
 * NOT annotated with @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) (inutile, esiste già @Service) <br>
 */
@Service
public class DoppionomeBackend extends WikiBackend {

    private DoppionomeRepository repository;

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
    public DoppionomeBackend(@Autowired @Qualifier(TAG_DOPPIO_NOME) final MongoRepository crudRepository) {
        super(crudRepository, Doppionome.class);
        this.repository = (DoppionomeRepository) crudRepository;
//        super.lastDownload = WPref.downloadNomi;
    }

    public Doppionome creaIfNotExist(final String nome) {
        return checkAndSave(newEntity(nome));
    }

    public Doppionome checkAndSave(final Doppionome doppionome) {
        return isExist(doppionome.nome) ? null : repository.insert(doppionome);
    }

    public boolean isExist(final String nome) {
        return repository.findFirstByNome(nome) != null;
    }

    /**
     * Creazione in memoria di una nuova entity che NON viene salvata <br>
     * Usa il @Builder di Lombok <br>
     * Eventuali regolazioni iniziali delle property <br>
     *
     * @return la nuova entity appena creata (non salvata)
     */
    public Doppionome newEntity() {
        return newEntity(VUOTA);
    }

    /**
     * Creazione in memoria di una nuova entity che NON viene salvata <br>
     * Usa il @Builder di Lombok <br>
     * Eventuali regolazioni iniziali delle property <br>
     * All properties <br>
     *
     * @param nome (obbligatorio, unico)
     *
     * @return la nuova entity appena creata (non salvata e senza keyID)
     */
    public Doppionome newEntity(final String nome) {
        return Doppionome.builder()
                .nome(textService.isValid(nome) ? nome : null)
                .build();
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

        String tag = "</li>\n<li>";
        String nome;
        String testoPagina = webService.leggeWikiTxt(wikiTitle);
        testoPagina = textService.estrae(testoPagina, "<ul><li>", "</li></ul>");
        String[] righe = testoPagina.split(tag);

        if (righe != null) {
            deleteAll();
            for (int k = 0; k < righe.length; k++) {
                nome = righe[k];
                if (creaIfNotExist(nome) != null) {
                    size++;
                }
            }
        }

        super.fixDownload(inizio, wikiTitle, righe.length, size);
    }

}// end of crud backend class
