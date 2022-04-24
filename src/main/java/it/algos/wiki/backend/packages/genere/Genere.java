package it.algos.wiki.backend.packages.genere;

import static it.algos.vaad23.backend.boot.VaadCost.*;
import com.querydsl.core.annotations.*;
import com.vaadin.flow.component.icon.*;
import com.vaadin.flow.spring.annotation.SpringComponent;
import it.algos.vaad23.backend.annotation.*;
import it.algos.vaad23.backend.entity.*;
import it.algos.vaad23.backend.enumeration.*;
import lombok.*;
import org.springframework.data.annotation.*;
import org.springframework.data.mongodb.core.index.*;
import org.springframework.data.mongodb.core.mapping.*;

import javax.validation.constraints.*;
import javax.validation.constraints.Size;

import com.vaadin.flow.spring.annotation.SpringComponent;
import org.springframework.context.annotation.Scope;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import com.vaadin.flow.component.textfield.TextField;

/**
 * Project wiki
 * Created by Algos
 * User: gac
 * Date: dom, 24-apr-2022
 * Time: 10:17
 * <p>
 * Estende la entity astratta AEntity che contiene la key property ObjectId <br>
 */
//Lombok
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder()
@EqualsAndHashCode(callSuper = false)
public class Genere extends AEntity {

    private static final transient int WIDTHEM = 20;

    @AIField(type = AETypeField.text, widthEM = WIDTHEM, search = true)
    public String singolare;

    @AIField(type = AETypeField.text, widthEM = WIDTHEM)
    public String pluraleMaschile;

    @AIField(type = AETypeField.text, widthEM = WIDTHEM)
    public String pluraleFemminile;

    @AIField(type = AETypeField.booleano, headerIcon = VaadinIcon.MALE)
    public boolean maschile;

    @Override
    public String toString() {
        return singolare;
    }

}// end of crud entity class