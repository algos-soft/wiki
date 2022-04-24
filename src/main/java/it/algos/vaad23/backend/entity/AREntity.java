package it.algos.vaad23.backend.entity;

/**
 * Project vaadin23
 * Created by Algos
 * User: gac
 * Date: ven, 01-apr-2022
 * Time: 11:58
 */
public abstract class AREntity extends AEntity {

    /**
     * flag per le collezioni che usano 'reset' e 'leggono' i dati
     * true se la entities viene costruita dal programma (all'avvio o dal bottone reset)
     * false se viene inserita direttamente dall'utente
     * di default reset=false
     */
    //        @Indexed()
    //        @AIField(type = AETypeField.booleano, typeBool = AETypeBoolField.checkBox)
    //        @AIColumn(typeBool = AETypeBoolCol.checkBox, header = "R.", widthEM = 5)
    public boolean reset;

}
