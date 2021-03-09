package com.xenoamess.to4096words.converter;

import java.io.File;
import java.io.IOException;
import org.jetbrains.annotations.NotNull;

public interface To4096Converter {
    void convert(@NotNull File inputFile, @NotNull File outputFolder) throws IOException;
}
