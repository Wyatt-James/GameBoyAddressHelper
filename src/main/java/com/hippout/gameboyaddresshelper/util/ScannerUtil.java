package com.hippout.gameboyaddresshelper.util;

import com.hippout.gameboyaddresshelper.*;

import javax.annotation.*;
import java.util.*;
import java.util.function.*;

public final class ScannerUtil {

    public static List<String> getLines(@Nonnull Scanner sc)
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
            @Nonnull Predicate<String> successCondition,
            @Nonnull String errorMessage)
    {
        return getLines(sc, String::isEmpty, successCondition, errorMessage);
    }

    public static List<String> getLines(
            @Nonnull Scanner sc,
            @Nonnull Predicate<String> exitCondition,
            @Nonnull Predicate<String> successCondition,
            @Nonnull String errorMessage)
    {
        final List<String> strings = new LinkedList<>();
        boolean keepRunning = true;

        while (keepRunning) {
            final String line = sc.nextLine();

            if (exitCondition.test(line))
                keepRunning = false;

            else if (!successCondition.test(line))
                System.out.printf(errorMessage + "\n", line);

            else
                strings.add(line);
        }

        return strings;
    }

    public static int getInt(
            @Nonnull Scanner sc,
            @Nonnull Predicate<Integer> successCondition,
            @Nonnull String errorMessage)
    {
        while (true) {
            final String line = sc.nextLine();
            int val;

            try {
                val = Integer.decode(line);
            } catch (NumberFormatException e) {
                System.out.printf("Error: %s is not a number.\n", line);
                continue;
            }

            if (!successCondition.test(val))
                System.out.printf(errorMessage + "\n", val);
            else
                return val;
        }
    }

    public static String getString(
            @Nonnull Scanner sc,
            @Nonnull Predicate<String> successCondition,
            @Nonnull String errorMessage)
    {
        while (true) {
            final String line = sc.nextLine();

            if (!successCondition.test(line))
                System.out.printf(errorMessage + "\n", line);
            else
                return line;
        }
    }

    public static Address getAddress(
            @Nonnull Scanner sc,
            @Nonnull Predicate<Address> successCondition,
            @Nonnull String errorMessage)
    {
        while (true) {
            final String line = sc.nextLine();
            final Address addr;

            try {
                addr = Address.fromString(line);
            } catch (Exception e) {
                System.out.println("Error: invalid Address.");
                continue;
            }

            if (!successCondition.test(addr))
                System.out.printf(errorMessage + "\n", line);
            else
                return addr;
        }
    }
}
