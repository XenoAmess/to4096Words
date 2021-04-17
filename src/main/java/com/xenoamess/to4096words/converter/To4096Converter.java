package com.xenoamess.to4096words.converter;

import java.io.File;
import java.io.IOException;
import org.jetbrains.annotations.NotNull;

/**
 * @author XenoAmess
 */
public interface To4096Converter {

    /**
     * convert a file to several 4096 files
     *
     * @param inputFile input file
     * @param outputFolder output folder
     * @throws IOException
     */
    void convert(@NotNull File inputFile, @NotNull File outputFolder) throws IOException;
}
