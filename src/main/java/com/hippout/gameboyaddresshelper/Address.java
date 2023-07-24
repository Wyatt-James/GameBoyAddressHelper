package com.hippout.gameboyaddresshelper;

import com.hippout.gameboyaddresshelper.util.*;

import javax.annotation.*;
import java.util.*;
import java.util.regex.*;

public final class Address implements Comparable<Address> {
    public static final Comparator<Address> ASCENDING_COMPARATOR = Address::compareTo;
    public static final Comparator<Address> DESCENDING_COMPARATOR = Address::compareToReverse;

    public static final String GB_ADDRESS_REGEX_STR = "^((?<bank>[0-9a-fA-F]{2}):)?(?<addr>[0-9a-fA-F]{4})$";
    public static final Pattern GB_ADDRESS_PATTERN = Pattern.compile(GB_ADDRESS_REGEX_STR);

    public final int bank;
    public final int address;

    public Address(int address)
    {
        this(0, address);
    }

    public Address(int bank, int address)
    {
        if (bank < 0 || bank > 0xFF)
            throw new IllegalArgumentException("Bank out of range [00-FF]. Given: " + bank);

        if (address < 0 || address > 0xFFFF)
            throw new IllegalArgumentException("Bank out of range [0000-FFFF]. Given: " + address);

        this.bank = bank;
        this.address = address;
    }

    public static Address fromString(@Nonnull String str)
    {
        Objects.requireNonNull(str, "String cannot be null.");

        final Matcher m = GB_ADDRESS_PATTERN.matcher(str);

        if (!m.matches())
            throw new IllegalArgumentException("Invalid address String: " + str);

        final String bankStr = MiscUtil.defaultIfNull(m.group("bank"), "00").toUpperCase(Locale.ROOT);
        final String addrStr = m.group("addr").toUpperCase(Locale.ROOT);

        int bank, address;

        try {
            bank = Integer.decode("0x" + bankStr);
            address = Integer.decode("0x" + addrStr);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid address string: " + str);
        }

        return new Address(bank, address);
    }

    public static boolean isValidString(@Nullable String str)
    {
        if (str == null) return false;
        return GB_ADDRESS_PATTERN.matcher(str).matches();
    }

    public static boolean isNotValidString(@Nullable String str)
    {
        return !isValidString(str);
    }

    /**
     * Compares two Addresses. Sorts in ascending order.
     * <p/>
     * An address is greater than another if this.bank > other.bank, OR if banks are equal and this.bank > other.bank
     * . E.g, banks take priority over addresses.
     *
     * @param other Address to compare against.
     * @return 1 if this > other, -1 if this < other, or 0 if this.equals(other).
     */
    @Override
    public int compareTo(@Nonnull Address other)
    {
        if (bank != other.bank)
            return Integer.compare(bank, other.bank);
        else
            return Integer.compare(address, other.address);
    }

    /**
     * The inverse of compareTo. Sorts in descending order.
     * <p/>
     * An address is greater than another if this.bank > other.bank, OR if banks are equal and this.bank > other.bank
     * . E.g, banks take priority over addresses.
     *
     * @param other Address to compare against.
     * @return -1 if this > other, 1 if this < other, or 0 if this.equals(other).
     */
    public int compareToReverse(@Nonnull Address other)
    {
        return -1 * compareTo(other);
    }

    @Override
    @Nonnull
    public String toString()
    {
        return String.format("%02X:%04X", bank, address);
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;

        if (o instanceof Address other)
            return bank == other.bank && address == other.address;

        return false;
    }

    @Override
    public int hashCode()
    {
        return (bank << 16) | address;
    }
}
