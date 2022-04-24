package it.algos.vaad23.backend.exception;


import static it.algos.vaad23.backend.boot.VaadCost.*;
import it.algos.vaad23.backend.entity.*;

import java.util.*;

/**
 * Project vaadflow14
 * Created by Algos
 * User: gac
 * Date: mer, 25-ago-2021
 * Time: 20:00
 */
public class AlgosException extends Exception {

    private String message;

    private AEntity entityBean;

    public AlgosException() {
        super(VUOTA);
        this.message = VUOTA;
    }

    public AlgosException(final Throwable cause) {
        super(cause);
    }

    public AlgosException(final String message) {
        super(message);
        this.message = message;
    }

    public AlgosException(final Throwable cause, final String message) {
        super(message, cause);
        this.message = message;
    }

    public AlgosException(final Throwable cause, final AEntity entityBean) {
        this(cause, VUOTA, entityBean);
    }

    public AlgosException(final Throwable cause, final String message, final AEntity entityBean) {
        super(message, cause);
        this.message = message;
        this.entityBean = entityBean;
    }

    public static AlgosException crea(final Throwable cause) {
        return new AlgosException(cause);
    }

    //    public static AlgosException crea(final Throwable cause, final String message) {
    //        return new AlgosException(cause, message);
    //    }
    //
    //    public static AlgosException crea(final Throwable cause, final AEntity entityBean) {
    //        return new AlgosException(cause, VUOTA, entityBean);
    //    }
    //
    //    public static AlgosException crea(final Throwable cause, final String message, final AEntity entityBean) {
    //        AlgosException algosException = new AlgosException(cause, message);
    //        algosException.entityBean = entityBean;
    //
    //        return algosException;
    //    }


    public AEntity getEntityBean() {
        return entityBean;
    }

    public String getMessage() {
        if (getCause() != null && getCause() instanceof Exception eccezione) {
            if (eccezione.getMessage() != null) {
                return eccezione.getMessage();
            }
            else {
                return eccezione.toString();
            }
        }
        return message;
    }

    /**
     * Classe da cui proviene l'errore <br>
     *
     * @return simpleName della classe di errore
     */
    public String getClazz() {
        StackTraceElement stack = getStack();
        return stack != null ? stack.getClassName() : VUOTA;
    }

    /**
     * Metodo da cui proviene l'errore <br>
     *
     * @return nome del metodo di errore
     */
    public String getMethod() {
        StackTraceElement stack = getStack();
        return stack != null ? stack.getMethodName() : VUOTA;
    }

    /**
     * Riga da cui proviene l'errore <br>
     *
     * @return numero della linea di errore
     */
    public int getLineNum() {
        StackTraceElement stack = getStack();
        return stack != null ? stack.getLineNumber() : 0;
    }

    /**
     * Riga da cui proviene l'errore <br>
     *
     * @return testo della linea di errore
     */
    public String getLine() {
        return getLineNum() + VUOTA;
    }


    /**
     * Stack dell'errore <br>
     */
    public StackTraceElement getStack() {
        StackTraceElement stack = null;
        StackTraceElement[] matrice = null;

        if (this.getCause() != null) {
            matrice = this.getCause().getStackTrace();
        }

        if (matrice == null) {
            matrice = this.getStackTrace();
        }

        if (matrice != null) {
            Optional stackPossibile = Arrays.stream(matrice)
                    .filter(algos -> algos.getClassName().startsWith(PATH_ALGOS))
                    .findFirst();
            if (stackPossibile != null) {
                stack = (StackTraceElement) stackPossibile.get();
            }
        }

        return stack;
    }

}
