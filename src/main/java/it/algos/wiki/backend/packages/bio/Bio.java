package it.algos.wiki.backend.packages.bio;

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

import javax.persistence.*;
import javax.validation.constraints.*;
import javax.validation.constraints.Size;

import com.vaadin.flow.spring.annotation.SpringComponent;
import org.springframework.context.annotation.Scope;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import com.vaadin.flow.component.textfield.TextField;

import java.time.*;

/**
 * Project wiki
 * Created by Algos
 * User: gac
 * Date: gio, 28-apr-2022
 * Time: 11:57
 * <p>
 * Estende la entity astratta AEntity che contiene la key property ObjectId <br>
 */
//Lombok
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder()
@EqualsAndHashCode(callSuper = false)
public class Bio extends AEntity {

    @AIField(type = AETypeField.lungo,  enabled = false)
    public long pageId;

    @NotBlank()
    @Indexed(unique = true, direction = IndexDirection.DESCENDING)
    @AIField(type = AETypeField.text )
    public String wikiTitle;

    @Lob
    @AIField(type = AETypeField.textArea, required = true,  widthEM = 48)
    public String tmplBio;


    @AIField(type = AETypeField.localDateTime )
    public LocalDateTime lastServer;

    @AIField(type = AETypeField.localDateTime )
    public LocalDateTime lastMongo;

    /**
     * valido se lastLettura >= lastModifica
     */
    @AIField(type = AETypeField.booleano)
    public boolean valido;

    @AIField(type = AETypeField.text )
    public String nome;

    @AIField(type = AETypeField.text )
    public String cognome;

    @AIField(type = AETypeField.text )
    public String sesso;

    @AIField(type = AETypeField.text )
    public String giornoNato;

    @AIField(type = AETypeField.text )
    public String annoNato;

    @AIField(type = AETypeField.text )
    public String luogoNato;

    @AIField(type = AETypeField.text )
    public String luogoNatoLink;

    @AIField(type = AETypeField.text )
    public String giornoMorto;

    @AIField(type = AETypeField.text )
    public String annoMorto;

    @AIField(type = AETypeField.text )
    public String luogoMorto;

    @AIField(type = AETypeField.text )
    public String luogoMortoLink;

    @AIField(type = AETypeField.text )
    public String attivita;

    @AIField(type = AETypeField.text )
    public String attivita2;

    @AIField(type = AETypeField.text )
    public String attivita3;

    @AIField(type = AETypeField.text )
    public String nazionalita;



    @Override
    public String toString() {
        return wikiTitle;
    }

}// end of crud entity class