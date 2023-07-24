package com.hippout.gameboyaddresshelper.io;

import com.hippout.gameboyaddresshelper.util.*;

import javax.annotation.*;
import java.io.*;
import java.nio.file.*;
import java.util.*;

/**
 * For loading resources externally.
 *
 * @author Wyatt James
 */
public final class LocalFileResourceLoader implements ResourceLoader {
    private static final OpenOption[] OPEN_OPTIONS = {StandardOpenOption.READ};

    public final String basePath;

    private final Path rootPath;

    private boolean isClosed;

    public LocalFileResourceLoader(@Nonnull String basePath) throws IOException
    {
        this.basePath = Objects.requireNonNull(basePath, "Base path cannot be null.");

        rootPath = Paths.get(basePath);
        isClosed = false;

        if (!Files.exists(rootPath))
            throw new FileNotFoundException(String.format("Base path could not be found: %s", basePath));

        if (!Files.isDirectory(rootPath))
            throw new IllegalArgumentException(String.format("Base path is not a directory: %s", basePath));
    }

    @Override
    public InputStream loadResource(@Nonnull String path, @Nonnull String fileName) throws IOException
    {
        return loadResource(combinePath(path, fileName));
    }

    @Override
    public InputStream loadResource(@Nonnull String filePathStr) throws IOException
    {
        if (isClosed)
            throw new IllegalStateException("Cannot use a closed LocalFileResourceLoader.");

        final Path filePath = rootPath.resolve(filePathStr);

        if (!Files.exists(filePath))
            throw new FileNotFoundException(String.format("File path could not be found: %s", filePath.toAbsolutePath()));

        if (!Files.isRegularFile(filePath))
            throw new IOException(String.format("File is not a regular readable file: %s", filePath.toAbsolutePath()));

        return Files.newInputStream(filePath, OPEN_OPTIONS);
    }

    @Override
    public String combinePath(@Nonnull String... parts)
    {
        return StringUtil.concatArrayToString(File.separator, (Object[]) parts);
    }

    @Override
    public void close()
    {
        if (isClosed)
            throw new IllegalStateException("Cannot close already-closed LocalFileResourceLoader.");

        isClosed = true;
    }
}
