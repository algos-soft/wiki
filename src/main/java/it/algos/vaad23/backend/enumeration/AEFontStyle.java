package it.algos.vaad23.backend.enumeration;

import it.algos.vaad23.backend.interfaces.*;

import java.util.*;

/**
 * Project vaadin23
 * Created by Algos
 * User: gac
 * Date: ven, 08-apr-2022
 * Time: 20:13
 */
public enum AEFontStyle implements AIType {

    normal("normal"),
    italic("italic"),
    ;

    public static final String HTML = "font-style";

    private String tag;

    AEFontStyle(String tag) {
        this.tag = tag;
    }

    public static List<AEFontStyle> getAllEnums() {
        return Arrays.stream(values()).toList();
    }


    public static List<String> getAllStringValues() {
        List<String> listaValues = new ArrayList<>();

        getAllEnums().forEach(font -> listaValues.add(font.toString()));
        return listaValues;
    }

    @Override
    public List<AEFontStyle> getAll() {
        return Arrays.stream(values()).toList();
    }

    @Override
    public String getTag() {
        return tag;
    }
}
