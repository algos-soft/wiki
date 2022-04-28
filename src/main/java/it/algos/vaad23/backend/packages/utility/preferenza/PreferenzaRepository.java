package it.algos.vaad23.backend.packages.utility.preferenza;

import com.vaadin.flow.spring.annotation.*;
import static it.algos.vaad23.backend.boot.VaadCost.*;
import it.algos.vaad23.backend.entity.*;
import it.algos.vaad23.backend.enumeration.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.beans.factory.config.*;
import org.springframework.context.annotation.Scope;
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
 * Estende la l'interfaccia MongoRepository col casting alla Entity relativa di questa repository <br>
 * <br>
 * Annotated with @SpringComponent (obbligatorio) <br>
 * Annotated with @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) (obbligatorio) <br>
 * Annotated with @Qualifier (obbligatorio) per permettere a Spring di istanziare la classe specifica <br>
 */
@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
@Qualifier(TAG_PRE)
@Repository
public interface PreferenzaRepository extends MongoRepository<Preferenza, String> {

    List<Preferenza> findAll();

    <Preferenza extends AEntity> Preferenza insert(Preferenza entity);

    <Preferenza extends AEntity> Preferenza save(Preferenza entity);

    void delete(Preferenza entity);

    List<Preferenza> findAllByCode(String code);

//    List<Preferenza> findByCodeStartingWithIgnoreCase(String code);

    List<Preferenza> findByType(AETypePref type);

    List<Preferenza> findByCodeStartingWithIgnoreCaseAndType(String code, AETypePref type);

    Preferenza findFirstByCode(String code);

}// end of crud repository class