package it.algos.wiki.backend.packages.wiki;

import com.vaadin.flow.component.button.*;
import com.vaadin.flow.component.icon.*;
import it.algos.vaad23.backend.logic.*;
import it.algos.vaad23.backend.service.*;
import it.algos.vaad23.ui.views.*;
import org.springframework.beans.factory.annotation.*;

/**
 * Project vaadwiki
 * Created by Algos
 * User: gac
 * Date: ven, 22-apr-2022
 * Time: 08:01
 */
public abstract class WikiView extends CrudView {

    protected boolean usaBottoneDownload;

    protected Button buttonDownload;

    protected String parametri;

    protected String alfabetico;

    protected String singolare;

    protected String plurale;

    /**
     * Istanza unica di una classe @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) di servizio <br>
     * Iniettata automaticamente dal framework SpringBoot/Vaadin con l'Annotation @Autowired <br>
     * Disponibile DOPO il ciclo init() del costruttore di questa classe <br>
     */
    @Autowired
    public DateService dateService;

    /**
     * Costruttore @Autowired (facoltativo) <br>
     * In the newest Spring release, it’s constructor does not need to be annotated with @Autowired annotation <br>
     * Inietta con @Autowired il service con la logica specifica e lo passa al costruttore della superclasse <br>
     * Regola la entityClazz (final nella superclasse) associata a questa @Route view e la passa alla superclasse <br>
     *
     * @param crudBackend service specifico per la businessLogic e il collegamento con la persistenza dei dati
     */
    public WikiView(final CrudBackend crudBackend, final Class entityClazz) {
        super(crudBackend, entityClazz);
    }


    /**
     * Preferenze usate da questa 'view' <br>
     * Primo metodo chiamato dopo init() (implicito del costruttore) e postConstruct() (facoltativo) <br>
     * Puo essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    @Override
    protected void fixPreferenze() {
        super.fixPreferenze();

        super.usaBottoneDeleteReset = false;
        super.usaBottoneNew = false;
        super.usaBottoneEdit = false;
        super.usaBottoneDelete = false;

        this.usaBottoneDownload = true;
    }


    /**
     * Bottoni standard (solo icone) Reset, New, Edit, Delete, ecc.. <br>
     * Può essere sovrascritto, invocando DOPO il metodo della superclasse <br>
     */
    protected void fixBottoniTopStandard() {
        if (usaBottoneDownload) {
            buttonDownload = new Button();
            buttonDownload.getElement().setAttribute("theme", "error");
            buttonDownload.getElement().setProperty("title", "Download: ricarica tutti i valori dal server wiki");
            buttonDownload.setIcon(new Icon(VaadinIcon.ARROW_CIRCLE_DOWN));
            buttonDownload.addClickListener(event -> download());
            topPlaceHolder.add(buttonDownload);
        }

        super.fixBottoniTopStandard();
    }

    protected void fixBottomLayout() {
        super.fixBottomLayout();
    }

    /**
     * Esegue un azione di download, specifica del programma/package in corso <br>
     * Deve essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    public void download() {
        crudBackend.download();
        reload();
    }

}
