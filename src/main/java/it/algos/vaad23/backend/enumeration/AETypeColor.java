package it.algos.vaad23.backend.enumeration;

import it.algos.vaad23.backend.interfaces.*;

import java.util.*;

/**
 * Project vaadflow14
 * Created by Algos
 * User: gac
 * Date: lun, 15-feb-2021
 * Time: 20:42
 */
public enum AETypeColor implements AIType {
    normale("black"),
    nero("black"),
    blu("blue"),
    verde("green"),
    rosso("red"),
    ;

    public static final String HTML = "color";

    private String tag;


    AETypeColor(String tag) {
        this.tag = tag;
    }

    public static List<AETypeColor> getAllEnums() {
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

    public static AETypeColor getType(final String tag) {
        return getAllEnums()
                .stream()
                .filter(type -> type.getTag().equals(tag))
                .findAny()
                .orElse(null);
    }

    @Override
    public List<AETypeColor> getAll() {
        return Arrays.stream(values()).toList();
    }

    @Override
    public String getTag() {
        return tag;
    }

}
