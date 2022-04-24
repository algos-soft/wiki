package it.algos.vaad23.backend.functional;

import org.jsoup.internal.*;

import java.util.function.*;

/**
 * Project vaadflow14
 * Created by Algos
 * User: gac
 * Date: mar, 01-dic-2020
 * Time: 21:20
 * <p>
 * Predicate(T): Takes one parameter type (T), produces boolean value <br>
 * Predicate<T> is a generic functional interface representing <br>
 * a single argument function that returns a boolean value <br>
 */
public abstract class APredicate {

    public static Predicate<Object> valido = new Predicate<>() {

        /**
         * Evaluates this predicate on the given argument.
         *
         * @param obj the input argument
         *
         * @return {@code true} if the input argument matches the predicate,
         * otherwise {@code false}
         */
        @Override
        public boolean test(Object obj) {
            return (obj instanceof String) ? !StringUtil.isBlank((String) obj) : obj != null;
        }
    };

    public static Predicate<Object> nonValido = Predicate.not(valido);

    public static Predicate<Integer> pari = i -> i % 2 == 0;

    public static Predicate<Integer> dispari = Predicate.not(pari);

}