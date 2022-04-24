package it.algos.vaad23.backend.annotation;

import com.vaadin.flow.component.icon.*;
import static it.algos.vaad23.backend.boot.VaadCost.*;
import it.algos.vaad23.backend.enumeration.*;

import java.lang.annotation.*;

/**
 * /**
 * Project vaadflow15
 * Created by Algos
 * User: gac
 * Date: lun, 27-apr-2020
 * Time: 14:55
 * <p>
 * Annotation per i fields (property) delle Entity Class <br>
 * Annotation to add some property for a single field <br>
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD) //--Field declaration (includes enum constants)
public @interface AIField {


    /**
     * (Required) The type of the field.
     * Defaults to the text type.
     *
     * @return the ae field type
     */
    AETypeField type() default AETypeField.text;


    /**
     * (Optional) Classe della property.
     * Utilizzato nei Link.
     *
     * @return the class
     */
    Class<?> linkClazz() default Object.class;

    /**
     * (Optional) Classe della property.
     * Utilizzato nelle Enumeration.
     *
     * @return the class
     */
    Class<?> enumClazz() default Object.class;

    /**
     * (Optional) Classe della property.
     * Utilizzato nei fields calcolati (ed altro).
     *
     * @return the class
     */
    Class<?> serviceClazz() default Object.class;

    /**
     * (Optional) Classe della property.
     * Utilizzato nei fields calcolati (ed altro).
     *
     * @return the class
     */
    Class<?> logicClazz() default Object.class;

    /**
     * (Optional) Classe della property.
     * Utilizzato nei Combo.
     */
    Class<?> comboClazz() default Object.class;

    /**
     * (Optional) property
     * Utilizzato nei Link.
     * Defaults to keyPropertyName.
     * Defaults to vuota.
     *
     * @return the string
     */
    String linkProperty() default VUOTA;

    /**
     * (Optional) valori (properties) delle colonne di una grid linkata
     * Presentati in successione e separati da virgola
     * Vengono poi convertiti in una List
     * Defaults to vuota.
     *
     * @return the string
     */
    String properties() default VUOTA;


    /**
     * (Optional) valori (items) della enumeration
     * Presentati in successione e separati da virgola
     * Vengono poi convertiti in una List
     * Defaults to vuota.
     *
     * @return the string
     */
    String items() default VUOTA;


    /**
     * (Optional) The visible name of the field.
     * Defaults to the property or field name.
     *
     * @return the string
     */
    String caption() default VUOTA;

    /**
     * (Optional) The visible header of the column.
     * Defaults to the property or field name.
     *
     * @return the string
     */
    String header() default VUOTA;


    /**
     * (Optional) The width of the field.
     * Expressed in double, to be converted in String ending with "em"
     * Defaults to 0.
     *
     * @return the int
     */
    double widthEM() default 0;

    /**
     * (Optional) The height of the field.
     * Expressed in double, to be converted in String ending with "em"
     * Defaults to 0.
     *
     * @return the int
     */
    double heightEM() default 0;


    /**
     * (Optional) The number of rows of textArea field.
     * Expressed in int
     * Defaults to 3.
     *
     * @return the int
     */
    int numRowsTextArea() default 3;


    /**
     * (Optional) Status (field required for DB) of the the field.
     * Defaults to false.
     *
     * @return the boolean
     */
    boolean required() default false;


    /**
     * (Optional) field that get focus
     * Only one for form
     * Defaults to false.
     *
     * @return the boolean
     */
    boolean focus() default false;


    /**
     * (Optional) help text on rollover
     * Defaults to vuota.
     *
     * @return the string
     */
    String help() default VUOTA;


    /**
     * (Optional) Status (first letter capital) of the the field.
     * Defaults to false.
     *
     * @return the boolean
     */
    boolean firstCapital() default false;


    /**
     * (Optional) Status (all letters upper) of the the field.
     * Defaults to false.
     *
     * @return the boolean
     */
    boolean allUpper() default false;


    /**
     * (Optional) Status (all letters lower) of the the field.
     * Defaults to false.
     *
     * @return the boolean
     */
    boolean allLower() default false;


    /**
     * (Optional) Status (only number) of the the field.
     * Defaults to false.
     *
     * @return the boolean
     */
    boolean onlyNumber() default false;


    /**
     * (Optional) Status (only letters) of the the field.
     * Defaults to false.
     *
     * @return the boolean
     */
    boolean onlyLetter() default false;


    /**
     * (Optional) Status (allowed null selection in popup) of the the field.
     * Meaning sense only for EAFieldType.combo.
     * Defaults to false.
     *
     * @return the boolean
     */
    boolean nullSelectionAllowed() default false;


    /**
     * (Optional) Status (allowed new selection in popup) of the the field.
     * Meaning sense only for AETypeField.combo.
     * Defaults to false.
     *
     * @return the boolean
     */
    boolean allowCustomValue() default false;

    /**
     * (Optional) color of the component
     * Defaults to vuota.
     *
     * @return the string
     */
    String color() default VUOTA;

    /**
     * (Optional) Usa un metodo specifico per calcolare gli items di un comboBox.
     * Meaning sense only for AETypeField.combo.
     * Defaults to false.
     *
     * @return the boolean
     */
    boolean usaComboMethod() default false;

    /**
     * (Optional) method name for reflection
     * Defaults to findItems.
     *
     * @return the string
     */
    String methodName() default "findItems";


    /**
     * (Optional) Usa un comboBox di selezione come bottone nel header della Grid.
     * Meaning sense only for AETypeField.combo.
     * Defaults to false.
     *
     * @return the boolean
     */
    boolean usaComboBox() default false;


    /**
     * (Optional) Usa un checkBox come filtro nel header della Grid.
     * Meaning sense only for AETypeField.booleano and typeBool.
     * Defaults to false.
     *
     * @return the boolean
     */
    boolean usaCheckBox() default false;

    /**
     * (Optional) Usa un checkBox a 3 vie come filtro nel header della Grid.
     * Meaning sense only for AETypeField.booleano and typeBool.
     * Defaults to false.
     *
     * @return the boolean
     */
    boolean usaCheckBox3Vie() default false;

    /**
     * (Optional) placeholder for empty field
     * Defaults to vuota.
     *
     * @return the string
     */
    String placeholder() default VUOTA;

    /**
     * (Optional) The type of the number range.
     * Defaults to the positiviOnly type.
     *
     * @return the field type
     */
    //    AETypeNum typeNum() default AETypeNum.positiviOnly;

    /**
     * (Optional) The type of the boolean type.
     * Defaults to the checkBox type.
     *
     * @return the field type
     */
    //    AETypeBoolField typeBool() default AETypeBoolField.checkBox;


    /**
     * (Optional) The two strings for boolean type.
     * Defaults to vuota.
     *
     * @return the strings
     */
    String boolEnum() default VUOTA;

    /**
     * The type of the data type
     * Defaults to standard type.
     *
     * @return the type
     */
    //    AETypeData typeData() default AETypeData.standard;

    /**
     * Valore iniziale (text) di un combo
     * Defaults to VUOTA.
     *
     * @return the strings
     */
    String comboInitialValue() default VUOTA;

    /**
     * (Optional) Flag per la modificabilità del field
     * Defaults to true.
     *
     * @return the boolean
     */
    boolean enabled() default true;

    /**
     * (Optional) Flag per la colonna espandibile
     * Defaults to false.
     *
     * @return the boolean
     */
    boolean flexGrow() default false;

    /**
     * (Optional) header icon
     * Defaults to false.
     *
     * @return the vaadin icon
     */
    VaadinIcon headerIcon() default VaadinIcon.YOUTUBE;

    /**
     * (Optional) header icon size
     * Defaults to 20.
     *
     * @return the int
     */
    int headerIconSizePX() default 20;

    /**
     * (Optional) header icon color
     * Defaults to blue.
     *
     * @return the string
     */
    String headerIconColor() default COLOR_BLUE;

    /**
     * (Optional) Flag per il campo ricerca testuale
     * Defaults to false.
     *
     * @return the boolean
     */
    boolean search() default false;

}
