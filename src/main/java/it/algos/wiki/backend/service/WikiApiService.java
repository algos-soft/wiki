package it.algos.wiki.backend.service;

import com.vaadin.flow.component.*;
import it.algos.vaad23.backend.boot.*;
import static it.algos.vaad23.backend.boot.VaadCost.*;
import it.algos.vaad23.backend.service.*;
import org.json.simple.*;
import org.springframework.beans.factory.config.*;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.*;

import java.net.*;
import java.util.*;
import java.util.regex.*;


/**
 * Project vaadin23
 * Created by Algos
 * User: gac
 * Date: gio, 07-mag-2020
 * Time: 07:49
 * <p>
 * Importazione di pagine da Wikipedia con API che NON richiedono login come Bot <br>
 * <p>
 * Classe di libreria; NON deve essere astratta, altrimenti SpringBoot non la costruisce <br>
 * L'istanza può essere richiamata con: <br>
 * 1) StaticContextAccessor.getBean(AAnnotationService.class); <br>
 * 3) @Autowired private AWikiService wiki; <br>
 * <p>
 * Annotated with @Service (obbligatorio, se si usa la catena @Autowired di SpringBoot) <br>
 * NOT annotated with @SpringComponent (inutile, esiste già @Service) <br>
 * Annotated with @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) (obbligatorio) <br>
 */
@Service
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class WikiApiService extends AbstractService {


    public static final String PAGINA_ISO_1 = "ISO 3166-1";

    public static final String PAGINA_ISO_2 = "ISO 3166-2:IT";

    public static final String PAGINA_PROVINCE = "Regioni_d'Italia";

    public static final String PAGINA_ISO_1_NUMERICO = "ISO 3166-1 numerico";

    public static final int LIMIT_USER = 50;

    public static final String PAGES = "pages";

    public static final String PAGE_ID = "pageid";

    public static final String TITLE = "title";

    public static final String QUERY = "query";

    public static final String EDIT = "edit";

    public static final String NEW_REV_ID = "newrevid";

    public static final String NEW_TIME_STAMP = "newtimestamp";

    public static final String NO_CHANGE = "nochange";

    public static final String LOGIN = "login";

    public static final String TOKENS = "tokens";

    public static final String RESULT = "result";

    public static final String LOGIN_TOKEN = "logintoken";

    public static final String LOGIN_USER_ID = "lguserid";

    public static final String LOGIN_USER_NAME = "lgusername";

    public static final String REVISIONS = "revisions";

    public static final String CATEGORY = "categorymembers";

    public static final String CATEGORY_INFO = "categoryinfo";

    public static final String CATEGORY_PAGES = "pages";

    public static final String CONTINUE = "continue";

    public static final String CONTINUE_CM = "cmcontinue";

    public static final String SLOTS = "slots";

    public static final String MAIN = "main";

    public static final String CONTENT = "content";

    public static final String TAG_DISAMBIGUA_UNO = "{{Disambigua}}";

    public static final String TAG_DISAMBIGUA_DUE = "{{disambigua}}";

    public static final String TAG_REDIRECT_UNO = "#redirect";

    public static final String TAG_REDIRECT_DUE = "#REDIRECT";

    public static final String TAG_REDIRECT_TRE = "#rinvia";

    public static final String TAG_REDIRECT_QUATTRO = "#RINVIA";

    //--L'API di 'parse' è leggermente più veloce e comunque il codice JSON è più semplice da interpretare
    public static final String WIKI_PARSE = "https://it.wikipedia.org/w/api.php?action=parse&prop=wikitext&formatversion=2&format=json&page=";

    public static final String API_VIEW = "https://it.wikipedia.org/wiki/";

    public static final String API_EDIT = "https://it.wikipedia.org/w/index.php?action=edit&section=0&title=";

    public static final String API_HISTORY = "https://it.wikipedia.org/w/index.php?action=history&title=";

//    /**
//     * Converte il valore stringa nel tipo previsto dal parametro PagePar
//     *
//     * @param key     del parametro PagePar in ingresso
//     * @param valueIn in ingresso
//     *
//     * @return valore della classe corretta
//     */
//    private Object fixValueMap(String key, Object valueIn) {
//        return fixValueMap(PagePar.getPar(key), valueIn);
//    }


//    /**
//     * Restituisce una colonna estratta da una wiki table <br>
//     *
//     * @param wikiTitle    della pagina wiki
//     * @param posTabella   della wikitable nella pagina se ce ne sono più di una
//     * @param rigaIniziale da cui estrarre le righe, scartando la testa della table
//     * @param numColonna   da cui estrarre la cella
//     *
//     * @return lista di coppia di valori: sigla e nome
//     */
//    public List<String> getColonna(String wikiTitle, int posTabella, int rigaIniziale, int numColonna) {
//        List<String> colonna = null;
//        List<List<String>> lista = null;
//        String cella = VUOTA;
//        String[] parti = null;
//
//        if (textService.isEmpty(wikiTitle)) {
//            return null;
//        }
//
//        try {
//            lista = getTable(wikiTitle, posTabella);
//        } catch (Exception unErrore) {
//        }
//
//        if (arrayService.isAllValid(lista)) {
//            lista = lista.subList(rigaIniziale - 1, lista.size());
//            colonna = new ArrayList<>();
//            for (List<String> riga : lista) {
//                if (riga.size() == 1 || (riga.size() == 2 && !riga.get(0).startsWith(GRAFFA_END))) {
//                    parti = riga.get(0).split(DOPPIO_PIPE_REGEX);
//                    if (parti != null && parti.length >= numColonna) {
//                        cella = parti[numColonna - 1];
//                    }
//                }
//                else {
//                    if (!riga.get(0).startsWith(ESCLAMATIVO)) {
//                        cella = riga.get(numColonna - 1);
//                    }
//                }
//                if (textService.isValid(cella)) {
//                    cella = cella.trim();
//                    cella = textService.setNoDoppieGraffe(cella);
//                    cella = fixCode(cella);
//                    colonna.add(cella);
//                }
//            }
//        }
//
//        return colonna;
//    }


    /**
     * Estrae una wikitable da una pagina wiki <br>
     * Restituisce una lista dei valori per ogni riga, esclusa la prima coi titoli <br>
     *
     * @param wikiTitle della pagina wiki
     *
     * @return lista di valori per ogni riga significativa della wikitable
     */
    public List<List<String>> getTable(String wikiTitle) {
        return getTable(wikiTitle, 1);
    }

    /**
     * Estrae una wikitable da una pagina wiki <br>
     * Restituisce una lista di valori per ogni riga valida <br>
     * Restituisce anche la prima lista di titoli <br>
     * Esclude il testo che precede il primo punto ESCLAMATIVO, da scartare <br>
     * Poi estrae i titoli e poi esegue lo spilt per separare le righe valide <br>
     *
     * @param wikiTitle  della pagina wiki
     * @param posTabella della wikitable nella pagina se ce ne sono più di una
     *
     * @return lista di valori per ogni riga significativa della wikitable
     */
    public List<List<String>> getTable(String wikiTitle, int posTabella) {
        List<List<String>> listaTable = new ArrayList<>();
        List<String> listaRiga;
        String[] righeTable;
        String testoRigaSingola;
        String[] partiRiga;
        String tagTable = "\\|-";
        String testoTable;

        testoTable = leggeTable(wikiTitle, posTabella);

        //--elimina la testa di apertura della table per evitare fuffa
        if (textService.isValid(testoTable)) {
            testoTable = textService.levaTestoPrimaDi(testoTable, ESCLAMATIVO);
            testoTable = testoTable.trim();
        }

        //--elimina la coda di chiusura della table per evitare che la suddivisione in righe contenga anche la chiusura della table
        if (textService.isValid(testoTable)) {
            if (testoTable.endsWith(GRAFFA_END)) {
                testoTable = textService.levaCodaDa(testoTable, GRAFFA_END);
            }
            testoTable = testoTable.trim();
            if (testoTable.endsWith(PIPE)) {
                testoTable = textService.levaCodaDa(testoTable, PIPE);
            }
            testoTable = testoTable.trim();
        }

        //--dopo aver eliminato la testa della tabella, la coda della tabella ed i titoli, individua le righe valide
        righeTable = testoTable.split(tagTable);

        if (righeTable != null && righeTable.length > 2) {
            for (int k = 0; k < righeTable.length; k++) {
                testoRigaSingola = righeTable[k].trim();
                if (testoRigaSingola.startsWith(ESCLAMATIVO)) {
                    continue;
                }
                partiRiga = getParti(testoRigaSingola);
                if (partiRiga != null && partiRiga.length > 0) {
                    listaRiga = new ArrayList<>();
                    for (String value : partiRiga) {
                        if (textService.isValid(value)) {
                            value = value.trim();
                            if (value.startsWith(PIPE)) {
                                value = textService.levaTesta(value, PIPE);
                            }
                            value = value.trim();
                            listaRiga.add(value);
                        }
                    }
                    if (!listaRiga.get(0).equals(ESCLAMATIVO)) {
                        listaTable.add(listaRiga);
                    }
                }
            }
        }

        return listaTable;
    }

    private String[] getParti(String testoRigaSingola) {
        String[] partiRiga = null;
        String tagUno = CAPO;
        String tagDue = DOPPIO_PIPE_REGEX;
        String tagTre = DOPPIO_ESCLAMATIVO;

        //--primo tentativo
        partiRiga = testoRigaSingola.split(tagUno);

        //--secondo tentativo
        if (partiRiga != null && partiRiga.length == 1) {
            partiRiga = testoRigaSingola.split(tagDue);
        }

        //--terzo tentativo
        if (partiRiga != null && partiRiga.length == 1) {
            partiRiga = testoRigaSingola.split(tagTre);
        }

        return partiRiga;
    }

    /**
     * Legge una wikitable da una pagina wiki <br>
     *
     * @param wikiTitle della pagina wiki
     *
     * @return testo completo della wikitable
     */
    public String leggeTable(final String wikiTitle) {
        try {
            return leggeTable(wikiTitle, 1);
        } catch (Exception unErrore) {
        }
        return VUOTA;
    }

    /**
     * Legge una wikitable da una pagina wiki, selezionandola se ce n'è più di una <br>
     *
     * @param wikiTitle della pagina wiki
     * @param pos       della wikitable nella pagina se ce ne sono più di una
     *
     * @return testo completo della wikitable alla posizione indicata
     */
    public String leggeTable(String wikiTitle, int pos) {
        String testoTable = VUOTA;
        String tag1 = "{|class=\"wikitable";
        String tag2 = "{| class=\"wikitable";
        String tag3 = "{|  class=\"wikitable";
        String tag4 = "{|class=\"sortable wikitable";
        String tag5 = "{| class=\"sortable wikitable";
        String tag6 = "{|  class=\"sortable wikitable";
        String tagEnd = "|}\n";
        int posIni = 0;
        int posEnd = 0;
        String testoPagina = this.legge(wikiTitle);

        if (textService.isValid(testoPagina)) {
            if (testoPagina.contains(tag1) || testoPagina.contains(tag2) || testoPagina.contains(tag3) || testoPagina.contains(tag4) || testoPagina.contains(tag5) || testoPagina.contains(tag6)) {
                if (testoPagina.contains(tag1)) {
                    for (int k = 1; k <= pos; k++) {
                        posIni = testoPagina.indexOf(tag1, posIni + tag1.length());
                    }
                }
                if (testoPagina.contains(tag2)) {
                    for (int k = 1; k <= pos; k++) {
                        posIni = testoPagina.indexOf(tag2, posIni + tag2.length());
                    }
                }
                if (testoPagina.contains(tag3)) {
                    for (int k = 1; k <= pos; k++) {
                        posIni = testoPagina.indexOf(tag3, posIni + tag3.length());
                    }
                }
                if (testoPagina.contains(tag4)) {
                    for (int k = 1; k <= pos; k++) {
                        posIni = testoPagina.indexOf(tag4, posIni + tag4.length());
                    }
                }
                posEnd = testoPagina.indexOf(tagEnd, posIni) + tagEnd.length();
                testoTable = testoPagina.substring(posIni, posEnd);
            }
            else {
                //                throw new Exception("La pagina wiki " + wikiTitle + " non contiene nessuna wikitable. AWikiService.leggeTable()");
                //                logger.warn("La pagina wiki " + wikiTitle + " non contiene nessuna wikitable", this.getClass(), "leggeTable");
            }
        }

        return textService.isValid(testoTable) ? testoTable.trim() : VUOTA;
    }

    /**
     * Legge un modulo di una pagina wiki <br>
     *
     * @param wikiTitle della pagina wiki
     *
     * @return testo completo del modulo, comprensivo delle graffe iniziale e finale
     */
    public String leggeModulo(final String wikiTitle) {
        String testoModulo = VUOTA;
        String testoPagina = this.legge(wikiTitle);
        String tag = "return";

        if (textService.isValid(testoPagina)) {
            if (testoPagina.contains(tag)) {
                testoModulo = textService.levaTestoPrimaDi(testoPagina, tag);
            }
        }

        return textService.isValid(testoModulo) ? testoModulo.trim() : VUOTA;
    }

    /**
     * Legge la mappa dei valori di modulo di una pagina wiki <br>
     *
     * @param wikiTitle della pagina wiki
     *
     * @return mappa chiave=valore del modulo
     */
    public Map<String, String> leggeMappaModulo(final String wikiTitle) {
        Map<String, String> mappa = null;
        String tagRighe = VIRGOLA_CAPO;
        String tagSezioni = UGUALE_SEMPLICE;
        String[] righe = null;
        String[] sezioni = null;
        String key;
        String value;
        String testoModulo = this.leggeModulo(wikiTitle);

        if (textService.isValid(testoModulo)) {
            testoModulo = textService.setNoGraffe(testoModulo);
            righe = testoModulo.split(tagRighe);
        }

        if (righe != null && righe.length > 0) {
            mappa = new LinkedHashMap<>();
            for (String riga : righe) {

                sezioni = riga.split(tagSezioni);
                if (sezioni != null && sezioni.length == 2) {
                    key = sezioni[0];

                    key = textService.setNoQuadre(key);
                    key = textService.setNoDoppiApici(key);
                    value = sezioni[1];
                    value = textService.setNoDoppiApici(value);
                    value = textService.setNoGraffe(value);
                    mappa.put(key, value);
                }
            }
        }

        return mappa;
    }


    /**
     * Legge (come user) il testo JSON di una pagina dal server wiki <br>
     * Usa una API con action=parse SENZA bisogno di loggarsi <br>
     * Recupera dalla urlRequest title, pageid e wikitext <br>
     * Estrae il wikitext in linguaggio wiki visibile <br>
     *
     * @param wikiTitleGrezzo della pagina wiki
     *
     * @return testo JSON
     */
    public String leggeJSONParse(final String wikiTitleGrezzo) {
        String webUrl = WIKI_PARSE + fixWikiTitle(wikiTitleGrezzo);
        String rispostaAPI = webService.leggeWebTxt(webUrl);

        return rispostaAPI;
    }


    /**
     * Legge (come user) una pagina dal server wiki <br>
     * Usa una API con action=parse SENZA bisogno di loggarsi <br>
     * Se la pagina è un redirect, legge la pagina 'puntata' <br>
     * Recupera dalla urlRequest title, pageid e wikitext <br>
     * Estrae il wikitext in linguaggio wiki visibile <br>
     *
     * @param wikiTitleGrezzo della pagina wiki
     *
     * @return mappa dei parametri
     */
    public Map<String, Object> leggeMappaParse(final String wikiTitleGrezzo) {
        Map<String, Object> mappa = new HashMap<>();
        String webUrl = WIKI_PARSE + fixWikiTitle(wikiTitleGrezzo);
        String rispostaAPI = webService.leggeWebTxt(webUrl);
        JSONObject jsonRisposta = (JSONObject) JSONValue.parse(rispostaAPI);

        if (jsonValida(jsonRisposta)) {
            JSONObject jsonParse = (JSONObject) jsonRisposta.get(KEY_MAPPA_PARSE);
            mappa.put(KEY_MAPPA_DOMAIN, webUrl);
            mappa.put(KEY_MAPPA_TITLE, jsonParse.get(KEY_MAPPA_TITLE));
            mappa.put(KEY_MAPPA_PAGEID, jsonParse.get(KEY_MAPPA_PAGEID));
            mappa.put(KEY_MAPPA_TEXT, jsonParse.get(KEY_MAPPA_TEXT));
        }

        if (isRedirect((String) mappa.get(KEY_MAPPA_TEXT))) {
            mappa = leggeMappaParse(getRedirect((String) mappa.get(KEY_MAPPA_TEXT)));
        }

        return mappa;
    }

    /**
     * Estrae il wikiTitle del #redirect contenuto in una pagina <br>
     *
     * @param paginaConRedirect contenuto della pagina wiki con #redirect
     *
     * @return wikiTitleGrezzo del #redirect
     */
    public String getRedirect(final String paginaConRedirect) {
        int ini = 0;
        int end = 0;
        ini = paginaConRedirect.indexOf(DOPPIE_QUADRE_INI) + DOPPIE_QUADRE_INI.length();
        end = paginaConRedirect.indexOf(DOPPIE_QUADRE_END, ini);

        return paginaConRedirect.substring(ini, end);
    }

    public boolean jsonValida(final JSONObject jsonRisposta) {
        return jsonRisposta.get(JSON_ERROR) == null;
    }

    /**
     * Legge (come user) una pagina dal server wiki <br>
     * Usa una API con action=parse SENZA bisogno di loggarsi <br>
     * Recupera dalla urlRequest title, pageid e wikitext <br>
     * Estrae il wikitext in linguaggio wiki visibile <br>
     *
     * @param wikiTitleGrezzo della pagina wiki
     *
     * @return testo completo (visibile) della pagina wiki
     */
    public String legge(final String wikiTitleGrezzo) {
        Map mappa = leggeMappaParse(wikiTitleGrezzo);
        return (String) mappa.get(KEY_MAPPA_TEXT);
    }

    /**
     * Recupera spazio e caratteri strani nel titolo <br>
     *
     * @param wikiTitleGrezzo della pagina wiki
     *
     * @return titolo 'spedibile' al server
     */
    public String fixWikiTitle(final String wikiTitleGrezzo) {
        String wikiTitle = wikiTitleGrezzo.replaceAll(SPAZIO, UNDERSCORE);
        try {
            wikiTitle = URLEncoder.encode(wikiTitle, ENCODE);

        } catch (Exception unErrore) {

        }

        return wikiTitle;
    }

    /**
     * Legge il testo di un template da una voce wiki <br>
     * Esamina il PRIMO template che trova <br>
     * Gli estremi sono COMPRESI <br>
     * <p>
     * Recupera il tag iniziale con o senza ''Template''
     * Recupera il tag iniziale con o senza primo carattere maiuscolo
     * Recupera il tag finale di chiusura con o senza ritorno a capo precedente
     * Controlla che non esistano doppie graffe dispari all'interno del template
     *
     * @param wikiTitle della pagina wiki
     *
     * @return template completo di doppie graffe iniziali e finali
     */
    public String leggeTmpl(final String wikiTitle, final String tag) {
        String testoPagina = this.legge(wikiTitle);
        return estraeTmpl(testoPagina, tag);
    }

    /**
     * Estrae il testo di un template dal testo completo di una voce wiki <br>
     * Esamina il PRIMO template che trova <br>
     * Gli estremi sono COMPRESI <br>
     * <p>
     * Recupera il tag iniziale con o senza ''Template''
     * Recupera il tag iniziale con o senza primo carattere maiuscolo
     * Recupera il tag finale di chiusura con o senza ritorno a capo precedente
     * Controlla che non esistano doppie graffe dispari all'interno del template
     *
     * @return template completo di doppie graffe iniziali e finali
     */
    public String estraeTmpl(final String testoPagina, String tag) {
        String templateTxt = VUOTA;
        boolean continua = false;
        String patternTxt = "";
        Pattern patttern = null;
        Matcher matcher = null;
        int posIni;
        int posEnd;
        String tagIniTemplate = VUOTA;

        // controllo di congruità
        if (textService.isValid(testoPagina) && textService.isValid(tag)) {
            // patch per nome template minuscolo o maiuscolo
            // deve terminare con 'aCapo' oppure 'return' oppure 'tab' oppure '|'(pipe) oppure 'spazio'(u0020)
            if (tag.equals("Bio")) {
                tag = "[Bb]io[\n\r\t\\|\u0020(grafia)]";
            }

            // Create a Pattern text
            patternTxt = "\\{\\{(Template:)?" + tag;

            // Create a Pattern object
            patttern = Pattern.compile(patternTxt);

            // Now create matcher object.
            matcher = patttern.matcher(testoPagina);
            if (matcher.find() && matcher.groupCount() > 0) {
                tagIniTemplate = matcher.group(0);
            }

            // controlla se esiste una doppia graffa di chiusura
            // non si sa mai
            if (!tagIniTemplate.equals("")) {
                posIni = testoPagina.indexOf(tagIniTemplate);
                posEnd = testoPagina.indexOf(DOPPIE_GRAFFE_END, posIni);
                templateTxt = testoPagina.substring(posIni);
                if (posEnd != -1) {
                    continua = true;
                }
            }

            // cerco la prima doppia graffa che abbia all'interno
            // lo stesso numero di aperture e chiusure
            // spazzola il testo fino a pareggiare le graffe
            if (continua) {
                templateTxt = chiudeTmpl(templateTxt);
            }
        }

        return templateTxt;
    }

    /**
     * Chiude il template <br>
     * <p>
     * Il testo inizia col template, ma prosegue (forse) anche oltre <br>
     * Cerco la prima doppia graffa che abbia all'interno lo stesso numero di aperture e chiusure <br>
     * Spazzola il testo fino a pareggiare le graffe <br>
     * Se non riesce a pareggiare le graffe, ritorna una stringa nulla <br>
     *
     * @param templateTxt da spazzolare
     *
     * @return template completo
     */
    public String chiudeTmpl(String templateTxt) {
        String templateOut;
        int posIni = 0;
        int posEnd = 0;
        boolean pari = false;

        templateOut = templateTxt.substring(posIni, posEnd + DOPPIE_GRAFFE_END.length()).trim();

        while (!pari) {
            posEnd = templateTxt.indexOf(DOPPIE_GRAFFE_END, posEnd + DOPPIE_GRAFFE_END.length());
            if (posEnd != -1) {
                templateOut = templateTxt.substring(posIni, posEnd + DOPPIE_GRAFFE_END.length()).trim();
                pari = htmlService.isPariTag(templateOut, DOPPIE_GRAFFE_INI, DOPPIE_GRAFFE_END);
            }
            else {
                break;
            }
        }

        if (!pari) {
            templateOut = VUOTA;
        }

        return templateOut;
    }

//    /**
//     * Converte i tipi di una mappa secondo i parametri PagePar
//     *
//     * @param mappaIn standard (valori String) in ingresso
//     *
//     * @return mappa tipizzata secondo PagePar
//     */
//    public HashMap<String, Object> converteMappa(HashMap mappaIn) {
//        HashMap<String, Object> mappaOut = new HashMap<String, Object>();
//        String key = "";
//        String valueTxt;
//        Object valueObj = null;
//
//        if (mappaIn != null) {
//            for (Object obj : mappaIn.keySet()) {
//                if (obj instanceof String) {
//                    key = (String) obj;
//                    valueObj = mappaIn.get(key);
//                    valueObj = fixValueMap(key, valueObj);
//                    mappaOut.put(key, valueObj);
//                }
//            }
//        }
//
//        return mappaOut;
//    }

//    /**
//     * Converte il valore stringa nel tipo previsto dal parametro PagePar
//     *
//     * @param par     parametro PagePar in ingresso
//     * @param valueIn in ingresso
//     *
//     * @return valore della classe corretta
//     */
//    private Object fixValueMap(PagePar par, Object valueIn) {
//        Object valueOut = null;
//        PagePar.TypeField typo = par.getType();
//
//        if (typo == PagePar.TypeField.string) {
//            valueOut = valueIn;
//        }
//
//        if (typo == PagePar.TypeField.booleano) {
//            valueOut = valueIn;
//        }
//
//        if (typo == PagePar.TypeField.longzero || typo == PagePar.TypeField.longnotzero) {
//            if (valueIn instanceof String) {
//                try {
//                    valueOut = Long.decode((String) valueIn);
//                } catch (Exception unErrore) { // intercetta l'errore
//                }
//            }
//            if (valueIn instanceof Integer) {
//                try {
//                    valueOut = new Long(valueIn.toString());
//                } catch (Exception unErrore) { // intercetta l'errore
//                }
//            }
//            if (valueIn instanceof Long) {
//                valueOut = valueIn;
//            }
//        }
//
//        if (typo == PagePar.TypeField.date) {
//            if (valueIn instanceof String) {
//                try {
//                    valueOut = date.convertTxtData((String) valueIn);
//                } catch (Exception unErrore) { // intercetta l'errore
//                    valueOut = DATA_NULLA; //@todo mettere LocalDate
//                }
//            }
//            if (valueIn instanceof Date) {
//                valueOut = valueIn;
//            }
//        }
//
//        if (typo == PagePar.TypeField.timestamp) {
//            if (valueIn instanceof String) {
//                try {
//                    valueOut = date.convertTxtTime((String) valueIn);
//                } catch (Exception unErrore) { // intercetta l'errore
//                    valueOut = DATA_NULLA;
//                }
//            }
//            if (valueIn instanceof Timestamp) {
//                valueOut = valueIn;
//            }
//        }
//
//        return valueOut;
//    }

    /**
     * Sorgente completo di una pagina wiki <br>
     * Testo 'grezzo' html <br>
     * Invoca il corrispondente metodo di AWebService <br>
     * Non usa le API di Mediawiki <br>
     *
     * @param wikiTitle della pagina wiki
     *
     * @return testo sorgente completo della pagina web in formato html
     */
    public String leggeHtml(final String wikiTitle) {
        return webService.leggeWikiTxt(wikiTitle);
    }


    /**
     * Legge una porzione di testo incolonnato dalla pagina wikipedia <br>
     *
     * @param wikiTitle della pagina wiki
     *
     * @return testo contenuto nelle colonne
     */
    public String leggeColonne(String wikiTitle) {
        String testoIncolonnato = VUOTA;
        String tagIni = "{{Colonne}}";
        String tagEnd = "{{Colonne fine}}";
        int posIni = 0;
        int posEnd = 0;
        String testoPagina = webService.leggeWebTxt(wikiTitle);

        if (textService.isValid(testoPagina)) {
            if (testoPagina.contains(tagIni)) {
                posIni = testoPagina.indexOf(tagIni);
                posEnd = testoPagina.indexOf(tagEnd, posIni);
                testoIncolonnato = testoPagina.substring(posIni, posEnd);
            }
        }

        return testoIncolonnato;
    }


//    /**
//     * Import da una pagina di wikipedia <br>
//     *
//     * @return lista di wrapper con due stringhe ognuno
//     */
//    @Deprecated
//    public List<WrapDueStringhe> estraeListaDue(String pagina, String titoli, int posUno, int posDue) {
//        List<WrapDueStringhe> listaWrap = null;
//        List<List<String>> matriceTable = null;
//        String[] titoliTable = text.getMatrice(titoli);
//        WrapDueStringhe wrapGrezzo;
//
//        matriceTable = web.getMatriceTableWiki(pagina, titoliTable);
//        if (matriceTable != null && matriceTable.size() > 0) {
//            listaWrap = new ArrayList<>();
//            for (List<String> riga : matriceTable) {
//                wrapGrezzo = new WrapDueStringhe(riga.get(posUno - 1), posDue > 0 ? riga.get(posDue - 1) : VUOTA);
//                listaWrap.add(wrapGrezzo);
//            }
//        }
//        return listaWrap;
//    }


//    /**
//     * Import da una pagina di wikipedia <br>
//     *
//     * @return lista di wrapper con tre stringhe ognuno
//     */
//    @Deprecated
//    public List<WrapTreStringhe> estraeListaTre(String pagina, String titoli) {
//        List<WrapTreStringhe> listaWrap = null;
//        LinkedHashMap<String, LinkedHashMap<String, String>> mappaGenerale = null;
//        LinkedHashMap<String, String> mappa;
//        String[] titoliTable = text.getMatrice(titoli);
//        String tagUno = titoliTable[0];
//        String tagDue = titoliTable[1];
//        String tagTre = titoliTable[2];
//        WrapTreStringhe wrapGrezzo;
//
//        mappaGenerale = web.getMappaTableWiki(pagina, titoliTable);
//        if (mappaGenerale != null && mappaGenerale.size() > 0) {
//            listaWrap = new ArrayList<>();
//            for (String elemento : mappaGenerale.keySet()) {
//                mappa = mappaGenerale.get(elemento);
//                wrapGrezzo = new WrapTreStringhe(mappa.get(tagUno), mappa.get(tagDue), mappa.get(tagTre));
//                listaWrap.add(wrapGrezzo);
//            }
//        }
//        return listaWrap;
//    }


    public String fixCode(String testoGrezzo) {
        String testoValido = VUOTA;
        String tagIni = "<code>";
        String tagEnd = "</code>";

        if (textService.isEmpty(testoGrezzo)) {
            return VUOTA;
        }

        if (!testoGrezzo.contains(tagIni) || !testoGrezzo.contains(tagEnd)) {
            return testoGrezzo;
        }

        testoValido = testoGrezzo.trim();
        if (testoValido.startsWith(tagIni)) {
            testoValido = textService.levaTesta(testoValido, tagIni);
            testoValido = textService.levaCoda(testoValido, tagEnd);
        }
        else {
            testoValido = textService.estrae(testoValido, tagIni, tagEnd);
        }

        return testoValido.trim();
    }


    public String estraeGraffaCon(String testoCompleto) {
        String testoValido = VUOTA;
        int posIni;
        int posEnd;

        if (testoCompleto.contains(DOPPIE_GRAFFE_INI) && testoCompleto.contains(DOPPIE_GRAFFE_END)) {
            posIni = testoCompleto.indexOf(GRAFFA_INI);
            posEnd = testoCompleto.indexOf(DOPPIE_GRAFFE_END) + DOPPIE_GRAFFE_END.length();
            if (posIni >= 0 && posEnd > posIni) {
                testoValido = testoCompleto.substring(posIni, posEnd);
            }
        }

        return testoValido;
    }

    public String estraeGraffa(String testoCompleto) {
        return textService.setNoDoppieGraffe(estraeGraffaCon(testoCompleto));
    }

    public void openWikiPage(String wikiTitle) {
        String link = "\"" + VaadCost.PATH_WIKI + wikiTitle + "\"";
        UI.getCurrent().getPage().executeJavaScript("window.open(" + link + ");");
    }

    /**
     * Controlla se il contenuto della pagina wiki inizia con un redirect <br>
     * Controlla tutte le possibili 'declinazione' ammesse dal software <br>
     *
     * @param testoPagina della pagina wiki
     *
     * @return true se è una pagina di #redirect
     */
    public boolean isRedirect(String testoPagina) {
        boolean redirect;
        String patternTxt;
        Pattern patttern;
        Matcher matcher;
        int pos;

        if (textService.isEmpty(testoPagina)) {
            return false;
        }

        //--solo prima riga
        pos = testoPagina.indexOf(CAPO);
        testoPagina = pos > 0 ? testoPagina.substring(0, pos) : testoPagina;

        //--Create a Pattern text
        patternTxt = "\\s?#\\s?(REDIRECT|RINVIA).*";

        //--Create a Pattern object
        patttern = Pattern.compile(patternTxt, Pattern.CASE_INSENSITIVE);

        //--Now create matcher object.
        matcher = patttern.matcher(testoPagina);
        redirect = matcher.matches();

        return redirect;
    }

}