package com.hippout.gameboyaddresshelper.util;

import javax.annotation.*;
import java.util.*;

public final class MiscUtil {

    @Nonnull
    public static <T> T defaultIfNull(T val, T defaultVal)
    {
        Objects.requireNonNull(defaultVal, "Default value cannot be null.");
        return val == null ? defaultVal : val;
    }
}
