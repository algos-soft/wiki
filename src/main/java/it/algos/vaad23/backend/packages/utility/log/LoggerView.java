package it.algos.vaad23.backend.packages.utility.log;

import com.vaadin.flow.component.combobox.*;
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
 * Date: mer, 16-mar-2022
 * Time: 19:47
 * <p>
 */
@PageTitle("Logger")
@Route(value = "logger", layout = MainLayout.class)
public class LoggerView extends CrudView {

    private ComboBox<AELogLevel> comboLivello;


    //--per eventuali metodi specifici
    private LoggerBackend backend;

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
    public LoggerView(@Autowired final LoggerBackend crudBackend) {
        super(crudBackend, Logger.class);
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
            super.gridPropertyNamesList = Arrays.asList("type", "livello", "evento", "descrizione", "company", "user", "classe", "metodo", "linea");
        }
        else {
            super.gridPropertyNamesList = Arrays.asList("type", "livello", "evento", "descrizione", "classe", "metodo", "linea");
        }
        super.sortOrder = Sort.by(Sort.Direction.DESC, "evento");
        super.usaBottoneDeleteReset = true;
        super.usaBottoneFilter = true;
        super.usaBottoneNew = false;
        super.usaBottoneEdit = false;
        super.usaBottoneDelete = false;
        super.usaComboType = true;
    }

    /**
     * Costruisce un (eventuale) layout per informazioni aggiuntive come header della view <br>
     * Può essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    @Override
    public void fixAlert() {
        super.fixAlert();

        addSpanBlue("Diverse modalità di 'uscita' dei logs, regolate da flag:");
        addSpanVerde("A) nella cartella di log (sempre)");
        addSpanVerde("B) nella finestra del terminale - sempre in debug - mai in produzione - regolato da flag");
        addSpanVerde("C) nella collection del database (facoltativo)");
        addSpanVerde("D) in una mail (facoltativo e di norma solo per 'error')");
        addSpanRosso("Necessita di config.logback-spring.xml e attivazione in application.properties");
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

        comboLivello = new ComboBox();
        comboLivello.setPlaceholder("Livello");
        comboLivello.getElement().setProperty("title", "Filtro di selezione");
        comboLivello.setClearButtonVisible(true);
        comboLivello.setItems(AELogLevel.getAllEnums());
        comboLivello.addValueChangeListener(event -> sincroFiltri());
        topPlaceHolder.add(comboLivello);
    }

    /**
     * Può essere sovrascritto, SENZA invocare il metodo della superclasse <br>
     */
    protected List sincroFiltri() {
        List items = null;
        String textSearch = VUOTA;
        AELogLevel level = null;
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

}// end of crud @Route view class