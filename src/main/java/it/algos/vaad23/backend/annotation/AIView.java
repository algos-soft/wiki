package it.algos.vaad23.backend.annotation;

import com.vaadin.flow.component.icon.*;
import static it.algos.vaad23.backend.boot.VaadCost.*;

import java.lang.annotation.*;

/**
 * Project vaadin23
 * Created by Algos
 * User: gac
 * Date: dom, 06-mar-2022
 * Time: 18:13
 * <p>
 * Annotation Algos per le Entity Class <br>
 * Controlla aspetti del menu, visivi e della @Route <br>
 * <p>
 * Regola:
 * menuName()=VUOTA -> (Optional) Label del menu
 * menuIcon()=VaadinIcon.ASTERISK -> (Optional) Icona visibile nel menu
 * searchProperty()=VUOTA -> (Mandatory) Property per la ricerca tramite il searchField
 * sortProperty()=VUOTA -> (Mandatory) Property per l'ordinamento della lista
 * sortDirection()="ASC" -> (Optional) Direzione per l'ordinamento della lista
 * <p>
 * Standard:
 * AIView(menuName = "Xxx", menuIcon = VaadinIcon.ASTERISK, searchProperty = "code", sortProperty = "code")
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
//--Class, interface (including annotation type), or enum declaration
public @interface AIView {

    /**
     * (Optional) Label del menu <br>
     * Vaadin usa SEMPRE il 'name' della Annotation @Route per identificare (internamente) e recuperare la view <br>
     * Nella menuBar appare invece visibile (con il primo carattere maiuscolo) il menuName, indicato qui <br>
     * Di default usa il 'name' della view (@Route) <br>
     *
     * @return the string
     */
    String menuName() default VUOTA;


    /**
     * (Optional) Icona visibile nel menu <br>
     * Di default un asterisco <br>
     *
     * @return the vaadin icon
     */
    VaadinIcon menuIcon() default VaadinIcon.ASTERISK;


    /**
     * (Optional) Nome dell'icona visibile nel menu <br>
     * Di default un asterisco <br>
     *
     * @return the vaadin icon
     */
    String lineawesomeClassnames() default "asterisk";


    /**
     * (Mandatory) Property per la ricerca tramite il searchField
     *
     * @return the string
     */
    String searchProperty() default VUOTA;


    /**
     * (Mandatory) Property per l'ordinamento della lista
     *
     * @return the string
     */
    String sortProperty() default VUOTA;


    /**
     * (Optional) Direzione per l'ordinamento
     *
     * @return the string
     */
    String sortDirection() default SORT_SPRING_ASC;

}// end of interface

