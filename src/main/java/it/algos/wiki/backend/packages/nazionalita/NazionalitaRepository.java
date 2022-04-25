package it.algos.wiki.backend.packages.nazionalita;

import com.vaadin.flow.spring.annotation.*;
import it.algos.vaad23.backend.entity.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.beans.factory.config.*;
import org.springframework.context.annotation.Scope;
import org.springframework.data.mongodb.repository.*;
import org.springframework.stereotype.*;

import java.util.*;

import com.vaadin.flow.spring.annotation.SpringComponent;
import org.springframework.context.annotation.Scope;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import com.vaadin.flow.component.textfield.TextField;

/**
 * Project wiki
 * Created by Algos
 * User: gac
 * Date: lun, 25-apr-2022
 * Time: 18:21
 * <p>
 * Estende l'interfaccia MongoRepository col casting alla Entity relativa di questa repository <br>
 * <p>
 * Annotated with @Repository (obbligatorio) <br>
 * Annotated with @Qualifier (obbligatorio) per permettere a Spring di istanziare la classe specifica <br>
 * Eventualmente usare una costante di VaadCost come @Qualifier sia qui che nella corrispondente classe xxxBackend <br>
 */
@Repository
@Qualifier("Nazionalita")
public interface NazionalitaRepository extends MongoRepository<Nazionalita, String> {

    @Override
    List<Nazionalita> findAll();

    <Nazionalita extends AEntity> Nazionalita insert(Nazionalita entity);

    <Nazionalita extends AEntity> Nazionalita save(Nazionalita entity);

    @Override
    void delete(Nazionalita entity);

    Nazionalita findFirstBySingolare(String singolare);

}// end of crud repository class