package com.hippout.gameboyaddresshelper.util;

import javax.annotation.*;
import java.util.*;
import java.util.function.*;

public final class ScannerUtil {
    public static List<String> getLines(
            @Nonnull Scanner sc)
    {
        return getLines(sc, String::isEmpty, (s) -> true, "");
    }

    public static List<String> getLines(
            @Nonnull Scanner sc,
            @Nonnull Predicate<String> exitCondition)
    {
        return getLines(sc, exitCondition, (s) -> true, "");
    }

    public static List<String> getLines(
            @Nonnull Scanner sc,
            @Nonnull Predicate<String> errorCondition,
            @Nonnull String errorMessage)
    {
        return getLines(sc, String::isEmpty, errorCondition, errorMessage);
    }

    public static List<String> getLines(
            @Nonnull Scanner sc,
            @Nonnull Predicate<String> exitCondition,
            @Nonnull Predicate<String> errorCondition,
            @Nonnull String errorMessage)
    {
        final List<String> strings = new LinkedList<>();
        boolean keepRunning = true;

        while (keepRunning) {
            final String line = sc.nextLine();

            if (exitCondition.test(line))
                keepRunning = false;

            else if (errorCondition.test(line))
                System.out.printf(errorMessage + "\n", line);

            else
                strings.add(line);
        }

        return strings;
    }
}
