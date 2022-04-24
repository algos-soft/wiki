package it.algos.vaad23.backend.enumeration;

import java.util.*;

/**
 * Project vaadflow14
 * Created by Algos
 * User: gac
 * Date: mer, 31-mar-2021
 * Time: 10:14
 */
public enum AETypeParam {
    segmentOnly, singleParameter, parametersMap, multiParametersMap;

    public static List<AETypeParam> getAllEnums() {
        return Arrays.stream(values()).toList();
    }

}
