package com.foureverinbeta.s3LinkGenerator.utilities;

/**
 * Created by Ryan on 10/3/16.
 * ${CLASSNAME}
 */
public class TypeCheckers {
    public static boolean empty( final String s ) {
        // Null-safe, short-circuit evaluation.
        return s == null || s.trim().isEmpty();
    }
}
