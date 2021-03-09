package com.xenoamess.to4096words.converter.impl;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Test;

public class FromAndTo4096ConverterImplTest {

    @Test
    public void test1() throws IOException {
        To4096ConverterImpl to4096ConverterImpl = new To4096ConverterImpl();
        to4096ConverterImpl.convert(
                new File("src/test/resources/ACM.png"),
                new File("target")
        );
    }

    @Test
    public void test2() throws IOException {
        From4096ConverterImpl from4096Converter = new From4096ConverterImpl();
        String nowHash = "ACM.png";
        for (int i = 0; i < 1000; i++) {
            try {
                from4096Converter.add(
                        FileUtils.readFileToString(
                                new File("target/" + nowHash + "_" + i + ".x8l"),
                                StandardCharsets.UTF_8
                        )
                );
            } catch (Exception ignored) {
//                ignored.printStackTrace();
            }
        }
        from4096Converter.convert(nowHash);
    }
}
