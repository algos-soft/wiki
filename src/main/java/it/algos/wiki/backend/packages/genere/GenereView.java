package it.algos.wiki.backend.packages.genere;

import com.vaadin.flow.router.*;
import it.algos.vaad23.ui.views.*;
import static it.algos.wiki.backend.boot.WikiCost.*;
import it.algos.wiki.backend.enumeration.*;
import it.algos.wiki.backend.packages.wiki.*;
import org.springframework.beans.factory.annotation.*;

import java.time.*;
import java.util.*;

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
 * Vista iniziale e principale di un package <br>
 *
 * @Route chiamata dal menu generale <br>
 * Presenta la Grid <br>
 * Su richiesta apre un Dialogo per gestire la singola entity <br>
 */
@PageTitle("Genere")
@Route(value = "genere", layout = MainLayout.class)
public class GenereView extends WikiView {


    //--per eventuali metodi specifici
    private GenereBackend backend;

    //--per eventuali metodi specifici
    private GenereDialog dialog;

    /**
     * Costruttore @Autowired (facoltativo) <br>
     * In the newest Spring release, it’s constructor does not need to be annotated with @Autowired annotation <br>
     * Inietta con @Autowired il service con la logica specifica e lo passa al costruttore della superclasse <br>
     * Regola la entityClazz (final nella superclasse) associata a questa @Route view e la passa alla superclasse <br>
     *
     * @param crudBackend service specifico per la businessLogic e il collegamento con la persistenza dei dati
     */
    public GenereView(@Autowired final GenereBackend crudBackend) {
        super(crudBackend, Genere.class);
        this.backend = crudBackend;
    }

    /**
     * Preferenze usate da questa 'view' <br>
     * Primo metodo chiamato dopo init() (implicito del costruttore) e postConstruct() (facoltativo) <br>
     * Puo essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    @Override
    protected void fixPreferenze() {
        super.fixPreferenze();

        super.gridPropertyNamesList = Arrays.asList("singolare", "pluraleMaschile", "pluraleFemminile","maschile");
        super.formPropertyNamesList = Arrays.asList("singolare", "pluraleMaschile", "pluraleFemminile");

        super.dialogClazz = GenereDialog.class;
    }

    /**
     * Costruisce un (eventuale) layout per informazioni aggiuntive come header della view <br>
     * Può essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    @Override
    public void fixAlert() {
        super.fixAlert();

        String message;

        String data = dateService.get((LocalDateTime) WPref.lastDownloadGenere.get());
        int durata = WPref.durataDownloadGenere.getInt();
        message = String.format("Ultimo download effettuato il %s in %d secondi", data, durata);
        addSpanBlue(message);

        message = "Contiene la tabella di conversione delle attività passate via parametri 'Attività/Attività2/Attività3'.";
        addSpanVerde(message);
        message = "Da singolare maschile e femminile (usati nell'incipit) al plurale maschile e femminile.";
        addSpanVerde(message);
        message = "Per le intestazioni dei paragrafi nelle liste di antroponimi previste nel Progetto:Antroponimi.";
        addSpanVerde(message);
        message = "Le attività sono elencate all'interno del modulo con la seguente sintassi:";
        addSpanVerde(message);
        message = "[\"attivita singolare maschile\"] = \"attività plurale maschile\"";
        addSpanVerde(message);
        message = "[\"attivita singolare femminile\"] = \"attività plurale femminile\"";
        addSpanVerde(message);
        message = String.format("Le voci delle ex-attività vengono aggiunte alla collection Attività");
        addSpanRosso(message);
        message = "Indipendentemente da come sono scritte nel modulo, tutte le attività singolari e plurali sono convertite in minuscolo";
        addSpanRosso(message);
    }

    /**
     * Può essere sovrascritto, SENZA invocare il metodo della superclasse <br>
     */
    protected List sincroFiltri() {
        List items = null;

        if (usaBottoneSearch && searchField != null) {
            items = backend.findBySingolare(searchField.getValue());
        }

        if (items != null) {
            grid.setItems(items);
        }

        return items;
    }

}// end of crud @Route view class