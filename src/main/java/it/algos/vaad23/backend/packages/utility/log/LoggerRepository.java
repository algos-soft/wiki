package it.algos.vaad23.backend.packages.utility.log;

import it.algos.vaad23.backend.enumeration.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.data.mongodb.repository.*;
import org.springframework.stereotype.*;

import java.util.*;

/**
 * Project vaadin23
 * Created by Algos
 * User: gac
 * Date: mer, 16-mar-2022
 * Time: 19:47
 * <p>
 * Estende la l'interfaccia MongoRepository col casting alla Entity relativa di questa repository <br>
 * <br>
 * Annotated with @SpringComponent (obbligatorio) <br>
 * Annotated with @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) (obbligatorio) <br>
 * Annotated with @Qualifier (obbligatorio) per permettere a Spring di istanziare la classe specifica <br>
 */
@Repository
@Qualifier("Logger")
public interface LoggerRepository extends MongoRepository<Logger, String> {

    @Override
    List<Logger> findAll();

    @Override
    Logger insert(Logger entity);

    @Override
    Logger save(Logger entity);

    @Override
    void delete(Logger entity);

    List<Logger> findByDescrizioneStartingWithIgnoreCaseAndLivelloOrderByEventoDesc(String descrizione, AELogLevel level);

    List<Logger> findByDescrizioneStartingWithIgnoreCaseAndTypeOrderByEventoDesc(String descrizione, AETypeLog type);

    List<Logger> findByDescrizioneStartingWithIgnoreCaseAndLivelloAndTypeOrderByEventoDesc(String descrizione, AELogLevel level, AETypeLog type);

    List<Logger> findByDescrizioneStartingWithIgnoreCaseOrderByEventoDesc(String descrizione);

}// end of crud repository class