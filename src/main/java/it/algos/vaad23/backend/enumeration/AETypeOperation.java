package it.algos.vaad23.backend.enumeration;

import static it.algos.vaad23.backend.boot.VaadCost.*;

/**
 * Project vaadflow
 * Created by Algos
 * User: gac
 * Date: sab, 03-nov-2018
 * Time: 16:22
 * The operations supported by this dialog.
 * Delete is enabled when editing an already existing item.
 */
public enum AETypeOperation {
    listNoForm(VUOTA, VUOTA, false, false, false),
    newEdit(VUOTA, VUOTA, true, true, false),
    newEditNoLog(VUOTA, VUOTA, true, true, false),
    addNew("New", "add", true, false, false),
    edit("Edit", "edit", true, true, true),
    editProfile("Edit", "edit", true, false, false),
    editNoDelete("Edit", "edit", true, false, true),
    editDaLink("Edit", "edit", true, true, false),
    showOnly("Mostra", "mostra", false, false, true);

    private final String nameInTitle;

    private final String nameInText;

    private final boolean saveEnabled;

    private final boolean deleteEnabled;

    private final boolean possibileUsoFrecce;


    AETypeOperation(String nameInTitle, String nameInText, boolean saveEnabled, boolean deleteEnabled, boolean possibileUsoFrecce) {
        this.nameInTitle = nameInTitle;
        this.nameInText = nameInText;
        this.saveEnabled = saveEnabled;
        this.deleteEnabled = deleteEnabled;
        this.possibileUsoFrecce = possibileUsoFrecce;
    }

    public static boolean contiene(String nome) {
        boolean contiene = false;

        for (AETypeOperation eaOperation : AETypeOperation.values()) {
            if (eaOperation.name().equals(nome)) {
                contiene = true;
            }
        }

        return contiene;
    }

    public String getNameInTitle() {
        return nameInTitle;
    }


    public String getNameInText() {
        return nameInText;
    }


    public boolean isSaveEnabled() {
        return saveEnabled;
    }


    public boolean isDeleteEnabled() {
        return deleteEnabled;
    }

    public boolean isPossibileUsoFrecce() {
        return possibileUsoFrecce;
    }
}

