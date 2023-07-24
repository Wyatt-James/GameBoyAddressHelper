package com.hippout.gameboyaddresshelper.program.programs;

import com.hippout.gameboyaddresshelper.*;
import com.hippout.gameboyaddresshelper.program.*;
import com.hippout.gameboyaddresshelper.util.*;

import javax.annotation.*;
import java.io.*;
import java.util.*;
import java.util.stream.*;

public class CommandLineAddressProgram implements AddressProgram {
    private final Scanner sc;
    private final PrintStream outS;
    private final List<Address> addrs;

    public CommandLineAddressProgram(@Nonnull Scanner sc, @Nonnull PrintStream outS)
    {
        this.sc = Objects.requireNonNull(sc, "Scanner cannot be null.");
        this.outS = Objects.requireNonNull(outS, "Output PrintStream cannot be null.");
        addrs = new ArrayList<>();
    }

    public void run()
    {
        boolean keepRunning = true;

        while (keepRunning) {
            switch (EnumInput.getInputByOrdinal(sc, outS, MainOption.values())) {
                case ADD -> add();
                case REMOVE -> remove();
                case DEDUPE -> dedupe();
                case SORT_ASCENDING -> addrs.sort(Address.ASCENDING_COMPARATOR);
                case SORT_DESCENDING -> addrs.sort(Address.DESCENDING_COMPARATOR);
                case PRINT -> outS.println(StringUtil.concatListToString("\n", addrs));
                case PRINT_COUNT -> outS.printf("There are %d addresses.\n", addrs.size());
                case EXPORT -> outS.println("Export is not implemented yet.");

                case CLEAR -> {
                    addrs.clear();
                    outS.println("Addresses cleared.");
                }

                case EXIT -> keepRunning = false;
            }

            outS.println();
        }
    }

    private void add()
    {
        switch (EnumInput.getInputByOrdinal(sc, outS, ImportOption.values())) {

            case FILE -> outS.println("File import is not implemented yet.");

            case COMMAND_LINE -> {
                outS.println("Please input your addresses, one per line, followed by an empty line.");
                final List<String> lines = ScannerUtil.getLines(
                        sc,
                        Address::isNotValidString,
                        "Invalid address format: %s");

                addrs.addAll(lines.stream().map(Address::fromString).collect(Collectors.toList()));
            }
        }
    }

    private void remove()
    {
        outS.println("Remove is not implemented yet.");
    }

    private void dedupe()
    {
        int sizeBefore = addrs.size();

        final List<Address> sorted = addrs.stream().distinct().collect(Collectors.toList());
        addrs.clear();
        addrs.addAll(sorted);

        outS.printf("%d duplicates removed.\n", sizeBefore - addrs.size());
    }

    public enum ImportOption {
        FILE("Import from file"),
        COMMAND_LINE("Input via command line"),
        CANCEL("Cancel");

        public final String name;

        ImportOption(String name)
        {
            this.name = name;
        }

        @Override
        @Nonnull
        public String toString()
        {
            return name;
        }
    }

    public enum MainOption {
        ADD("Add addresses"),
        REMOVE("Remove addresses"),
        DEDUPE("Remove duplicates"),
        SORT_ASCENDING("Sort addresses in ascending order"),
        SORT_DESCENDING("Sort addresses in descending order"),
        PRINT("Print"),
        PRINT_COUNT("Print number of addresses"),
        EXPORT("Export to file"),
        CLEAR("Clear addresses"),
        EXIT("Exit program");

        public final String name;

        MainOption(String name)
        {
            this.name = name;
        }

        @Override
        @Nonnull
        public String toString()
        {
            return name;
        }
    }
}
