package it.algos.vaad23.backend.enumeration;

import java.util.*;

/**
 * Project vaadflow14
 * Created by Algos
 * User: gac
 * Date: gio, 21-gen-2021
 * Time: 14:10
 */
public enum AECopy {

    dirDeletingAll("Cancella sempre la vecchia cartella e poi ricopia tutto.", AECopyType.directory),
    dirAddingOnly("Se non esiste, crea la cartella vuota. Aggiunge files e directories senza cancellare quelli esistenti.", AECopyType.directory),
    dirSoloSeNonEsiste("Se esiste già, non fa nulla. Se non esiste, crea la cartella vuota.", AECopyType.directory),
    fileSovrascriveSempreAncheSeEsiste("", AECopyType.file),
    fileSoloSeNonEsiste("", AECopyType.file),
    fileCheckFlagSeEsiste("", AECopyType.file),
    sourceSovrascriveSempreAncheSeEsiste("", AECopyType.source),
    sourceSoloSeNonEsiste("", AECopyType.source),
    sourceCheckFlagSeEsiste("", AECopyType.source),

    ;

    private String descrizione;

    private AECopyType type;

    /**
     * Costruttore <br>
     */
    AECopy(String descrizione, AECopyType type) {
        this.descrizione = descrizione;
        this.type = type;
    }

    public static List<AECopy> getAllEnums() {
        return Arrays.stream(values()).toList();
    }

    public AECopyType getType() {
        return type;
    }

    public String getDescrizione() {
        return descrizione;
    }
}

