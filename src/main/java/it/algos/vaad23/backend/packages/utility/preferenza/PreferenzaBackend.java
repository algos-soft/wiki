package it.algos.vaad23.backend.packages.utility.preferenza;

import static it.algos.vaad23.backend.boot.VaadCost.*;
import it.algos.vaad23.backend.logic.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.data.mongodb.repository.*;
import org.springframework.stereotype.*;

import java.util.*;

/**
 * Project vaadin23
 * Created by Algos
 * User: gac
 * Date: sab, 26-mar-2022
 * Time: 14:02
 * <p>
 * Service di una entityClazz specifica e di un package <br>
 * Garantisce i metodi di collegamento per accedere al database <br>
 * Non mantiene lo stato di una istanza entityBean <br>
 * Mantiene lo stato della entityClazz <br>
 * NOT annotated with @SpringComponent (inutile, esiste già @Service) <br>
 * NOT annotated with @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) (inutile, esiste già @Service) <br>
 */
@Service
@Qualifier(TAG_PRE)
public class PreferenzaBackend extends CrudBackend {

    private PreferenzaRepository repository;

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
    public PreferenzaBackend(@Autowired @Qualifier(TAG_PRE) final MongoRepository crudRepository) {
        super(crudRepository, Preferenza.class);
        this.repository = (PreferenzaRepository) crudRepository;
    }

    public boolean existsById(final String idKey) {
        return repository.existsById(idKey);
    }

    public boolean existsByCode(final String code) {
        return findByCode(code) != null;
    }


    public Preferenza findByKey(final String key) {
        return repository.findFirstByCode(key);
    }

    public Preferenza findByCode(final String code) {
        List<Preferenza> lista = findAllByCode(code);
        if (lista != null && lista.size() == 1) {
            return lista.get(0);
        }
        else {
            return null;
        }
    }


    public List<Preferenza> findAllByCode(final String code) {
        return repository.findAllByCode(code);
    }

}// end of crud backend class
