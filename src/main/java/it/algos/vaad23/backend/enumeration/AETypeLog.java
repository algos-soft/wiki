package it.algos.vaad23.backend.enumeration;

import it.algos.vaad23.backend.interfaces.*;

import java.util.*;

/**
 * Project vaadflow
 * Created by Algos
 * User: gac
 * Date: mer, 26-set-2018
 * Time: 07:39
 */
public enum AETypeLog implements AIType {

    system("system"),
    setup("setup"),
    login("login"),
    startup("startup"),
    sviluppo("sviluppo"),
    checkMenu("checkMenu"),
    checkData("checkData"),
    preferenze("preferenze"),
    nuovo("newEntity"),
    edit("edit"),
    modifica("modifica"),
    delete("delete"),
    deleteAll("deleteAll"),
    mongo("mongoDB"),
    file("file"),
    resources("resources"),
    debug("debug"),
    info("info"),
    warn("warn"),
    error("error"),
    wizard("wizard"),
    wizardDoc("wizardDoc"),
    importo("import"),
    export("export"),
    download("download"),
    upload("upload"),
    update("update"),
    elabora("elabora"),
    reset("reset"),
    utente("utente"),
    password("password"),
    bio("cicloBio"),
    test("test"),
    spazio("spazio"),
    ;

    private String tag;


    AETypeLog(String tag) {
        this.tag = tag;
    }


    public static List<AETypeLog> getAllEnums() {
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

    public static AETypeLog getType(final String tag) {
        return getAllEnums()
                .stream()
                .filter(type -> type.getTag().equals(tag))
                .findAny()
                .orElse(null);
    }

    @Override
    public List<AETypeLog> getAll() {
        return Arrays.stream(values()).toList();
    }

    @Override
    public String getTag() {
        return tag;
    }

}

