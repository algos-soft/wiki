package it.algos.vaad23.backend.packages.utility.preferenza;

import it.algos.vaad23.backend.enumeration.*;
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
@Qualifier("Preferenza") //@todo Qualifier da sostituire (eventualmente) con costante da registrare su VaadCost 
//@AIScript(sovraScrivibile = true)
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
    //@todo registrare eventualmente come costante in VaadCost il valore del Qualifier
    public PreferenzaBackend(@Autowired @Qualifier("Preferenza") final MongoRepository crudRepository) {
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
        Object alfa = repository.findById(key);
        List<Preferenza> lista = repository.findByCodeStartingWithIgnoreCase(key);
        if (lista != null && lista.size() == 1) {
            return lista.get(0);
        }
        else {
            return null;
        }
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

    public List<Preferenza> findAllByType(final AETypePref type) {
        if (type != null) {
            return repository.findByType(type);
        }
        else {
            return repository.findAll();
        }
    }

    public List<Preferenza> findAllByCodeAndType(final String code, final AETypePref type) {
        if (type == null) {
            return repository.findByCodeStartingWithIgnoreCase(code);
        }
        else {
            if (textService.isValid(code)) {
                return repository.findByCodeStartingWithIgnoreCaseAndType(code, type);
            }
            else {
                return repository.findByType(type);
            }
        }
    }

}// end of crud backend class
