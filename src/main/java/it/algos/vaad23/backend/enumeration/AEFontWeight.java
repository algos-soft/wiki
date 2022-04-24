package it.algos.vaad23.backend.enumeration;

import it.algos.vaad23.backend.interfaces.*;

import java.util.*;

/**
 * Project vaadflow14
 * Created by Algos
 * User: gac
 * Date: lun, 15-feb-2021
 * Time: 20:46
 */
public enum AEFontWeight implements AIType {

    normal("normal"),
    bold("bold"),
    bolder("bolder"),
    lighter("lighter"),
    w100("100"),
    w200("200"),
    w300("300"),
    w400("400"),
    w500("500"),
    w600("600"),
    w700("700"),
    w800("800"),
    w900("900");

    public static final String HTML = "font-weight";

    private String tag;


    AEFontWeight(String tag) {
        this.tag = tag;
    }

    public static List<AEFontWeight> getAllEnums() {
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

    public static AEFontWeight getType(final String tag) {
        return getAllEnums()
                .stream()
                .filter(type -> type.getTag().equals(tag))
                .findAny()
                .orElse(null);
    }

    @Override
    public List<AEFontWeight> getAll() {
        return Arrays.stream(values()).toList();
    }

    @Override
    public String getTag() {
        return tag;
    }


}
