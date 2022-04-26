package it.algos.wiki.backend.packages.nazionalita;

import com.vaadin.flow.router.*;
import static it.algos.vaad23.backend.boot.VaadCost.*;
import it.algos.vaad23.backend.entity.*;
import it.algos.vaad23.ui.views.*;
import static it.algos.wiki.backend.boot.WikiCost.*;
import it.algos.wiki.backend.enumeration.*;
import it.algos.wiki.backend.packages.wiki.*;
import org.springframework.beans.factory.annotation.*;

import java.time.*;
import java.util.*;

import com.vaadin.flow.component.textfield.TextField;
import org.springframework.data.domain.*;

/**
 * Project wiki
 * Created by Algos
 * User: gac
 * Date: lun, 25-apr-2022
 * Time: 18:21
 * <p>
 * Vista iniziale e principale di un package <br>
 *
 * @Route chiamata dal menu generale <br>
 * Presenta la Grid <br>
 * Su richiesta apre un Dialogo per gestire la singola entity <br>
 */
@PageTitle("Nazionalita")
@Route(value = TAG_NAZIONALITA, layout = MainLayout.class)
public class NazionalitaView extends WikiView {


    //--per eventuali metodi specifici
    private NazionalitaBackend backend;

    private TextField searchFieldPlurale;

    /**
     * Costruttore @Autowired (facoltativo) <br>
     * In the newest Spring release, it’s constructor does not need to be annotated with @Autowired annotation <br>
     * Inietta con @Autowired il service con la logica specifica e lo passa al costruttore della superclasse <br>
     * Regola la entityClazz (final nella superclasse) associata a questa @Route view e la passa alla superclasse <br>
     *
     * @param crudBackend service specifico per la businessLogic e il collegamento con la persistenza dei dati
     */
    public NazionalitaView(@Autowired final NazionalitaBackend crudBackend) {
        super(crudBackend, Nazionalita.class);
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

        super.gridPropertyNamesList = Arrays.asList("singolare", "plurale");
        super.formPropertyNamesList = Arrays.asList("singolare", "plurale");

        super.sortOrder = Sort.by(Sort.Direction.ASC, "singolare");
        super.lastDownload = WPref.lastDownloadNazionalita;
        super.durataDownload = WPref.durataDownloadNazionalita;
        super.wikiModuloTitle = PATH_MODULO_NAZIONALITA;
        super.wikiStatisticheTitle = PATH_STATISTICHE_NAZIONALITA;
    }

    /**
     * Costruisce un (eventuale) layout per informazioni aggiuntive come header della view <br>
     * Può essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    @Override
    public void fixAlert() {
        super.fixAlert();

        message = "Contiene la tabella di conversione delle attività passate via parametri 'Nazionalità/Cittadinanza/NazionalitàNaturalizzato',";
        message += " da singolare maschile e femminile (usati nell'incipit) al plurale maschile per categorizzare la pagina.";
        addSpanVerde(message);

        message = "Le nazionalità sono elencate all'interno del modulo con la seguente sintassi:";
        message += " [\"nazionalitaforma1\"]=\"nazionalità al plurale\"; [\"nazionalitaforma2\"]=\"nazionalità al plurale\".";
        addSpanVerde(message);

        message = "Indipendentemente da come sono scritte nel modulo, tutte le attività singolari e plurali sono convertite in minuscolo.";
        addSpanRosso(message);
    }

    protected void fixBottoniTopSpecifici() {
        searchFieldPlurale = new TextField();
        searchFieldPlurale.setPlaceholder("Filter by plurale");
        searchFieldPlurale.setClearButtonVisible(true);
        searchFieldPlurale.addValueChangeListener(event -> sincroFiltri());
        topPlaceHolder.add(searchFieldPlurale);
    }

    /**
     * Può essere sovrascritto, SENZA invocare il metodo della superclasse <br>
     */
    protected void sincroFiltri() {
        List<Nazionalita> items = backend.findAll(sortOrder);

        final String textSearch = searchField != null ? searchField.getValue() : VUOTA;
        if (textService.isValid(textSearch)) {
            items = items.stream().filter(naz -> naz.singolare.matches("^(?i)" + textSearch + ".*$")).toList();
        }

        final String textSearchPlurale = searchFieldPlurale != null ? searchFieldPlurale.getValue() : VUOTA;
        if (textService.isValid(textSearch)) {
            items = items.stream().filter(naz -> naz.plurale.matches("^(?i)" + textSearchPlurale + ".*$")).toList();
        }

        if (items != null) {
            grid.setItems((List) items);
            elementiFiltrati = items.size();
            sicroBottomLayout();
        }
    }

    /**
     * Esegue un azione di apertura di una pagina su wiki, specifica del programma/package in corso <br>
     * Deve essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    @Override
    protected AEntity wikiPage() {
        Nazionalita attivita = (Nazionalita) super.wikiPage();

        String path = PATH_NAZIONALITA + SLASH;
        String attivitaText = textService.primaMaiuscola(attivita.plurale);
        wikiApiService.openWikiPage(path + attivitaText);

        return null;
    }

}// end of crud @Route view class