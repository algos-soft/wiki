package it.algos.vaad23.backend.service;

import com.vaadin.flow.component.*;
import com.vaadin.flow.router.*;
import static it.algos.vaad23.backend.boot.VaadCost.*;
import it.algos.vaad23.backend.entity.*;
import it.algos.vaad23.backend.enumeration.*;
import it.algos.vaad23.backend.wrapper.*;
import org.springframework.beans.factory.config.*;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.*;

import java.io.*;
import java.net.*;
import java.util.*;


/**
 * Project vaadflow
 * Created by Algos
 * User: gac
 * Date: sab, 17-ago-2019
 * Time: 11:26
 */
@Service
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class RouteService extends AbstractService {

    /**
     * The name of a supported <a href="../lang/package-summary.html#charenc">character encoding</a>.
     */
    private static final String CODE = "UTF-8";

    //    /**
    //     * Costruisce una query di parametri per navigare verso una view di tipo 'form' <br>
    //     * Nuovo record <br>
    //     *
    //     * @param entityClazz verso cui navigare
    //     *
    //     * @return query da passare al Router di Vaadin
    //     */
    //    public QueryParameters getQueryForm(Class<?> entityClazz) {
    //        return entityClazz != null ? getQueryForm(entityClazz.getCanonicalName(), VUOTA, AEOperation.addNew) : null;
    //    }

    //    /**
    //     * Costruisce una query di parametri per navigare verso una view di tipo 'form' <br>
    //     *
    //     * @param entityClazz          verso cui navigare
    //     * @param integerEntityBeanKey key ID della entityBean come intero
    //     *
    //     * @return query da passare al Router di Vaadin
    //     */
    //    public QueryParameters getQueryForm(Class<?> entityClazz, int integerEntityBeanKey) {
    //        return getQueryForm(entityClazz, integerEntityBeanKey + VUOTA, AEOperation.edit);
    //    }

    //    /**
    //     * Costruisce una query di parametri per navigare verso una view di tipo 'form' <br>
    //     * Nuovo record <br>
    //     *
    //     * @param entityClazzCanonicalName verso cui navigare
    //     *
    //     * @return query da passare al Router di Vaadin
    //     */
    //    public QueryParameters getQueryForm(String entityClazzCanonicalName) {
    //        return getQueryForm(entityClazzCanonicalName, VUOTA, AEOperation.addNew);
    //    }

    //    /**
    //     * Costruisce una query di parametri per navigare verso una view di tipo 'form' <br>
    //     *
    //     * @param entityClazz   verso cui navigare
    //     * @param entityBeanKey key ID della entityBean
    //     *
    //     * @return query da passare al Router di Vaadin
    //     */
    //    public QueryParameters getQueryForm(Class<?> entityClazz, String entityBeanKey) {
    //        return entityClazz != null ? getQueryForm(entityClazz.getCanonicalName(), entityBeanKey, AEOperation.edit) : null;
    //    }

    //    /**
    //     * Costruisce una query di parametri per navigare verso una view di tipo 'form' <br>
    //     *
    //     * @param entityClazz   verso cui navigare
    //     * @param entityBeanKey key ID della entityBean
    //     * @param operationForm tipologia di Form da usare
    //     *
    //     * @return query da passare al Router di Vaadin
    //     */
    //    public QueryParameters getQueryForm(Class<?> entityClazz, String entityBeanKey, AEOperation operationForm) {
    //        return entityClazz != null ? getQueryForm(entityClazz.getCanonicalName(), entityBeanKey, operationForm) : null;
    //    }

    //    /**
    //     * Costruisce una query di parametri per navigare verso una view di tipo 'form' <br>
    //     *
    //     * @param entityClazzCanonicalName verso cui navigare
    //     * @param entityBeanKey            key ID della entityBean
    //     *
    //     * @return query da passare al Router di Vaadin
    //     */
    //    public QueryParameters getQueryForm(String entityClazzCanonicalName, String entityBeanKey) {
    //        return getQueryForm(entityClazzCanonicalName, entityBeanKey, AEOperation.edit);
    //    }


    /**
     * Costruisce una query di parametri per navigare verso una view di tipo 'list' <br>
     *
     * @param entityClazz verso cui navigare
     *
     * @return query da passare al Router di Vaadin
     */
    public QueryParameters getQueryList(final Class<?> entityClazz) {
        return entityClazz != null ? getQueryList(entityClazz.getCanonicalName()) : null;
    }


    /**
     * Costruisce una query di parametri per navigare verso una view di tipo 'list' <br>
     *
     * @param entityClazzCanonicalName verso cui navigare
     *
     * @return query da passare al Router di Vaadin
     */
    public QueryParameters getQueryList(final String entityClazzCanonicalName) {
        if (textService.isEmpty(entityClazzCanonicalName)) {
            return null;
        }

        Map<String, String> mappaQuery = new HashMap<>();
        mappaQuery.put(KEY_BEAN_CLASS, entityClazzCanonicalName);
        return QueryParameters.simple(mappaQuery);
    }


    /**
     * Costruisce una query di parametri per navigare verso una view di tipo 'form' <br>
     *
     * @param entityClazz verso cui navigare
     *
     * @return query da passare al Router di Vaadin
     */
    public QueryParameters getQueryForm(final Class<?> entityClazz) {
        return getQueryForm(entityClazz, AETypeOperation.addNew, VUOTA, VUOTA, VUOTA);
    }


    /**
     * Costruisce una query di parametri per navigare verso una view di tipo 'form' <br>
     *
     * @param entityClazz   verso cui navigare
     * @param operationForm tipologia di Form da usare
     *
     * @return query da passare al Router di Vaadin
     */
    public QueryParameters getQueryForm(final Class<?> entityClazz, final AETypeOperation operationForm) {
        return getQueryForm(entityClazz, operationForm, VUOTA, VUOTA, VUOTA);
    }


    /**
     * Costruisce una query di parametri per navigare verso una view di tipo 'form' <br>
     *
     * @param entityClazz   verso cui navigare
     * @param operationForm tipologia di Form da usare
     * @param entityBeanID  (obbligatorio) da visualizzare (eventualmente null)
     *
     * @return query da passare al Router di Vaadin
     */
    public QueryParameters getQueryForm(final Class<?> entityClazz, final AETypeOperation operationForm, final String entityBeanID) {
        return getQueryForm(entityClazz, operationForm, entityBeanID, VUOTA, VUOTA);
    }

    /**
     * Costruisce una query di parametri per navigare verso una view di tipo 'form' <br>
     *
     * @param entityClazz      verso cui navigare
     * @param operationForm    tipologia di Form da usare
     * @param entityBeanID     (obbligatorio) da visualizzare (eventualmente null)
     * @param entityBeanPrevID (eventuale) ID della entity precedente
     * @param entityBeanNextID (eventuale) ID della entity successiva
     *
     * @return query da passare al Router di Vaadin
     */
    public QueryParameters getQueryForm(final Class<?> entityClazz, final AETypeOperation operationForm, final String entityBeanID, final String entityBeanPrevID, final String entityBeanNextID) {
        if (entityClazz == null) {
            return null;
        }

        Map<String, List<String>> mappaQuery = new HashMap<>();
        if (!classService.isLogicFormClassFromEntityClazz(entityClazz)) {
            mappaQuery.put(KEY_BEAN_CLASS, arrayService.creaArraySingolo(entityClazz.getCanonicalName()));
        }

        if (textService.isValid(entityBeanID)) {
            mappaQuery.put(KEY_BEAN_ENTITY, arrayService.creaArraySingolo(entityBeanID));
        }

        if (textService.isValid(entityBeanPrevID)) {
            mappaQuery.put(KEY_BEAN_PREV_ID, arrayService.creaArraySingolo(entityBeanPrevID));
        }

        if (textService.isValid(entityBeanNextID)) {
            mappaQuery.put(KEY_BEAN_NEXT_ID, arrayService.creaArraySingolo(entityBeanNextID));
        }

        if (operationForm != null) {
            mappaQuery.put(KEY_FORM_TYPE, arrayService.creaArraySingolo(operationForm.name()));
        }

        return new QueryParameters(mappaQuery);
    }


    /**
     * Costruisce una query di parametri da una singola coppia chiave-valore <br>
     * Entrambe le stringhe devono essere valori validi <br>
     *
     * @param keyMap   di chiave=valore
     * @param valueMap di chiave=valore
     *
     * @return query da passare al Router di Vaadin
     */
    public QueryParameters getQuery(String keyMap, String valueMap) {
        return getQuery(arrayService.creaMappaSingola(keyMap, valueMap));
    }


    /**
     * Costruisce una query di parametri da una mappa <br>
     * Deve esistere (not null) <br>
     * Deve avere degli elementi (size maggiore di 0) <br>
     * La mappa in ingresso è semplice: un valore per ogni chiave <br>
     * La classe QueryParameters è però prevista per valori multipli per ogni chiave <br>
     * Occorre quindi costruire una mappa chiave=lista di valori, con un solo valore <br>
     *
     * @param mappa di chiave=valore
     *
     * @return query da passare al Router di Vaadin
     */
    public QueryParameters getQuery(Map<String, String> mappa) {
        QueryParameters query;
        ArrayList<String> lista;
        HashMap<String, List<String>> mappaQuery = new HashMap<String, List<String>>();

        if (mappa == null) {
            return null;
        }

        if (mappa.size() < 1) {
            return null;
        }

        for (String key : mappa.keySet()) {
            lista = new ArrayList<>();
            lista.add(mappa.get(key));
            mappaQuery.put(key, lista);
        }

        query = new QueryParameters(mappaQuery);

        return query;
    }


    /**
     * Naviga verso l' URL indicato con la mappa di parametri <br>
     *
     * @param interfacciaUtente del chiamante
     * @param routeName         destinazione del Router
     * @param mappa             di chiave=valore
     */
    public void navigate(Optional<UI> interfacciaUtente, String routeName, HashMap<String, String> mappa) {
        QueryParameters query = getQuery(mappa);
        UI ui = null;

        if (interfacciaUtente != null) {
            ui = interfacciaUtente.get();
        }// end of if cycle

        try { // prova ad eseguire il codice
            if (ui != null) {
                ui.navigate(routeName, query);
            }// end of if cycle
        } catch (Exception unErrore) { // intercetta l'errore
        }// fine del blocco try-catch

    }// end of method


    /**
     * Codifica un testo DOPO averlo ricevuto come URL dal browser <br>
     * <p>
     * Decodes a {@code application/x-www-form-urlencoded} string using a specific encoding scheme.
     * The supplied encoding is used to determine what characters are represented by any consecutive sequences of the
     * form "<i>{@code %xy}</i>".
     * <p>
     * <em><strong>Note:</strong> The <a href="http://www.w3.org/TR/html40/appendix/notes.html#non-ascii-chars">
     * World Wide Web Consortium Recommendation</a> states that UTF-8 should be used.
     * Not doing so may introduce incompatibilities.</em>
     *
     * @param textUTF8 the {@code String} to decode
     *
     * @return the newly decoded {@code String}
     *
     * @throws UnsupportedEncodingException If character encoding needs to be consulted, but
     *                                      named character encoding is not supported
     * @see URLEncoder#encode(String, String)
     * @since 1.4
     */
    public String decodifica(String textUTF8) {
        String text = textUTF8;

        try {
            text = URLDecoder.decode(textUTF8, CODE);
        } catch (Exception unErrore) {
            logger.error(unErrore);
        }

        return text;
    }


    /**
     * Parameters passed through the URL
     * <p>
     * This method is called by the Router, based on values extracted from the URL
     * This method will always be invoked before a navigation target is activated.
     * URL parameters can be annotated as optional using @OptionalParameter.
     * Where more parameters are needed, the URL parameter can also be annotated with @WildcardParameter.
     * The wildcard parameter will never be null.
     * It is possible to get any query parameters contained in a URL, for example ?name1=value1&name2=value2.
     * Use the getQueryParameters() method of the Location class to access query parameters.
     * You can obtain the Location class through the BeforeEvent parameter of the setParameter method.
     * A Location object represents a relative URL made up of path segments and query parameters,
     * but without the hostname, e.g. new Location("foo/bar/baz?name1=value1").
     * getQueryParameters() supports multiple values associated with the same key,
     * for example https://example.com/?one=1&two=2&one=3
     * will result in the corresponding map {"one" : [1, 3], "two": [2]}}.
     */
    public WrapParametro estraeParametri(BeforeEvent event, @OptionalParameter String parameter) {
        Location location = event.getLocation();
        String primoSegmento = location.getFirstSegment() != null ? location.getFirstSegment() : VUOTA;
        QueryParameters queryParameters = location.getQueryParameters();
        Map<String, List<String>> multiParametersMap = queryParameters.getParameters();
        WrapParametro parametro = null;

        //        if (location.getFirstSegment() != null) {
        //            primoSegmento = location.getFirstSegment();
        //        }

        //        if (text.isEmpty(parameter) && text.isValid(primoSegmento)) {
        //            parametro = Parametro.segmento(primoSegmento);
        //        }

        //        if (text.isValid(parameter)) {
        //            parametro = new Parametro(decodifica(parameter), primoSegmento);
        //        }

        if (textService.isValid(primoSegmento)) {
            parametro = WrapParametro.segmento(primoSegmento);
        }

        if (arrayService.isAllValid(multiParametersMap)) {
            if (arrayService.isMappaSemplificabile(multiParametersMap)) {
                parametro = WrapParametro.simple(primoSegmento, arrayService.semplificaMappa(multiParametersMap));
                //                parametro = Parametro.simple(primoSegmento, multiParametersMap);
            }
            else {
                parametro = WrapParametro.full(primoSegmento, multiParametersMap);
                //                parametro = Parametro.full(primoSegmento, array.semplificaMappa(multiParametersMap));
            }
        }

        return parametro;
    }


    public final void openList(Class<? extends AEntity> entityClazz) {
        openList(entityClazz.getCanonicalName());
    }


    public final void openList(String canonicalName) {
        final QueryParameters query = this.getQueryList(canonicalName);
        UI.getCurrent().navigate(ROUTE_NAME_GENERIC_LIST, query);
    }


}


