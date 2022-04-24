package it.algos.vaad23.backend.packages.geografia.continente;

import it.algos.vaad23.backend.annotation.*;
import it.algos.vaad23.backend.entity.*;
import it.algos.vaad23.backend.enumeration.*;
import lombok.*;
import org.springframework.data.mongodb.core.index.*;

import javax.validation.constraints.*;

/**
 * Project vaadin23
 * Created by Algos
 * User: gac
 * Date: dom, 03-apr-2022
 * Time: 08:39
 * <p>
 * Estende la entity astratta AEntity che contiene la key property ObjectId <br>
 */
//Lombok
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder()
@EqualsAndHashCode(callSuper = false)
public class Continente extends AREntity {

    @Indexed(unique = true, direction = IndexDirection.DESCENDING)
    @AIField(type = AETypeField.integer)
    public int ordine;

    @NotBlank()
    @Indexed(unique = true, direction = IndexDirection.DESCENDING)
    @AIField(type = AETypeField.text)
    public String nome;

    @AIField(type = AETypeField.booleano)
    public boolean abitato;

    @Override
    public String toString() {
        return nome;
    }

}// end of crud entity class