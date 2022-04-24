package it.algos.vaad23.backend.packages.geografia.continente;

import it.algos.vaad23.backend.entity.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.data.mongodb.repository.*;
import org.springframework.stereotype.*;

import java.util.*;

/**
 * Project vaadin23
 * Created by Algos
 * User: gac
 * Date: dom, 03-apr-2022
 * Time: 08:39
 * <p>
 * Estende l'interfaccia MongoRepository col casting alla Entity relativa di questa repository <br>
 * <p>
 * Annotated with @Repository (obbligatorio) <br>
 * Annotated with @Qualifier (obbligatorio) per permettere a Spring di istanziare la classe specifica <br>
 * Eventualmente usare una costante di VaadCost come @Qualifier sia qui che nella corrispondente classe xxxBackend <br>
 */
@Repository
@Qualifier("Continente")
public interface ContinenteRepository extends MongoRepository<Continente, String> {

    @Override
    List<Continente> findAll();

    <Continente extends AEntity> Continente insert(Continente entity);

    <Continente extends AEntity> Continente save(Continente entity);

    @Override
    void delete(Continente entity);

}// end of crud repository class