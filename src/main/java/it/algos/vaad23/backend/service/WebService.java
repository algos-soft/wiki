package it.algos.vaad23.backend.service;

import static it.algos.vaad23.backend.boot.VaadCost.*;
import it.algos.vaad23.backend.interfaces.*;
import it.algos.vaad23.backend.wrapper.*;
import org.springframework.beans.factory.config.*;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.*;

import java.io.*;
import java.net.*;

/**
 * Project vaadin23
 * Created by Algos
 * User: gac
 * Date: mer, 06-apr-2022
 * Time: 06:27
 * <p>
 * Classe di libreria; NON deve essere astratta, altrimenti SpringBoot non la costruisce <br>
 * Estende la classe astratta AbstractService che mantiene i riferimenti agli altri services <br>
 * L'istanza può essere richiamata con: <br>
 * 1) StaticContextAccessor.getBean(WebService.class); <br>
 * 3) @Autowired public WebService annotation; <br>
 * <p>
 * Annotated with @Service (obbligatorio, se si usa la catena @Autowired di SpringBoot) <br>
 * NOT annotated with @SpringComponent (inutile, esiste già @Service) <br>
 * Annotated with @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) (obbligatorio) <br>
 */
@Service
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class WebService extends AbstractService {

    public static final String URL_BASE_ALGOS = "http://www.algos.it/";

    public static final String URL_BASE_VAADIN23 = URL_BASE_ALGOS + "vaadin23/";


    /**
     * Crea la connessione di tipo GET
     */
    public URLConnection getURLConnection(String domain) throws Exception {
        URLConnection urlConn = null;

        if (domain != null && domain.length() > 0) {
            domain = domain.replaceAll(SPAZIO, UNDERSCORE);
            urlConn = new URL(domain).openConnection();
            urlConn.setDoOutput(true);
            urlConn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=utf-8");
            urlConn.setRequestProperty("User-Agent", "Mozilla/5.0 (Macintosh; U; PPC Mac OS X; it-it) AppleWebKit/418.9 (KHTML, like Gecko) Safari/419.3");
        }

        return urlConn;
    }


    /**
     * Request di tipo GET
     */
    public String getUrlRequest(URLConnection urlConn) throws Exception {
        String risposta;
        InputStream input;
        InputStreamReader inputReader;
        BufferedReader readBuffer;
        StringBuilder textBuffer = new StringBuilder();
        String stringa;

        input = urlConn.getInputStream();
        inputReader = new InputStreamReader(input, INPUT);

        // read the request
        readBuffer = new BufferedReader(inputReader);
        while ((stringa = readBuffer.readLine()) != null) {
            textBuffer.append(stringa);
            textBuffer.append(CAPO);
        }

        //--close all
        readBuffer.close();
        inputReader.close();
        input.close();

        risposta = textBuffer.toString();

        return risposta;
    }


    /**
     * Request di tipo GET. Legge la pagina intera. <br>
     * Accetta SOLO un urlDomain (indirizzo) completo <br>
     * Può essere un urlDomain generico di un sito web e restituisce il testo in formato html <br>
     * Può essere un urlDomain di una pagina wiki in lettura normale (senza API) e restituisce il testo in formato html <br>
     * Può essere un urlDomain che usa le API di Mediawiki e restituisce il testo in formato BSON <br>
     *
     * @param urlDomain completo
     *
     * @return risultato col testo grezzo in formato html oppure BSON
     */
    public String leggeWebTxt(final String urlDomain) {
        return legge(urlDomain).getResponse();
    }

    /**
     * Request di tipo GET. Legge la pagina intera. <br>
     * Accetta SOLO un urlDomain (indirizzo) completo <br>
     * Può essere un urlDomain generico di un sito web e restituisce il testo in formato html <br>
     * Può essere un urlDomain di una pagina wiki in lettura normale (senza API) e restituisce il testo in formato html <br>
     * Può essere un urlDomain che usa le API di Mediawiki e restituisce il testo in formato BSON <br>
     *
     * @param urlDomain completo
     *
     * @return risultato col testo grezzo in formato html oppure BSON
     */
    public AIResult legge(final String urlDomain) {
        AIResult result;
        String codiceSorgente;
        URLConnection urlConn;
        String tag = TAG_INIZIALE;
        String tag2 = TAG_INIZIALE_SECURE;

        try {
            String webUrl = urlDomain.startsWith(tag) || urlDomain.startsWith(tag2) ? urlDomain : tag2 + urlDomain;
            urlConn = getURLConnection(webUrl);
            codiceSorgente = getUrlRequest(urlConn);
            result = AResult.contenuto(codiceSorgente, urlDomain);
        } catch (Exception unErrore) {
            result = AResult.errato(unErrore.toString());
        }
        result.setUrlRequest(urlDomain);

        return result;
    }

    /**
     * Request di tipo GET <br>
     * Sorgente completo di una pagina wiki <br>
     * Non usa le API di Mediawiki <br>
     * Elabora il wikiTitle per eliminare gli spazi vuoti <br>
     *
     * @param wikiTitleGrezzo da controllare per riempire gli spazi vuoti
     *
     * @return testo sorgente completo della pagina web in formato html
     */
    public String leggeWikiTxt(String wikiTitleGrezzo) {
        return leggeWiki(wikiTitleGrezzo).getResponse();
    }


    /**
     * Request di tipo GET <br>
     * Sorgente completo di una pagina wiki <br>
     * Non usa le API di Mediawiki <br>
     * Elabora il wikiTitle per eliminare gli spazi vuoti <br>
     *
     * @param wikiTitleGrezzo da controllare per riempire gli spazi vuoti
     *
     * @return testo sorgente completo della pagina web in formato html
     */
    public AIResult leggeWiki(final String wikiTitleGrezzo) {
        AIResult result;
        String wikiTitleElaborato = wikiTitleGrezzo.replaceAll(SPAZIO, UNDERSCORE);
        result = legge(TAG_WIKI + wikiTitleElaborato);

        result.setWikiTitle(wikiTitleGrezzo);
        result.setUrlRequest(TAG_WIKI + wikiTitleGrezzo);

        return result;
    }

}