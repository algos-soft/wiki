package it.algos.vaad23.wizard.scripts;

import com.vaadin.flow.component.*;
import com.vaadin.flow.component.button.*;
import com.vaadin.flow.component.combobox.*;
import com.vaadin.flow.component.dialog.*;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.icon.*;
import com.vaadin.flow.component.notification.*;
import com.vaadin.flow.component.orderedlayout.*;
import it.algos.vaad23.backend.service.*;
import it.algos.vaad23.ui.dialog.*;
import static it.algos.vaad23.wizard.scripts.WizCost.*;
import org.springframework.beans.factory.annotation.*;

import javax.annotation.*;
import java.io.*;
import java.util.function.*;


/**
 * Project vaadflow
 * Created by Algos
 * User: gac
 * Date: lun, 13-apr-2020
 * Time: 05:17
 * <p>
 * Classe astratta per alcuni dialoghi di regolazione dei parametri per il Wizard <br>
 */
public abstract class WizDialog extends Dialog {

    //    /**
    //     * Istanza unica di una classe @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) di servizio <br>
    //     * Iniettata automaticamente dal framework SpringBoot/Vaadin con l'Annotation @Autowired <br>
    //     * Disponibile DOPO il ciclo init() del costruttore di questa classe <br>
    //     */
    //    @Autowired
    //    public WizService wizService;

    /**
     * Istanza unica di una classe @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) di servizio <br>
     * Iniettata automaticamente dal framework SpringBoot/Vaadin con l'Annotation @Autowired <br>
     * Disponibile DOPO il ciclo init() del costruttore di questa classe <br>
     */
    @Autowired
    public LogService logger;

    //    /**
    //     * Istanza unica di una classe @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) di servizio <br>
    //     * Iniettata automaticamente dal framework SpringBoot/Vaadin con l'Annotation @Autowired <br>
    //     * Disponibile DOPO il ciclo init() del costruttore di questa classe <br>
    //     */
    //    @Autowired
    //
    //    public DateService date;

    /**
     * Istanza unica di una classe @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) di servizio <br>
     * Iniettata automaticamente dal framework SpringBoot/Vaadin con l'Annotation @Autowired <br>
     * Disponibile DOPO il ciclo init() del costruttore di questa classe <br>
     */
    @Autowired
    public HtmlService htmlService;

    /**
     * Istanza unica di una classe @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) di servizio <br>
     * Iniettata automaticamente dal framework SpringBoot/Vaadin con l'Annotation @Autowired <br>
     * Disponibile DOPO il ciclo init() del costruttore di questa classe <br>
     */
    @Autowired
    public ClassService classService;

    /**
     * Istanza unica di una classe @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) di servizio <br>
     * Iniettata automaticamente dal framework SpringBoot/Vaadin con l'Annotation @Autowired <br>
     * Disponibile DOPO il ciclo init() del costruttore di questa classe <br>
     */
    @Autowired
    public WizService wizService;

    /**
     * Istanza unica di una classe @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) di servizio <br>
     * Iniettata automaticamente dal framework SpringBoot/Vaadin con l'Annotation @Autowired <br>
     * Disponibile DOPO il ciclo init() del costruttore di questa classe <br>
     */
    @Autowired
    protected ArrayService array;

    /**
     * Istanza unica di una classe @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) di servizio <br>
     * Iniettata automaticamente dal framework SpringBoot/Vaadin con l'Annotation @Autowired <br>
     * Disponibile DOPO il ciclo init() del costruttore di questa classe <br>
     */
    @Autowired
    protected TextService textService;

    /**
     * Istanza unica di una classe @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) di servizio <br>
     * Iniettata automaticamente dal framework SpringBoot/Vaadin con l'Annotation @Autowired <br>
     * Disponibile DOPO il ciclo init() del costruttore di questa classe <br>
     */
    @Autowired
    protected FileService fileService;


    //    protected LinkedHashMap<String, Checkbox> mappaCheckboxOld;
    //
    //    protected LinkedHashMap<String, WizBox> mappaWizBox;
    //
    //    protected boolean isNuovoProgetto;
    //
    //    protected boolean isNuovoPackage;
    //
    //    protected boolean isStartThisProgetto;

    protected Button confirmButton;

    protected Button cancelButton;


    protected Consumer annullaHandler;

    //    protected Button buttonForzaDirectory;
    //
    //    protected TextField fieldPackageName;
    //
    //    protected TextField fieldProjectNameUpper;
    //

    /**
     * Sezione superiore,coi titoli e le info <br>
     */
    protected VerticalLayout topLayout;

    /**
     * Sezione centrale con la scelta del progetto <br>
     */
    protected VerticalLayout selezioneLayout;

    /**
     * Sezione centrale con la selezione dei flags <br>
     */
    protected VerticalLayout checkBoxLayout;

    /**
     * Sezione inferiore coi bottoni di uscita e conferma <br>
     */
    protected VerticalLayout bottomLayout;

    //    protected H3 titoloCorrente;

    protected ComboBox<File> fieldComboProgettiNuovi;

    //    protected ComboBox<AEProgetto> fieldComboProgetti;
    //
    //    protected ComboBox<String> fieldComboPackages;
    //
    //    protected String nomeModulo;
    protected HorizontalLayout spanConferma;

    /**
     * Regolazioni grafiche
     */
    @PostConstruct
    protected void inizia() {
        this.setCloseOnEsc(true);
        this.setCloseOnOutsideClick(true);
        this.removeAll();
        this.regolazioniIniziali();

        //--creazione iniziale dei bottoni (chiamati anche da selezioneLayout)
        this.creaBottoni();

        //--info di avvisi iniziali
        this.creaTopLayout();

        //--solo per newProject
        this.creaSelezioneLayout();

        //--checkbox di spunta
        this.creaCheckBoxLayout();

        //--spazio per distanziare i bottoni
        this.add(new H3());

        //--creazione bottoni di comando
        this.creaBottomLayout();

    }// end of method


    protected void regolazioniIniziali() {
        //        //--pulisce le costanti dei packages
        //        AEPackage.reset();
        //
        //        //-recupera il progetto target
        //        AEWizCost.pathTargetProjectRoot.setValue(AEWizCost.pathCurrentProjectRoot.get());
        //        AEWizCost.nameTargetProjectUpper.setValue(AEWizCost.nameCurrentProjectUpper.get());
        //        if (AEFlag.isBaseFlow.is()) {
        //            AEWizCost.nameTargetProjectModulo.setValue(nomeModulo);
        //        }
        //        else {
        //            AEWizCost.nameTargetProjectModulo.setValue(AEWizCost.nameCurrentProjectModulo.get());
        //        }
        //
        //        //--regola tutti i valori automatici, dopo aver inserito quelli fondamentali
        //        AEWizCost.fixValoriDerivati();
        //
        //        wizService.printInfoStart();
        //        wizService.printInfoCheck();
    }

    /**
     * Controlla che il dialogo possa usare alcuni flag compatibili (tra di loro) <br>
     */
    protected boolean check() {
        boolean valido = true;

        //        //--Deve essere o un progetto o un package
        //        valido = valido && (AEFlag.isProject.is() || AEFlag.isPackage.is());
        //
        //        //--Se è un progetto, deve essere nuovo o update
        //        if (AEFlag.isProject.is()) {
        //            valido = valido && (AEFlag.isNewProject.is() != AEFlag.isUpdateProject.is());
        //        }
        //
        //        //--Se è un package, deve essere nuovo o update
        //        if (AEFlag.isPackage.is()) {
        //            valido = valido && (AEFlag.isNewPackage.is() != AEFlag.isUpdatePackage.is());
        //        }
        //
        //        if (!valido) {
        //            wizService.printInfo("Blocco entrata dialogo");
        //            logger.warn("Il dialogo non è stato aperto perché alcuni flags non sono validi per operare correttamente");
        //        }

        return valido;
    }


    /**
     * Sezione superiore,coi titoli e le info <br>
     * Legenda iniziale <br>
     * Può essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    protected void creaTopLayout() {
    }// end of method


    /**
     * Sezione centrale con la scelta del progetto <br>
     * Spazzola la directory 'ideaProjects' <br>
     * Recupera i possibili progetti 'vuoti' <br>
     * Può essere sovrascritto, invocando PRIMA il metodo della superClasse <br>
     */
    protected void creaSelezioneLayout() {
    }


    /**
     * Sezione centrale con la selezione dei flags <br>
     * Crea i checkbox di controllo <br>
     * Spazzola (nella sottoclasse) la Enumeration per aggiungere solo i checkbox adeguati: <br>
     * newProject
     * updateProject
     * newPackage
     * updatePackage
     * Spazzola la Enumeration e regola a 'true' i checkBox secondo il flag 'isAcceso' <br>
     * Può essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    protected void creaCheckBoxLayout() {
        //        checkBoxLayout = fixSezione("Flags di regolazione");
        //        this.add(checkBoxLayout);
        //        mappaWizBox = new LinkedHashMap<>();
    }


    protected void creaBottoni() {
        cancelButton = new Button("Annulla", event -> esceDalDialogo(false));
        cancelButton.setIcon(VaadinIcon.ARROW_LEFT.create());
        cancelButton.addClickShortcut(Key.ARROW_LEFT);
        cancelButton.setWidth(NORMAL_WIDTH);
        cancelButton.setHeight(NORMAL_HEIGHT);
        cancelButton.setVisible(true);

        confirmButton = new Button("Conferma", event -> esceDalDialogo(true));
        confirmButton.setIcon(VaadinIcon.EDIT.create());
        confirmButton.setWidth(NORMAL_WIDTH);
        confirmButton.setHeight(NORMAL_HEIGHT);
        confirmButton.setVisible(true);
        confirmButton.setEnabled(false);
    }


    protected void creaBottomLayout() {
        bottomLayout = new VerticalLayout();
        bottomLayout.setPadding(false);
        bottomLayout.setSpacing(false);
        bottomLayout.setMargin(false);
        HorizontalLayout layoutFooter = new HorizontalLayout();
        layoutFooter.setPadding(false);
        layoutFooter.setMargin(false);
        layoutFooter.setMargin(false);

        layoutFooter.add(cancelButton, confirmButton);
        bottomLayout.add(layoutFooter);
        this.add(bottomLayout);
    }


    /**
     * Aggiunge al layout i checkbox di controllo <br>
     */
    protected void addCheckBoxMap() {
        //        checkBoxLayout.removeAll();
        //        for (String key : mappaWizBox.keySet()) {
        //            checkBoxLayout.add(mappaWizBox.get(key));
        //        }
    }


    /**
     * Chiamato alla dismissione del dialogo <br>
     * Regola tutti i valori delle enumeration AEDir, AECheck e EAToken che saranno usati da: <br>
     * WizElaboraNewProject, WizElaboraUpdateProject,WizElaboraNewPackage, WizElaboraUpdatePackage <br>
     */
    protected boolean regolazioniFinali() {
        boolean status = true;

        //        status = status && this.regolaAEWizCost();
        //
        //        //        status = status && this.regolaAEDir();
        //        status = status && this.regolaAECheck();
        //        status = status && this.regolaAEPackage();
        //        status = status && this.regolaAEToken();
        //        AEModulo.fixValues(AEWizCost.pathTargetProjectModulo.get(), AEWizCost.nameTargetProjectUpper.get());
        //
        //        wizService.printInfoCheck();
        return status;
    }

    /**
     * Chiamato alla dismissione del dialogo <br>
     * Può essere sovrascritto, SENZA invocare il metodo della superclasse <br>
     */
    protected boolean regolaAEWizCost() {
        return true;
    }


    /**
     * Chiamato alla dismissione del dialogo <br>
     * Resetta i valori regolabili della Enumeration AEDir <br>
     * Elabora tutti i valori della Enumeration AEDir dipendenti dal nome del progetto <br>
     * Verranno usati da: <br>
     * WizElaboraNewProject, WizElaboraUpdateProject,WizElaboraNewPackage, WizElaboraUpdatePackage <br>
     * Può essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    protected boolean regolaAEDir() {
        return true;
    }


    /**
     * Chiamato alla dismissione del dialogo <br>
     * Elabora tutti i valori della Enumeration AECheck dipendenti dal nome del progetto <br>
     * Verranno usati da: <br>
     * WizElaboraNewProject, WizElaboraUpdateProject, WizElaboraNewPackage, WizElaboraUpdatePackage <br>
     */
    protected boolean regolaAECheck() {
        //        for (AECheck check : AECheck.values()) {
        //            if (mappaWizBox != null && mappaWizBox.get(check.name()) != null) {
        //                check.setAcceso(mappaWizBox.get(check.name()).is());
        //                if (check.isFieldAssociato()) {
        //                    WizBox alfa = mappaWizBox.get(check.name());
        //                    String beta = alfa.getValue();
        //                    check.setFieldName(beta.toLowerCase());
        //                }
        //            }
        //        }

        return true;
    }


    /**
     * Chiamato alla dismissione del dialogo <br>
     * Regola alcuni valori della Enumeration EAToken che saranno usati da WizElaboraNewProject e WizElaboraUpdateProject <br>
     */
    protected boolean regolaAEToken() {
        //        String projectNameUpper;
        //        String projectModuloLower;
        //        String packageName;
        //        String fileName;
        //        AEToken.reset();
        //
        //        projectNameUpper = AEWizCost.nameTargetProjectUpper.isValida() ? AEWizCost.nameTargetProjectUpper.get() : VUOTA;
        //        projectModuloLower = AEWizCost.nameTargetProjectModulo.isValida() ? AEWizCost.nameTargetProjectModulo.get() : VUOTA;
        //        packageName = AEWizCost.nameTargetPackage.isValida() ? AEWizCost.nameTargetPackage.get() : VUOTA;
        //        fileName = AEWizCost.nameTargetFileUpper.get();
        //        return wizService.regolaAEToken(projectNameUpper, projectModuloLower, packageName, fileName);

        return false;
    }

    /**
     * Chiamato alla dismissione del dialogo <br>
     * Elabora tutti i valori della Enumeration AEPackage dipendenti dalla classe del package <br>
     * Verranno usati da: <br>
     * WizElaboraNewProject, WizElaboraUpdateProject, WizElaboraNewPackage, WizElaboraUpdatePackage <br>
     */
    protected boolean regolaAEPackage() {
        //        WizBox wizBox;
        //        String fieldName;
        //
        //        if (mappaWizBox == null) {
        //            return false;
        //        }
        //
        //        for (AEPackage pack : AEPackage.values()) {
        //            wizBox = mappaWizBox.get(pack.name());
        //            if (wizBox != null) {
        //                pack.setAcceso(wizBox.is());
        //                if (pack.isProperty()) {
        //                    fieldName = wizBox.getValue();
        //                    if (text.isValid(fieldName)) {
        //                        pack.setFieldName(fieldName);
        //                    }
        //                }
        //            }
        //        }
        return true;
    }

    protected VerticalLayout fixSezione(String titolo) {
        return fixSezione(titolo, "black");
    }


    protected VerticalLayout fixSezione(String titolo, String color) {
        VerticalLayout layoutTitolo = new VerticalLayout();

        H3 titoloH3 = new H3(textService.primaMaiuscola(titolo));
        titoloH3.getElement().getStyle().set("color", "blue");

        layoutTitolo.setMargin(false);
        layoutTitolo.setSpacing(false);
        layoutTitolo.setPadding(false);
        layoutTitolo.add(titoloH3);
        layoutTitolo.getElement().getStyle().set("color", color);

        return layoutTitolo;
    }

    /**
     * Chiamato alla dismissione del dialogo <br>
     * Recupera i valori inseriti dall'utente <br>
     * Regola i valori regolabili della Enumeration AEWizCost <br>
     * Verranno usati da: <br>
     * WizElaboraNewProject, WizElaboraUpdateProject,WizElaboraNewPackage, WizElaboraUpdatePackage <br>
     * Metodo che NON può essere sovrascritto <br>
     *
     * @param pathProject            (obbligatorio) path completo del progetto target. Da cui si ricava nameTargetProjectModulo (file.estraeClasseFinale).
     * @param nameTargetProjectUpper (obbligatorio) nome maiuscolo del progetto. Può essere diverso da nameTargetProjectModulo (Es. vaadwiki e Wiki)
     * @param packageName            (facoltativo) nome del package da creare/modificare. Eventualmente con sub-directory (separatore slash)
     *
     * @return false se manca uno dei due parametri obbligatori
     */
    @Deprecated
    protected boolean fixValoriInseriti(final String pathProject, final String nameTargetProjectUpper, final String packageName) {

        if (textService.isEmpty(pathProject) || textService.isEmpty(nameTargetProjectUpper)) {
            return false;
        }

        //        //--inserisce il path completo del progetto selezionato nella Enumeration
        //        //--dal path completo deriva il valore di directory/modulo -> nameTargetProjectModulo
        //        //--mentre il nome (maiuscolo) del progetto deve essere inserito -> nameTargetProjectUpper
        //        //--perché potrebbe essere diverso (Es. vaadwiki -> Wiki)
        //        AEWizCost.pathTargetProjectRoot.setValue(pathProject);
        //
        //        //--inserisce  il nome (maiuscolo) del progetto
        //        //--perché potrebbe essere diverso dal valore di directory/modulo (Es. vaadwiki -> Wiki)
        //        AEWizCost.nameTargetProjectUpper.setValue(text.primaMaiuscola(nameTargetProjectUpper));
        //
        //        //--inserisce il nome (eventuale) del package da creare/modificare
        //        if (text.isValid(packageName)) {
        //            AEWizCost.nameTargetPackagePunto.setValue(text.fixSlashToPunto(packageName));
        //        }
        //
        //        //--regola tutti i valori automatici, dopo aver inserito quelli fondamentali
        //        AEWizCost.fixValoriDerivati();

        //        wizService.printProgetto();
        return true;
    }


    /**
     * Esce dal dialogo con due possibilità (a seconda del flag) <br>
     * 1) annulla <br>
     * 2) esegue <br>
     */
    protected void esceDalDialogo(boolean esegue) {
        if (esegue) {
            //            if (!regolazioniFinali()) {
            //                if (AEFlag.isNewPackage.is()) {
            //                    logger.info("Manca il nome del nuovo package che non può quindi essere creato ", this.getClass(), "esceDalDialogo");
            //                    //                }
            //                    //                else {
            //                    //                    logger.info("Mancano alcuni dati essenziali per l'elaborazione richiesta, che è stata quindi abortita", this.getClass(), "esceDalDialogo");
            //                }
            //
            //                this.close();
            //                return;
            //            }
            //            //            wizService.printInfoCompleto("Uscita del dialogo");
            //
            this.close();
        }
        else {
            this.close();
            Avviso.show("Dialogo annullato").addThemeVariants(NotificationVariant.LUMO_PRIMARY);
            //        }
        }
    }

}

