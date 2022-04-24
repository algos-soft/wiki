package it.algos.wiki.backend.packages.attivita;

import com.vaadin.flow.router.*;
import it.algos.vaad23.ui.views.*;
import static it.algos.wiki.backend.boot.WikiCost.*;
import it.algos.wiki.backend.enumeration.*;
import it.algos.wiki.backend.packages.wiki.*;
import org.springframework.beans.factory.annotation.*;

import java.time.*;
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
@Route(value = "attivita", layout = MainLayout.class)
public class AttivitaView extends WikiView {


    //--per eventuali metodi specifici
    private AttivitaBackend backend;

    //--per eventuali metodi specifici
    private AttivitaDialog dialog;

    protected String parametri;

    protected String alfabetico;

    protected String singolare;

    protected String plurale;

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

        super.gridPropertyNamesList = Arrays.asList("singolare", "plurale", "aggiunta");
        super.formPropertyNamesList = Arrays.asList("singolare", "plurale", "aggiunta");

        super.dialogClazz = AttivitaDialog.class;
    }

    /**
     * Costruisce un (eventuale) layout per informazioni aggiuntive come header della view <br>
     * Può essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    @Override
    public void fixAlert() {
        super.fixAlert();
        String message;
        String uno;
        String due;
        String anche;
        String minuscolo;
        String data = dateService.get((LocalDateTime) WPref.lastDownloadAttivita.get());
        int durata = WPref.durataDownloadAttivita.getInt();

        parametri = htmlService.bold("Attività/Attività2/Attività3");
        alfabetico = htmlService.bold("alfabetico");
        singolare = htmlService.bold("singolare");
        plurale = htmlService.bold("plurale");
        minuscolo = htmlService.bold("minuscolo");
        uno = htmlService.bold("Forma1");
        due = htmlService.bold("Forma2");
        anche = htmlService.bold("anche");
        String ex = htmlService.bold("ex");
        String moduloTxt = PATH_MODULO_ATTIVITA + " genere";

        message = String.format("Ultimo download effettuato il %s in %d secondi", data, durata);
        addSpanBlue(message);

        message = String.format("Contiene la tabella di conversione delle attività passate via parametri %s.", parametri, singolare, plurale);
        addSpanVerde(message);
        message = String.format("Da maschile e femminile (usati nell'incipit) al %s maschile, per categorizzare la pagina.", singolare, plurale);
        addSpanVerde(message);
        message = String.format("Le attività sono elencate nel modulo '%s'", PATH_MODULO_ATTIVITA);
        addSpanVerde(message);
        message = String.format(" con la sintassi: [\"attivita%s\"]=\"attività al plurale\", [\"attivita%s\"]=\"attività al plurale\".", uno, due);
        addSpanVerde(message);
        message = String.format("Nella collezione locale mongoDB vengono aggiunte %s le voci delle %s-attività (non presenti nel modulo)", anche, ex);
        addSpanRosso(message);
        message = String.format("Le voci aggiunte vengono recuperate dal modulo '%s' ", moduloTxt);
        addSpanRosso(message);
        message = String.format("Indipendentemente da come sono scritte nel modulo, tutte le attività singolari e plurali sono convertite in %s ", minuscolo);
        addSpanRosso(message);
    }

}// end of crud @Route view class