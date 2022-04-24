package it.algos.vaad23.backend.packages.utility.nota;

import com.vaadin.flow.component.combobox.*;
import com.vaadin.flow.router.*;
import static it.algos.vaad23.backend.boot.VaadCost.*;
import it.algos.vaad23.backend.enumeration.*;
import it.algos.vaad23.ui.views.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.data.domain.*;

import java.util.*;

/**
 * Project vaadin23
 * Created by Algos
 * User: gac
 * Date: ven, 18-mar-2022
 * Time: 06:55
 * <p>
 * Vista iniziale e principale di un package <br>
 *
 * @Route chiamata dal menu generale <br>
 * Presenta la Grid <br>
 * Su richiesta apre un Dialogo per gestire la singola entity <br>
 */
@PageTitle("Note")
@Route(value = TAG_NOTA, layout = MainLayout.class)
public class NotaView extends CrudView {


    private ComboBox<AENotaLevel> comboLivello;

    //--per eventuali metodi specifici
    private NotaBackend backend;

    /**
     * Costruttore @Autowired (facoltativo) <br>
     * In the newest Spring release, it’s constructor does not need to be annotated with @Autowired annotation <br>
     * Si usa un @Qualifier(), per specificare la classe che incrementa l'interfaccia repository <br>
     * Si usa una costante statica, per essere sicuri di scriverla uguale a quella di xxxRepository <br>
     * Regola il service specifico di persistenza dei dati e lo passa al costruttore della superclasse <br>
     * Regola la entityClazz (final nella superclasse) associata a questa @Route view <br>
     *
     * @param crudBackend service specifico per la businessLogic e il collegamento con la persistenza dei dati
     */
    public NotaView(@Autowired final NotaBackend crudBackend) {
        super(crudBackend, Nota.class);
        this.backend = crudBackend;
    }

    /**
     * Preferenze usate da questa view <br>
     * Primo metodo chiamato dopo AfterNavigationEvent <br>
     * Può essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    @Override
    public void fixPreferenze() {
        super.fixPreferenze();

        super.gridPropertyNamesList = Arrays.asList("type", "livello", "inizio", "descrizione", "fatto", "fine");
        super.formPropertyNamesList = Arrays.asList("type", "livello", "descrizione", "fatto", "fine");
        super.sortOrder = Sort.by(Sort.Direction.DESC, "inizio");
        this.usaBottoneDeleteReset = true;
        this.usaBottoneFilter = true;
    }

    /**
     * Costruisce un (eventuale) layout per informazioni aggiuntive come header della view <br>
     * Può essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    @Override
    public void fixAlert() {
        super.fixAlert();

        addSpanVerde("Appunti per sviluppo e @todo");
        addSpanRosso("Al termine spuntarli e non cancellarli");
    }

    @Override
    protected void addColumnsOneByOne() {
        columnService.addColumnsOneByOne(grid, entityClazz, gridPropertyNamesList);
    }


    /**
     * Componenti aggiuntivi oltre quelli base <br>
     * Tipicamente bottoni di selezione/filtro <br>
     * Può essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    @Override
    protected void fixBottoniTopSpecifici() {
        super.fixBottoniTopSpecifici();

        comboLivello = new ComboBox<>();
        comboLivello.setPlaceholder("Livello");
        comboLivello.getElement().setProperty("title", "Filtro di selezione");
        comboLivello.setClearButtonVisible(true);
        comboLivello.setItems(AENotaLevel.getAllEnums());
        comboLivello.addValueChangeListener(event -> sincroFiltri());
        topPlaceHolder.add(comboLivello);
    }


    /**
     * Può essere sovrascritto, SENZA invocare il metodo della superclasse <br>
     */
    protected List sincroFiltri() {
        List items = null;
        String textSearch = VUOTA;
        AENotaLevel level = null;
        AETypeLog type = null;

        if (usaBottoneFilter && filter != null) {
            textSearch = filter != null ? filter.getValue() : VUOTA;
            items = backend.findByDescrizione(textSearch);
        }

        if (comboLivello != null) {
            level = comboLivello.getValue();
        }

        if (comboTypeLog != null) {
            type = comboTypeLog.getValue();
        }

        if (usaBottoneFilter) {
            items = backend.findByDescrizioneAndLivelloAndType(textSearch, level, type);
        }

        if (items != null) {
            grid.setItems(items);
        }

        return items;
    }

    @Override
    public void newItem() {
        super.formPropertyNamesList = Arrays.asList("type", "livello", "descrizione");
        super.newItem();
    }

    @Override
    public void updateItem() {
        super.formPropertyNamesList = Arrays.asList("type", "livello", "descrizione", "fatto");
        super.updateItem();
    }

}// end of crud @Route view class