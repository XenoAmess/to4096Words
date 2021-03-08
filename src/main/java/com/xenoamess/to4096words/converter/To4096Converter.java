package com.xenoamess.to4096words.converter;

import java.io.File;
import java.io.InputStream;
import org.jetbrains.annotations.NotNull;

public interface To4096Converter {
    void convert(@NotNull InputStream inputStream,@NotNull File outputFolder);
}
