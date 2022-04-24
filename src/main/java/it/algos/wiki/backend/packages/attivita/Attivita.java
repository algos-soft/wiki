package it.algos.wiki.backend.packages.attivita;

import com.vaadin.flow.component.icon.*;
import it.algos.vaad23.backend.annotation.*;
import it.algos.vaad23.backend.entity.*;
import it.algos.vaad23.backend.enumeration.*;
import lombok.*;

import javax.validation.constraints.*;

/**
 * Project vaadwiki
 * Created by Algos
 * User: gac
 * Date: lun, 18-apr-2022
 * Time: 21:25
 * <p>
 * Estende la entity astratta AEntity che contiene la key property ObjectId <br>
 */
//Lombok
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder()
@EqualsAndHashCode(callSuper = false)
public class Attivita extends AEntity {

    /**
     * larghezza delle colonne
     */
    private static final transient int WIDTHEM = 20;

    @NotBlank(message = "Il singolare è obbligatorio")
    //    @Indexed(unique = false, direction = IndexDirection.DESCENDING)
    //    @Size(min = 2, max = 50)
    @AIField(type = AETypeField.text, widthEM = WIDTHEM)
    public String singolare;


    //    @Indexed(unique = false, direction = IndexDirection.DESCENDING)
    //    @Size(min = 2, max = 50)
    @AIField(type = AETypeField.text, widthEM = WIDTHEM)
    public String plurale;

    //    /**
    //     * flag aggiunta (facoltativo, di default false) <br>
    //     */
//    @AIField(type = AETypeField.booleano, headerIcon = VaadinIcon.LEVEL_DOWN_BOLD, caption = "aggiunta (ex-attività)", usaCheckBox3Vie = true)
    @AIField(type = AETypeField.booleano, headerIcon = VaadinIcon.ADD_DOCK, caption = "aggiunta (ex-attività)", usaCheckBox3Vie = true)
    public boolean aggiunta;


    /**
     * @return a string representation of the object.
     */
    @Override
    public String toString() {
        return singolare;
    }


}// end of crud entity class