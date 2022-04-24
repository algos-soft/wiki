package it.algos.vaad23.backend.enumeration;

import static com.vaadin.flow.server.frontend.FrontendUtils.*;
import static it.algos.vaad23.backend.boot.VaadCost.*;
import it.algos.vaad23.backend.interfaces.*;

import java.util.*;

/**
 * Project vaadin23
 * Created by Algos
 * User: gac
 * Date: lun, 07-mar-2022
 * Time: 11:45
 */
public enum AELogLevel implements AIType, AIPref {
    debug(GREEN),
    info(BRIGHT_BLUE),
    warn(YELLOW),
    error(RED),
    ;

    public String tag;


    AELogLevel(String tag) {
        this.tag = tag;
    }

    public static List<AELogLevel> getAllEnums() {
        return Arrays.stream(values()).toList();
    }

    public static List<String> getAllStringValues() {
        List<String> listaValues = new ArrayList<>();

        getAllEnums().forEach(type -> listaValues.add(type.toString()));
        return listaValues;
    }

    public static List<String> getAllTags() {
        List<String> listaTags = new ArrayList<>();

        getAllEnums().forEach(type -> listaTags.add(type.getTag()));
        return listaTags;
    }

    public static AELogLevel getType(final String tag) {
        return getAllEnums()
                .stream()
                .filter(type -> type.getTag().equals(tag))
                .findAny()
                .orElse(null);
    }

    @Override
    public List<AELogLevel> getAll() {
        return Arrays.stream(values()).toList();
    }

    @Override
    public String getTag() {
        return tag;
    }

    /**
     * Stringa di valori (text) da usare per memorizzare la preferenza <br>
     * La stringa è composta da tutti i valori separati da virgola <br>
     * Poi, separato da punto e virgola, viene il valore corrente <br>
     *
     * @return stringa di valori e valore di default
     */
    @Override
    public String getPref() {
        StringBuffer buffer = new StringBuffer();

        getAllEnums().forEach(level -> buffer.append(level.name() + VIRGOLA));

        buffer.delete(buffer.length() - 1, buffer.length());
        buffer.append(PUNTO_VIRGOLA);
        buffer.append(name());

        return buffer.toString();
    }

}
