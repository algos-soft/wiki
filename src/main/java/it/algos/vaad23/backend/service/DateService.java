package it.algos.vaad23.backend.service;

import org.springframework.beans.factory.config.*;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.*;

import java.time.*;
import java.time.format.*;
import java.util.*;

/**
 * Project vaadin23
 * Created by Algos
 * User: gac
 * Date: lun, 07-mar-2022
 * Time: 14:57
 * <p>
 * Classe di libreria; NON deve essere astratta, altrimenti SpringBoot non la costruisce <br>
 * Estende la classe astratta AbstractService che mantiene i riferimenti agli altri services <br>
 * L'istanza può essere richiamata con: <br>
 * 1) StaticContextAccessor.getBean(ADateService.class); <br>
 * 3) @Autowired public DateService annotation; <br>
 * <p>
 * Annotated with @Service (obbligatorio, se si usa la catena @Autowired di SpringBoot) <br>
 * NOT annotated with @SpringComponent (inutile, esiste già @Service) <br>
 * Annotated with @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) (obbligatorio) <br>
 */
@Service
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class DateService extends AbstractService {

    public static final String INFERIORE_SECONDO = "meno di un secondo";

    public static final String INFERIORE_MINUTO = "meno di un minuto";

    private static final String MILLI_SECONDI = " millisecondi";

    private static final String SECONDI = " sec.";

    private static final String MINUTI = " min.";

    private static final String ORA = " ora";

    private static final String ORE = " ore";

    private static final String GIORNO = " giorno";

    private static final String GIORNI = " gg.";

    private static final String ANNO = " anno";

    private static final String ANNI = " anni";

    private static final long MAX_MILLISEC = 1000;

    private static final long MAX_SECONDI = 60 * MAX_MILLISEC;

    private static final long MAX_MINUTI = 60 * MAX_SECONDI;

    private static final long MAX_ORE = 24 * MAX_MINUTI;

    private static final long MAX_GIORNI = 365 * MAX_ORE;

    /**
     * Restituisce come stringa (intelligente) un durata espressa in long <br>
     *
     * @return tempo esatto in millisecondi in forma leggibile
     */
    public String deltaTextEsatto(final long inizio) {
        long fine = System.currentTimeMillis();
        return textService.format(fine - inizio) + MILLI_SECONDI;
    }

    /**
     * Restituisce come stringa (intelligente) un durata espressa in long <br>
     * Esegue degli arrotondamenti <br>
     *
     * @return tempo arrotondato in forma leggibile
     */
    public String deltaText(final long inizio) {
        long fine = System.currentTimeMillis();
        return toText(fine - inizio);
    }


    /**
     * Restituisce come stringa (intelligente) una durata espressa in long
     * - Meno di 1 secondo
     * - Meno di 1 minuto
     * - Meno di 1 ora
     * - Meno di 1 giorno
     * - Meno di 1 anno
     *
     * @param durata in millisecondi
     *
     * @return durata (arrotondata e semplificata) in forma leggibile
     */
    public String toText(final long durata) {
        String tempo = "null";
        long div;
        long mod;

        if (durata < MAX_MILLISEC) {
            tempo = INFERIORE_SECONDO;
        }
        else {
            if (durata < MAX_SECONDI) {
                div = Math.floorDiv(durata, MAX_MILLISEC);
                mod = Math.floorMod(durata, MAX_MILLISEC);
                if (div < 60) {
                    tempo = div + SECONDI;
                }
                else {
                    tempo = "1" + MINUTI;
                }
            }
            else {
                if (durata < MAX_MINUTI) {
                    div = Math.floorDiv(durata, MAX_SECONDI);
                    mod = Math.floorMod(durata, MAX_SECONDI);
                    if (div < 60) {
                        tempo = div + MINUTI;
                    }
                    else {
                        tempo = "1" + ORA;
                    }
                }
                else {
                    if (durata < MAX_ORE) {
                        div = Math.floorDiv(durata, MAX_MINUTI);
                        mod = Math.floorMod(durata, MAX_MINUTI);
                        if (div < 24) {
                            if (div == 1) {
                                tempo = div + ORA;
                            }
                            else {
                                tempo = div + ORE;
                            }
                            tempo += " e " + toText(durata - (div * MAX_MINUTI));
                        }
                        else {
                            tempo = "1" + GIORNO;
                        }
                    }
                    else {
                        if (durata < MAX_GIORNI) {
                            div = Math.floorDiv(durata, MAX_ORE);
                            mod = Math.floorMod(durata, MAX_ORE);
                            if (div < 365) {
                                if (div == 1) {
                                    tempo = div + GIORNO;
                                }
                                else {
                                    tempo = div + GIORNI;
                                }
                            }
                            else {
                                tempo = "1" + ANNO;
                            }
                        }
                        else {
                            div = Math.floorDiv(durata, MAX_GIORNI);
                            mod = Math.floorMod(durata, MAX_GIORNI);
                            if (div == 1) {
                                tempo = div + ANNO;
                            }
                            else {
                                tempo = div + ANNI;
                            }
                        }
                    }
                }
            }
        }

        return tempo;
    }

    public String toTextSecondi(final long durata) {
        return durata < 1 ? INFERIORE_SECONDO : toText(durata * 1000);
    }

    public String toTextMinuti(final long durata) {
        return durata < 1 ? INFERIORE_MINUTO : toTextSecondi(durata * 60);
    }

    public String get() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("d-MMM-yy"));
    }

    public String get(LocalDateTime localDateTime) {
        return localDateTime.format(DateTimeFormatter.ofPattern("d-MMM-yy H:mm", Locale.ITALIAN));
    }

}