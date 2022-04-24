package it.algos.vaad23.backend.configuration;

import org.springframework.beans.factory.annotation.*;
import org.springframework.context.annotation.*;
import org.springframework.data.mongodb.core.convert.*;

import javax.annotation.*;

/**
 * Project vaadin23
 * Created by Algos
 * User: gac
 * Date: ven, 18-mar-2022
 * Time: 19:45
 */
@Configuration
public class MongoConfiguration {

    @Autowired
    private MappingMongoConverter mappingMongoConverter;

    // remove _class
    @PostConstruct
    public void setUpMongoEscapeCharacterConversion() {
        mappingMongoConverter.setTypeMapper(new DefaultMongoTypeMapper(null));
    }

}
