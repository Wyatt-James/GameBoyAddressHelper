package com.hippout.gameboyaddresshelper;

import com.hippout.gameboyaddresshelper.program.programs.*;

import java.util.*;

public class GameBoyAddressHelper {
    public static void main(String[] args)
    {
        System.out.println("""
                Game Boy Address Helper
                By: Wyatt-James
                URL: https://github.com/Wyatt-James/GameBoyAddressHelper""");

        final Scanner sysInScanner = new Scanner(System.in);
        new CommandLineAddressProgram(sysInScanner, System.out).run();
        sysInScanner.close();

        System.out.println("Thank you for using GB Address Dedupe.");
    }
}
