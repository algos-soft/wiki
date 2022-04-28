package it.algos.wiki.backend.packages.bio;

import com.vaadin.flow.component.button.*;
import com.vaadin.flow.component.dialog.*;
import com.vaadin.flow.component.orderedlayout.*;
import com.vaadin.flow.router.*;
import it.algos.vaad23.backend.exception.*;
import it.algos.vaad23.backend.wrapper.*;
import it.algos.vaad23.ui.dialog.*;
import it.algos.vaad23.ui.views.*;
import static it.algos.wiki.backend.boot.WikiCost.*;
import org.springframework.beans.factory.annotation.*;

import java.util.*;

import com.vaadin.flow.spring.annotation.SpringComponent;
import org.springframework.context.annotation.Scope;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import com.vaadin.flow.component.textfield.TextField;
import org.vaadin.crudui.crud.*;

/**
 * Project wiki
 * Created by Algos
 * User: gac
 * Date: gio, 28-apr-2022
 * Time: 11:57
 * <p>
 * Vista iniziale e principale di un package <br>
 *
 * @Route chiamata dal menu generale <br>
 * Presenta la Grid <br>
 * Su richiesta apre un Dialogo per gestire la singola entity <br>
 */
@PageTitle("Biografie")
@Route(value = TAG_BIO, layout = MainLayout.class)
public class BioView extends CrudView {


    //--per eventuali metodi specifici
    private BioBackend backend;

    //--per eventuali metodi specifici
    private BioDialog dialog;

    /**
     * Costruttore @Autowired (facoltativo) <br>
     * In the newest Spring release, it’s constructor does not need to be annotated with @Autowired annotation <br>
     * Inietta con @Autowired il service con la logica specifica e lo passa al costruttore della superclasse <br>
     * Regola la entityClazz (final nella superclasse) associata a questa @Route view e la passa alla superclasse <br>
     *
     * @param crudBackend service specifico per la businessLogic e il collegamento con la persistenza dei dati
     */
    public BioView(@Autowired final BioBackend crudBackend) {
        super(crudBackend, Bio.class);
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

        super.gridPropertyNamesList = Arrays.asList("pageId", "wikiTitle", "nome", "cognome");
        super.formPropertyNamesList = Arrays.asList("nome", "cognome");
        super.riordinaColonne = true; //se rimane true, uguale al default, si può cancellare
        super.usaBottoneRefresh = false; //se rimane false, uguale al default, si può cancellare
        super.usaBottoneDeleteReset = false; //se rimane false, uguale al default, si può cancellare
        super.usaBottoneNew = true; //se rimane true, uguale al default, si può cancellare
        super.usaBottoneEdit = true; //se rimane true, uguale al default, si può cancellare
        super.usaBottoneDelete = true; //se rimane true, uguale al default, si può cancellare

        super.dialogClazz = BioDialog.class;
    }

    /**
     * Costruisce un (eventuale) layout per informazioni aggiuntive come header della view <br>
     * Può essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    @Override
    public void fixAlert() {
        super.fixAlert();
        addSpanVerde("Prova di colori");
    }

    /**
     * Apre un dialogo di creazione <br>
     * Proveniente da un click sul bottone New della Top Bar <br>
     * Sempre attivo <br>
     * Passa al dialogo gli handler per annullare e creare <br>
     */
    public void newItem() {
        String message;
        try {
            appContext.getBean(DialogInputText.class).open(this::downloadBio);
        } catch (Exception unErrore) {
            message = String.format("Non sono riuscito a costruire un'istanza di %s", DialogInputText.class.getSimpleName());
            logger.error(new WrapLog().exception(new AlgosException(message)).usaDb());
        }
    }


    protected void getInput() {
        //        EnhancedDialog dialog2 = new EnhancedDialog();
        //        dialog2.setHeader("Example Header");
        //        dialog2.setContent(new Span("Content"));
        //        dialog2.setFooter(new Button("Close", evt -> dialog2.close()));
        //        dialog2.open();

        VerticalLayout layout = new VerticalLayout();
        Dialog dialog = new Dialog();
        TextField field = new TextField("Titolo della pagina sul server wiki");
        field.setWidth("16em");
        field.addValueChangeListener(event -> {
//            newFormEsegue(field.getValue());
            dialog.close();
        });
        layout.add(field);
        field.setAutofocus(true);

        dialog.setWidth("400px");
        dialog.setHeight("200px");
        HorizontalLayout layout2 = new HorizontalLayout();

        layout2.add(new Button("Annulla", evt -> dialog.close()));
        layout2.add(new Button("Download", evt -> dialog.close()));
        layout.add(layout2);
        dialog.add(layout);
        dialog.open();
    }

    protected void downloadBio(String wikiTitle) {
        int a = 87;
        //        Bio bio = ((BioService) entityService).downloadBioSave(wikiTitle);
        //
        //        if (bio != null) {
        //            this.executeRoute(bio);
        //        }
    }

}// end of crud @Route view class