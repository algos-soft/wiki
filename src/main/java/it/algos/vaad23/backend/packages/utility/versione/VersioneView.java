package it.algos.vaad23.backend.packages.utility.versione;

import ch.carnet.kasparscherrer.*;
import com.vaadin.flow.component.combobox.*;
import com.vaadin.flow.component.orderedlayout.*;
import com.vaadin.flow.router.*;
import static it.algos.vaad23.backend.boot.VaadCost.*;
import it.algos.vaad23.backend.boot.*;
import it.algos.vaad23.backend.enumeration.*;
import it.algos.vaad23.ui.views.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.data.domain.*;

import java.util.*;

/**
 * Project vaadin23
 * Created by Algos
 * User: gac
 * Date: sab, 12-mar-2022
 * Time: 18:24
 */
@PageTitle("Versioni")
@Route(value = TAG_VERSIONE, layout = MainLayout.class)
public class VersioneView extends CrudView {

    private ComboBox<AETypeVers> comboTypeVers;

    //--per eventuali metodi specifici
    private VersioneBackend backend;

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
    public VersioneView(@Autowired final VersioneBackend crudBackend) {
        super(crudBackend, Versione.class);
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

        if (VaadVar.usaCompany) {
            super.gridPropertyNamesList = Arrays.asList("code", "type", "titolo", "descrizione", "company", "vaadin23");
            super.formPropertyNamesList = Arrays.asList("code", "type", "titolo", "descrizione", "company", "vaadin23");
        }
        else {
            super.gridPropertyNamesList = Arrays.asList("code", "type", "titolo", "descrizione", "vaadin23");
            super.formPropertyNamesList = Arrays.asList("code", "type", "titolo", "descrizione", "vaadin23");
        }
        super.sortOrder = Sort.by(Sort.Direction.ASC, "ordine");
        super.usaBottoneDeleteReset = false;
        super.usaBottoneNew = false;
        super.usaBottoneDelete = false;
    }

    /**
     * Costruisce un (eventuale) layout per informazioni aggiuntive come header della view <br>
     * Può essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    @Override
    public void fixAlert() {
        super.fixAlert();

        addSpanVerde("Sviluppo, patch e update del programma. Sigla V iniziale per il programma base Vaadin23");
        addSpanRosso("Solo hard coded. Non creabili e non modificabili");
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

        comboTypeVers = new ComboBox<>();
        comboTypeVers.setPlaceholder("Type");
        comboTypeVers.getElement().setProperty("title", "Filtro di selezione");
        comboTypeVers.setClearButtonVisible(true);
        comboTypeVers.setItems(AETypeVers.getAllEnums());
        comboTypeVers.addValueChangeListener(event -> sincroFiltri());
        topPlaceHolder.add(comboTypeVers);

        boxBox = new IndeterminateCheckbox();
        boxBox.setLabel("Vaad23 / Specifica");
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
        List<Versione> items = backend.findAll(sortOrder);

        final String textSearch = searchField != null ? searchField.getValue() : VUOTA;
        if (textService.isValid(textSearch)) {
            items = items.stream().filter(vers -> vers.descrizione.matches("^(?i)" + textSearch + ".*$")).toList();
        }

        final AETypeVers level = comboTypeVers != null ? comboTypeVers.getValue() : null;
        if (level != null) {
            items = items.stream().filter(vers -> vers.type == level).toList();
        }

        if (boxBox != null && !boxBox.isIndeterminate()) {
            items = items.stream().filter(vers -> vers.vaadin23 == boxBox.getValue()).toList();
        }

        if (items != null) {
            grid.setItems((List) items);
        }
    }


}// end of crud @Route view class