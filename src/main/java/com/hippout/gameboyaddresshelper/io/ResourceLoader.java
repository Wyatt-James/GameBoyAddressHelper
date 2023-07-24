package com.hippout.gameboyaddresshelper.io;

import javax.annotation.*;
import java.io.*;
import java.nio.charset.*;
import java.util.stream.*;

/**
 * ResourceLoader interface
 * <p/>
 * Represents a persistent loader for file resources.
 *
 * @author Wyatt James
 */
@SuppressWarnings("unused")
public interface ResourceLoader {

    default String loadText(@Nonnull String path, @Nonnull String fileName) throws IOException
    {
        return new BufferedReader(new InputStreamReader(loadResource(path, fileName), StandardCharsets.UTF_8)).lines().collect(Collectors.joining("\n"));
    }

    default String loadText(@Nonnull String filePath) throws IOException
    {
        return new BufferedReader(new InputStreamReader(loadResource(filePath), StandardCharsets.UTF_8)).lines().collect(Collectors.joining("\n"));
    }

    /**
     * Loads a File at the designated path.
     *
     * @param path     Path of the file.
     * @param fileName Name of the file, including the extension.
     * @return a File object pointing to the requested file.
     * @throws FileNotFoundException if the file could not be found.
     */
    InputStream loadResource(@Nonnull String path, @Nonnull String fileName) throws IOException;

    /**
     * Loads a File at the designated path.
     *
     * @param filePath Path of the file, including the name and extension.
     * @return a File object pointing to the requested file.
     * @throws FileNotFoundException if the file could not be found.
     */
    InputStream loadResource(@Nonnull String filePath) throws IOException;

    /**
     * Combines the two path elements with the correct separator for this ResourceLoader.
     *
     * @param parts path parts to combine in order.
     * @return the combined path String.
     */
    String combinePath(@Nonnull String... parts);

    /**
     * Closes this ResourceLoader, if applicable.
     *
     * @throws IOException           if this ResourceLoader fails to close.
     * @throws IllegalStateException if this ResourceLoader was already closed.
     */
    void close() throws IOException;
}
