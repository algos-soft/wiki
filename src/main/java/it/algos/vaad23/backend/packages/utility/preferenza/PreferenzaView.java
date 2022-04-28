package it.algos.vaad23.backend.packages.utility.preferenza;

import ch.carnet.kasparscherrer.*;
import com.vaadin.flow.component.*;
import com.vaadin.flow.component.button.*;
import com.vaadin.flow.component.combobox.*;
import com.vaadin.flow.component.grid.*;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.icon.*;
import com.vaadin.flow.component.notification.*;
import com.vaadin.flow.component.orderedlayout.*;
import com.vaadin.flow.component.page.*;
import com.vaadin.flow.component.textfield.*;
import com.vaadin.flow.data.renderer.*;
import com.vaadin.flow.data.selection.*;
import com.vaadin.flow.router.*;
import com.vaadin.flow.server.*;
import it.algos.vaad23.backend.annotation.*;
import static it.algos.vaad23.backend.boot.VaadCost.*;
import it.algos.vaad23.backend.boot.*;
import it.algos.vaad23.backend.enumeration.*;
import it.algos.vaad23.backend.service.*;
import it.algos.vaad23.backend.wrapper.*;
import it.algos.vaad23.ui.dialog.*;
import it.algos.vaad23.ui.views.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.context.*;
import org.vaadin.crudui.crud.*;

import java.util.*;

/**
 * Project vaadin23
 * Created by Algos
 * User: gac
 * Date: dom, 27-mar-2022
 * Time: 10:29
 */
@PageTitle("Preferenze")
@Route(value = TAG_PRE, layout = MainLayout.class)
@AIView(lineawesomeClassnames = "wrench")
public class PreferenzaView extends VerticalLayout implements AfterNavigationObserver {

    /**
     * Istanza unica di una classe @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) di servizio <br>
     * Iniettata automaticamente dal framework SpringBoot/Vaadin con l'Annotation @Autowired <br>
     * Disponibile DOPO il ciclo init() del costruttore di questa classe <br>
     */
    @Autowired
    public TextService textService;

    @Autowired
    protected ApplicationContext appContext;

    protected Grid<Preferenza> grid;

    protected List<Preferenza> items;

    protected TextField filter;

    protected ComboBox<AETypePref> comboTypePref;

    @Autowired
    protected PreferenzaBackend backend;

    @Autowired
    protected HtmlService htmlService;

    @Autowired
    protected LogService logger;

    @Autowired
    protected VaadPref vaadPref;

    protected Button refreshButton;

    protected Button addButton;

    protected Button editButton;

    protected Button deleteButton;

    protected int width;

    protected IndeterminateCheckbox boxBoxVaad23;

    protected IndeterminateCheckbox boxBoxRiavvio;

    @Override
    public void afterNavigation(AfterNavigationEvent afterNavigationEvent) {
        WebBrowser browser = VaadinSession.getCurrent().getBrowser();
        UI.getCurrent().getPage().retrieveExtendedClientDetails(details -> fixBrowser(details));
        this.fixAlert();
        this.fixTop();
        this.fixCrud();
        this.fixColumns();
        this.fixFields();
        this.fixOrder();
        this.addListeners();
    }

    /**
     *
     */
    public void fixBrowser(ExtendedClientDetails details) {
        width = details.getBodyClientWidth();
    }

    /**
     * Costruisce un (eventuale) layout per informazioni aggiuntive come header della view <br>
     * Può essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    public void fixAlert() {
        spanBlue("Preferenze registrate nel database mongoDB");
        spanRosso("Mostra solo le properties di un programma non multiCompany");
        span(String.format("Vaad23=true per le preferenze del programma base '%s'", VaadVar.projectVaadFlow));
        span(String.format("Vaad23=false per le preferenze del programma corrente '%s'", VaadVar.projectCurrent));
        span("NeedRiavvio=true se la preferenza ha effetto solo dopo un riavvio del programma");
        spanRosso("Refresh ripristina nel database i valori di default annullando le successive modifiche");
    }

    /**
     * Costruisce un (eventuale) layout per bottoni di comando in testa alla grid <br>
     * Può essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    public void fixTop() {
        HorizontalLayout layout = new HorizontalLayout();
        layout.setClassName("buttons");
        layout.setPadding(false);
        layout.setSpacing(true);
        layout.setMargin(false);
        layout.setClassName("confirm-dialog-buttons");

        refreshButton = new Button();
        refreshButton.getElement().setAttribute("theme", "error");
        refreshButton.setIcon(new Icon(VaadinIcon.REFRESH));
        refreshButton.addClickListener(e -> refresh());
        layout.add(refreshButton);

        addButton = new Button();
        addButton.getElement().setAttribute("theme", "secondary");
        addButton.setIcon(new Icon(VaadinIcon.PLUS));
        addButton.addClickListener(e -> newItem());
        //        layout.add(addButton);

        editButton = new Button();
        editButton.getElement().setAttribute("theme", "secondary");
        editButton.setIcon(new Icon(VaadinIcon.PENCIL));
        editButton.addClickListener(e -> updateItem());
        editButton.setEnabled(false);
        layout.add(editButton);

        deleteButton = new Button();
        deleteButton.getElement().setAttribute("theme", "secondary");
        deleteButton.setIcon(new Icon(VaadinIcon.TRASH));
        deleteButton.addClickListener(e -> deleteItem());
        deleteButton.setEnabled(false);
        layout.add(deleteButton);

        filter = new TextField();
        filter.setPlaceholder("Filter by code");
        filter.setClearButtonVisible(true);
        filter.addValueChangeListener(event -> sincroFiltri());
        layout.add(filter);

        comboTypePref = new ComboBox<>();
        comboTypePref.setPlaceholder("Type");
        comboTypePref.setClearButtonVisible(true);
        List<AETypePref> items2 = AETypePref.getAllEnums();
        comboTypePref.setItems(items2);
        comboTypePref.addValueChangeListener(event -> sincroFiltri());
        layout.add(comboTypePref);

        boxBoxVaad23 = new IndeterminateCheckbox();
        boxBoxVaad23.setLabel("Vaad23 / Specifica");
        boxBoxVaad23.setIndeterminate(true);
        boxBoxVaad23.addValueChangeListener(event -> sincroFiltri());
        HorizontalLayout layout2 = new HorizontalLayout(boxBoxVaad23);
        layout2.setAlignItems(Alignment.CENTER);
        layout.add(layout2);

        boxBoxRiavvio = new IndeterminateCheckbox();
        boxBoxRiavvio.setLabel("Riavvio");
        boxBoxRiavvio.setIndeterminate(true);
        boxBoxRiavvio.addValueChangeListener(event -> sincroFiltri());
        HorizontalLayout layout3 = new HorizontalLayout(boxBoxRiavvio);
        layout3.setAlignItems(Alignment.CENTER);
        layout.add(layout3);

        this.add(layout);
    }

    /**
     * Logic configuration <br>
     * Qui vanno i collegamenti con la logica del backend <br>
     * Può essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    protected void fixCrud() {
        // Create a listing component for a bean type
        grid = new Grid<>(Preferenza.class, false);

        // Pass all Preferenza objects to a grid from a Spring Data repository object
        grid.setItems(backend.findAll());

        // The row-stripes theme produces a background color for every other row.
        grid.addThemeVariants(GridVariant.LUMO_ROW_STRIPES);

        // switch to single select mode
        grid.setSelectionMode(Grid.SelectionMode.SINGLE);

        grid.addSelectionListener(event -> sincroSelection(event));

        // layout configuration
        setSizeFull();
        this.add(grid);
    }

    /**
     * Regola la visibilità delle colonne della grid <br>
     * Può essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    public void fixColumns() {
        grid.addColumns("code", "type");

        grid.addColumn(new ComponentRenderer<>(pref ->
                switch (pref.getType()) {
                    case string, integer, localdate, localtime, localdatetime -> {
                        Label label = new Label(pref.getType().bytesToString(pref.getValue()));
                        label.getElement().getStyle().set("color", pref.getType().getColor());
                        label.getElement().getStyle().set("fontWeight", "bold");
                        yield label;
                    }
                    case bool -> {
                        boolean vero = (boolean) pref.getType().bytesToObject(pref.getValue());
                        Icon icona = vero ? VaadinIcon.CHECK.create() : VaadinIcon.CLOSE.create();
                        icona.setColor(vero ? COLOR_VERO : COLOR_FALSO);
                        yield icona;
                    }
                    default -> new Label(TRE_PUNTI);
                })).setHeader("Value").setKey("value");

        grid.addColumns("descrizione");

        String larCode = "12em";
        String larType = "9em";
        String larValue = "10em";
        String larDesc = "30em";
        String larBool = "6em";

        grid.getColumnByKey("code").setWidth(larCode).setFlexGrow(0);
        grid.getColumnByKey("type").setWidth(larType).setFlexGrow(0).setTextAlign(ColumnTextAlign.CENTER);
        grid.getColumnByKey("value").setWidth(larValue).setFlexGrow(0).setTextAlign(ColumnTextAlign.CENTER);
        grid.getColumnByKey("descrizione").setWidth(larDesc).setFlexGrow(1);

        grid.addColumn(new ComponentRenderer<>(pref -> {
            Icon icona = pref.vaad23 ? VaadinIcon.CHECK.create() : VaadinIcon.CLOSE.create();
            icona.setColor(pref.vaad23 ? COLOR_VERO : COLOR_FALSO);
            return icona;
        })).setHeader("Vaad23").setKey("vaadFlow").setWidth(larBool).setFlexGrow(0).setTextAlign(ColumnTextAlign.CENTER);
        grid.addColumn(new ComponentRenderer<>(pref -> {
            Icon icona = pref.needRiavvio ? VaadinIcon.CHECK.create() : VaadinIcon.CLOSE.create();
            icona.setColor(pref.needRiavvio ? COLOR_VERO : COLOR_FALSO);
            return icona;
        })).setHeader("Riavvio").setKey("needRiavvio").setWidth(larBool).setFlexGrow(0).setTextAlign(ColumnTextAlign.CENTER);

    }

    /**
     * Regola la visibilità dei fields del Form<br>
     * Può essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    public void fixFields() {
    }

    /**
     * Regola l'ordinamento della <grid <br>
     * Può essere sovrascritto, SENZA invocare il metodo della superclasse <br>
     */
    public void fixOrder() {
    }


    /**
     * Aggiunge tutti i listeners ai bottoni di 'topPlaceholder' che sono stati creati SENZA listeners <br>
     * <p>
     * Chiamato da afterNavigation() <br>
     * Può essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    protected void addListeners() {
        // pass the row/item that the user double-clicked to method openDialog
        grid.addItemClickListener(listener -> {
            if (listener.getClickCount() == 2)
                updateItem(listener.getItem());
        });
    }

    public void newItem() {
        Preferenza entityBean = new Preferenza();
        entityBean.setType(AETypePref.string);
        PreferenzaDialog dialog = appContext.getBean(PreferenzaDialog.class, entityBean, CrudOperation.ADD);
        dialog.open(this::saveHandler, this::deleteHandler, this::annullaHandler);
    }

    public void updateItem() {
        Optional entityBean = grid.getSelectedItems().stream().findFirst();
        if (entityBean.isPresent()) {
            updateItem((Preferenza) entityBean.get());
        }
    }

    public void updateItem(Preferenza entityBean) {
        PreferenzaDialog dialog = appContext.getBean(PreferenzaDialog.class, entityBean, CrudOperation.UPDATE);
        dialog.open(this::saveHandler, this::deleteHandler, this::annullaHandler);
    }

    public void deleteItem() {
        Optional entityBean = grid.getSelectedItems().stream().findFirst();
        if (entityBean.isPresent()) {
            PreferenzaDialog dialog = appContext.getBean(PreferenzaDialog.class, entityBean.get(), CrudOperation.DELETE);
            dialog.open(this::saveHandler, this::deleteHandler, this::annullaHandler);
        }
    }

    protected void sincroFiltri() {
        List<Preferenza> items = backend.findAll();

        final String textSearch = filter != null ? filter.getValue() : VUOTA;
        if (textService.isValid(textSearch)) {
            items = items.stream().filter(pref -> pref.code.matches("^(?i)" + textSearch + ".*$")).toList();
        }

        final AETypePref type = comboTypePref != null ? comboTypePref.getValue() : null;
        if (type != null) {
            items = items.stream().filter(pref -> pref.type == type).toList();
        }

        if (boxBoxVaad23 != null && !boxBoxVaad23.isIndeterminate()) {
            items = items.stream().filter(pref -> pref.vaad23 == boxBoxVaad23.getValue()).toList();
        }

        if (boxBoxRiavvio != null && !boxBoxRiavvio.isIndeterminate()) {
            items = items.stream().filter(pref -> pref.needRiavvio == boxBoxRiavvio.getValue()).toList();
        }

        if (items != null) {
            grid.setItems((List) items);
        }
    }

    protected void sincroSelection(SelectionEvent event) {
        boolean singoloSelezionato = event.getAllSelectedItems().size() == 1;
        editButton.setEnabled(singoloSelezionato);
        deleteButton.setEnabled(singoloSelezionato);
    }

    protected void refresh() {
        //        appContext.getBean(DialogDelete.class, "tutta la collection").open(this::refreshAll);
        appContext.getBean(DialogRefreshPreferenza.class).open(this::refreshAll);
    }

    protected void refreshAll() {
        backend.deleteAll();
        vaadPref.inizia();
        grid.setItems(backend.findAll());
        Avviso.show("Refreshed view").addThemeVariants(NotificationVariant.LUMO_PRIMARY);
    }

    /**
     * Primo ingresso dopo il click sul bottone del dialogo <br>
     */
    protected void saveHandler(final Preferenza entityBean, final CrudOperation operation) {
        grid.setItems(backend.findAll());
    }

    public void deleteHandler(final Preferenza entityBean) {
        backend.delete(entityBean);
        grid.setItems(backend.findAll());
        Avviso.show(String.format("%s successfully deleted", entityBean.code)).addThemeVariants(NotificationVariant.LUMO_SUCCESS);
    }

    public void annullaHandler(final Preferenza entityBean) {
        //        Notification.show(entityBean + " successfully deleted.", 3000, Notification.Position.BOTTOM_START);
    }


    public Span getSpan(final String avviso) {
        return htmlService.getSpanVerde(avviso);
    }

    public void spanBlue(final String message) {
        span(new WrapSpan(message).color(AETypeColor.blu));
    }

    public void spanRosso(final String message) {
        span(new WrapSpan(message).color(AETypeColor.rosso));
    }

    public void span(final String message) {
        span(new WrapSpan(message));
    }

    public void span(WrapSpan wrap) {
        Span span;

        if (wrap.getColor() == null) {
            wrap.color(AETypeColor.verde);
        }
        if (wrap.getWeight() == null) {
            wrap.weight(AEFontWeight.bold);
        }
        if (wrap.getFontHeight() == null) {
            if (width == 0 || width > 500) {
                wrap.fontHeight(AEFontHeight.em9);
            }
            else {
                wrap.fontHeight(AEFontHeight.em7);
            }
        }

        if (wrap.getLineHeight() == null) {
            if (width == 0 || width > 500) {
                wrap.lineHeight(AELineHeight.em3);
            }
            else {
                wrap.lineHeight(AELineHeight.em12);
            }
        }

        span = htmlService.getSpan(wrap);
        if (span != null) {
            this.add(span);
        }
    }

}
