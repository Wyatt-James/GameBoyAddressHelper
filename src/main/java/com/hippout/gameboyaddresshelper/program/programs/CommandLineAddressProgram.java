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
                outS.println("Please input your addresses in format XX:XXXX, one per line, followed by an empty line.");
                final List<String> lines = ScannerUtil.getLines(
                        sc,
                        Address::isValidString,
                        "Invalid address format: %s");

                addrs.addAll(lines.stream().map(Address::fromString).collect(Collectors.toList()));
            }

            case GENERATE -> {
                Address addr;
                int numToGenerate;
                int step;

                while (true) {
                    outS.println("Please input a start address, in format XX:XXXX");
                    addr = ScannerUtil.getAddress(sc, a -> true, "");

                    outS.println("How many addresses to generate?");
                    numToGenerate = ScannerUtil.getInt(sc, i -> i > 0, "Must be > 0.");

                    outS.println("What to step between each address? You can use hexadecimal with the format 0x#");
                    step = ScannerUtil.getInt(sc, i -> i != 0, "Must not be 0.");

                    int destAddr = addr.address + ((numToGenerate - 1) * step);

                    if (destAddr > 0xFFFF) {
                        outS.println("Error: this will overflow. Try again.");
                        continue;
                    }

                    if (destAddr < 0) {
                        outS.println("Error: this will underflow. Try again.");
                        continue;
                    }

                    break;
                }

                for (int i = 0; i < numToGenerate; i++)
                    addrs.add(new Address(addr.bank, addr.address + (i * step)));
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
        GENERATE("Generate"),
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
