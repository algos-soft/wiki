package it.algos.vaad23.wizard.scripts;

import com.vaadin.flow.component.combobox.*;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.*;
import com.vaadin.flow.spring.annotation.*;
import static it.algos.vaad23.backend.boot.VaadCost.PROJECT_VAADFLOW;
import static it.algos.vaad23.backend.boot.VaadCost.*;
import it.algos.vaad23.backend.enumeration.*;
import it.algos.vaad23.backend.wrapper.*;
import static it.algos.vaad23.wizard.scripts.WizCost.*;
import org.springframework.beans.factory.config.*;
import org.springframework.context.annotation.Scope;

import java.io.*;
import java.util.*;
import java.util.function.*;


/**
 * Project my-project
 * Created by Algos
 * User: gac
 * Date: lun, 12-ott-2020
 * Time: 12:24
 */
@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class WizDialogNewProject extends WizDialog {

    private static final String LABEL_COMBO_UNO = "Progetti vuoti esistenti (directory IdeaProjects.operativi)";

    private static final String LABEL_COMBO_DUE = "Tutti i progetti esistenti (directory IdeaProjects.operativi)";


    private Consumer<String> confirmHandler;


    public WizDialogNewProject() {
        super();
    }// end of constructor


    /**
     * Legenda iniziale <br>
     * Viene sovrascritta nella sottoclasse che deve invocare PRIMA questo metodo <br>
     */
    @Override
    protected void creaTopLayout() {
        String message;
        topLayout = fixSezione(TITOLO_NUOVO_PROGETTO, "green");
        this.add(topLayout);

        message = "Creazione di un nuovo project. Devi prima creare un new project IntelliJIdea (minuscolo).";
        topLayout.add(htmlService.getSpan(new WrapSpan().message(message).weight(AEFontWeight.bold)));

        message = "Di tipo 'MAVEN' senza selezionare archetype. Rimane il POM vuoto, ma verrà sovrascritto.";
        topLayout.add(htmlService.getSpan(new WrapSpan().message(message).weight(AEFontWeight.bold)));

        H3 titoloH3 = new H3(textService.primaMaiuscola(TITOLO_MODIFICA_PROGETTO));
        titoloH3.getElement().getStyle().set("color", "blue");
        topLayout.add(titoloH3);

        message = "Modifica di un project esistente. Sostituisce completamente il modulo 'vaad23'.";
        topLayout.add(htmlService.getSpan(new WrapSpan().message(message).weight(AEFontWeight.bold)));
    }


    /**
     * Sezione centrale con la scelta del progetto <br>
     * Spazzola la directory 'ideaProjects' <br>
     * Recupera i possibili progetti 'vuoti' <br>
     * Può essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    @Override
    protected void creaSelezioneLayout() {
        selezioneLayout = fixSezione("Selezione...");
        this.add(selezioneLayout);

        String message;
        message = "Seleziona il progetto dalla lista sottostante.";
        selezioneLayout.add(htmlService.getSpan(new WrapSpan().message(message).weight(AEFontWeight.bold).color(AETypeColor.rosso)));

        List<File> progetti;
        String pathDirectory = System.getProperty("user.dir");

        fieldComboProgettiNuovi = new ComboBox<>();
        fieldComboProgettiNuovi.setRequired(true);
        fieldComboProgettiNuovi.setClearButtonVisible(true);
        fieldComboProgettiNuovi.setItemLabelGenerator(File::getName);
        fieldComboProgettiNuovi.setWidth("25em");
        fieldComboProgettiNuovi.setAllowCustomValue(false);
        fieldComboProgettiNuovi.setLabel(LABEL_COMBO_UNO);

        pathDirectory = fileService.findPathDirectory(pathDirectory, PROJECT_VAADFLOW);
        progetti = fileService.getEmptyProjects(pathDirectory);
        if (progetti == null || progetti.size() == 0) {
            progetti = fileService.getAllProjects(pathDirectory);
            fieldComboProgettiNuovi.setLabel(LABEL_COMBO_DUE);
        }

        fieldComboProgettiNuovi.setItems(progetti);
        if (progetti.size() == 1) {
            fieldComboProgettiNuovi.setValue(progetti.get(0));
            sincroProjectNuovi(progetti.get(0));
        }
        fieldComboProgettiNuovi.addValueChangeListener(event -> sincroProjectNuovi(event.getValue()));

        HorizontalLayout rigaLayout = new HorizontalLayout();
        rigaLayout.setAlignItems(FlexComponent.Alignment.BASELINE);
        rigaLayout.add(fieldComboProgettiNuovi);

        if (progetti.size() > 0) {
            selezioneLayout.add(rigaLayout);
        }
        else {
            selezioneLayout.add(htmlService.getSpan(new WrapSpan().message(LABEL_COMBO_UNO)));
            message = "Non ci sono progetti vuoti nella directory.";
            selezioneLayout.add(htmlService.getSpan(new WrapSpan().message(message).weight(AEFontWeight.bold).color(AETypeColor.rosso)));
            sincroProjectNuovi(null);
        }
    }


    @Override
    protected void creaBottomLayout() {
        super.creaBottomLayout();
        this.add(spanConferma);
    }

    protected void creaBottoni() {
        super.creaBottoni();

        cancelButton.getElement().setAttribute("theme", "secondary");
        confirmButton.getElement().setAttribute("theme", "primary");

        spanConferma = new HorizontalLayout();
    }


    private void sincroProjectNuovi(File valueFromProject) {
        String message;
        boolean progettoSelezionato = valueFromProject != null;
        String project = progettoSelezionato ? valueFromProject.getName() : TRE_PUNTI;

        confirmButton.setEnabled(progettoSelezionato);

        spanConferma.removeAll();
        message = String.format("Confermando viene creato/aggiornato il modulo 'vaad23' nel progetto '%s'", project);
        if (progettoSelezionato) {
            spanConferma.add(htmlService.getSpan(new WrapSpan().message(message).color(AETypeColor.rosso)));
        }
        else {
            spanConferma.add(htmlService.getSpan(new WrapSpan().message(message).style(AEFontStyle.italic)));
        }
    }

    /**
     * Apertura del dialogo <br>
     */
    public void open(final Consumer<String> confirmHandler) {
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
            confirmHandler.accept(fieldComboProgettiNuovi.getValue().getPath());
        }
    }

}
