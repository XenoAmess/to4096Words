package com.xenoamess.to4096words.converter;

import java.io.File;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @author XenoAmess
 */
public interface From4096Converter {

    /**
     * convert back to original file, using 4096 Strings already in pool.
     *
     * @param outputFolder output folder
     * @param hash file hash
     */
    void convert(@Nullable File outputFolder, @NotNull String hash);

    /**
     * convert back to original file, using 4096 Strings already in pool.
     *
     * @param outputFolder output folder
     * @param hash file hash
     */
    void convert(@Nullable String outputFolder, @NotNull String hash);

    /**
     * convert back to original file, using 4096 Strings already in pool.
     *
     * @param hash file hash
     */
    void convert(@NotNull String hash);

    /**
     * add a 4096 String into pool.
     *
     * @param inputString a 4096 String
     */
    void add(@NotNull String inputString);
}
