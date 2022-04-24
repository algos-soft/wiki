package it.algos.vaad23.backend.enumeration;

import static it.algos.vaad23.backend.boot.VaadCost.*;
import it.algos.vaad23.backend.interfaces.*;

import java.util.*;

/**
 * Project vaadin23
 * Created by Algos
 * User: gac
 * Date: sab, 26-mar-2022
 * Time: 10:44
 */
public enum AELineHeight implements AIType, AIPref {


    normal("1"),
    px0("0px"),
    px2("2px"),
    px4("4px"),
    px6("6px"),
    px8("8px"),
    px10("10px"),
    px12("12px"),
    cm05("0.5cm"),
    cm08("0.5cm"),
    em2("0.2em"),
    em3("0.3em"),
    em4("0.4em"),
    em6("0.6em"),
    em8("0.8em"),
    em12("1.2em"),
    number03("0.3"),
    number05("0.5"),
    number16("1.6"),
    cento120("120%"),
    cento80("80%"),
    ;

    public static final String HTML = "line-height";

    private String tag;


    AELineHeight(String tag) {
        this.tag = tag;
    }

    public static List<AELineHeight> getAllEnums() {
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

    public static AELineHeight getType(final String tag) {
        return getAllEnums()
                .stream()
                .filter(type -> type.getTag().equals(tag))
                .findAny()
                .orElse(null);
    }

    @Override
    public List<AELineHeight> getAll() {
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
