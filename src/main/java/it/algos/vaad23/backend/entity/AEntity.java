package it.algos.vaad23.backend.entity;


import com.vaadin.flow.component.template.Id;

import javax.persistence.*;
import java.io.*;
import java.time.*;

/**
 * Project vaadflow15
 * Created by Algos
 * User: gac
 * Date: ven, 01-set-2017
 * Time: 18:30
 * <p>
 * Classe astratta generica per regolare alcune property comuni a tutte le Entity Class <br>
 * Le sottoclassi concrete sono di tipo JavaBean <br>
 * <p>
 * Annotated with @Getter (obbligatorio, Lombok) per rendere visibili le properties ai @Builder delle sottoclassi <br>
 * La classe NON può usare la Annotation @Setter (contrariamente alle altre classi di Entity), <br>
 * perché 'oscurerebbe' la gestione automatica della key property ObjectId da parte di mongo <br>
 * Le property sono tutte pubbliche per essere accessibili visto che mancano i 'setters' <br>
 * La gestione delle property 'dataCreazione' e 'dataModifica' è automatica in AService.save() <br>
 * <p>
 * SOTTOCLASSI:
 * Not annotated with @SpringComponent (inutile).  <br>
 * Not annotated with @Scope (inutile). Le istanze 'prototype' vengono generate da xxxService.newEntity() <br>
 * Not annotated with @Qualifier (inutile) <br>
 * Annotated with @QueryEntity (facoltativo MongoDB) per specificare che si tratta di una collection (DB Mongo) <br>
 * Annotated with @Document (facoltativo MongoDB) per avere un nome della collection diverso dal nome della Entity <br>
 * Annotated with @TypeAlias (facoltativo MongoDB) to replace the fully qualified class name with a different value <br>
 * Annotated with @Data (facoltativo Lombok) for automatic use of Getter and Setter <br>
 * Annotated with @NoArgsConstructor (facoltativo Lombok) for JavaBean specifications <br>
 * Annotated with @AllArgsConstructor (facoltativo Lombok) per usare il costruttore completo nel Service <br>
 * Annotated with @Builder (facoltativo Lombok) per usare un costruttore specifico <br>
 * - lets you automatically produce the code required to have your class be instantiable with code such as:
 * - Person.builder().name("Adam Savage").city("San Francisco").build(); <br>
 * Annotated with @EqualsAndHashCode (facoltativo Lombok) per l'uguaglianza di due istanze della classe <br>
 * Annotated with @AIScript (facoltativo Algos) per controllare la ri-creazione di questo file dal Wizard <br>
 * Annotated with @AIEntity (facoltativo Algos) per alcuni parametri generali del modulo <br>
 * Annotated with @AIView (facoltativo Algos) per alcuni parametri generali delle Views <br>
 * Annotated with @AIList (facoltativo Algos) per le colonne automatiche della Grid nella lista <br>
 * Annotated with @AIForm (facoltativo Algos) per i fields automatici nel dialogo del Form <br>
 * In ogni caso la ri-creazione del file header avviene sempre FINO alla Annotation @AIScript <br>
 * <p>
 * SOTTOCLASSI:
 * Inserire sempre la versione di serializzazione <br>
 * Deve avere un costruttore senza argomenti (fornito in automatico da Lombok) <br>
 * Non deve contenere nessun metodo per la gestione degli eventi <br>
 * Le singole properties devono essere pubbliche per poter usare la @Reflection <br>
 * Le singole properties sono accessibili con get, set e is (creati in automatico da Lombok) <br>
 * Le singole properties sono annotate con @Field("xxx") (facoltativo MongoDB) per collections molto numerose <br>
 * Le singole properties sono annotate con @AIField (obbligatorio Algos) per il tipo di fields nel dialogo del Form <br>
 * Le singole properties sono annotate con @AIColumn (facoltativo Algos) per il tipo di Column nella Grid <br>
 * Le singole properties possono avere @AIColumn(flexGrow = true) per una larghezza flessibile nella Grid <br>
 * -which gives a name to the key to be used to store the field inside the document <br>
 * -The property name (i.e. 'descrizione') would be used as the field key if this annotation was not included <br>
 * -Remember that field keys are repeated for every document so using a smaller key name will reduce the required space <br>
 * Le property non primitive, di default sono EMBEDDED con un riferimento statico
 * (EAFieldType.link e XxxPresenter.class)
 * Le singole property possono essere annotate con @DBRef per un riferimento DINAMICO (not embedded)
 * (EAFieldType.combo e XXService.class, con inserimento automatico nel ViewDialog)
 */
//@Setter @todo rimettere
//@Getter @todo rimettere
public abstract class AEntity implements Serializable {


    /**
     * key property ObjectId <br>
     * di default gestita direttamente da mongo
     * può essere usata direttamente per identificare la entity con key 'leggibili' <br>
     * NON va usato @NotEmpty, perché altrimenti binder.validate().isOk() va in errore <br>
     * Ci pensa mongo a riempire il valore
     */
    //    @AIField(caption = "Key", required = true)  @todo rimettere
    //    @AIColumn(header = "Key", widthEM = 12) @todo rimettere
    @Id
    @GeneratedValue
    public String id;


    /**
     * Eventuali note (facoltativo) <br>
     */
    //    @AIField(type = AETypeField.textArea, widthEM = 24) @todo rimettere
    //    @AIColumn()  @todo rimettere
    public String note;


    /**
     * Data di creazione del nuovo record (facoltativa, non modificabile) <br>
     * Gestita in automatico. <br>
     * Utilizzo obbligatorio o facoltativo. <br>
     * Regolato uguale per tutta l' applicazione col flag KEY_USE_PROPERTY_CREAZIONE_AND_MODIFICA <br>
     * Field visibile solo al developer <br>
     */
    //    @AIField(type = AETypeField.localDateTime, caption = "Creazione della entity")  @todo rimettere
    //    @AIColumn() @todo rimettere
    public LocalDateTime creazione;


    /**
     * Data dell' ultimo edit del record (facoltativa, modificabile solo da codice, non da UI) <br>
     * Utilizzo obbligatorio o facoltativo. <br>
     * Regolato uguale per tutta l' applicazione col flag KEY_USE_PROPERTY_CREAZIONE_AND_MODIFICA <br>
     * Field visibile solo al developer <br>
     */
    //    @AIField(type = AETypeField.localDateTime, caption = "Ultima modifica della entity")  @todo rimettere
    //    @AIColumn() @todo rimettere
    public LocalDateTime modifica;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}


