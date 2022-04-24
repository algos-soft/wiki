package it.algos.vaad23.backend.service;

import com.vaadin.flow.component.html.*;
import static it.algos.vaad23.backend.boot.VaadCost.*;
import it.algos.vaad23.backend.enumeration.*;
import it.algos.vaad23.backend.wrapper.*;
import org.springframework.beans.factory.config.*;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.*;

/**
 * Project vaadflow14
 * Created by Algos
 * User: gac
 * Date: mar, 16-feb-2021
 * Time: 17:38
 * <p>
 * Classe di libreria; NON deve essere astratta, altrimenti SpringBoot non la costruisce <br>
 * Estende la classe astratta AAbstractService che mantiene i riferimenti agli altri services <br>
 * L'istanza può essere richiamata con: <br>
 * 1) StaticContextAccessor.getBean(AHtmlService.class); <br>
 * 3) @Autowired public AHtmlService annotation; <br>
 * <p>
 * Annotated with @Service (obbligatorio, se si usa la catena @Autowired di SpringBoot) <br>
 * NOT annotated with @SpringComponent (inutile, esiste già @Service) <br>
 * Annotated with @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) (obbligatorio) <br>
 */
@Service
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class HtmlService extends AbstractService {


    /**
     * Costruisce uno span completo <br>
     *
     * @param wrap con le varie regolazioni
     *
     * @return elemento Span per html
     */
    public Span getSpan(final WrapSpan wrap) {
        Span span = new Span();
        //        String height = AEPreferenza.lineHeight.getStr();
        //        String heightText = "10px";
        String message;
        AETypeColor color;
        AEFontWeight weight;
        AEFontHeight fontHeight;
        AELineHeight lineHeight;
        AEFontStyle style;

        if (wrap == null) {
            return span;
        }

        message = wrap.getMessage();
        if (textService.isEmpty(message)) {
            return span;
        }
        span.setText(message);
        span.getElement().setProperty("innerHTML", message);

        color = wrap.getColor();
        if (color != null) {
            span.getElement().getStyle().set(AETypeColor.HTML, color.getTag());
        }

        weight = wrap.getWeight();
        if (weight != null) {
            span.getElement().getStyle().set(AEFontWeight.HTML, weight.getTag());
        }

        fontHeight = wrap.getFontHeight();
        if (fontHeight != null) {
            span.getElement().getStyle().set(AEFontHeight.HTML, fontHeight.getTag());
        }

        lineHeight = wrap.getLineHeight();
        if (lineHeight != null) {
            span.getElement().getStyle().set(AELineHeight.HTML, lineHeight.getTag());
        }

        style = wrap.getStyle();
        if (style != null) {
            span.getElement().getStyle().set(AEFontStyle.HTML, style.getTag());
        }

        return span;
    }

    /**
     * Costruisce uno span semplice <br>
     *
     * @param message da visualizzare
     *
     * @return elemento Span per html
     */
    public Span getSpan(final String message) {
        return getSpan(new WrapSpan(message));
    }


    /**
     * Costruisce uno span colorato verde <br>
     *
     * @param message da visualizzare
     *
     * @return elemento Span per html
     */
    public Span getSpanVerde(final String message) {
        return getSpan(new WrapSpan(message).color(AETypeColor.verde));
    }


    /**
     * Costruisce uno span colorato
     *
     * @param message da visualizzare
     *
     * @return elemento per html
     */
    public Span getSpanBlu(final String message) {
        return getSpan(new WrapSpan(message).color(AETypeColor.blu));
    }


    /**
     * Costruisce uno span colorato
     *
     * @param message da visualizzare
     *
     * @return elemento per html
     */
    public Span getSpanRosso(final String message) {
        return getSpan(new WrapSpan(message).color(AETypeColor.rosso));
    }


    /**
     * Contorna il testo con uno span
     *
     * @param testoIn da regolare
     *
     * @return testo contornato da tag iniziale e finale
     */
    private String colore(final String colore, final String testoIn) {
        String testoOut = VUOTA;
        String tagIni = String.format("<span style=\"color:%s\">", colore);
        String tagEnd = "</span>";

        if (textService.isValid(testoIn)) {
            testoOut += tagIni;
            testoOut += testoIn;
            testoOut += tagEnd;
        }

        return testoOut;
    }

    /**
     * Contorna il testo con uno span
     *
     * @param testoIn da regolare
     *
     * @return testo contornato da tag iniziale e finale
     */
    public String verde(final String testoIn) {
        return colore("green", testoIn);
    }

    /**
     * Contorna il testo con uno span
     *
     * @param testoIn da regolare
     *
     * @return testo contornato da tag iniziale e finale
     */
    public String blue(final String testoIn) {
        return colore("blue", testoIn);
    }

    /**
     * Contorna il testo con uno span
     *
     * @param testoIn da regolare
     *
     * @return testo contornato da tag iniziale e finale
     */
    public String rosso(final String testoIn) {
        return colore("red", testoIn);
    }

    /**
     * Elimina un tag HTML in testa e coda della stringa. <br>
     * Funziona solo se i tags sono esattamente in TESTA e in CODA alla stringa <br>
     * Se arriva una stringa vuota, restituisce una stringa vuota <br>
     * Elimina spazi vuoti iniziali e finali <br>
     *
     * @param stringaIn in ingresso
     * @param tag       html iniziale
     *
     * @return stringa con tags eliminati
     */
    public String setNoHtmlTag(String stringaIn, String tag) {
        String stringaOut = stringaIn;
        String tagIni = "<" + tag + ">";
        String tagEnd = "</" + tag + ">";

        if (textService.isValid(stringaIn)) {
            stringaIn = stringaIn.trim();

            if (stringaIn.startsWith(tagIni) && stringaIn.endsWith(tagEnd)) {
                stringaOut = stringaIn;
                stringaOut = textService.levaCoda(stringaOut, tagEnd);
                stringaOut = textService.levaTesta(stringaOut, tagIni);
            }
        }

        return stringaOut.trim();
    }

    /**
     * Contorna il testo con un uno span bold. <br>
     *
     * @param stringaIn in ingresso
     *
     * @return stringa regolata secondo la property html
     */
    public String bold(String stringaIn) {
        String stringaOut = VUOTA;
        String tagIni = String.format("<span style=\"font-weight:%s\">", AEFontWeight.bold.getTag());
        String tagEnd = "</span>";

        if (textService.isValid(stringaIn)) {
            stringaOut = tagIni;
            stringaOut += stringaIn;
            stringaOut += tagEnd;
        }

        return stringaOut.trim();
    }

    /**
     * Controlla che le occorrenze del tag iniziale e di quello finale si pareggino all'interno del testo. <br>
     * Ordine e annidamento NON considerato <br>
     *
     * @param testo  da spazzolare
     * @param tagIni tag iniziale
     * @param tagEnd tag finale
     *
     * @return vero se il numero di tagIni è uguale al numero di tagEnd
     */
    public boolean isPariTag(final String testo, final String tagIni, final String tagEnd) {
        boolean pari = false;
        int numIni;
        int numEnd;

        // controllo di congruità
        if (testo != null && tagIni != null && tagEnd != null) {
            numIni = getNumTag(testo, tagIni);
            numEnd = getNumTag(testo, tagEnd);
            pari = (numIni == numEnd);
        }

        return pari;
    }

    /**
     * Restituisce il numero di occorrenze di un tag nel testo. <br>
     * Il tag non viene trimmato ed è sensibile agli spazi prima e dopo <br>
     *
     * @param testo da spazzolare
     * @param tag   da cercare
     *
     * @return numero di occorrenze - zero se non ce ne sono
     */
    public int getNumTag(final String testo, final String tag) {
        int numTag = 0;
        int pos;

        // controllo di congruità
        if (textService.isValid(testo) && textService.isValid(tag)) {
            if (testo.contains(tag)) {
                pos = testo.indexOf(tag);
                while (pos != -1) {
                    pos = testo.indexOf(tag, pos + tag.length());
                    numTag++;
                }
            }
            else {
                numTag = 0;
            }
        }

        return numTag;
    }

}