package it.algos.vaad23.wizard;

import com.vaadin.flow.component.button.*;
import com.vaadin.flow.component.checkbox.*;
import com.vaadin.flow.component.dependency.*;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.*;
import com.vaadin.flow.router.*;
import com.vaadin.flow.spring.annotation.*;
import it.algos.vaad23.backend.boot.*;
import static it.algos.vaad23.backend.boot.VaadCost.*;
import it.algos.vaad23.backend.exception.*;
import it.algos.vaad23.backend.service.*;
import it.algos.vaad23.backend.wrapper.*;
import it.algos.vaad23.ui.dialog.*;
import it.algos.vaad23.ui.views.*;
import it.algos.vaad23.wizard.scripts.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.context.*;

import javax.annotation.*;
import java.util.*;

/**
 * Project vaadin23
 * Created by Algos
 * User: gac
 * Date: gio, 07-apr-2022
 * Time: 21:27
 * Utilizzato da Vaadin23 direttamente, per creare/aggiornare un nuovo progetto esterno <br>
 * Utilizzato dal progetto corrente, per importare/aggiornare il codice da Vaadin23 <br>
 * Utilizzato dal progetto corrente, per creare/aggiornare nuovi packages forse <br>
 */
@SpringComponent
@Route(value = VaadCost.TAG_WIZ, layout = MainLayout.class)
@CssImport("./styles/shared-styles.css")
public class WizardView extends VerticalLayout {

    /**
     * Istanza unica di una classe @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) di servizio <br>
     * Iniettata automaticamente dal framework SpringBoot/Vaadin con l'Annotation @Autowired <br>
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
    public FileService fileService;

    private boolean projectBaseFlow;

    private String updateProject;

    /**
     * Questa classe viene costruita partendo da @Route e NON dalla catena @Autowired di SpringBoot <br>
     */
    public WizardView() {
        super();
    }// end of Vaadin/@Route constructor


    @PostConstruct
    protected void postConstruct() {
        initView();
    }

    /**
     * Qui va tutta la logica iniziale della view principale <br>
     * Per adesso regolo SOLO le costanti base <br>
     * Per adesso posso solo selezionare se sono in VaadFlow14 oppure no <br>
     * In base a questo decido quale paragrafo/possibilità mostrare <br>
     */
    protected void initView() {
        this.setMargin(true);
        this.setPadding(false);
        this.setSpacing(false);

        this.titolo();

        this.projectBaseFlow = isProjectBaseFlow();
        if (projectBaseFlow) {
            this.paragrafoNewProject();
        }
        else {
            this.paragrafoUpdateProject();
        }

        //--spazio per distanziare i paragrafi
        this.add(new H3());

        //        this.paragrafoNewPackage();

        if (!projectBaseFlow) {
            paragrafoFeedBackWizard();
        }

    }

    public boolean isProjectBaseFlow() {
        String srcVaadin23 = System.getProperty("user.dir");
        updateProject = fileService.estraeClasseFinaleSenzaJava(srcVaadin23).toLowerCase();

        return updateProject.equals(PROJECT_VAADFLOW);
    }

    public void titolo() {
        H1 titolo = new H1("Gestione Wizard");
        titolo.getElement().getStyle().set("color", "green");
        this.add(titolo);
    }


    public void paragrafoNewProject() {
        VerticalLayout layout = new VerticalLayout();
        layout.setMargin(false);
        layout.setPadding(false);
        layout.setSpacing(false);
        H3 paragrafo = new H3(String.format("%s%s%s", WizCost.TITOLO_NUOVO_PROGETTO, SLASH, WizCost.TITOLO_MODIFICA_PROGETTO));
        paragrafo.getElement().getStyle().set("color", "blue");

        layout.add(new Label("Crea un nuovo project IntelliJIdea, nella directory 'IdeaProjects'."));

        Button bottone = new Button("New/Update project");
        bottone.getElement().setAttribute("theme", "primary");
        bottone.addClickListener(event -> openNewProject());

        this.add(paragrafo);
        layout.add(bottone);
        this.add(layout);
    }

    private void openNewProject() {
        appContext.getBean(WizDialogNewProject.class).open(this::elaboraNewProject);
    }

    private void elaboraNewProject(final String pathNewUpdateProject) {
        appContext.getBean(WizElaboraNewProject.class).esegue(pathNewUpdateProject);
    }


    public void paragrafoUpdateProject() {
        VerticalLayout layout = new VerticalLayout();
        layout.setMargin(false);
        layout.setPadding(false);
        layout.setSpacing(false);
        H3 paragrafo = new H3(WizCost.TITOLO_MODIFICA_QUESTO_PROGETTO);
        paragrafo.getElement().getStyle().set("color", "blue");

        layout.add(new Label("Aggiorna il modulo base 'vaad23' di questo progetto"));

        Button bottone = new Button("Update project");
        bottone.getElement().setAttribute("theme", "primary");
        bottone.addClickListener(event -> openUpdateProject());

        this.add(paragrafo);
        layout.add(bottone);
        this.add(layout);
    }

    private void openUpdateProject() {
        appContext.getBean(WizDialogUpdateProject.class, updateProject).open(this::elaboraUpdateProject);
    }

    private void elaboraUpdateProject(final LinkedHashMap<String, Checkbox> mappaCheckbox) {
        appContext.getBean(WizElaboraUpdateProject.class, updateProject).esegue(mappaCheckbox);
    }

    public void paragrafoFeedBackWizard() {
        VerticalLayout layout = new VerticalLayout();
        layout.setMargin(false);
        layout.setPadding(false);
        layout.setSpacing(false);
        H3 paragrafo = new H3(WizCost.TITOLO_FEEDBACK_PROGETTO);
        paragrafo.getElement().getStyle().set("color", "blue");
        this.add(paragrafo);

        layout.add(new Label("Ricopia su Vaadin23 la directory 'wizard' di questo progetto"));

        Button bottone = new Button(String.format("Send back to %s", PROJECT_VAADFLOW));
        bottone.getElement().setAttribute("theme", "primary");
        bottone.addClickListener(event -> openFeedBack());

        layout.add(bottone);
        this.add(layout);
        this.add(new H2());
    }

    private void openFeedBack() {
        appContext.getBean(WizDialogFeedBack.class).open(this::elaboraFeedBack);
    }

    private void elaboraFeedBack(final boolean nonUsato) {
        appContext.getBean(WizElaboraFeedBack.class).esegue();
    }

    public void paragrafoNewPackage() {
        VerticalLayout layout = new VerticalLayout();
        layout.setMargin(false);
        layout.setPadding(false);
        layout.setSpacing(false);
        H3 paragrafo;
        Label label;

        paragrafo = new H3(WizCost.TITOLO_NEW_PACKAGE);
        paragrafo.getElement().getStyle().set("color", "blue");
        label = new Label("Creazione di un nuovo package. Regola alcuni flags di possibili opzioni");

        Button bottone = new Button(String.format("New package vaadin23"));
        bottone.getElement().setAttribute("theme", "primary");
        layout.add(label, new HorizontalLayout(bottone));

        this.add(paragrafo);
        this.add(layout);
    }

    private void elaboraNewPackage() {
        //        elaboraNewPackage.esegue();
        //        dialogNewPackage.close();
    }

}
