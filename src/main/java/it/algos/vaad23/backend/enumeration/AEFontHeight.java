package it.algos.vaad23.backend.enumeration;


import static it.algos.vaad23.backend.boot.VaadCost.*;
import it.algos.vaad23.backend.interfaces.*;

import java.util.*;

/**
 * Project vaadflow14
 * Created by Algos
 * User: gac
 * Date: sab, 20-feb-2021
 * Time: 17:40
 */
public enum AEFontHeight implements AIType, AIPref {

    normal("1"),
    number16("1.6"),
    number20("2"),
    em6("0.6em"),
    em7("0.7em"),
    em8("0.8em"),
    em9("0.9em"),
    px6("6px"),
    px10("10px"),
    px14("14px"),
    px18("18px"),
    px22("22px"),
    cento80("80%"),
    cento120("120%"),
    ;

    public static final String HTML = "font-size";

    private String tag;


    AEFontHeight(String tag) {
        this.tag = tag;
    }

    public static List<AEFontHeight> getAllEnums() {
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

    public static AEFontHeight getType(final String tag) {
        return getAllEnums()
                .stream()
                .filter(type -> type.getTag().equals(tag))
                .findAny()
                .orElse(null);
    }

    @Override
    public List<AEFontHeight> getAll() {
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

        getAllEnums().forEach(enumeration -> buffer.append(enumeration.name() + VIRGOLA));

        buffer.delete(buffer.length() - 1, buffer.length());
        buffer.append(PUNTO_VIRGOLA);
        buffer.append(name());

        return buffer.toString();
    }
}
