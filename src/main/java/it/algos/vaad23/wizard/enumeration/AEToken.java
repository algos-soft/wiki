package it.algos.vaad23.wizard.enumeration;


import static it.algos.vaad23.backend.boot.VaadCost.*;

import java.time.*;
import java.time.format.*;


/**
 * Project springvaadin
 * Created by Algos
 * User: gac
 * Date: mar, 06-mar-2018
 * Time: 19:46
 */
public enum AEToken {

    user("USER"),
    targetProject("PROJECT"),
    targetProjectUpper("PROJECTUPPER"),

    targetProjectAllUpper("PROJECTALLUPPER"),

    firstProject("FIRSTPROJECT"),

    today("TODAY"),
    todayAnno("TODAYANNO"),
    todayMese("TODAYMESE"),
    todayGiorno("TODAYGIORNO"),
    ;

    private static String DELIMITER = "@";

    private String tokenTag;

    private String value;


    AEToken(final String tokenTag) {
        this.tokenTag = tokenTag;
    }


    public static void reset() {
        for (AEToken aeToken : AEToken.values()) {
            aeToken.value = VUOTA;
        } AEToken.user.value = "gac";
    }

    public static void setCrono() {
        LocalDateTime adesso = LocalDateTime.now();
        AEToken.today.value = LocalDateTime.now().format(DateTimeFormatter.ofPattern("EEE, d MMM yy"));
        AEToken.todayAnno.value = adesso.getYear() + VUOTA; AEToken.todayMese.value = adesso.getMonthValue() + VUOTA;
        AEToken.todayGiorno.value = adesso.getDayOfMonth() + VUOTA;
    }

    public static String replaceAll(String textReplacing) {
        for (AEToken aeToken : AEToken.values()) {
            textReplacing = aeToken.replace(textReplacing);
        } return textReplacing;
    }


    public String replace(String textReplacing) {
        if (value != null && !value.equals(VUOTA)) {
            return textReplacing.replaceAll(DELIMITER + this.tokenTag + DELIMITER, value);
        }
        else {
            return textReplacing;
        }
    }


    public void set(String value) {
        this.value = value;
    }

}
