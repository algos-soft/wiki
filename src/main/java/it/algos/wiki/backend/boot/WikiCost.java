package it.algos.wiki.backend.boot;

import com.vaadin.flow.spring.annotation.*;
import static it.algos.vaad23.backend.boot.VaadCost.*;
import org.springframework.beans.factory.config.*;
import org.springframework.context.annotation.Scope;

/**
 * Project Wiki
 * Created by Algos
 * User: gac
 * Date: lun, 25 apr 22
 */
@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class WikiCost {

    public static final String TAG_WIKI_VERSION = "wikiversion";

    public static final String PATH_MODULO = "Modulo:Bio/";

    public static final String PATH_PROGETTO = "Progetto:Biografie/";

    public static final String PATH_MODULO_PLURALE = PATH_MODULO + "Plurale ";

    public static final String PATH_MODULO_LINK = PATH_MODULO + "Link ";

    public static final String GENERE = "genere";

    public static final String GIORNI = "Giorni";

    public static final String ANNI = "ANNI";

    public static final String ATT = "Attività";

    public static final String NAZ = "Nazionalità";

    public static final String PATH_WIKI = "https://it.wikipedia.org/wiki/";

    public static final String PATH_ATTIVITA = PATH_PROGETTO + ATT;

    public static final String PATH_NAZIONALITA = PATH_PROGETTO + NAZ;


    public static final String ATT_LOWER = ATT.toLowerCase();

    public static final String PATH_MODULO_GENERE = PATH_MODULO_PLURALE + ATT_LOWER + SPAZIO + GENERE;

    public static final String PATH_MODULO_ATTIVITA = PATH_MODULO_PLURALE + ATT_LOWER;

    public static final String PATH_TABELLA_NOMI_DOPPI = "Progetto:Antroponimi/Nomi_doppi";

    public static final String PATH_MODULO_PROFESSIONE = PATH_MODULO_LINK + ATT_LOWER;


    public static final String NAZ_LOWER = NAZ.toLowerCase();

    public static final String PATH_MODULO_NAZIONALITA = PATH_MODULO_PLURALE + NAZ_LOWER;

    public static final String PATH_STATISTICHE_ATTIVITA = PATH_PROGETTO + ATT;

    public static final String PATH_STATISTICHE_NAZIONALITA = PATH_PROGETTO + NAZ;

    public static final String PATH_MODULO_PRENOME = "Progetto:Antroponimi/Nomi doppi";

    public static final String PATH_STATISTICHE_GIORNI = PATH_PROGETTO + GIORNI;

    public static final String TAG_EX = "ex ";

    public static final String TAG_EX2 = "ex-";

    public static final String TAG_GENERE = "genere";

    public static final String TAG_ATTIVITA = "attivita";

    public static final String TAG_NAZIONALITA = "nazionalita";

    public static final String TAG_PROFESSIONE = "professione";

    public static final String TAG_DOPPIO_NOME = "doppionome";



}