package com.hippout.gameboyaddresshelper;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

public class AddressTest {

    static void assertAddressEquals(Address a, int bank, int address)
    {
        assertEquals(a.bank, bank, "Non-matching bank");
        assertEquals(a.address, address, "Non-matching address");
    }

    @Test
    void testConstructorNoBankOk()
    {
        final Address lowerBound = new Address(0);
        final Address upperBound = new Address(0xFF);

        assertEquals(lowerBound.bank, 0, String.format("Address(0) invalid bank: %d", lowerBound.bank));
        assertEquals(upperBound.bank, 0, String.format("Address(FF) invalid bank: %d", upperBound.bank));

        assertEquals(lowerBound.address, 0, String.format("Address(0) invalid address: %d", lowerBound.address));
        assertEquals(upperBound.address, 0xFF, String.format("Address(FF) invalid address: %d", upperBound.address));
    }

    @Test
    void testConstructorNoBankLowerBound()
    {
        assertThrows(IllegalArgumentException.class, () -> new Address(-1), "Address lower bound did not throw " +
                "exception.");
    }

    @Test
    void testConstructorNoBankUpperBound()
    {
        assertThrows(IllegalArgumentException.class, () -> new Address(0xFFFF + 1), "Address upper bound did not " +
                "throw exception.");
    }

    @Test
    void testConstructorWithBankOk()
    {
        final Address bound1 = new Address(0, 0);
        final Address bound2 = new Address(0, 0xFFFF);
        final Address bound3 = new Address(0xFF, 0);
        final Address bound4 = new Address(0xFF, 0xFFFF);

        assertEquals(bound1.bank, 0, String.format("Address(0, 0) invalid bank: %d", bound1.bank));
        assertEquals(bound2.bank, 0, String.format("Address(0, FFFF) invalid bank: %d", bound2.bank));
        assertEquals(bound3.bank, 0xFF, String.format("Address(FF, 0) invalid bank: %d", bound3.bank));
        assertEquals(bound4.bank, 0xFF, String.format("Address(FF, FFFF) invalid bank: %d", bound4.bank));

        assertEquals(bound1.address, 0, String.format("Address(0, 0) invalid addr: %d", bound1.address));
        assertEquals(bound2.address, 0xFFFF, String.format("Address(0, FFFF) invalid addr: %d", bound2.address));
        assertEquals(bound3.address, 0, String.format("Address(FF, 0) invalid addr: %d", bound3.address));
        assertEquals(bound4.address, 0xFFFF, String.format("Address(FF, FFFF) invalid addr: %d", bound4.address));
    }

    @Test
    void testConstructorWithBankBankLowerBound()
    {
        assertThrows(IllegalArgumentException.class, () -> new Address(-1, 0),
                "Address lower bank bound did not throw exception.");
    }

    @Test
    void testConstructorWithBankBankUpperBound()
    {
        assertThrows(IllegalArgumentException.class, () -> new Address(0xFF + 1, 0),
                "Address upper bank bound did not throw exception.");
    }

    @Test
    void testConstructorWithBankAddrLowerBound()
    {
        assertThrows(IllegalArgumentException.class, () -> new Address(0, -1),
                "Address lower addr bound did not throw exception.");
    }

    @Test
    void testConstructorWithBankAddrUpperBound()
    {
        assertThrows(IllegalArgumentException.class, () -> new Address(0, 0xFFFF + 1),
                "Address upper addr bound did not throw exception.");
    }

    @Test
    void testFromString()
    {
        Address a1 = Address.fromString("ffff");
        Address a2 = Address.fromString("fFFf");
        Address a3 = Address.fromString("ff:ffff");
        Address a4 = Address.fromString("fF:fFFf");

        assertAddressEquals(a1, 0, 0xFFFF);
        assertAddressEquals(a2, 0, 0xFFFF);
        assertAddressEquals(a3, 0xFF, 0xFFFF);
        assertAddressEquals(a4, 0xFF, 0xFFFF);
    }

    @Test
    void testToString()
    {
        Address a1 = new Address(0, 0);
        Address a2 = new Address(0, 0xFFFF);
        Address a3 = new Address(0xFF, 0);
        Address a4 = new Address(0xFF, 0xFFFF);

        assertEquals("00:0000", a1.toString(), "Address toString not matching.");
        assertEquals("00:FFFF", a2.toString(), "Address toString not matching.");
        assertEquals("FF:0000", a3.toString(), "Address toString not matching.");
        assertEquals("FF:FFFF", a4.toString(), "Address toString not matching.");
    }

    @SuppressWarnings("EqualsWithItself")
    @Test
    void testCompareToSameObject()
    {
        Address a1 = new Address(0, 0);
        assertEquals(0, a1.compareTo(a1), "Address compareTo failed for same object.");
    }

    @Test
    void testCompareToSameValue()
    {
        Address a1 = new Address(0, 0);
        Address a2 = new Address(0, 0);
        assertEquals(0, a1.compareTo(a2), "Address compareTo failed for same value.");
    }

    @Test
    void testCompareToSameBankLessAddr()
    {
        Address a1 = new Address(0, 0);
        Address a2 = new Address(0, 1);
        assertEquals(-1, a1.compareTo(a2), "Address compareTo failed for same bank less addr.");
    }

    @Test
    void testCompareToSameBankGreaterAddr()
    {
        Address a1 = new Address(0, 1);
        Address a2 = new Address(0, 0);
        assertEquals(1, a1.compareTo(a2), "Address compareTo failed for same bank greater addr.");
    }

    @Test
    void testCompareToLessBankSameAddr()
    {
        Address a1 = new Address(0, 0);
        Address a2 = new Address(1, 0);
        assertEquals(-1, a1.compareTo(a2), "Address compareTo failed for less bank same addr.");
    }

    @Test
    void testCompareToLessBankLessAddr()
    {
        Address a1 = new Address(0, 0);
        Address a2 = new Address(1, 1);
        assertEquals(-1, a1.compareTo(a2), "Address compareTo failed for less bank less addr.");
    }

    @Test
    void testCompareToLessBankGreaterAddr()
    {
        Address a1 = new Address(0, 1);
        Address a2 = new Address(1, 0);
        assertEquals(-1, a1.compareTo(a2), "Address compareTo failed for less bank greater addr.");
    }

    @Test
    void testCompareToGreaterBankSameAddr()
    {
        Address a1 = new Address(1, 0);
        Address a2 = new Address(0, 0);
        assertEquals(1, a1.compareTo(a2), "Address compareTo failed for greater bank same addr.");
    }

    @Test
    void testCompareToGreaterBankLessAddr()
    {
        Address a1 = new Address(1, 0);
        Address a2 = new Address(0, 1);
        assertEquals(1, a1.compareTo(a2), "Address compareTo failed for greater bank less addr.");
    }

    @Test
    void testCompareToGreaterBankGreaterAddr()
    {
        Address a1 = new Address(1, 1);
        Address a2 = new Address(0, 0);
        assertEquals(1, a1.compareTo(a2), "Address compareTo failed for greater bank greater addr.");
    }

    @Test
    void testHashCode()
    {
        Address a1 = new Address(0, 0);
        Address a2 = new Address(0xFF, 0xFFFF);

        assertEquals(0, a1.hashCode(), "Invalid hashCode for 0x00, 0x0000");
        assertEquals(0xFFFFFF, a2.hashCode(), "Invalid hashCode for 0xFF, 0xFFFF");
    }
}
