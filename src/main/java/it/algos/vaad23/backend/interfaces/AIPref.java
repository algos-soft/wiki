package it.algos.vaad23.backend.interfaces;

/**
 * Project vaadin23
 * Created by Algos
 * User: gac
 * Date: lun, 07-mar-2022
 * Time: 11:44
 */
public interface AIPref {

    /**
     * Stringa di valori (text) da usare per memorizzare la preferenza <br>
     * La stringa è composta da tutti i valori separati da virgola <br>
     * Poi, separato da punto e virgola viene il valore selezionato di default <br>
     *
     * @return stringa di valori e valore di default
     */
    String getPref();

}// end of interface

