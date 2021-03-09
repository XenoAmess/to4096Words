package com.xenoamess.to4096words.converter;

import org.jetbrains.annotations.NotNull;

public interface From4096Converter {
    void convert(@NotNull String hash);

    void add(@NotNull String inputString);
}
