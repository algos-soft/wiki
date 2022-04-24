package it.algos.vaad23.backend.packages.utility.nota;

import com.vaadin.flow.component.icon.*;
import it.algos.vaad23.backend.annotation.*;
import it.algos.vaad23.backend.entity.*;
import it.algos.vaad23.backend.enumeration.*;
import lombok.*;

import javax.validation.constraints.*;
import java.time.*;
import java.time.format.*;

/**
 * Project vaadin23
 * Created by Algos
 * User: gac
 * Date: ven, 18-mar-2022
 * Time: 06:55
 * <p>
 * Estende la entity astratta AEntity che contiene la key property ObjectId <br>
 */
//Lombok
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder()
@EqualsAndHashCode(callSuper = false)
public class Nota extends AEntity {

    @AIField(type = AETypeField.enumeration, enumClazz = AETypeLog.class)
    public AETypeLog type;

    @AIField(type = AETypeField.enumeration, enumClazz = AENotaLevel.class)
    public AENotaLevel livello;

    @AIField(type = AETypeField.localDate)
    public LocalDate inizio;

    @NotEmpty
    @AIField(type = AETypeField.text, flexGrow = true, focus = true, search = true)
    public String descrizione;

    @AIField(type = AETypeField.booleano, headerIcon = VaadinIcon.CHECK)
    public boolean fatto;

    @AIField(type = AETypeField.localDate)
    public LocalDate fine;

    @Override
    public String toString() {
        return DateTimeFormatter.ofPattern("d-MMM-yy").format(inizio);
    }

}// end of crud entity class