package com.hippout.gameboyaddresshelper.util;

import javax.annotation.*;
import java.io.*;
import java.util.*;

public final class EnumInput {
    public static final String DEFAULT_TYPE_NAME = "option";
    public static final int DEFAULT_COUNT_FROM = 1;

    public static <T> T getInputByOrdinal(
            T[] values)
    {
        return getInputByOrdinal(DEFAULT_TYPE_NAME, DEFAULT_COUNT_FROM, values);
    }

    public static <T> T getInputByOrdinal(
            int countFrom,
            T[] values)
    {
        return getInputByOrdinal(DEFAULT_TYPE_NAME, countFrom, values);
    }

    public static <T> T getInputByOrdinal(
            @Nullable String typeName,
            T[] values)
    {
        return getInputByOrdinal(typeName, DEFAULT_COUNT_FROM, values);
    }

    public static <T> T getInputByOrdinal(
            @Nullable String typeName,
            int countFrom,
            T[] values)
    {
        return getInputByOrdinal(System.in, System.out, typeName, countFrom, values);
    }

    public static <T> T getInputByOrdinal(
            @Nonnull InputStream is,
            @Nonnull PrintStream out,
            int countFrom,
            T[] values)
    {
        return getInputByOrdinal(is, out, DEFAULT_TYPE_NAME, countFrom, values);
    }

    public static <T> T getInputByOrdinal(
            @Nonnull InputStream is,
            @Nonnull PrintStream out,
            @Nullable String typeName,
            T[] values)
    {
        return getInputByOrdinal(is, out, typeName, DEFAULT_COUNT_FROM, values);
    }

    public static <T> T getInputByOrdinal(
            @Nonnull InputStream is,
            @Nonnull PrintStream out,
            @Nullable String typeName,
            int countFrom,
            T[] values)
    {
        final Scanner sc = new Scanner(is);
        T result = getInputByOrdinal(sc, out, typeName, countFrom, values);
        sc.close();
        return result;
    }

    public static <T> T getInputByOrdinal(
            @Nonnull Scanner sc,
            @Nonnull PrintStream out,
            T[] values)
    {
        return getInputByOrdinal(sc, out, DEFAULT_TYPE_NAME, DEFAULT_COUNT_FROM, values);
    }

    public static <T> T getInputByOrdinal(
            @Nonnull Scanner sc,
            @Nonnull PrintStream out,
            int countFrom,
            T[] values)
    {
        return getInputByOrdinal(sc, out, DEFAULT_TYPE_NAME, countFrom, values);
    }

    public static <T> T getInputByOrdinal(
            @Nonnull Scanner sc,
            @Nonnull PrintStream out,
            @Nullable String typeName,
            T[] values)
    {
        return getInputByOrdinal(sc, out, typeName, DEFAULT_COUNT_FROM, values);
    }

    public static <T> T getInputByOrdinal(
            @Nonnull Scanner sc,
            @Nonnull PrintStream out,
            @Nullable String typeName,
            int countFrom,
            T[] values)
    {
        Objects.requireNonNull(sc, "Scanner cannot be null.");
        Objects.requireNonNull(out, "Output PrintStream cannot be null.");
        Objects.requireNonNull(values, "Values cannot be null.");
        if (values.length == 0) throw new IllegalArgumentException("Values cannot be empty.");

        typeName = MiscUtil.defaultIfNull(typeName, "option");

        final String choicesString = StringUtil.formatNumberedArray(
                countFrom,
                "\t%d: %s",
                "\n",
                (Object[]) values);

        int maxInd = countFrom + (values.length - 1);

        int result;
        out.println("Choices:");
        out.println(choicesString);
        out.printf("Select a(n) %s with a number [%d-%d]: ", typeName, countFrom, maxInd);

        while (true) {
            final String input = sc.nextLine();

            try {
                result = Integer.parseInt(input);

                if (result >= countFrom && result <= maxInd)
                    break;
                else
                    out.printf("Incorrect choice: %s. Try again: ", result);

            } catch (NumberFormatException e) {
                out.printf("Error: %s is not a number. Try again: ", input);
            }
        }

        out.println();

        // Adjust result to be in the array's range
        return values[result - countFrom];
    }
}
