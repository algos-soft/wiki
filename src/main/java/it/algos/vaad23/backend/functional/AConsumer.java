package it.algos.vaad23.backend.functional;

import java.util.function.*;

/**
 * Project vaadflow14
 * Created by Algos
 * User: gac
 * Date: gio, 03-dic-2020
 * Time: 17:26
 * Consumer(T): Takes one parameter type (T), produces nothing (void). <br>
 */
public abstract class AConsumer {


    public static Consumer<String> print = value -> System.out.println(value);

}
