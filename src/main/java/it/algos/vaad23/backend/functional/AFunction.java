package it.algos.vaad23.backend.functional;

import java.util.*;
import java.util.function.*;

/**
 * Project vaadflow14
 * Created by Algos
 * User: gac
 * Date: mer, 02-dic-2020
 * Time: 15:20
 * Function(T,R): Takes one parameter type (T), produces one result (R).
 * FunctionalInterface
 * public interface Function<T, R> {
 * <p>
 * R apply(T t);
 * <p>
 * }
 * <p>
 * T – Type of the input to the function.
 * R – Type of the result of the function.
 */
public abstract class AFunction {

    public static Function<List<String>, String> riduce = n -> n.get(0);

}
