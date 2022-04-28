package it.algos.wiki.backend.packages.bio;

import com.vaadin.flow.spring.annotation.*;
import it.algos.vaad23.backend.entity.*;
import static it.algos.wiki.backend.boot.WikiCost.*;
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
 * Date: gio, 28-apr-2022
 * Time: 11:57
 * <p>
 * Estende l'interfaccia MongoRepository col casting alla Entity relativa di questa repository <br>
 * <p>
 * Annotated with @Repository (obbligatorio) <br>
 * Annotated with @Qualifier (obbligatorio) per permettere a Spring di istanziare la classe specifica <br>
 * Eventualmente usare una costante di VaadCost come @Qualifier sia qui che nella corrispondente classe xxxBackend <br>
 */
@Repository
@Qualifier(TAG_BIO)
public interface BioRepository extends MongoRepository<Bio, String> {

    @Override
    List<Bio> findAll();

    <Bio extends AEntity> Bio insert(Bio entity);

    <Bio extends AEntity> Bio save(Bio entity);

    @Override
    void delete(Bio entity);

}// end of crud repository class