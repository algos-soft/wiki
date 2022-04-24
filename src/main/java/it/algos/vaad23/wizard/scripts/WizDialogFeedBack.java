package it.algos.vaad23.wizard.scripts;

import com.vaadin.flow.spring.annotation.*;
import static it.algos.vaad23.backend.boot.VaadCost.PROJECT_VAADFLOW;
import static it.algos.vaad23.backend.boot.VaadCost.*;
import it.algos.vaad23.backend.enumeration.*;
import it.algos.vaad23.backend.wrapper.*;
import static it.algos.vaad23.wizard.scripts.WizCost.*;
import org.springframework.beans.factory.config.*;
import org.springframework.context.annotation.Scope;

import java.util.function.*;

/**
 * Project provider
 * Created by Algos
 * User: gac
 * Date: dom, 25-ott-2020
 * Time: 17:57
 */
@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class WizDialogFeedBack extends WizDialog {
    private Consumer<Boolean> confirmHandler;

    public WizDialogFeedBack() {
        super();
    }// end of constructor


    /**
     * Legenda iniziale <br>
     * Viene sovrascritta nella sottoclasse che deve invocare PRIMA questo metodo <br>
     */
    @Override
    protected void creaTopLayout() {
        String message;
        String srcVaadin23 = System.getProperty("user.dir");
        String currentProject = fileService.estraeClasseFinaleSenzaJava(srcVaadin23).toLowerCase();
        topLayout = fixSezione(TITOLO_FEEDBACK_PROGETTO, "green");
        this.add(topLayout);

        message = String.format("Ricopia la directory %s di %s su %s", TAG_WIZ, currentProject, PROJECT_VAADFLOW);
        topLayout.add(htmlService.getSpan(new WrapSpan().message(message).weight(AEFontWeight.bold)));

        message = String.format("Non modifica la sub-directory %s esistente su %s", DIR_SOURCES, PROJECT_VAADFLOW);
        topLayout.add(htmlService.getSpan(new WrapSpan().message(message).weight(AEFontWeight.bold)));

        message = "Le modifiche sono irreversibili";
        topLayout.add(htmlService.getSpan(new WrapSpan().message(message).color(AETypeColor.rosso).weight(AEFontWeight.bold)));
    }


    protected void creaBottoni() {
        super.creaBottoni();

        cancelButton.getElement().setAttribute("theme", "primary");
        confirmButton.getElement().setAttribute("theme", "error");
        confirmButton.setEnabled(true);
    }


    /**
     * Apertura del dialogo <br>
     */
    public void open(final Consumer<Boolean> confirmHandler) {
        this.confirmHandler = confirmHandler;
        this.getElement().getStyle().set("background-color", "#ffffff");

        super.open();
    }

    /**
     * Esce dal dialogo con due possibilità (a seconda del flag) <br>
     * 1) annulla <br>
     * 2) esegue <br>
     */
    protected void esceDalDialogo(boolean esegue) {
        this.close();
        if (esegue) {
            confirmHandler.accept(true);
        }
    }

}
