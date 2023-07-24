package com.hippout.gameboyaddresshelper.util;

import javax.annotation.*;
import java.util.*;

public class StringUtil {

    /**
     * Combines each element of the Object array with a separator between.
     *
     * @param separator String with which to separate the parts.
     * @param parts     Object array to convert.
     * @return The combined String.
     */
    public static String concatArrayToString(@Nonnull String separator, @Nonnull Object... parts)
    {
        Objects.requireNonNull(separator, "Separator cannot be null.");
        Objects.requireNonNull(parts, "Args cannot be null.");

        if (parts.length == 0) return "";

        final StringBuilder sb = new StringBuilder(parts[0].toString());

        for (int i = 1; i < parts.length; ++i) {
            sb.append(separator);
            sb.append(parts[i].toString());
        }

        return sb.toString();
    }

    /**
     * Combines each element of the Object List with a separator between.
     *
     * @param separator String with which to separate the parts.
     * @param parts     Object List to convert.
     * @return The combined String.
     */
    public static String concatListToString(@Nonnull String separator, @Nonnull List<?> parts)
    {
        Objects.requireNonNull(separator, "Separator cannot be null.");
        Objects.requireNonNull(parts, "Args cannot be null.");

        if (parts.size() == 0) return "";

        final Iterator<?> it = parts.iterator();
        final StringBuilder sb = new StringBuilder(it.next().toString());

        while (it.hasNext()) {
            sb.append(separator);
            sb.append(it.next().toString());
        }

        return sb.toString();
    }

    public static String prefixEachArray(@Nonnull String prefix, @Nonnull Object... parts)
    {
        return prefix + concatArrayToString(prefix, parts);
    }

    public static String prefixEach(@Nonnull String prefix, @Nonnull List<?> parts)
    {
        return prefix + concatListToString(prefix, parts);
    }

    public static String formatNumberedArray(
            int initial,
            @Nonnull String format,
            @Nonnull String separator,
            @Nonnull Object... elems)
    {
        Objects.requireNonNull(format, "Format cannot be null.");
        Objects.requireNonNull(elems, "Elems cannot be null.");

        if (elems.length == 0) return "";

        final StringBuilder sb = new StringBuilder(String.format(format, initial, elems[0]));

        for (int i = 1; i < elems.length; i++) {
            sb.append(separator);
            sb.append(String.format(format, initial + i, elems[i]));
        }

        return sb.toString();
    }

    public static boolean isBlank(@Nonnull String str)
    {
        return str.trim().isEmpty();
    }
}
