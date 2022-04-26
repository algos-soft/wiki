package it.algos.wiki.backend.packages.doppionome;

import it.algos.vaad23.backend.annotation.*;
import it.algos.vaad23.backend.entity.*;
import it.algos.vaad23.backend.enumeration.*;
import lombok.*;
import org.springframework.data.mongodb.core.index.*;

import javax.validation.constraints.*;

/**
 * Project wiki
 * Created by Algos
 * User: gac
 * Date: mar, 26-apr-2022
 * Time: 19:34
 * <p>
 * Estende la entity astratta AEntity che contiene la key property ObjectId <br>
 */
//Lombok
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder()
@EqualsAndHashCode(callSuper = false)
public class Doppionome extends AEntity {

    private static final transient int WIDTHEM = 20;

    @NotBlank()
    @AIField(type = AETypeField.text, widthEM = WIDTHEM, search = true)
    public String nome;


    @Override
    public String toString() {
        return nome;
    }

}// end of crud entity class