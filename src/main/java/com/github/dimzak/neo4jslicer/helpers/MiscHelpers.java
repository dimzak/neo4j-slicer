package com.github.dimzak.neo4jslicer.helpers;

import com.github.dimzak.neo4jslicer.constants.Mode;

import java.util.Locale;

/**
 * Misc helpers across project
 */
public class MiscHelpers {

    /**
     * Get mode from a string (case insensitive)
     * @param mode
     * @return {@link Mode}
     */
    public static Mode getMode(String mode) {
        return Mode.valueOf(mode.toUpperCase(Locale.ENGLISH));
    }

    public static String escapeDoubleQuotes(String string) {
        return string.replaceAll("\"","\\\"");
    }
}
