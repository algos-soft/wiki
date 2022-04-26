package it.algos.wiki.backend.packages.professione;

import ch.carnet.kasparscherrer.*;
import com.vaadin.flow.component.orderedlayout.*;
import com.vaadin.flow.router.*;
import static it.algos.vaad23.backend.boot.VaadCost.*;
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
import org.springframework.data.domain.*;

/**
 * Project wiki
 * Created by Algos
 * User: gac
 * Date: mar, 26-apr-2022
 * Time: 07:19
 * <p>
 * Vista iniziale e principale di un package <br>
 *
 * @Route chiamata dal menu generale <br>
 * Presenta la Grid <br>
 * Su richiesta apre un Dialogo per gestire la singola entity <br>
 */
@PageTitle("Professione")
@Route(value = TAG_PROFESSIONE, layout = MainLayout.class)
public class ProfessioneView extends WikiView {


    //--per eventuali metodi specifici
    private ProfessioneBackend backend;

    private TextField searchFieldPagina;

    /**
     * Costruttore @Autowired (facoltativo) <br>
     * In the newest Spring release, it’s constructor does not need to be annotated with @Autowired annotation <br>
     * Inietta con @Autowired il service con la logica specifica e lo passa al costruttore della superclasse <br>
     * Regola la entityClazz (final nella superclasse) associata a questa @Route view e la passa alla superclasse <br>
     *
     * @param crudBackend service specifico per la businessLogic e il collegamento con la persistenza dei dati
     */
    public ProfessioneView(@Autowired final ProfessioneBackend crudBackend) {
        super(crudBackend, Professione.class);
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

        super.gridPropertyNamesList = Arrays.asList("attivita", "pagina", "aggiunta");

        super.sortOrder = Sort.by(Sort.Direction.ASC, "attivita");
        super.lastDownload = WPref.lastDownloadProfessione;
        super.durataDownload = WPref.durataDownloadProfessione;
        super.wikiModuloTitle = PATH_MODULO_PROFESSIONE;

        super.usaBottoneUpload = false;
        super.usaBottoneStatistiche = false;
        super.usaBottoneUploadStatistiche = false;
        super.usaBottonePaginaWiki = false;
        super.usaBottoneTest = false;
        super.usaBottoneUploadPagina = false;
    }

    /**
     * Costruisce un (eventuale) layout per informazioni aggiuntive come header della view <br>
     * Può essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    @Override
    public void fixAlert() {
        super.fixAlert();

        String uno = "Forma1";
        String due = "Forma2";
        parametri = "Attività/Attività2/Attività3";

        message = String.format("Contiene la tabella di conversione delle attività passate via parametri %s dal nome dell'attività a " +
                "quello della pagina corrispondente, per creare dei piped wikiLink.",parametri);
        addSpanVerde(message);

        message = String.format("Le attività sono elencate nel modulo '%s'", PATH_MODULO_PROFESSIONE);
        addSpanVerde(message);

        message = String.format(" con la sintassi: [\"attivita%s\"]=\"%s\", [\"attivita%s\"]=\"%s\".", uno, due);
        addSpanVerde(message);

        message = String.format("Nella collezione locale mongoDB vengono aggiunte anche le voci maschili che corrispondono alla pagina (non presenti nel modulo [%s])",PATH_MODULO_PROFESSIONE);
        addSpanRosso(message);

        message = String.format("Le attività e le pagine mantengono maiuscolo/minuscolo previsto nel [%s].", PATH_MODULO_PROFESSIONE);
        addSpanRosso(message);
    }

    protected void fixBottoniTopSpecifici() {
        searchFieldPagina = new TextField();
        searchFieldPagina.setPlaceholder("Filter by pagina");
        searchFieldPagina.setClearButtonVisible(true);
        searchFieldPagina.addValueChangeListener(event -> sincroFiltri());
        topPlaceHolder.add(searchFieldPagina);

        boxBox = new IndeterminateCheckbox();
        boxBox.setLabel("Aggiunta");
        boxBox.setIndeterminate(true);
        boxBox.addValueChangeListener(event -> sincroFiltri());
        HorizontalLayout layout = new HorizontalLayout(boxBox);
        layout.setAlignItems(Alignment.CENTER);
        topPlaceHolder.add(layout);
    }


    /**
     * Può essere sovrascritto, SENZA invocare il metodo della superclasse <br>
     */
    protected void sincroFiltri() {
        List<Professione> items = backend.findAll(sortOrder);

        final String textSearch = searchField != null ? searchField.getValue() : VUOTA;
        if (textService.isValid(textSearch)) {
            items = items.stream().filter(prof -> prof.attivita.matches("^(?i)" + textSearch + ".*$")).toList();
        }

        final String textSearchPagina = searchFieldPagina != null ? searchFieldPagina.getValue() : VUOTA;
        if (textService.isValid(textSearch)) {
            items = items.stream().filter(gen -> gen.pagina.matches("^(?i)" + textSearchPagina + ".*$")).toList();
        }

        if (boxBox != null && !boxBox.isIndeterminate()) {
            items = items.stream().filter(prof -> prof.aggiunta == boxBox.getValue()).toList();
        }

        if (items != null) {
            grid.setItems((List) items);
        }
    }

}// end of crud @Route view class