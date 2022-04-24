package it.algos.vaad23.ui.views;

import com.vaadin.flow.component.*;
import com.vaadin.flow.component.button.*;
import com.vaadin.flow.component.grid.*;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.icon.*;
import com.vaadin.flow.component.notification.*;
import com.vaadin.flow.component.orderedlayout.*;
import com.vaadin.flow.component.page.*;
import com.vaadin.flow.component.textfield.*;
import com.vaadin.flow.router.*;
import static it.algos.vaad23.backend.boot.VaadCost.*;
import it.algos.vaad23.backend.enumeration.*;
import it.algos.vaad23.backend.logic.*;
import it.algos.vaad23.backend.service.*;
import it.algos.vaad23.backend.wrapper.*;
import it.algos.vaad23.ui.dialog.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.context.*;
import org.vaadin.crudui.crud.impl.*;
import org.vaadin.crudui.form.*;
import org.vaadin.crudui.layout.impl.*;

import java.util.*;

/**
 * Project vaadin23
 * Created by Algos
 * User: gac
 * Date: dom, 20-mar-2022
 * Time: 11:05
 */
public abstract class CrudViewOld extends VerticalLayout implements AfterNavigationObserver {

    /**
     * Istanza di una interfaccia SpringBoot <br>
     * Iniettata automaticamente dal framework SpringBoot con l'Annotation @Autowired <br>
     * Disponibile DOPO il ciclo init() del costruttore di questa classe <br>
     */
    @Autowired
    public ApplicationContext appContext;

    /**
     * Istanza unica di una classe @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) di servizio <br>
     * Iniettata automaticamente dal framework SpringBoot/Vaadin con l'Annotation @Autowired <br>
     * Disponibile DOPO il ciclo init() del costruttore di questa classe <br>
     */
    @Autowired
    public LogService logger;


    /**
     * Istanza unica di una classe @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) di servizio <br>
     * Iniettata automaticamente dal framework SpringBoot/Vaadin con l'Annotation @Autowired <br>
     * Disponibile DOPO il ciclo init() del costruttore di questa classe <br>
     */
    @Autowired
    protected HtmlService htmlService;

    protected CrudBackend crudBackend;

    protected GridCrud gridCrud;

    protected Button buttonDeleteAll;

    protected TextField filter;

    protected boolean usaBottoneDeleteAll;

    protected boolean usaBottoneFilter;

    protected Grid grid;

    protected CrudFormFactory crudForm;

    protected boolean splitLayout;

    Class entityClazz;

    protected int width;

    public CrudViewOld(CrudBackend crudBackend, Class entityClazz) {
        this.crudBackend = crudBackend;
        this.entityClazz = entityClazz;
    }


    /**
     * Qui va tutta la logica della view <br>
     * Invocato da SpringBoot dopo il metodo init() del costruttore <br>
     * Sono disponibili tutte le istanze @Autowired <br>
     * Può essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    @Override
    public void afterNavigation(AfterNavigationEvent beforeEnterEvent) {
        UI.getCurrent().getPage().retrieveExtendedClientDetails(details -> fixBrowser(details));
        this.fixPreferenze();
        this.fixAlert();
        this.fixCrud();
        this.fixColumns();
        this.fixFields();
        this.fixOrder();
        this.fixAdditionalComponents();
        this.addListeners();
    }

    /**
     *
     */
    public void fixBrowser(ExtendedClientDetails details) {
        width = details.getBodyClientWidth();
    }

    /**
     * Preferenze usate da questa view <br>
     * Primo metodo chiamato dopo AfterNavigationEvent <br>
     * Può essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    protected void fixPreferenze() {
        this.splitLayout = false;
        this.usaBottoneDeleteAll = false;
        this.usaBottoneFilter = false;
    }

    /**
     * Costruisce un (eventuale) layout per informazioni aggiuntive come header della view <br>
     * Può essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    public void fixAlert() {
    }

    /**
     * Logic configuration <br>
     * Qui vanno i collegamenti con la logica del backend <br>
     * Può essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    protected void fixCrud() {
        // crud instance
        if (splitLayout) {
            gridCrud = new GridCrud<>(entityClazz, new HorizontalSplitCrudLayout());
        }
        else {
            gridCrud = new GridCrud<>(entityClazz);
        }

        // grid configuration
        gridCrud.getCrudFormFactory().setUseBeanValidation(true);

        // logic configuration
        gridCrud.setFindAllOperation(() -> sincroFiltri());
        gridCrud.setAddOperation(bean -> crudBackend.add(bean));
        gridCrud.setUpdateOperation(bean -> crudBackend.update(bean));
        gridCrud.setDeleteOperation(bean -> crudBackend.delete(bean));

        //                gridCrud.setOperations(
        //                        () -> sincroFiltri(),
        //                        user -> crudBackend.add(user),
        //                        user -> crudBackend.update(user),
        //                        user -> crudBackend.delete(user)
        //                );

        grid = gridCrud.getGrid();
        crudForm = gridCrud.getCrudFormFactory();

        // layout configuration
        setSizeFull();
        this.add(gridCrud);
    }


    /**
     * Regola la visibilità delle colonne della grid <br>
     * Può essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    public void fixColumns() {
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
     * Componenti aggiuntivi oltre quelli base <br>
     * Tipicamente bottoni di selezione/filtro <br>
     * Può essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    protected void fixAdditionalComponents() {
        if (usaBottoneDeleteAll) {
            buttonDeleteAll = new Button();
            buttonDeleteAll.setIcon(VaadinIcon.TRASH.create());
            buttonDeleteAll.setText("Delete All");
            buttonDeleteAll.getElement().setAttribute("theme", "error");
            gridCrud.getCrudLayout().addFilterComponent(buttonDeleteAll);
            buttonDeleteAll.addClickListener(event -> openConfirmDeleteAll());
        }

        if (usaBottoneFilter) {
            filter = new TextField();
            filter.setPlaceholder("Filter by descrizione");
            filter.setClearButtonVisible(true);
            gridCrud.getCrudLayout().addFilterComponent(filter);
            filter.addValueChangeListener(event -> gridCrud.refreshGrid());
        }
    }

    /**
     * Aggiunge tutti i listeners ai bottoni di 'topPlaceholder' che sono stati creati SENZA listeners <br>
     * <p>
     * Chiamato da afterNavigation() <br>
     * Può essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    protected void addListeners() {
    }

    /**
     * Può essere sovrascritto, SENZA invocare il metodo della superclasse <br>
     */
    protected List sincroFiltri() {
        List items = null;
        String textSearch;

        if (usaBottoneFilter && filter != null) {
            textSearch = filter != null ? filter.getValue() : VUOTA;
            items = crudBackend.findByDescrizione(textSearch);
        }

        if (items != null) {
            gridCrud.getGrid().setItems(items);
        }

        return items;
    }


    /**
     * Opens the confirmation dialog before deleting all items. <br>
     * <p>
     * The dialog will display the given title and message(s), then call <br>
     * Può essere sovrascritto dalla classe specifica se servono avvisi diversi <br>
     */
    protected final void openConfirmDeleteAll() {
        appContext.getBean(DialogDelete.class, "tutta la collection").open(this::deleteAll);
    }

    /**
     * Cancellazione effettiva (dopo dialogo di conferma) di tutte le entities della collezione. <br>
     * Rimanda al service specifico <br>
     * Azzera gli items <br>
     * Ridisegna la GUI <br>
     */
    public void deleteAll() {
        if (crudBackend.countAll() > 0) {
            try {
                crudBackend.deleteAll();
            } catch (Exception unErrore) {
                Notification.show("Non sono riuscito a cancellare la collection").addThemeVariants(NotificationVariant.LUMO_ERROR);
                logger.error(unErrore);
                return;
            }
            gridCrud.refreshGrid();
            Notification.show("Cancellata tutta la collection").addThemeVariants(NotificationVariant.LUMO_SUCCESS);
        }
        else {
            Notification.show("La collection era già vuota").addThemeVariants(NotificationVariant.LUMO_PRIMARY);
        }
    }

    /**
     * Può essere sovrascritto, SENZA invocare il metodo della superclasse <br>
     */
    protected void delete() {
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
