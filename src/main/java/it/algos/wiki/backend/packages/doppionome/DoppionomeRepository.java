package it.algos.wiki.backend.packages.doppionome;

import it.algos.vaad23.backend.entity.*;
import static it.algos.wiki.backend.boot.WikiCost.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.data.mongodb.repository.*;
import org.springframework.stereotype.*;

import java.util.*;

/**
 * Project wiki
 * Created by Algos
 * User: gac
 * Date: mar, 26-apr-2022
 * Time: 19:34
 * <p>
 * Estende l'interfaccia MongoRepository col casting alla Entity relativa di questa repository <br>
 * <p>
 * Annotated with @Repository (obbligatorio) <br>
 * Annotated with @Qualifier (obbligatorio) per permettere a Spring di istanziare la classe specifica <br>
 * Eventualmente usare una costante di VaadCost come @Qualifier sia qui che nella corrispondente classe xxxBackend <br>
 */
@Repository
@Qualifier(TAG_DOPPIO_NOME)
public interface DoppionomeRepository extends MongoRepository<Doppionome, String> {

    @Override
    List<Doppionome> findAll();

    <Doppionome extends AEntity> Doppionome insert(Doppionome entity);

    <Doppionome extends AEntity> Doppionome save(Doppionome entity);

    @Override
    void delete(Doppionome entity);

    Doppionome findFirstByNome(String nome);

}// end of crud repository class