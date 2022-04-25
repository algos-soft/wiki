package it.algos.vaad23.backend.packages.utility.nota;

import static it.algos.vaad23.backend.boot.VaadCost.*;
import it.algos.vaad23.backend.enumeration.*;
import it.algos.vaad23.backend.logic.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.data.mongodb.repository.*;
import org.springframework.stereotype.*;

import java.time.*;

/**
 * Project vaadin23
 * Created by Algos
 * User: gac
 * Date: ven, 18-mar-2022
 * Time: 06:55
 * <p>
 * Service di una entityClazz specifica e di un package <br>
 * Garantisce i metodi di collegamento per accedere al database <br>
 * Non mantiene lo stato di una istanza entityBean <br>
 * Mantiene lo stato della entityClazz <br>
 * Annotated with @Service (obbligatorio) <br>
 */
@Service
public class NotaBackend extends CrudBackend {

    private NotaRepository repository;

    /**
     * Costruttore @Autowired (facoltativo) con @Qualifier (obbligatorio) <br>
     * In the newest Spring release, it’s constructor does not need to be annotated with @Autowired annotation <br>
     * Si usa un @Qualifier(), per specificare la classe che incrementa l'interfaccia repository <br>
     * Si usa una costante statica, per essere sicuri di scriverla uguale a quella di xxxRepository <br>
     * Regola la classe di persistenza dei dati specifica e la passa al costruttore della superclasse <br>
     * Regola la entityClazz (final nella superclasse) associata a questo service <br>
     *
     * @param crudRepository per la persistenza dei dati
     */
    public NotaBackend(@Autowired @Qualifier(TAG_NOTA) final MongoRepository crudRepository) {
        super(crudRepository, Nota.class);
        this.repository = (NotaRepository) crudRepository;
    }


    /**
     * Creazione in memoria di una nuova entity che NON viene salvata <br>
     * Usa il @Builder di Lombok <br>
     * Eventuali regolazioni iniziali delle property <br>
     *
     * @return la nuova entity appena creata (non salvata)
     */
    public Nota newEntity() {
        return newEntity(null, null, VUOTA);
    }

    /**
     * Creazione in memoria di una nuova entity che NON viene salvata <br>
     * Usa il @Builder di Lombok <br>
     * Eventuali regolazioni iniziali delle property <br>
     *
     * @param type        merceologico della nota
     * @param livello     di importanza o rilevanza della nota
     * @param descrizione dettagliata della nota
     *
     * @return la nuova entity appena creata (non salvata)
     */
    public Nota newEntity(final AETypeLog type, final AENotaLevel livello, final String descrizione) {
        return Nota.builder()
                .type(type != null ? type : AETypeLog.system)
                .livello(livello != null ? livello : AENotaLevel.normale)
                .inizio(LocalDate.now())
                .descrizione(textService.isValid(descrizione) ? descrizione : null)
                .build();
    }


    @Override
    public Nota update(Object objEntity) {
        if (objEntity instanceof Nota notaEntity) {
            notaEntity.fine = notaEntity.fine == null && notaEntity.fatto ? LocalDate.now() : null;

            return (Nota) crudRepository.save(notaEntity);
        }
        else {
            return null;
        }

    }

}// end of crud backend class
