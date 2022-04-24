package it.algos.vaad23.wizard.enumeration;

/**
 * Project wikibio
 * Created by Algos
 * User: gac
 * Date: mer, 10-mar-2021
 * Time: 16:07
 */
public enum AEWizValue {

    costante("Costanti", false, false, "AEWizValue.costante - Costanti con valore statico fisso e non modificabile. Possono (devono) cambiare su un altro computer."),
    calcolato("Calcolati", false, false, "AEWizValue.calcolato - Valori in base al programma in uso. Regolati automaticamente alla partenza di Wizard (prima dei dialoghi) e poi statici."),
    inserito("Inseriti", true, true, "AEWizValue.inserito - Valori in base al project o al package selezionato. Inseriti dall'utente (con un combobox). Regolati all'uscita del dialogo."),
    derivato("Derivati", false, true, "AEWizValue.derivato - Valori in base al project o al package selezionato. Derivati automaticamente da un precedente valore inserito dall'utente."),

    //    nonModificabile("Costanti", false, "Valore statico fisso e non modificabile."),
    //    regolatoSistema("Finali", true,  "Dipende dal programma in uso. Regolato alla partenza di Wizard (prima dei dialoghi) e poi statico."),
    //    regolatoSistemaAutomatico("Finali", true,  "Dipende dal programma in uso. Regolato da un precedente valore dopo che è stato precedentemente inserito."),
    //    necessarioProgetto("Variabili", true, "Dipende dal project selezionato. Inserito dall'utente (con un combobox). Regolato all'uscita del dialogo."),
    //    necessarioProgettoAutomatico("Variabili", true, "Dipende dal project selezionato. Regolato da un precedente valore dopo che è stato precedentemente inserito."),
    //    necessarioPackage("Variabili", true, "Dipende dal package selezionato. Inserito dall'utente (con un combobox). Regolato all'uscita del dialogo."),
    //    necessarioPackageAutomatico("Variabili", true, "Dipende dal package selezionato. Regolato da un precedente valore dopo che è stato precedentemente inserito."),

    //    necessarioEntrambi("Variabili", true, "Dipende dal project e/o dal package selezionato. Inserito dall'utente (con un combobox). Regolato all'uscita del dialogo."),
    //    necessarioEntrambiAutomatico("Variabili", true, "Dipende dal project e/o dal package selezionato. Regolato da un precedente valore dopo che è stato precedentemente inserito."),
    ;

    private String tag;

    private boolean serveInserireValore;

    private boolean serveValoreValido;

    private String descrizione;


    AEWizValue(String tag, boolean serveInserireValore, boolean serveValoreValido, String descrizione) {
        this.tag = tag;
        this.serveInserireValore = serveInserireValore;
        this.serveValoreValido = serveValoreValido;
        this.descrizione = descrizione;
    }

    public String getTag() {
        return tag;
    }

    public boolean isServeInserireValore() {
        return serveInserireValore;
    }

    public boolean isServeValoreValido() {
        return serveValoreValido;
    }

    public String getDescrizione() {
        return descrizione;
    }

}
