package it.algos.vaad23.backend.wrapper;


import static it.algos.vaad23.backend.boot.VaadCost.*;
import it.algos.vaad23.backend.enumeration.*;

import java.util.*;

/**
 * Project vaadflow15
 * Created by Algos
 * User: gac
 * Date: sab, 02-mag-2020
 * Time: 20:16
 */
public class WrapParametro {

    /**
     * Valore del primo segmento. Obbligatorio <br>
     * La prima stringa DOPO lo 'slash' <br>
     * Nelle classi specifiche: xxx per la lista e xxxForm per il form <br>
     * Nelle classi generiche: list oppure form <br>
     */
    private String primoSegmento;

    /**
     * Valore del parametro unico. Facoltativo <br>
     * Poco usato <br>
     */
    private String singleParameter;

    /**
     * Mappa semplice di parametri SINGOLI (UN solo valore per tipo) <br>
     */
    private Map<String, String> parametersMap;

    /**
     * Mappa complessa di parametri COMPOSTI (una LISTA di valori per tipo) (poco usata) <br>
     */
    private Map<String, List<String>> multiParametersMap;

    /**
     * Tipologia del/dei parametri di questa istanza <br>
     */
    private AETypeParam typeParam;

    //    /**
    //     * Flag se questa istanza ha un solo parametro <br>
    //     */
    //    private boolean singoloParametro;
    //
    //    /**
    //     * Flag se questa istanza ha una mappa di parametri singoli <br>
    //     * Se mappa=true allora multiMappa=false <br>
    //     */
    //    private boolean mappa;
    //
    //    /**
    //     * Flag se questa istanza ha una mappa di parametri composti <br>
    //     */
    //    private boolean multiMappa;


    public WrapParametro() {
    }


    public WrapParametro(String singleParameter) {
        this.singleParameter = singleParameter;
        //        this.setSingoloParametro(true);
        //        this.setMappa(false);
        //        this.setMultiMappa(false);
        //        this.setValido(!singleParameter.equals(VUOTA));
    }


    public WrapParametro(Map<String, String> parametersMap) {
        this.parametersMap = parametersMap;
        //        this.setSingoloParametro(false);
        //        this.setValido(parametersMap != null);
    }


    public WrapParametro(String singleParameter, String primoSegmento) {
        this.singleParameter = singleParameter;
        //        this.setPrimoSegmento(primoSegmento);
        //        this.setSingoloParametro(true);
        //        this.setMappa(false);
        //        this.setMultiMappa(false);
        //        this.setValido(!singleParameter.equals(VUOTA));
    }


    public WrapParametro(Map<String, String> parametersMap, String primoSegmento) {
        this.parametersMap = parametersMap;
        //        this.setPrimoSegmento(primoSegmento);
        //        this.setSingoloParametro(false);
        //        this.setValido(parametersMap != null);
    }

    public WrapParametro(Map<String, List<String>> multiParametersMap, String primoSegmento, boolean multiParameters) {
        //        this.setMultiParametersMap(multiParametersMap);
        //        this.setPrimoSegmento(primoSegmento);
        //        this.setSingoloParametro(false);
        //        this.setMappa(false);
        //        this.setMultiMappa(true);
        //        this.setValido(multiParametersMap != null);
    }


    /**
     * C'è solo il primo segmento <br>
     *
     * @param primoSegmento la prima stringa DOPO lo 'slash'
     */
    public static WrapParametro segmento(String primoSegmento) {
        WrapParametro parametro = new WrapParametro();
        parametro.primoSegmento = primoSegmento;
        parametro.typeParam = AETypeParam.segmentOnly;

        return parametro;
    }


    /**
     * C'è il primo segmento ed una mappa 'semplice' <br>
     *
     * @param primoSegmento la prima stringa DOPO lo 'slash'
     * @param parametersMap mappa semplice di parametri SINGOLI (UN solo valore per tipo)
     */
    public static WrapParametro simple(String primoSegmento, Map<String, String> parametersMap) {
        WrapParametro parametro = WrapParametro.segmento(primoSegmento);
        parametro.parametersMap = parametersMap;
        parametro.typeParam = AETypeParam.parametersMap;

        return parametro;
    }


    /**
     * C'è il primo segmento ed una mappa 'complessa' <br>
     * Poco usato <br>
     *
     * @param primoSegmento      la prima stringa DOPO lo 'slash'
     * @param multiParametersMap mappa complessa di parametri COMPOSTI (una LISTA di valori per tipo) <br>
     */
    public static WrapParametro full(String primoSegmento, Map<String, List<String>> multiParametersMap) {
        WrapParametro parametro = WrapParametro.segmento(primoSegmento);
        parametro.multiParametersMap = multiParametersMap;
        parametro.typeParam = AETypeParam.multiParametersMap;

        return parametro;
    }


    public String getSingleParameter() {
        return singleParameter;
    }


    public Map<String, String> getParametersMap() {
        return parametersMap;
    }


    public Map<String, List<String>> getMultiParametersMap() {
        return multiParametersMap;
    }

    //    public void setMultiParametersMap(Map<String, List<String>> multiParametersMap) {
    //        this.multiParametersMap = multiParametersMap;
    //    }

    //    public boolean isMappa() {
    //        return mappa;
    //    }
    //
    //
    //    public void setMappa(boolean mappa) {
    //        this.mappa = mappa;
    //    }
    //
    //
    //    public boolean isMultiMappa() {
    //        return multiMappa;
    //    }
    //
    //
    //    public void setMultiMappa(boolean multiMappa) {
    //        this.multiMappa = multiMappa;
    //    }


    public String get(String key) {
        String value = VUOTA;

        if (parametersMap != null && parametersMap.get(key) != null) {
            value = parametersMap.get(key);
        }

        return value;
    }

    //    public boolean containsKey(String key) {
    //        return get(key) != null;
    //    }
    //

    public String getPrimoSegmento() {
        return primoSegmento;
    }

    //    public boolean isSingoloParametro() {
    //        return singoloParametro;
    //    }
    //
    //
    //    public void setSingoloParametro(boolean singoloParametro) {
    //        this.singoloParametro = singoloParametro;
    //    }
    //
    //

    public AETypeOperation getOperationForm() {
        AETypeOperation operationForm = AETypeOperation.edit;
        String operationTxt;

        operationTxt = this.get(KEY_FORM_TYPE);
        if (!operationTxt.equals(VUOTA)) {
            operationForm = AETypeOperation.valueOf(operationTxt);
        }

        return operationForm;
    }

    public AETypeParam getTypeParam() {
        return typeParam;
    }
    //
    //
    //    public void setValido(boolean valido) {
    //        this.valido = valido;
    //    }

}
