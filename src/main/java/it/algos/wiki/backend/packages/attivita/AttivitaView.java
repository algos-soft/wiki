package it.algos.wiki.backend.packages.attivita;

import ch.carnet.kasparscherrer.*;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.*;
import com.vaadin.flow.component.textfield.*;
import com.vaadin.flow.router.*;
import it.algos.vaad23.backend.boot.*;
import static it.algos.vaad23.backend.boot.VaadCost.*;
import it.algos.vaad23.backend.entity.*;
import it.algos.vaad23.backend.enumeration.*;
import it.algos.vaad23.ui.views.*;
import static it.algos.wiki.backend.boot.WikiCost.*;
import it.algos.wiki.backend.enumeration.*;
import it.algos.wiki.backend.packages.wiki.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.data.domain.*;

import java.util.*;

/**
 * Project vaadwiki
 * Created by Algos
 * User: gac
 * Date: lun, 18-apr-2022
 * Time: 21:25
 * <p>
 * Vista iniziale e principale di un package <br>
 *
 * @Route chiamata dal menu generale <br>
 * Presenta la Grid <br>
 * Su richiesta apre un Dialogo per gestire la singola entity <br>
 */
@PageTitle("Attivita")
@Route(value = TAG_ATTIVITA, layout = MainLayout.class)
public class AttivitaView extends WikiView {


    //--per eventuali metodi specifici
    private AttivitaBackend backend;

    private TextField searchFieldPlurale;

    protected IndeterminateCheckbox boxBoxPagina;

    /**
     * Costruttore @Autowired (facoltativo) <br>
     * In the newest Spring release, it’s constructor does not need to be annotated with @Autowired annotation <br>
     * Inietta con @Autowired il service con la logica specifica e lo passa al costruttore della superclasse <br>
     * Regola la entityClazz (final nella superclasse) associata a questa @Route view e la passa alla superclasse <br>
     *
     * @param crudBackend service specifico per la businessLogic e il collegamento con la persistenza dei dati
     */
    public AttivitaView(@Autowired final AttivitaBackend crudBackend) {
        super(crudBackend, Attivita.class);
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

        super.gridPropertyNamesList = Arrays.asList("singolare", "plurale", "aggiunta", "bio", "pagina");

        super.sortOrder = Sort.by(Sort.Direction.ASC, "singolare");
        super.lastDownload = WPref.downloadAttivita;
        super.lastElaborazione = WPref.elaboraAttivita;
        super.durataElaborazione = WPref.elaboraAttivitaTime;
        super.lastUpload = WPref.uploadAttivita;
        //        super.wikiModuloTitle = PATH_MODULO_ATTIVITA;
        //        super.wikiStatisticheTitle = PATH_STATISTICHE_ATTIVITA;
        super.usaBottoneCategoria = true;

        super.fixPreferenzeBackend();
    }

    /**
     * Costruisce un (eventuale) layout per informazioni aggiuntive come header della view <br>
     * Può essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    @Override
    public void fixAlert() {
        super.fixAlert();

        Anchor anchor = new Anchor(VaadCost.PATH_WIKI + PATH_MODULO_ATTIVITA, PATH_MODULO_ATTIVITA);
        anchor.getElement().getStyle().set(AEFontWeight.HTML, AEFontWeight.bold.getTag());
        Anchor anchor2 = new Anchor(VaadCost.PATH_WIKI + PATH_STATISTICHE_ATTIVITA, PATH_STATISTICHE_ATTIVITA);
        anchor2.getElement().getStyle().set(AEFontWeight.HTML, AEFontWeight.bold.getTag());
        alertPlaceHolder.add(new Span(anchor, new Label(SEP), anchor2));

        message = "Contiene la tabella di conversione delle attività passate via parametri 'Attività/Attività2/Attività3',";
        message += " da singolare maschile e femminile (usati nell'incipit) al plurale maschile per categorizzare la pagina.";
        addSpanVerde(message);

        message = "Le attività sono elencate all'interno del modulo con la seguente sintassi:";
        message += " [\"attivitaforma1\"]=\"attività al plurale\"; [\"attivitaforma2\"]=\"attività al plurale\".";
        addSpanVerde(message);

        message = "Indipendentemente da come sono scritte nel modulo, tutte le attività singolari e plurali sono convertite in minuscolo.";
        message += " Le voci delle ex-attività (non presenti nel modulo) vengono aggiunte prendendole dal package 'genere'";
        addSpanRosso(message);

        message = String.format("Le singole pagine di attività vengono create su wiki quando superano le %s biografie.", WPref.sogliaAttNazWiki.get());
        addSpanRossoBold(message);
    }

    protected void fixBottoniTopSpecifici() {
        searchFieldPlurale = new TextField();
        searchFieldPlurale.setPlaceholder("Filter by plurale");
        searchFieldPlurale.setClearButtonVisible(true);
        searchFieldPlurale.addValueChangeListener(event -> sincroFiltri());
        topPlaceHolder.add(searchFieldPlurale);

        boxBox = new IndeterminateCheckbox();
        boxBox.setLabel("Aggiunti da Genere");
        boxBox.setIndeterminate(true);
        boxBox.addValueChangeListener(event -> sincroFiltri());
        HorizontalLayout layout = new HorizontalLayout(boxBox);
        layout.setAlignItems(Alignment.CENTER);
        topPlaceHolder.add(layout);

        boxBoxPagina = new IndeterminateCheckbox();
        boxBoxPagina.setLabel("Pagina wiki");
        boxBoxPagina.setIndeterminate(true);
        boxBoxPagina.addValueChangeListener(event -> sincroFiltri());
        HorizontalLayout layout2 = new HorizontalLayout(boxBoxPagina);
        layout2.setAlignItems(Alignment.CENTER);
        topPlaceHolder.add(layout2);
    }

    /**
     * Può essere sovrascritto, SENZA invocare il metodo della superclasse <br>
     */
    protected void sincroFiltri() {
        List<Attivita> items = backend.findAll(sortOrder);

        final String textSearch = searchField != null ? searchField.getValue() : VUOTA;
        if (textService.isValid(textSearch)) {
            items = items.stream().filter(att -> att.singolare.matches("^(?i)" + textSearch + ".*$")).toList();
        }

        final String textSearchPlurale = searchFieldPlurale != null ? searchFieldPlurale.getValue() : VUOTA;
        if (textService.isValid(textSearch)) {
            items = items.stream().filter(att -> att.plurale.matches("^(?i)" + textSearchPlurale + ".*$")).toList();
        }

        if (boxBox != null && !boxBox.isIndeterminate()) {
            items = items.stream().filter(att -> att.aggiunta == boxBox.getValue()).toList();
        }

        if (boxBoxPagina != null && !boxBoxPagina.isIndeterminate()) {
            items = items.stream().filter(att -> att.pagina == boxBoxPagina.getValue()).toList();
            if (boxBoxPagina.getValue()) {
                sortOrder = Sort.by(Sort.Direction.ASC, "plurale");
            }
            else {
                sortOrder = Sort.by(Sort.Direction.ASC, "singolare");
            }
        }

        if (items != null) {
            grid.setItems((List) items);
            elementiFiltrati = items.size();
            sicroBottomLayout();
        }
    }

    /**
     * Esegue un azione di apertura di una categoria su wiki, specifica del programma/package in corso <br>
     * Deve essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    public AEntity wikiCategoria() {
        Attivita attivita = (Attivita) super.wikiCategoria();

        String path = "Categoria:";
        wikiApiService.openWikiPage(path + attivita.plurale);

        return null;
    }

    /**
     * Esegue un azione di apertura di una pagina su wiki, specifica del programma/package in corso <br>
     * Deve essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    @Override
    protected AEntity wikiPage() {
        Attivita attivita = (Attivita) super.wikiPage();

        String path = PATH_ATTIVITA + SLASH;
        String attivitaText = textService.primaMaiuscola(attivita.plurale);
        wikiApiService.openWikiPage(path + attivitaText);

        return null;
    }

}// end of crud @Route view class