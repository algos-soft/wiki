package it.algos.vaad23.ui.dialog;

import com.vaadin.flow.component.*;
import com.vaadin.flow.component.button.*;
import com.vaadin.flow.component.dialog.*;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.icon.*;
import com.vaadin.flow.component.orderedlayout.*;
import com.vaadin.flow.spring.annotation.*;
import static it.algos.vaad23.backend.boot.VaadCost.*;
import org.springframework.beans.factory.config.*;
import org.springframework.context.annotation.Scope;

import javax.annotation.*;

/**
 * Project vaadin23
 * Created by Algos
 * User: gac
 * Date: lun, 21-mar-2022
 * Time: 05:41
 * Se sono 3 bottoni:
 * Cancel
 * Reject
 * Confirm
 * Se sono 2 (in questo caso):
 * Cancel
 * Delete
 */
@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class DialogDelete extends Dialog {

    //--Titolo standard, eventualmente modificabile nelle sottoclassi
    private static String TITOLO = "Delete";

    private static String ANNULLA = "Annulla";

    private static String message = "Vuoi veramente cancellare questo elemento ?";

    private static String additionalMessage = "L'operazione non è reversibile";

    protected final H2 titleField = new H2();

    protected final Div messageLabel = new Div();

    protected final Div extraMessageLabel = new Div();

    public Runnable cancelHandler;

    public Runnable confirmHandler;

    /**
     * Titolo del dialogo <br>
     */
    protected String title;

    /**
     * Corpo centrale del Dialog <br>
     * Placeholder (eventuale, presente di default) <br>
     */
    protected VerticalLayout bodyPlaceHolder = new VerticalLayout();

    /**
     * Barra dei bottoni di comando <br>
     * Placeholder (eventuale, presente di default) <br>
     */
    protected HorizontalLayout bottomLayout = new HorizontalLayout();

    protected Button cancelButton = new Button(ANNULLA);

    protected Button deleteButton = new Button(TITOLO);

    protected Button confirmButton = new Button(TITOLO);

    /**
     * Costruttore <br>
     */
    public DialogDelete() {
        this.title = TITOLO;
    }// end of constructor

    /**
     * Costruttore <br>
     */
    public DialogDelete(String message) {
        this.title = TITOLO;
        this.message = String.format("Vuoi veramente cancellare %s ?", message);
    }// end of constructor


    @PostConstruct
    protected void inizia() {
        this.setCloseOnEsc(true);
        this.setCloseOnOutsideClick(true);
        this.getElement().getClassList().add("confirm-dialog");

        //--Titolo placeholder del dialogo
        titleField.setText(title);
        this.add(titleField);

        //--Corpo centrale del Dialog
        this.add(bodyPlaceHolder);

        //--spazio per distanziare i bottoni sottostanti
        this.add(new H3());

        //--Barra placeholder dei bottoni, creati e regolati
        this.add(bottomLayout);
    }


    /**
     * Corpo centrale del Dialog, alternativo al Form <br>
     *
     * @param message           Detail message
     * @param additionalMessage Additional message (optional, may be empty)
     */
    protected void fixBodyLayout(String message, String additionalMessage) {
        bodyPlaceHolder.setPadding(false);
        bodyPlaceHolder.setSpacing(true);
        bodyPlaceHolder.setMargin(false);
        VerticalLayout bodyLayout = new VerticalLayout();
        bodyLayout.setPadding(false);
        bodyLayout.setSpacing(true);
        bodyLayout.setMargin(false);
        bodyPlaceHolder.removeAll();

        if (!message.equals(VUOTA)) {
            messageLabel.setText(message);
            bodyLayout.add(messageLabel);
        }

        if (!additionalMessage.equals(VUOTA)) {
            extraMessageLabel.setText(additionalMessage);
            bodyLayout.add(extraMessageLabel);
        }

        bodyPlaceHolder.add(bodyLayout);
    }

    /**
     * Barra dei bottoni
     */
    protected void fixBottomLayout() {
        bottomLayout.setClassName("buttons");
        bottomLayout.setPadding(false);
        bottomLayout.setSpacing(true);
        bottomLayout.setMargin(false);
        bottomLayout.setClassName("confirm-dialog-buttons");

        Label spazioVuotoEspandibile = new Label("");
        bottomLayout.add(spazioVuotoEspandibile);
        bottomLayout.setFlexGrow(1, spazioVuotoEspandibile);

        cancelButton.getElement().setAttribute("theme", "primary");
        cancelButton.addClickListener(e -> cancellaHandler());
        cancelButton.setIcon(new Icon(VaadinIcon.ARROW_LEFT));
        cancelButton.addClickShortcut(Key.ARROW_LEFT);
        bottomLayout.add(cancelButton);

        deleteButton.getElement().setAttribute("theme", "error");
        deleteButton.addClickListener(e -> confermaHandler());
        deleteButton.setIcon(new Icon(VaadinIcon.CLOSE_CIRCLE));
        bottomLayout.add(deleteButton);
    }


    /**
     * Apre il dialogo <br>
     *
     * @param deleteHandler The confirmation handler function for deleting entities
     */
    public void open(Runnable deleteHandler) {
        this.confirmHandler = deleteHandler;

        //--Body placeholder
        this.fixBodyLayout(message, additionalMessage);

        //--Barra placeholder dei bottoni, creati e regolati
        this.fixBottomLayout();

        super.open();
    }

    public void cancellaHandler() {
        if (cancelHandler != null) {
            cancelHandler.run();
        }
        close();
    }


    public void confermaHandler() {
        if (confirmHandler != null) {
            confirmHandler.run();
        }
        close();
    }

}
