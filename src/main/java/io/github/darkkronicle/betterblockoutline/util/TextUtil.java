package io.github.darkkronicle.betterblockoutline.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class TextUtil {

    private final char[] SUPERSCRIPTS =
            new char[] {
                    '\u2070', '\u00B9', '\u00B2', '\u00B3', '\u2074', '\u2075', '\u2076', '\u2077',
                    '\u2078', '\u2079'
            };

    private final char[] SUBSCRIPTS =
            new char[] {
                    '\u2080', '\u2081', '\u2082', '\u2083', '\u2084', '\u2085', '\u2086', '\u2087',
                    '\u2088', '\u2089'
            };

    /**
     * Converts a number into a superscript representation.
     * @param num Number to convert
     * @return Number out of superscript
     */
    public String toSuperscript(int num) {
        StringBuilder sb = new StringBuilder();
        do {
            sb.append(SUPERSCRIPTS[num % 10]);
        } while ((num /= 10) > 0);
        return sb.reverse().toString();
    }

    /**
     * Converts a number into a subscript representation.
     * @param num Number to convert
     * @return Number out of subscript
     */
    public String toSubscript(int num) {
        StringBuilder sb = new StringBuilder();
        do {
            sb.append(SUBSCRIPTS[num % 10]);
        } while ((num /= 10) > 0);
        return sb.reverse().toString();
    }



}
