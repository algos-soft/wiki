package it.algos.vaad23.wizard.enumeration;

/**
 * Project vaadflow14
 * Created by Algos
 * User: gac
 * Date: sab, 10-apr-2021
 * Time: 16:36
 */
public enum AEWizUso {
    nullo("Nessuno", "AEWizUso.nullo - Nome usato nelle dir e nei path."),
    flagProject("Project", "AEWizUso.flagProject - Costante per un name/file/directory usato per selezionare i flag di un progetto."),
    flagPackages("Package", "AEWizUso.flagPackages - Costante per un name/file/directory usato per selezionare i flag di un package."),
    ;

    private String tag;

    private String descrizione;


    /**
     * Costruttore <br>
     */
    AEWizUso(String tag, String descrizione) {
        this.tag = tag;
        this.descrizione = descrizione;
    }

    public String getTag() {
        return tag;
    }

    public String getDescrizione() {
        return descrizione;
    }
}
